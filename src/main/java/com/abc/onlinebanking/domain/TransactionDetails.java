package com.abc.onlinebanking.domain;
import java.time.*;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TRANSACTION")

public class TransactionDetails {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String transactionId;
	
	@Column(name = "AMOUNT")
	private float transactionAmount;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "DATE_OF_TRANSACTION")
	private LocalDate transactionDate;
	
	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;
	
	@Column(name = "TO_ACCOUNT")
	private String transactionToAccount; // transferred to which account
	
	@JsonIgnore
	@ManyToOne
	private AccountDetails account;
	public AccountDetails getAccount() { return account; }
	public void AccountDetails(AccountDetails account) { this.account = account; }
	
	//constructor
	public TransactionDetails(){}
	public TransactionDetails(String transactionId, float transactionAmount, LocalDate transactionDate,
			String transactionType, String transactionToAccount, AccountDetails account) {
		super();
		this.transactionId = transactionId;
		this.transactionAmount = transactionAmount;
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
		this.transactionToAccount = transactionToAccount;
		this.account = account;
	}
	
	//getter and setter methods
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public float getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getTransactionToAccount() {
		return transactionToAccount;
	}
	public void setTransactionToAccount(String transactionToAccount) {
		this.transactionToAccount = transactionToAccount;
	}
}
