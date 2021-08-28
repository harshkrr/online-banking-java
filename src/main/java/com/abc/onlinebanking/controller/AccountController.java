package com.abc.onlinebanking.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abc.onlinebanking.Exception.BankTransactionException;
import com.abc.onlinebanking.domain.AccountDetails;
import com.abc.onlinebanking.domain.TransactionDetails;
import com.abc.onlinebanking.service.AccountService;
import com.abc.onlinebanking.service.TransactionService;

//creating RestController
@RestController
public class AccountController
{
    //autowired the AccountService class
    @Autowired
    AccountService accountService;
    TransactionService transactionService;

    //creating a get mapping that retrieves all the acc details from the database
    @GetMapping("/account")
    private List<AccountDetails> getAllAccounts()
    {
        return accountService.getAllAccounts();
    }

    //creating a get mapping that retrieves the detail of a specific acc
    @GetMapping("/account/{id}")
    private AccountDetails getAccount(@PathVariable("id") String id)
    {
        return accountService.getAccountById(id);
    }

    //creating a delete mapping that deletes a specific acc
    @DeleteMapping("/account/{id}")
    private String deleteAccount(@PathVariable("id") String id)
    {
        accountService.delete(id);
        return id;
    }

    //creating post mapping that post the acc detail in the database
    @PostMapping("/account")
    private String saveAccount(@RequestBody AccountDetails acc)
    {
        accountService.saveOrUpdate(acc);
        return acc.getAccountNumber();
    }
    
    @RequestMapping(value = "/moneyTranscation", method = RequestMethod.POST)
    public String MoneyTrancation(Model model, TransactionDetails money) throws BankTransactionException  {
        String transcationType = money.getTransactionType();
		if(transcationType.equals("Transfer")){
			 try {
		        	accountService.transferMoney(money.getTransactionId(), //
		                    money.getTransactionToAccount(), //
		                    money.getTransactionAmount());
		        	AccountDetails account  = accountService.getAccountById(money.getTransactionId());
		        	account.addTransaction(money);
		        	accountService.saveOrUpdate(account);
		        	
		        } catch (BankTransactionException e) {
		            model.addAttribute("errorMessage", "Error: " + e.getMessage());
		            return "/sendMoneyPage";
		        }
		}else if(transcationType.equals("Deposit")){
			try {
		    	accountService.depositMoney(money.getTransactionId(), //
		    			money.getTransactionAmount());
		    	AccountDetails account  = accountService.getAccountById(money.getTransactionId());
	        	account.addTransaction(money);
	        	accountService.saveOrUpdate(account);
		    } catch (BankTransactionException e) {
		        model.addAttribute("errorMessage", "Error: " + e.getMessage());
		        return "/depositPage";
		    }
		}else if(transcationType.equals("Withdraw")) {
			try {
		    	accountService.withdrawMoney(money.getTransactionId(), //
		                money.getTransactionAmount());
		    	AccountDetails account  = accountService.getAccountById(money.getTransactionId());
	        	account.addTransaction(money);
	        	accountService.saveOrUpdate(account);
		    } catch (BankTransactionException e) {
		        model.addAttribute("errorMessage", "Error: " + e.getMessage());
		        return "/withdrawMoneyPage";
		    }
		}
        return "redirect:/";
    }
}