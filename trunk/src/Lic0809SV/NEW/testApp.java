package Lic0809SV;

import isel.leic.utils.Time;
import Lic0809SV.LicConstants;

/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */
public class testApp{

	public static void main(String[] args) {
		MaintenanceMode m;
		AccessControl a=new AccessControl();
		while (true){
			Time.sleep(LicConstants.MAINSLEEP);
			if (a.isLock()){
				a.enterMantenanceMode();
				m=new MaintenanceMode();
				m.mainMenu();
				a=new AccessControl();
			}else{
				a.operationAccess();
			}
			
		}
		
		

	}
}

