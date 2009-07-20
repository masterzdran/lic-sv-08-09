package licFinal;

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

	private AccessDb ourDB;
	private char controlChar;
	private boolean aShow = false;

	public AccessControl() {
		ourDB = new AccessDb();
		Kit.resetBits(DOOR_MASK);
		controlChar = 0;
	}

	private String greetings() {
		Calendar cal = new GregorianCalendar();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int idx = (hour >= 20 && hour < 7) ? 2 : ((hour >= 7 && hour < 12) ? 0
				: 1);
		return SALUTATION[idx];
	}

	private void writeMessage(String txt) {
		LcdAccess.clear();
		LcdAccess.writeLine(0, txt);
	}

	private int getNbr(int i) {
		int nbr = 0;
		char key = 0;
		int nbrEntered = 0;
		boolean test = (i == CHECKUSER);
		while (((key = Keyboard.waitKey(5000)) != 0)
				&& !(key >= 'A' && key <= 'F')) {
			if (test) {
				LcdAccess.write(key);
			} else {
				LcdAccess.write('*');
			}

			nbr = nbr * 10 + (key - 48);
			nbrEntered++;
		}

		if (key == 0) {
			if ((i == CHECKNEWPIN))
				writeMessage(TIMEOUT);
			return -1;
		}
		if (((i == CHECKUSER) || (i == CHECKNEWPIN)) && (key != 'E')) {
			writeMessage(INVALID_KEY);
			aShow = false;
			return -1;
		}
		if ((i == CHECKPIN) && (key != 'A' && key != 'E' && key != 'C')) {
			writeMessage(INVALID_KEY);
			aShow = false;
			return -1;
		}
		controlChar = key;
		return nbr;
	}

	private boolean verifyPin(User user, int pin) {
		if (user.getUserPin() == pin) {
			writeMessage(OK_MESSAGE);
			Kit.sleep(1000);
			if (user.hasMessage()) {
				writeMessage(user.getUserMessage());
				Kit.sleep(1000);
				// changePIN(user);
				user.removeUserMessage();
			}

		} else {
			writeMessage(ERROR_PIN_MESSAGE);
			return false;
		}
		return true;
	}

	private void openDoor() {
		LcdAccess.clear();
		LcdAccess.writeLine(0, DOOROPEN);
		Kit.setBits(DOOR_MASK);
	}

	private void closeDoor() {
		LcdAccess.clear();
		LcdAccess.writeLine(0, CLOSEDOOR);
		Kit.resetBits(DOOR_MASK);
	}

	public int ask4User() {
		if (!aShow) {
			LcdAccess.clear();
			LcdAccess.setCenter(true);
			LcdAccess.writeLine(0, greetings());
			LcdAccess.setCenter(false);
			LcdAccess.writeLine(1, ASK4USER);
			aShow = true;
		}
		return getNbr(CHECKUSER);
	}

	public int ask4Pin(String name) {
		aShow = false;
		LcdAccess.clear();
		LcdAccess.writeLine(0, name);
		LcdAccess.writeLine(1, ASK4PIN);
		LcdAccess.posCursor(1, LcdAccess.getPos(ASK4PIN, PINNBR));
		return getNbr(CHECKPIN);
	}

	public int ask4NewPin() {
		LcdAccess.writeLine(1, ASK4NEWPIN);
		LcdAccess.posCursor(1, LcdAccess.getPos(ASK4NEWPIN, PINNBR));
		return getNbr(CHECKNEWPIN);
	}

	public void close() {
		LcdAccess.displayControlOff();
	}

	public void checkUser() {

	}

	public boolean operationAccess() {
		int nbr = ask4User();
		if (nbr == -1)
			return false;

		User user = ourDB.verifyUser(nbr);

		if (user == null) {
			writeMessage(INVALIDUSER);
			aShow = false;
			return false;
		}

		int pin = ask4Pin(user.getUserName());
		if (pin == -1)
			return false;

		if (verifyPin(user, pin)) {
			if (controlChar == 'A')
				changePIN(user);
			openDoor();
			Kit.sleep(5000);
			closeDoor();
		} else {
			return false;
		}

		return true;

	}

	public AccessDb getDB() {
		return ourDB;
	}

	private void changePIN(User u) {
		int p = ask4NewPin();
		if (p != -1) {
			u.setUserPin(p);
			ourDB.save();
		}
	}

	public static void main(String[] args) {

	}
}
