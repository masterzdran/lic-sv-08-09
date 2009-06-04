import isel.leic.utils.Time;


public class Keyboard  {
	// Mascaras das ligações ao input port.
	private static final int KEY_VAL_MASK = 0x40;
	private static final int KEY_DAT_MASK = 0x0F;
	private static final char[] KEYS ={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	private static final long WAIT=200;
	private static char keyPressed=0;

	/**
	// Se uma tecla está premida retorna a tecla: '0'..'9','A'..'F'
	// Retorna o valor 0 (zero) se nenhuma tecla está premida.
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
	// Retorna o valor 0 (zero) se a tecla premida já foi retornada.
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
	// Retorna o valor 0 (zero) se não for premida uma tecla durante
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
		LCD.init();
		Time.sleep(2000);
		while(true){
			key=waitKey(10000);
			switch (key) {
			case 'C':
				LCD.clear();
				LCD.setCenter(true);
				LCD.writeLine(0, "Ecra apagado");
				LCD.posCursor(1, 0);
				break;
			case 'A':
				LCD.posCursor(0, 0);
				break;
			case 'B':
				LCD.posCursor(1, 0);
				break;
			case 'F':
				LCD.clear();
				LCD.setCenter(true);
				LCD.writeLine(0, "Operacao");
				LCD.posCursor(1, 0);
				LCD.setCenter(false);
				LCD.writeLine(1, "Finalizada");
				Time.sleep(2000);
				LCD.cursorOff();
				Time.sleep(2000);
				LCD.displayControlOff();
				System.exit(0);
			default:
				LCD.write(key);
				break;

			}

		}
	}
}
