//import java.sql.Time;
import isel.leic.utils.Time;

/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */
public class AccessControl implements AccessControlConstants {
	
	private Keyboard ourKb;
	private LCD ourLCD;
	private AccessDb ourDB;
	
	public AccessControl(){
		ourKb=new Keyboard();
		ourLCD=new LCD();
		ourDB= new AccessDb();
		ask4User();
	}
//	private String greetings(){
//		Calendar cal = new GregorianCalendar();
//		int hour = cal.get(Calendar.HOUR_OF_DAY);
//		int idx=(hour >= 20 || hour < 7) ? 2 : ((hour >= 7 || hour < 12) ? 0 : 1);
//		return SALUTATION[idx];
//	}

	public void ask4User(){
//		ourLCD.writeLine(0, greetings());
//		isel.leic.utils.Time.sleep(3000);
		ourLCD.setCenter(false);
		ourLCD.clear();
		ourLCD.writeLine(0,ASK4USER);
	}
	public void getUserName(int id){
		ourLCD.clear();
		ourLCD.writeLine(0, ourDB.getUserFullName(id));
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
	private void errorFound(){
		ourLCD.clear();
		ourLCD.writeLine(0, "Invalid Key.");
		ourLCD.writeLine(1, "Try Again.");
	}
	public int getUserId(){
		int id=0;
		boolean t=false;
		char key=0;
		while ((key=ourKb.waitKey(5000))!= 0){
			if ((key >= 'A' && key <='D') ||( key=='F')){
				errorFound();
				ask4User();
				return getUserId();
			}else if(key=='E'){
				System.out.println(key);
				break;
			}
			ourLCD.write(key);
			System.out.println(key+"--"+id);
			id=id*10+(key - 48);
		}
		
		return id; 
	}
	public void setACK(){
		ourKb.setACK();
	}
	public void unsetACK(){
		ourKb.unsetACK();
	}
	public void close(){
		Time.sleep(5000);
		ourLCD.displayControlOff();
		
	}
	public static void main(String[] args) {
	}
}
