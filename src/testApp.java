
/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */
public class testApp implements AccessControlConstants {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// // TODO Auto-generated method stub
		// LCD l=new LCD();
		// Keyboard k=new Keyboard();
		// l.setCenter(true);
		// l.blinkOff();
		// l.writeLine(0,ASK4PIN );
		// int x=l.getPos(ASK4PIN, PINNBR);
		// l.posCursor(0, x);
		//
		//		
		// char key=0;
		// int c=0;
		// while(c<PINNBR){
		// key=k.waitKey(10000);
		// System.out.println(key);
		// l.write('*');
		// c++;
		// }
		// key=k.waitKey(10000);
		// switch (key) {
		// case 'C':
		// l.writeLine(1, "Code Canceled");
		// Time.sleep(2000);
		// l.clear();
		// l.writeLine(0,ASK4PIN );
		// l.posCursor(0, x);
		// break;
		// case 'E':
		// l.writeLine(1, "Code Inserted");
		// Time.sleep(2000);
		// break;
		// default:
		// break;
		// }
		// Time.sleep(5000);
		// l.displayControlOff();
		//
		// }~
		AccessControl a = new AccessControl();
		User u=new User("Nuno Cancelo",341,123,"Change Pin");
		
		int userId = a.getUserId();
		a.getUserName(userId);
		a.close();
	}

}
