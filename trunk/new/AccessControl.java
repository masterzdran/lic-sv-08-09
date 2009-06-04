import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */
public class AccessControl implements AccessControlConstants {
	private static final String[] SALUTATION={"Good Morning","Good Afternoon","Good Evening"};
	Keyboard ourKb;
	LCD ourLCD;
	AccessDb ourDB;
	
	public AccessControl(){
		ourKb=new Keyboard();
		ourLCD=new LCD();
		ourDB= new AccessDb();
	}
	private String greetings(){
		Calendar cal = new GregorianCalendar();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int idx=(hour >= 20 || hour < 7) ? 2 : ((hour >= 7 || hour < 12) ? 0 : 1);
		return SALUTATION[idx];
	}

	public void ask4User(){
		ourLCD.setCenter(true);
		ourLCD.writeLine(0, greetings());
		ourLCD.writeLine(1,ASK4USER);
	}
	
	public void ask4Pin(User u){
		ourLCD.writeLine(0, u.getUserName());
		ourLCD.writeLine(1, ASK4PIN);
		ourLCD.posCursor(1, ourLCD.getPos(ASK4PIN,3));
	}

	public void ask4NewPin(User u){
		ourLCD.clearLine(1);
		ourLCD.writeLine(1, ASK4NEWPIN);
		ourLCD.posCursor(1, ourLCD.getPos(ASK4NEWPIN,3));
	}
	public void setACK(){
		ourKb.setACK();
	}
	public void unsetACK(){
		ourKb.unsetACK();
	}
	public static void main(String[] args) {
	}
}
