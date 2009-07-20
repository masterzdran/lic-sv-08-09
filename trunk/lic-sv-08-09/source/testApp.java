
/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */
public class testApp{
	public static void tes1(){
		AccessControl a = new AccessControl();
		MaintenanceMode m = new MaintenanceMode(a.getDB());
		//LcdAccess.init();
		while (true) {
			if (m.isLocked()) {
				LcdAccess.clear();
				LcdAccess.writeLine(0, LicConstants.MAINTENANCE_MESSAGE);
				//m.mainMenu();
			} else {
				LcdAccess.clear();
				a.operationAccess();
			}
			Kit.sleep(100);
		}
		
	}
	public static void tes2(){
		char k;
		while (true){
			k=Keyboard.waitKey(5000);
			if (k != 0){
				if (k == 'C')
					LcdAccess.clear();
				else
				LcdAccess.write(k);
			}
			Kit.sleep(200);
		}
		
	}
	public static void tes3(){
		char c;
		while (true){
			c=Keyboard.waitKey(5000);
			if (c!= 0) System.out.print(c);
		}
	}
	public static void main(String[] args) {

		LcdAccess.init();
		Keyboard.resetFifo();
		tes1();

	}
}
