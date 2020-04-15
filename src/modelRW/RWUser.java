package modelRW;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import model.Administrator;
import model.Checker;
import model.User;
import model.normalUser;

public class RWUser {
	 private ArrayList<User> users;
	 private File file;
	 public RWUser() {
	    	users = new ArrayList<User>();
	    	file = new File("users.bin");
	    	if(file.exists()) {
	    		readUsers();
	    	}else {
	    		createFile();
	    	}
	    }

	public void writeUsers() {
			try {
				FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(users);
				oos.close(); fos.close();	
			} catch (FileNotFoundException e) {
				System.out.println("File not accessible...");
			} catch (IOException e) {
				System.out.println("File cannot be written");
			}	
			
		}

	
	private void readUsers() {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			users=(ArrayList<User>)ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not accessible");
		} catch (IOException e) {
			System.err.println("File corrupted...");
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found...");
		}
	}



	private void createFile() {
		System.out.println("First Time Usage!!");
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter administrator data:");
		System.out.println("Please enter admin userID: ");
		boolean c = true;
		String userID="";
		while(c) {
			userID = in.next();
		    if(userID.matches("\\d{8}")) 
		    	c = false;
		    else {
		    	System.out.println("Enter a valid id, must contain 8 digits.");
		    
		    }
		}
		System.out.println("Please enter PIN: ");
		String pin=in.next();
		System.out.println("Please enter Name: ");
		String name=in.next();
		System.out.println("Please enter Surname: ");
		String surname=in.next();
		System.out.println("Please enter Bank: ");
		String bank=in.next();
		users.add(new Administrator(userID, pin, name, surname, bank));
		writeUsers();
		
	}



	public User checkLogIn(String userId, String pin) {
		for(User i: users)
			if(((Checker)i).checkUser(userId,pin))
				return i;
        return null;
	}
	
	public void add(User x) {
		users.add(x);
		writeUsers();
	}
	public void editUser(String id) {
		for(User i: users) {
			if((i.getUserID()).equals(id)) {
				System.out.println("1. Change Name");
				System.out.println("2. Change Surname");
				System.out.println("3. Change PIN");
				Scanner in = new Scanner(System.in);
				switch(in.nextInt()) {
				case 1: System.out.println("Enter new name: ");
				        i.setName(in.next());
				        writeUsers();
				        break;
				case 2: System.out.println("Enter new surname: ");
		                i.setSurname(in.next());
		                writeUsers();
		                break;
				case 3: System.out.println("Enter new pin: ");
				        i.setPin(in.next()); writeUsers();
				        break ; 
				        default: System.out.println("Wrong action!");
				      
				}
			}
				
		}
	}
	public void delete(User x) {
		users.remove(x);
		writeUsers();
	}

	
	public ArrayList<User> getUsers(){
		return users;
	}
	
	public normalUser getUserbyID(String id) {
		for(User i: users) {
			if((i.getUserID()).equals(id))
				return (normalUser)i;
		}
		return null;
	}
	
	public normalUser searchUserbyName(String name) {
		for(User i: users) {
			if((i.getName()).equals(name))
				return (normalUser)i;
		}
		return null;
	}
	
	public normalUser searchUserbySurname(String surname) {
		for(User i: users) {
			if((i.getSurname()).equals(surname))
				return (normalUser)i;
		}
		return null;
	}

	public void writeTransactions(normalUser us, double previousBalance, double amount, String string) {
		FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;
		try {
            fw = new FileWriter("reports.txt", true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            Date dateOfTransaction = new Date();
            pw.println(us.getUserID());
            pw.println(string+" amount: "+amount+" Previous Balance: "+previousBalance+" Actual balance: "+us.getBalance()+" Date: "+ dateOfTransaction  );
            

            System.out.println("Data Successfully appended into file");
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
		}catch (IOException io) {
			System.out.println("IO exception");
        }	
	}

	public void viewTransactionHistory(normalUser us) {
		try {
			Scanner in = new Scanner(new File("reports.txt"));
			while(in.hasNextLine()) {
				if((in.nextLine()).equals(us.getUserID()))
					System.out.println(in.nextLine());				
			}
			
		}catch(FileNotFoundException e) {
			System.out.println("File cannot be read!");
		}
		
	}
}
