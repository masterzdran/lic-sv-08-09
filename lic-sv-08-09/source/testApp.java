
/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */
public class testApp{
	public static void main(String[] args) {

		LcdAccess.init();
		Keyboard.resetFifo();
		AccessControl a = new AccessControl();
		MaintenanceMode m = new MaintenanceMode(a.getDB());

		while (true) {
			if (m.isLocked()) {
				LcdAccess.writeLine(0, LicConstants.MAINTENANCE_MESSAGE);
				m.mainMenu();
			} else {
				a.operationAccess();
			}
			Kit.sleep(100);
		}

	}
}
