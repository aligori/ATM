package defaultpackage;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.Administrator;
import model.User;
import model.normalUser;
import modelRW.RWUser;

//Admin id: 11111111 pin: epoka123

public class ATM {
	
	 public static void main(String[] args) {
		 RWUser rwu = new RWUser();
		 boolean c = true;
		 while(c) {
			displayATM();
			System.out.println("Enter a choice: ");
			Scanner in = new Scanner(System.in);
			switch(in.nextInt()) {
			case 1: enter(rwu); break;
			case 2: createAccount(rwu); break; 
			case 3: c=false;break;
			default: System.out.println("Choose the right action!");
			}
		 }
	}
    
     private static void createAccount(RWUser rwu) {
    	Scanner in = new Scanner(System.in);
		System.out.println("CREATE USER ACCOUNT");
		System.out.println("Please enter account ID");
		boolean c = true;
		String id="";
		while(c) {
			id = in.next();
		    if(id.matches("\\d{8}")) { 
		    	if(rwu.getUserbyID(id)!=null) 
		    		System.err.println("ID is currently in use! Choose another one.");
		    	else
		    		c=false;
		    }
		    else System.err.println("Enter a valid id, must contain 8 digits.");
		    
		   
		}
		System.out.println("Please enter PIN: ");
		String pin=in.next();
		System.out.println("Please enter Name: ");
		String name=in.next();
		System.out.println("Please enter Surname: ");
		String surname=in.next();
		System.out.println("Please enter Balance: ");
		double balance = 0;
		boolean t = true;
		while(t) {
		try{
			balance=in.nextDouble();
			t=false;
		}catch(InputMismatchException ex) {
			System.err.println("Invalid balance! Enter again..");
			in.next();
		}
		}
		rwu.add(new normalUser(balance, id, pin, name, surname));
		
		
	}

	public static void displayATM() {
    	 System.out.println("ATM");
    	 System.out.println("---------------------");
    	 System.out.println("1. Enter");
    	 System.out.println("2. Create Account");
    	 System.out.println("3. Exit");
    	 System.out.println("---------------------");
     }
     
     public static void enter(RWUser rwu) {
    	 Scanner in = new Scanner(System.in);
    	 while(true) {
    		 
    		System.out.println("Enter Account ID");
	        String userId = in.next();
	        System.out.println("Enter pin");
	        String pin = in.next();
	        
	        User us = rwu.checkLogIn(userId, pin);
	        
	        if(us!=null) {
	        	if(us instanceof Administrator) {
	        		veprimeAdm(rwu,(Administrator)us);
	        	}else {
	        		veprimeUser(rwu,(normalUser)us);
	        	}
	        }else System.out.println("The credentials you entered are wrong!!");
	     }
     }

	private static void veprimeUser(RWUser rwu, normalUser us) {
		boolean c = true;
		while(c) {
			System.out.println("ATM - "+us.getName()+" "+us.getSurname());
			System.out.println("------------------");
			System.out.println( "1. Edit Account\n"
			                   +"2. Withdraw\n"
			                   +"3. Deposit\n"
			                   +"4. Transfer\n"
			                   +"5. View Transaction History\n"
			                   +"6. Exit");
			System.out.println("------------------");
			System.out.println("Please choose the action to do");
			Scanner in = new Scanner(System.in);
			double amount;
			double previousBalance;
			switch(in.nextInt()) {
			case 1: rwu.editUser(us.getUserID()); break;
			case 2: System.out.println("Enter amount to withdraw: ");
			        amount = in.nextDouble();
			        previousBalance = us.getBalance();
			        us.withdraw(amount);
			        rwu.writeTransactions(us,previousBalance,amount,"Withdraw");
			        rwu.writeUsers(); //so the changes aren't lost when we close the program
			        break; 
			case 3: System.out.println("Enter amount to deposit: ");
	                amount = in.nextDouble();
	                previousBalance = us.getBalance();
	                us.deposit(amount);
	                rwu.writeTransactions(us,previousBalance,amount,"Deposit");
	                rwu.writeUsers(); break;                
			case 4: System.out.println("Enter amount to transfer: ");
	                amount = in.nextDouble();
	                previousBalance = us.getBalance();
	                us.withdraw(amount);
	                System.out.println("Enter accountID to whom you want to transfer");
	                String id = in.next();
	                normalUser target = rwu.getUserbyID(id);
	                if(target!=null) {
	                   double targetBalance = target.getBalance();
	                   target.deposit(amount);
	                   rwu.writeTransactions(target,targetBalance,amount,"Transfer+"); 
	                   rwu.writeTransactions(us,previousBalance,amount,"Transfer-");
	                   rwu.writeUsers(); 
	                }else 
	                	System.err.println("No such account exists.");
	                
	                break;
			case 5: rwu.viewTransactionHistory(us);
			        break;
			case 6: c=false;break;
				default: System.out.println("Enter the right action!!");
			}
		}
		
	}

