import java.util.Scanner;


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
		boolean maint=false;
		//LcdAccess.init();
		while (true) {
			if (m.isLocked()) {
				if(!maint){
				a.resetShow();
				LcdAccess.clear();
				LcdAccess.writeLine(0, LicConstants.MAINTENANCE_MESSAGE);
				m.mainMenu();
				maint=true;
				}
				
			} else {
				
				maint=false;
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
			if (c!= 0) {System.out.println(c);LcdAccess.write(c);}
		}
	}
	public static void tes4(){
		Keyboard.unsetACK();
		LcdAccess.writeLine(0, "Teste ao fifo. Teste ao fifo.");
		LcdAccess.posCursor(1, 0);
		while (true){
			tes3();
			
		}
		
	}
	public static void tes5(){
		System.out.println("Insert New Password: ");
		Scanner option = new Scanner(System.in);
		if (option.nextInt()== 0){
			
		}
		tes3();
		
	}
	public static void main(String[] args) {

		LcdAccess.init();
		Keyboard.setACK();
		Keyboard.resetFifo();
		tes1();

	}
}
