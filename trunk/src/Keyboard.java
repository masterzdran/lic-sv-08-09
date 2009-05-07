import isel.leic.utils.Time;


public class Keyboard  {
	// Mascaras das liga��es ao input port.
	private static final int KEY_VAL_MASK = 0x40;
	private static final int KEY_DAT_MASK = 0x0F;
	private static final char[] KEYS ={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	private static final long WAIT=200;
	private static char keyPressed=0;

	/**
	// Se uma tecla est� premida retorna a tecla: '0'..'9','A'..'F'
	// Retorna o valor 0 (zero) se nenhuma tecla est� premida.
	 * 
	 * @return
	 */
	public static char pressedKey() {
		if(Kit.readBit((KEY_VAL_MASK))){
			return KEYS[Kit.getBits(KEY_DAT_MASK)];
		}
		keyPressed=0;
		return 0;
	}
	/**
	// Se uma tecla foi premida retorna a tecla: '0'..'9','A'..'F'
	// Retorna o valor 0 (zero) se nenhuma tecla foi premida.
	// Retorna o valor 0 (zero) se a tecla premida j� foi retornada.
	 *  
	 * @return
	 */
	public static char getKey() {
		char key;
		if((key=pressedKey())!= 0 && key != keyPressed){
			keyPressed=key;
			return key;
		}
		return 0;
	}
	
	/**
	// Se uma tecla foi premida retorna a tecla: '0'..'9','A'..'F'
	// Se nenhuma tecla foi premida espera durante o tempo indicado
	//  em milisegundos que seja premida uma tecla.
	// Retorna o valor 0 (zero) se n�o for premida uma tecla durante
	//  o tempo indicado.
	 * 
	 * @param timeout
	 * @return
	 */
	public static char waitKey(long timeout) {
		char key;
		long elapsed=0;
		long towait=(WAIT>timeout)?timeout:WAIT;
		
		while (((key=getKey()) == 0 )||(elapsed >= timeout) ){
			elapsed+=towait;
			Time.sleep(towait);
		}
		
		return key;
	}
	public static void main(String[] args) {
		char key;
		while(true){
			key=waitKey(10000);
			System.out.println(key+"<>"+(int)key);
			//Time.sleep(1000);
		}
	}
}
