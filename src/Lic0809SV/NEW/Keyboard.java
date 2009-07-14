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
public class Keyboard{
	private static char keyPressed;
	private Kit ourKit;
	/**
	 * Construtor que inicia a tecla de input e cria uma ligação para o Kit.
	 */
	public Keyboard(){
		keyPressed=0;
		ourKit=new Kit();
	}

	/**
	// Se uma tecla está premida retorna a tecla: '0'..'9','A'..'F'
	// Retorna o valor 0 (zero) se nenhuma tecla está premida.
	 * 
	 * @return
	 */
	public  char pressedKey() {
		if(ourKit.readBit((LicConstants.KEY_VAL_MASK))){
			return LicConstants.KEYS[ourKit.getBits(LicConstants.KEY_DAT_MASK)];
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
	public  char getKey() {
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
	public  char waitKey(long timeout) {
		char key;
		long elapsed=0;
		long towait=(LicConstants.WAIT>timeout)?timeout:LicConstants.WAIT;
		
		while (((key=getKey()) == 0 )||(elapsed >= timeout) ){
			elapsed+=towait;
			Time.sleep(towait);
		}
		
		return key;
	}
	public void setACK(){
		ourKit.setBits(LicConstants.ACK_MASK);
	}
	public void unsetACK(){
		ourKit.resetBits(LicConstants.ACK_MASK);
	}
	public  void main(String[] args) {

	}
}
