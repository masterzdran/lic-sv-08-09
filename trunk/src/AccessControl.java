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
	private void writeMessage(String txt){
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
			writeMessage(TIMEOUT);
			return -1;
		}
		if (((i == CHECKUSER)|| (i==CHECKNEWPIN))&& key !='E'){
			writeMessage(INVALID_KEY);		
			return -1;
		}
		if ((i == CHECKPIN)&& (key !='A' &&  key !='E')){
			writeMessage(INVALID_KEY);		
			return -1;
		}
		controlChar=key;
		return nbr; 
	}

	private boolean verifyPin(User user,int pin){
		if (user.getUserPin() == pin){
			writeMessage(OK_MESSAGE);
		}else{
			writeMessage(ERROR_PIN_MESSAGE);
			return false;
		}
		return true;
	}
	private void openDoor(){
		ourLCD.clear();
		ourLCD.writeLine(0, DOOROPEN);
	}
	private void closeDoor(){
		ourLCD.clear();
		ourLCD.writeLine(0, CLOSEDOOR);
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
	
	public boolean operationAccess(){
		int nbr=ask4User();
		if (nbr == -1)
			 return false;
		
		User user=ourDB.verifyUser(nbr);
		
		if (user == null){
			writeMessage(INVALIDUSER);
			return false;
		}
			
		int pin=ask4Pin(user.getUserName());
		if (pin == -1)
			return false;
		
		if (verifyPin(user, pin)){
			if (controlChar == 'A')
				user.setUserPin(ask4NewPin());
			openDoor();
		}else{
			return false;
		}
		
		return true;
		
	
	}

}
