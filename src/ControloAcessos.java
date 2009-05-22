
public class ControloAcessos {
	private static final int MAXUSERS=1000;
	private static final String ASK4USER="Insert Number";
	private static final String ASK4PIN="Insert PIN ---";
	private static final String ASK4NEWPIN="New PIN ---";
	private static final String OK_MESSAGE="OK";
	private static final String ERROR_PIN_MESSAGE="Invalid PIN";
	private static final String ERROR_USER_MESSAGE="Invalid User ID";
	private static final String MAINTENANCE_MESSAGE="Out off Service";
	private static final int PINPOS=11;

	private static int nbrUsers;
	private static User[] users;

	private static void prepareDB(){
		users=new User[MAXUSERS];
		nbrUsers=0;
	}
	
	public static void ask4User(){
		LCD.setCenter(true);
		LCD.writeLine(0, ASK4USER );
	}
	
	public static void ask4Pin(User u){
		LCD.setCenter(true);
		LCD.writeLine(0, u.getUserName());
		LCD.setCenter(false);
		LCD.writeLine(1, ASK4PIN);
		LCD.posCursor(0, PINPOS);
	}
	private static void getUserInput(){
	}
	
	public static void entryMaintenanceMode(){
		LCD.setCenter(true);
		LCD.write(MAINTENANCE_MESSAGE);
	}
	
	
	
}
