package com.abc.onlinebanking.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.abc.onlinebanking.Exception.BankTransactionException;
import com.abc.onlinebanking.domain.AccountDetails;
import com.abc.onlinebanking.repository.AccountRepository;
@Service
public class AccountService
{
    @Autowired
    AccountRepository accountRepository;

    //getting all records
    public List<AccountDetails> getAllAccounts()
    {
        List<AccountDetails> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accounts::add);
        return accounts;
    }

    //getting a specific record
    public AccountDetails getAccountById(String id)
    {
        return accountRepository.findById(id).get();
    }

    //saving data
    public void saveOrUpdate(AccountDetails account)
    {
    	AccountDetails accountDetails = new AccountDetails();
    	accountDetails.setAccountBalance(account.getAccountBalance());
    	accountDetails.setAccountNumber(account.getAccountNumber());
    	accountDetails.setCustomer(account.getCustomer());
    	accountDetails.setDateCreated(account.getDateCreated());
    	accountDetails.setTransaction(account.getTransaction());
        accountRepository.save(accountDetails);
    }

    //deleting a specific record
    public void delete(String id)
    {
        accountRepository.deleteById(id);
    }
    
    // MANDATORY: Transaction must be created before.
    @Transactional(propagation = Propagation.REQUIRED)
    public void addAmount(String id, float amount) throws BankTransactionException {
        AccountDetails account = this.getAccountById(id);
        if (account == null) {
            throw new BankTransactionException("Account not found " + id);
        }
        double  newBalance = account.getAccountBalance() + amount;
        if (account.getAccountBalance() + amount < 0) {
            throw new BankTransactionException(
                    "The money in the account '" + id + "' is not enough (" + account.getAccountBalance() + ")");
        }
        account.setAccountBalance((float) newBalance);
        saveOrUpdate(account);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BankTransactionException.class)
    public void transferMoney(String fromAccountId, String toAccountId, float amount) throws BankTransactionException {
        addAmount(toAccountId, amount);
        addAmount(fromAccountId, -amount);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BankTransactionException.class)
    public void depositMoney(String fromAccountId, float amount) throws BankTransactionException {
        addAmount(fromAccountId, amount);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BankTransactionException.class)
    public void withdrawMoney(String fromAccountId, float amount) throws BankTransactionException {
        addAmount(fromAccountId, -amount);
    }
    
}