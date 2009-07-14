package Lic0809SV;
import java.util.Calendar;
import java.util.GregorianCalendar;
import Lic0809SV.LicConstants; 

/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */
public class AccessControl{
	
	private Keyboard ourKb;
	private LcdAccess ourLCD;
	private AccessDb ourDB;
	private char controlChar;
	private Kit ourKit;
	
	public AccessControl(){
		ourKb=new Keyboard();
		ourLCD=new LcdAccess();
		ourDB= new AccessDb();
		ourKit=new Kit();
		controlChar=0;
		
	}
	private String greetings(){
		Calendar cal = new GregorianCalendar();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int idx=(hour >= 20 || hour < 7) ? 2 : ((hour >= 7 || hour < 12) ? 0 : 1);
		return LicConstants.SALUTATION[idx];
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
			if (i == LicConstants.CHECKUSER){
				ourLCD.write(key);
			}else{
				ourLCD.write('*');
			}
			
			nbr=nbr*10+(key - 48);
			nbrEntered++;
		}
		if (key==0){
			writeMessage(LicConstants.TIMEOUT);
			return -1;
		}
		if (((i == LicConstants.CHECKUSER)|| (i==LicConstants.CHECKNEWPIN))&& key !='E'){
			writeMessage(LicConstants.INVALID_KEY);		
			return -1;
		}
		if ((i == LicConstants.CHECKPIN)&& (key !='A' &&  key !='E')){
			writeMessage(LicConstants.INVALID_KEY);		
			return -1;
		}
		controlChar=key;
		return nbr; 
	}
	public boolean isLock(){
		return ourKit.readBit(LicConstants.KEY_LOCK_MASK);
	}
	private boolean verifyPin(User user,int pin){
		if (user.getUserPin() == pin){
			writeMessage(LicConstants.OK_MESSAGE);
		}else{
			writeMessage(LicConstants.ERROR_PIN_MESSAGE);
			return false;
		}
		return true;
	}
	private void openDoor(){
		ourLCD.clear();
		ourLCD.writeLine(0, LicConstants.DOOROPEN);
	}
	private void closeDoor(){
		ourLCD.clear();
		ourLCD.writeLine(0, LicConstants.CLOSEDOOR);
	}
	public void enterMantenanceMode(){
		ourLCD.clear();
		ourLCD.writeLine(0, LicConstants.MAINTENANCE_MESSAGE);
	}
	public int ask4User(){
		ourLCD.clear();
		ourLCD.setCenter(true);
		ourLCD.writeLine(0, greetings());
		ourLCD.setCenter(false);
		ourLCD.writeLine(1,LicConstants.ASK4USER);
		return getNbr(LicConstants.CHECKUSER);
	}
	public int ask4Pin(String name){
		ourLCD.clear();
		ourLCD.writeLine(0, name);
		ourLCD.writeLine(1, LicConstants.ASK4PIN);
		ourLCD.posCursor(1, ourLCD.getPos(LicConstants.ASK4PIN,LicConstants.PINNBR));
		return getNbr(LicConstants.CHECKPIN);
	}
	
	public int ask4NewPin(){
		ourLCD.clearLine(1);
		ourLCD.writeLine(1, LicConstants.ASK4NEWPIN);
		ourLCD.posCursor(1, ourLCD.getPos(LicConstants.ASK4NEWPIN,LicConstants.PINNBR));
		return getNbr(LicConstants.CHECKNEWPIN);
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
			writeMessage(LicConstants.INVALIDUSER);
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
