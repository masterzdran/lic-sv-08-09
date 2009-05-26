/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */
public class testApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LCD l=new LCD();
		Keyboard k=new Keyboard();
		
		char key;
		while(true){
			key=k.waitKey(10000);
			System.out.println(key+"<>"+(int)key);
			l.write(key);
		}

	}

}
