package com.abc.onlinebanking.domain;

public class BankAccountTransfer {
	
	private String fromAccount;
	private String toAccount;
	private float accountBalance;
	
	public BankAccountTransfer(String fromAccount, String toAccount, float accountBalance) {
		super();
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.accountBalance = accountBalance;
	}
	public String getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}
	public String getToAccount() {
		return toAccount;
	}
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	public float getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(float accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	
	
		
}
