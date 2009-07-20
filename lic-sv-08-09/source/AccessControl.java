import java.util.Calendar;
import java.util.GregorianCalendar;

/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */
public class AccessControl {

	private AccessDb ourDB;
	private char controlChar;
	private boolean aShow = false;

	public AccessControl() {
		ourDB = new AccessDb();
		Kit.resetBits(LicConstants.DOOR_MASK);
		controlChar = 0;
	}

	private String greetings() {
		Calendar cal = new GregorianCalendar();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int idx = (hour >= 20 && hour < 7) ? 2 : ((hour >= 7 && hour < 12) ? 0
				: 1);
		return LicConstants.SALUTATION[idx];
	}

	private void writeMessage(String txt) {
		LcdAccess.clear();
		LcdAccess.writeLine(0, txt);
	}

	private void write(char c) {
		LcdAccess.write(c);
	}

	private void clearNbr(String s, int max, int i) {
		posCursor(1, s, max);
		if (i == LicConstants.CHECKUSER){
			for (int j = 0; j < max; j++)
				write(' ');
		}else{
			for (int j = 0; j < max; j++)
				write('-');
			
		}
		posCursor(1, s, max);
	}

	private void cancel(int s) {
		switch (s) {
		case LicConstants.CHECKUSER:
			clearNbr(LicConstants.ASK4USER, LicConstants.MAXDIGIT,s);
			break;
		case LicConstants.CHECKPIN:
			clearNbr(LicConstants.ASK4PIN, LicConstants.MAXDIGIT,s);
			break;

		case LicConstants.CHECKNEWPIN:
			clearNbr(LicConstants.ASK4NEWPIN, LicConstants.MAXDIGIT,s);
			break;

		case LicConstants.CONFIRMPIN:
			clearNbr(LicConstants.CONFIRMNEWPIN, LicConstants.MAXDIGIT,s);
			break;
		}
	}

	private void clear() {
		LcdAccess.clear();
	}

	private void setCenter(boolean b) {
		LcdAccess.setCenter(b);
	}

	private void writeLine(int line, String text) {
		LcdAccess.writeLine(line, text);
	}

	private void posCursor(int line, String text, int max) {
		LcdAccess.posCursor(line, LcdAccess.getPos(text, max));
	}

	private int getNbr(int i) {
		int nbr = 0;
		char key = 0;
		int nbrEntered = 0;
		// Colocar Limite de Caracteres Inseridos
		while (((key = Keyboard.waitKey(5000)) != 0)&& !(key >= 'A' && key <= 'F') && (nbrEntered<3)) {
			if (i == LicConstants.CHECKUSER) {
				write(key);
			} else {
				write('*');
			}

			nbr = nbr * 10 + (key - 48);
			nbrEntered++;
		}
		//key = Keyboard.waitKey(5000);
		switch (key) {
		case 'A':
			if (i == LicConstants.CHECKPIN)
				controlChar = key;
			return nbr;
		case 'C':
			cancel(i);
			nbr=0;
			return getNbr(i);
			
		case 'E':

			return nbr;
		default:
			return -1;
		}

		// if (key == 0) {
		// if ((i == LicConstants.CHECKNEWPIN))
		// writeMessage(LicConstants.TIMEOUT);
		// return -1;
		// }
		// if (((i == LicConstants.CHECKUSER) || (i ==
		// LicConstants.CHECKNEWPIN)) && (key != 'E')) {
		// writeMessage(LicConstants.INVALID_KEY);
		// aShow = false;
		// return -1;
		// }
		// if ((i == LicConstants.CHECKPIN) && (key != 'A' && key != 'E' && key
		// != 'C')) {
		// writeMessage(LicConstants.INVALID_KEY);
		// aShow = false;
		// return -1;
		// }
	}

	private boolean verifyPin(User user, int pin) {
		if (user.getUserPin() == pin) {
			writeMessage(LicConstants.OK_MESSAGE);
			Kit.sleep(1000);
			if (user.hasMessage()) {
				writeMessage(user.getUserMessage());
				Kit.sleep(1000);
				// changePIN(user);
				user.removeUserMessage();
			}

		} else {
			writeMessage(LicConstants.ERROR_PIN_MESSAGE);
			return false;
		}
		return true;
	}

	private void openDoor() {
		LcdAccess.clear();
		LcdAccess.writeLine(0, LicConstants.DOOROPEN);
		Kit.setBits(LicConstants.DOOR_MASK);
	}

	private void closeDoor() {
		LcdAccess.clear();
		LcdAccess.writeLine(0, LicConstants.CLOSEDOOR);
		Kit.resetBits(LicConstants.DOOR_MASK);
	}

	private int newPin() {
		int nPin = ask4NewPin();
		int cnPin = confirm4NewPin();

		if (nPin == cnPin)
			return nPin;

		return -1;
	}

	private int ask4NewPin() {
		writeLine(1, LicConstants.ASK4NEWPIN);
		posCursor(1, LicConstants.ASK4NEWPIN, LicConstants.MAXDIGIT);
		return getNbr(LicConstants.CHECKNEWPIN);
	}

	private int confirm4NewPin() {
		writeLine(1, LicConstants.CONFIRMNEWPIN);
		posCursor(1, LicConstants.CONFIRMNEWPIN, LicConstants.MAXDIGIT);
		return getNbr(LicConstants.CONFIRMPIN);
	}

	private void changePIN(User u) {
		int p = newPin();
		if (p != -1) {
			u.setUserPin(p);
			ourDB.save();
		}
	}

	public void close() {
		LcdAccess.displayControlOff();
	}

	public boolean operationAccess() {
		/**
		 * Pede o Utilizador
		 */
		int nbr = ask4User();
		if (nbr == -1)
			return false;

		/**
		 * Verifica se o Utilizador Existe!
		 */
		User user = ourDB.verifyUser(nbr);

		if (user == null) {
			writeMessage(LicConstants.INVALIDUSER);
			aShow = false;
			return false;
		}

		/**
		 * Pede o Pin do Utilizador
		 */
		int pin = ask4Pin(user.getUserName());
		if (pin == -1)
			return false;

		/**
		 * Verifica o Pin do Utilizado
		 */
		if (verifyPin(user, pin)) {
			/**
			 * Verifica se Pede Para alterar.
			 */
			if (controlChar == 'A') {
				changePIN(user);
				controlChar = ' ';
			}
			openDoor();
			Kit.sleep(5000);
			closeDoor();
		} else {
			return false;
		}

		return true;
	}

	public int ask4User() {
		//if (!aShow) {
			clear();
			setCenter(true);
			writeLine(0, greetings());
			setCenter(false);
			writeLine(1, LicConstants.ASK4USER);
			posCursor(1, LicConstants.ASK4USER, LicConstants.MAXDIGIT);
			aShow = true;
		//}
		return getNbr(LicConstants.CHECKUSER);
	}

	public int ask4Pin(String name) {
		aShow = false;
		clear();
		writeLine(0, name);
		writeLine(1, LicConstants.ASK4PIN);
		posCursor(1, LicConstants.ASK4PIN, LicConstants.MAXDIGIT);
		return getNbr(LicConstants.CHECKPIN);
	}

	public AccessDb getDB() {
		return ourDB;
	}

	public static void main(String[] args) {

	}
}
