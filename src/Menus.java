
public class Menus {

	/**
	 * @param args
	 */
	public static void maintenaceMainMenu(){
		System.out.println("Maintenace Mode");
		System.out.println("Main Menu:");
		System.out.println("[1] - Add New User");
		System.out.println("[2] - Remove User");
		System.out.println("[3] - Associate Message to User");
		System.out.println("[4] - Search User");
		System.out.println("[5] - List Users");
		System.out.println("[6] - Exit");
	}
	
	public static void addUserMenu(){
		System.out.println("Maintenace Mode");
		System.out.println("Main Menu");
		System.out.println("\t[1] - Add New User");
		System.out.println("\tUser Id: ");
		System.out.println("\tUser Name: ");
		System.out.println("\tUser Pin: ");
		System.out.println("\tAssociate User Message:");
		System.out.println("Confirm data?");
	}

	public static void removeUserMenu(){
		System.out.println("Maintenace Mode");
		System.out.println("Main Menu");
		System.out.println("\t[2] - Remove User");
		System.out.println("\tUser Id: ");
		System.out.println("Confirm data?");
	}
	public static void addMessageUser(){
		System.out.println("Maintenace Mode");
		System.out.println("Main Menu");
		System.out.println("\t[3] - Associate Message to User");
		System.out.println("\tUser Id: ");
		System.out.println("\tAssociate User Message:");
		System.out.println("Confirm data?");
	}
	public static void searchUserMenu(){
		System.out.println("Maintenace Mode");
		System.out.println("Main Menu");
		System.out.println("\t[4] - Search User");
		System.out.println("\tUser Id: ");
	}
	public static void listUsersMenu(){
		System.out.println("Maintenace Mode");
		System.out.println("Main Menu");
		System.out.println("\t[5] - List Users");
		System.out.println("\t");
	}
	
	public static void clearScreen(){
		for(int i=0;i<100;i++){
			System.out.println("");
		}
	}
	
	public static void mainMenu(){
		System.out.println("Main Menu");
		System.out.println("\t[M] - Maintenace Mode");
		System.out.println("\t");
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
