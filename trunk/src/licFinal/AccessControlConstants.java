package licFinal;

/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */
public interface AccessControlConstants {
	public final String ASK4USER = "Insert Number";
	public final String ASK4PIN = "Insert PIN ---";
	public final String ASK4NEWPIN = "New PIN ---";
	public final String OK_MESSAGE = "OK";
	public final String ERROR_PIN_MESSAGE = "Invalid PIN";
	public final String[] SALUTATION = { "Good Morning", "Good Afternoon",
			"Good Evening" };
	public final String DOOROPEN = "Door is Open!";
	public final String CLOSEDOOR = "Door is Locked!";
	public final String INVALIDUSER = "Invalid User!";
	public final String TIMEOUT = "Timeout!";
	public final String INVALID_KEY = "Invalid Key!";
	public final String MAINTENANCE_MESSAGE = "Out off Service";
	public final int PINPOS = 11;
	public final int PINNBR = 3;
	public final int IDNBR = 3;
	public final int CHECKUSER = 0;
	public final int CHECKPIN = 1;
	public final int CHECKNEWPIN = 2;
	public final int DOOR_MASK = 0x20;

}
