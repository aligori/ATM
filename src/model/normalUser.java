package model;

public class normalUser extends User implements Checker{
	double balance;
    public normalUser(double balance,String userID, String pin, String name, String surname) {
		super(userID, pin, name, surname);
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public boolean checkUser(String id, String pin) {
		return id.equals(this.getUserID())&& pin.equals(this.getPin());
	}
    
	 public void withdraw(double amount) {
	     this.balance -= amount;
	 }

	 public void deposit(double amount) {
	     this.balance += amount;
	 }

	@Override
	public String toString() {
		return "normalUser [balance=" + balance + "]";
	}
	 
}
