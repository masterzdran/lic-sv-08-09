package Lic0809SV;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class MaintenanceMode {
	private final int KEY_LOCK_MASK = 0x10;
	private final String configFile="user.netrc";
	private AccessDb db;
	private String adminPassword;
	private boolean au=false;
	private Kit ourKit;
	
	public MaintenanceMode(AccessDb ourDB){
		db=ourDB;
		ourKit= new  Kit();
		getPassword();
	}
	private void setPassword(String pw){
		adminPassword=pw;
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter(new File(configFile)));
			bw.write(adminPassword+";");
			bw.close();
		} catch (IOException e) {
			System.out.println(configFile+" file with I/O problems.");
		}
	}
	
	private void getPassword(){
		try {
			BufferedReader bR=new BufferedReader(new FileReader(new File(configFile)));
			String line;
			String pw;
			if ((line=bR.readLine()) != null){
				Scanner lineField=new Scanner(line).useDelimiter(";");
				pw=lineField.next();
				adminPassword=pw;
			}
			bR.close();
		} catch (FileNotFoundException e) {
			System.out.println(configFile+" not found.");
		} catch (IOException e) {
			System.out.println(configFile+" file with I/O problems.");
		}
		
	}
	
	private void changePwd(){
		clearScreen();

		System.out.println("Maintenace Mode");
		System.out.println("Main Menu");
		System.out.println("Change Password");
		
		System.out.println("Insert New Password: ");
		char[] newPwd=System.console().readPassword();
		System.out.println("Confirm New Password: ");
		char[] confirmNewPwd=System.console().readPassword();
		if (verify(newPwd, confirmNewPwd)){
			String pw="";
			for (int i=0;i<newPwd.length;i++){
				pw+=newPwd[i];
			}
			setPassword(pw);
			System.out.println("Password changed successfully");
			return;
		}
		System.out.println("Password missmatch!");
	}
	public  boolean isLocked() {
		 return ourKit.readBit(KEY_LOCK_MASK);
	}
	
	private boolean verify (char[] pw){
		if (adminPassword.length() != pw.length) return false;
		for (int i=0;i<adminPassword.length();i++){
			if (pw[i] != adminPassword.charAt(i)) return false;
		}
		return true;
	}
	private boolean verify (char[] pw1,char[] pw2){
		if (pw1.length != pw2.length) return false;
		for (int i=0;i<pw1.length;i++){
			if (pw1[i] != pw2[i]) return false;
		}
		return true;
	}
	private boolean auth(){
		if(!au){
			clearScreen();
			System.out.println("Enter password:");
			char[] p=System.console().readPassword();
			System.out.println(p);
			if (verify(p)){
				return 	au=true;
			}else{
				return au=false;
			}
		}
		return true;
	}
	
	private  void maintenaceMainMenu(){

		clearScreen();
		System.out.println("Maintenace Mode");
		System.out.println("Main Menu:");
		System.out.println("[1] - Add New User");
		System.out.println("[2] - Remove User");
		System.out.println("[3] - Associate Message to User");
		System.out.println("[4] - Search User");
		System.out.println("[5] - List Users");
		System.out.println("[6] - Exit");
		System.out.println("Enter your option (1-6):");

		Scanner option=new Scanner(System.in);
		
		try{
		switch(option.nextInt()){
			case 1:
				addUserMenu();
				break;
			case 2:
				removeUserMenu();
				break;
			case 3:
				addMessageUser();
				break;
			case 4:
				searchUserMenu();
				break;
			case 5:
				listUsersMenu();
				break;
			case 6:
				mainMenu();
				break;
			default:
				mainMenu();
				break;
		}
		}catch (Exception e){
			maintenaceMainMenu();
		}

	}
	
	public  void addUserMenu(){
		clearScreen();
		Scanner newUser=new Scanner(System.in);
		
		System.out.println("Maintenace Mode");
		System.out.println("Main Menu");
		System.out.println("[1] - Add New User");
		
		System.out.println("User Id: ");
		int userId=newUser.nextInt();
		
		System.out.println("User Name: ");
		String userName= newUser.nextLine();
		userName= newUser.nextLine();
		
		System.out.println("User Pin: ");
		int userPin=newUser.nextInt();
		
		System.out.println("Associate User Message:");
		String userMsg=newUser.nextLine();
		userMsg=newUser.nextLine();
		
		System.out.println("\n");
		System.out.println("User Id: "+userId+"\nUser Name: "+userName+"\nUser Pin: "+userPin+"\nUser Message: "+userMsg);
		System.out.println("Confirm data? (Yes,No)");
		
		String option=newUser.next();
		option=option.toLowerCase();
		if (option.equals("yes")|| option.equals("y")){
			User user=new User(userName,userId,userPin,userMsg);
			if(db.addUser(user))
				System.out.println("User added successfully!");
			else
				System.out.println("UserId already exists or DataBase is full!");
			
		}else if (option.equals("no") ||option.equals("n")){
			System.out.println("Insertion not Confirmed!");
		}else{
			System.out.println("Try Again!");
			addUserMenu();
		}
		
		System.out.println("\n");
		System.out.println("Add Another User? (Yes,No)");
		option=newUser.next();
		option=option.toLowerCase();
		if (option.equals("yes")|| option.equals("y")){
			addUserMenu();
		}else if (option.equals("no")|| option.equals("n")){
			maintenaceMainMenu();
		}else{
			System.out.println("Try Again!");
			addUserMenu();
		}
	}

	public  void removeUserMenu(){
		clearScreen();
		Scanner removeUser=new Scanner(System.in);
		System.out.println("Maintenace Mode");
		System.out.println("Main Menu");
		System.out.println("[2] - Remove User");
		System.out.println("User Id: ");
		
		int userId=removeUser.nextInt();
		System.out.println(db.verifyUser(userId).toString());
		
		System.out.println("Confirm data? (Yes,No)");
		String option=removeUser.next();
		option=option.toLowerCase();
		if (option.equals("yes")|| option.equals("y")){
			
			if (db.removeUser(db.verifyUser(userId))){
				System.out.println("User removed!");
			}else{
				System.out.println("Error Ocorred!");
			}
			
		}else if (option.equals("no")|| option.equals("n")){
			System.out.println("Remove Operation cancelled!");
		}else{
			System.out.println("Try Again!");
			removeUserMenu();
		}
		
		System.out.println("\n");
		System.out.println("Remove Another User? (Yes,No)");
		option=removeUser.next();
		option=option.toLowerCase();
		if (option.equals("yes")|| option.equals("y")){
			removeUserMenu();
		}else if (option.equals("no")|| option.equals("n")){
			maintenaceMainMenu();
		}else{
			System.out.println("Try Again!");
			removeUserMenu();
		}
	}
	public  void addMessageUser(){
		clearScreen();
		Scanner addMsgUser=new Scanner(System.in);
		System.out.println("Maintenace Mode");
		System.out.println("Main Menu");
		System.out.println("[3] - Associate Message to User");

		System.out.println("User Id: ");
		int userId=addMsgUser.nextInt();

		System.out.println(db.verifyUser(userId).toString());

		System.out.println("Associate User Message:");
		String userMsg=addMsgUser.nextLine();
		userMsg=addMsgUser.nextLine();
		
		System.out.println("Confirm data? (Yes,No)");
		String option=addMsgUser.next();
		option=option.toLowerCase();
		if (option.equals("yes")|| option.equals("y")){
			db.verifyUser(userId).setUserMessage(userMsg);
			System.out.println("Message added!");
		}else if (option.equals("no")|| option.equals("n")){
			System.out.println("Operation cancelled!");
		}else{
			System.out.println("Try Again!");
			addMessageUser();
		}
		
		System.out.println("\n");
		System.out.println("Add Message to Another User? (Yes,No)");
		option=addMsgUser.next();
		option=option.toLowerCase();
		if (option.equals("yes")|| option.equals("y")){
			addMessageUser();
		}else if (option.equals("no")|| option.equals("n")){
			maintenaceMainMenu();
		}else{
			System.out.println("Try Again!");
			addMessageUser();
		}

	}
	public  void searchUserMenu(){
		clearScreen();
		Scanner searchUser=new Scanner(System.in);
		System.out.println("Maintenace Mode");
		System.out.println("Main Menu");
		System.out.println("[4] - Search User");
		System.out.println("User Id: ");
		int userId=searchUser.nextInt();
		User u;
		if ((u=db.verifyUser(userId))!=null)
			System.out.println(u.toString());
		else
			System.out.println("User not Found!");
		

		System.out.println("Search Another User? (Yes,No)");
		String option=searchUser.next();		
		System.out.println("\n");
		option=option.toLowerCase();
		if (option.equals("yes")|| option.equals("y")){
			searchUserMenu();
		}else if (option.equals("no")|| option.equals("n")){
			maintenaceMainMenu();
		}else{
			System.out.println("Try Again!");
			searchUserMenu();
		}
		
	}
	public  void listUsersMenu(){
		clearScreen();
		System.out.println("Maintenace Mode");
		System.out.println("Main Menu");
		System.out.println("[5] - List Users");
		db.list();
		System.out.println("Enter 'C' to continue...");
		Scanner listUser=new Scanner(System.in);
		listUser.next();
		maintenaceMainMenu();
	}
	
	public  void clearScreen(){
		for(int i=0;i<50;i++){
			System.out.println("");
		}
	}
	public  void exit(){
		clearScreen();
		System.out.println("Exit!");

	}
	public  void mainMenu(){
		clearScreen();
		if (!au){
			if (!auth())mainMenu() ;
		}
		Scanner newOption=new Scanner(System.in);
		System.out.println("Main Menu");
		System.out.println("[1] - Change Password");
		System.out.println("[2] - Maintenace Mode");
		System.out.println("[3] - Exit");
		System.out.println("Option: ");
		int option=newOption.nextInt();
 
		switch (option){
			case 1:
				changePwd();
				break;
			case 2:
				maintenaceMainMenu();
				break;
			case 3:
				exit();
				break;
			default:
				mainMenu();
				break;
		}
		
	}
	
	public static void main(String[] args) {
		AccessDb a=new AccessDb();
		MaintenanceMode m=new MaintenanceMode(a);
		m.mainMenu();
	}

}
