package model;

public class Administrator extends User implements Checker{
    String bank;
	public Administrator(String userID, String pin, String name, String surname, String bank) {
		super(userID, pin,name,surname);
		this.bank=bank;
	}
	
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Override
	public String toString() {
		return "Administrator [name=" + getName() +", surname="+getSurname()+ ", bank=" + bank + "]";
	}

	@Override
	public boolean checkUser(String id, String  pin) {
	    return id.equals(this.getUserID())&& pin.equals(this.getPin());
	}

}
