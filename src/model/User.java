package model;

import java.io.Serializable;

public abstract class User implements Serializable{
    public String userID;
    public String pin;
    public String name;
    public String surname;
	public User(String userID, String pin,String name, String surname) {
		this.userID = userID;
		this.pin = pin;
		this.name = name;
		this.surname = surname;
	}
	public String getUserID() {
		return userID;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
}