	private static void veprimeAdm(RWUser rwu, Administrator us) {
		
		boolean c = true;
		while(c) {
			System.out.println("ATM - "+us.getName()+" "+us.getSurname());
			System.out.println("------------------");
			System.out.println("1. Add Account");
			System.out.println("2. Edit Account");
			System.out.println("3. Search user");
			System.out.println("4. Remove user");
			System.out.println("5. List of users");
			System.out.println("6. Exit");
			System.out.println("------------------");
			System.out.println("Please choose the action to do");
			Scanner in = new Scanner(System.in);
			switch(in.nextInt()) {
			case 1: createAccount(rwu); break;
			case 2: System.out.println("Enter accountID to edit: "); 
			        rwu.editUser(in.next());  break;
			case 3: searchUsers(rwu);
			        break;			        
			case 4: System.out.println("Remove user by entering account ID:");
		            rwu.delete(rwu.getUserbyID(in.next())); break;
			case 5: System.out.println("<<<List of users>>>");
			        ListOfUsers((rwu.getUsers())); break;
			case 6: c=false;break;
				default: System.out.println("Enter the right action!!");
			}
		}
		
	}
	
	private static void searchUsers(RWUser rwu) {
		boolean t = true;
		Scanner in = new Scanner(System.in);
        while(t) {
	        System.out.println("------------------");
	        System.out.println("-Search by:");
	        System.out.println("1. Name ");
	        System.out.println("2. Surname ");
	        System.out.println("3. Account Number ");
	        System.out.println("4. Back "); 
	        
	        normalUser user;
	        try {
        	switch(in.nextInt()) {
        	case 1: System.out.println("Enter Name to search user: ");
        	        user = rwu.searchUserbyName(in.next());
        	           if(user!=null) {
        	        	     System.out.println(user.getUserID()+" - "+user.getName()
        	        	                        +" - "+user.getSurname()+" -> "+user);
        
        	        	     System.out.println("1. View Transaction history");
        	        	     System.out.println("2. Back");
        	        	     boolean c =true;
        	        	     while(c) {
        	        	        switch(in.nextInt()) {
        	        	            case 1: rwu.viewTransactionHistory(user); break;
        	        	            case 2: c=false; break;
        	        	            default: System.out.println("Choose right action to perform.");
        	        	       }
        	        	     }
        	           }
        	           else
        	            	System.err.println("User with this name does not exist!!!");
        	        break;
        	case 2: System.out.println("Enter Surname to search user: ");
	                    user = rwu.searchUserbySurname(in.next());
	                    if(user!=null) 
	        	             System.out.println(user.getUserID()+" - "+user.getName()
	        	                                +" - "+user.getSurname()+" -> "+user);
	                    else
	        	             System.err.println("User with this surname does not exist!!!");
	                break;
        	case 3: System.out.println("Enter Account ID to search user: ");
	                user = rwu.getUserbyID(in.next());
	                    if(user!=null) 
	        	             System.out.println(user.getUserID()+" - "+user.getName()
	        	                                +" - "+user.getSurname()+" -> "+user);
	                    else
	        	             System.err.println("User does not exist!!!");
	                    break;
        	case 4:  t= false; break;
        	       default: System.out.println("Enter right action to perform");
        	}
	        }catch(InputMismatchException ex) {
	        	System.out.println("Please enter correct action. It should be a number: ");
	        	in.next();
	        }
        }
		
	}

	private static void ListOfUsers(ArrayList<User> users) {
		for(User i: users) {
			if(i instanceof normalUser) {
				System.out.println(i.getUserID()+" - "+i.getName()+" - "+i.getSurname()+" -> "+(normalUser)i);
			}
		}
		
	}
}
