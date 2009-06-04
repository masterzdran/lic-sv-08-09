import java.util.Calendar;
import java.util.GregorianCalendar;


/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */
public class AccessControl implements AccessControlConstants {
	
	private Keyboard ourKb;
	private LcdAccess ourLCD;
	private AccessDb ourDB;
	private char controlChar;
	
	public AccessControl(){
		ourKb=new Keyboard();
		ourLCD=new LcdAccess();
		ourDB= new AccessDb();
		controlChar=0;
	}
	private String greetings(){
		Calendar cal = new GregorianCalendar();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int idx=(hour >= 20 || hour < 7) ? 2 : ((hour >= 7 || hour < 12) ? 0 : 1);
		return SALUTATION[idx];
	}
	private void errorFound(String txt){
		ourLCD.clear();
		ourLCD.writeLine(0, txt);
	}

	private int getNbr(int i){
		int nbr=0;
		char key=0;
		int nbrEntered=0;
		while (((key=ourKb.waitKey(5000))!= 0)&& (key >= 'A' || key <='F')){
			if (i == CHECKUSER){
				ourLCD.write(key);
			}else{
				ourLCD.write('*');
			}
			
			nbr=nbr*10+(key - 48);
			nbrEntered++;
		}
		if (key==0){
			errorFound("Timeout!");
			return -1;
		}
		if (((i == CHECKUSER)|| (i==CHECKNEWPIN))&& key !='E'){
			errorFound("Invalid Key!");		
			return -1;
		}
		if ((i == CHECKPIN)&& (key !='A' || key !='E')){
			errorFound("Invalid Key!");		
			return -1;
		}
		controlChar=key;
		return nbr; 
	}

	private boolean verifyPin(User user,int pin){
		return (user.getUserPin() == pin); 
	}
	private void openDoor(){
		ourLCD.clear();
		ourLCD.writeLine(0, DOOROPEN);
	}
	public int ask4User(){
		ourLCD.clear();
		ourLCD.setCenter(true);
		ourLCD.writeLine(0, greetings());
		ourLCD.setCenter(false);
		ourLCD.writeLine(1,ASK4USER);
		return getNbr(CHECKUSER);
	}
	public int ask4Pin(String name){
		ourLCD.clear();
		ourLCD.writeLine(0, name);
		ourLCD.writeLine(1, ASK4PIN);
		ourLCD.posCursor(1, ourLCD.getPos(ASK4PIN,PINNBR));
		return getNbr(CHECKPIN);
	}
	
	public int ask4NewPin(){
		ourLCD.clearLine(1);
		ourLCD.writeLine(1, ASK4NEWPIN);
		ourLCD.posCursor(1, ourLCD.getPos(ASK4NEWPIN,PINNBR));
		return getNbr(CHECKNEWPIN);
	}


	public void close(){
		ourLCD.displayControlOff();
	}
	
	public void access(){
		int nbr=ask4User();
		if (nbr == -1)
			access();
		
		User user=ourDB.verifyUser(nbr);
		if (user == null){
			errorFound("Invalid User!");
			access();
		}
			
		int pin=ask4Pin(user.getUserName());
		if (pin == -1)
			access();
		if (controlChar == 'A' && verifyPin(user, pin))
			user.setUserPin(ask4NewPin());
		
		if (verifyPin(user, pin))openDoor();
	}

}
