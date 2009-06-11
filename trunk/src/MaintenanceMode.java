import java.util.Scanner;


public class MaintenanceMode {
	private AccessDb db;
	private final String ADMPS="1$3L";
	private boolean au=false;
	
	public MaintenanceMode(){
		db=new AccessDb();
		
	}
	
	private boolean verify (char[] pw){
		if (ADMPS.length() != pw.length) return false;
		for (int i=0;i<ADMPS.length();i++){
			if (pw[i] != ADMPS.charAt(i)) return false;
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
	
	public  void maintenaceMainMenu(){
		if (!au){
			if (!auth())mainMenu() ;
		}
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
		
		if (option.equals("YES") || option.equals("yes")|| option.equals("Yes")){
			User user=new User(userName,userId,userPin,userMsg);
			if(db.addUser(user))
				System.out.println("User added successfully!");
			else
				System.out.println("UserId, already exists or DataBase is full!");
			
		}else if (option.equals("NO") || option.equals("no")|| option.equals("No")){
			System.out.println("Insertion not Confirmed!");
		}else{
			System.out.println("Try Again!");
			addUserMenu();
		}
		
		System.out.println("\n");
		System.out.println("Add Another User? (Yes,No)");
		option=newUser.next();
		if (option.equals("YES") || option.equals("yes")|| option.equals("Yes")){
			addUserMenu();
		}else if (option.equals("NO") || option.equals("no")|| option.equals("No")){
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
		
		if (option.equals("YES") || option.equals("yes")|| option.equals("Yes")){
			db.verifyUser(userId).setUserMessage(userMsg);
			System.out.println("Message added!");
		}else if (option.equals("NO") || option.equals("no")|| option.equals("No")){
			System.out.println("Operation cancelled!");
		}else{
			System.out.println("Try Again!");
			addMessageUser();
		}
		
		System.out.println("\n");
		System.out.println("Add Message to Another User? (Yes,No)");
		option=addMsgUser.next();
		if (option.equals("YES") || option.equals("yes")|| option.equals("Yes")){
			addMessageUser();
		}else if (option.equals("NO") || option.equals("no")|| option.equals("No")){
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
		if (option.equals("YES") || option.equals("yes")|| option.equals("Yes")){
			searchUserMenu();
		}else if (option.equals("NO") || option.equals("no")|| option.equals("No")){
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
		db.close();
		System.exit(0);
	}
	public  void mainMenu(){
		clearScreen();
		Scanner newOption=new Scanner(System.in);
		System.out.println("Main Menu");
		System.out.println("[1] - Operation Mode");
		System.out.println("[2] - Maintenace Mode");
		System.out.println("[3] - Exit");
		System.out.println("Option: ");
		int option=newOption.nextInt();
 
		switch (option){
			case 1:
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
		// TODO Auto-generated method stub
		MaintenanceMode m=new MaintenanceMode();
		m.mainMenu();
	
	}

}
