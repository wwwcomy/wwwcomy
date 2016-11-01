package com.iteye.wwwcomy.concurrency.lock;

/**
 * Simulate from {@link http://cuisuqiang.iteye.com/blog/1458754}, write my own
 * test case.
 * 
 * @author wwwcomy
 *
 */
public class BankCard {

	private String cardId = "1001";
	private int balance = 10000;

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
}
