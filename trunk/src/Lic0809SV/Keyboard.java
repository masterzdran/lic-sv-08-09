package Lic0809SV;
import isel.leic.utils.Time;

/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */
public class Keyboard implements KitConstants {
	private static char keyPressed;
	//private Kit ourKit;

	/**
	 * Construtor que inicia a tecla de input e cria uma ligação para o Kit.
	 */
	public Keyboard() {

		keyPressed = 0;
		//ourKit = k;
		unsetACK();
		setACK();
	}

	/**
	 * // Se uma tecla está premida retorna a tecla: '0'..'9','A'..'F' //
	 * Retorna o valor 0 (zero) se nenhuma tecla está premida.
	 * 
	 * @return
	 */
	public char pressedKey() {


		if (Kit.readBit((KEY_VAL_MASK))) {
			char key = KEYS[Kit.getBits(KEY_DAT_MASK)];
			unsetACK();
			setACK();
			return key;
		}
		//unsetACK();
		setACK();

		keyPressed = 0;
		return 0;
	}

	/**
	 * // Se uma tecla foi premida retorna a tecla: '0'..'9','A'..'F' // Retorna
	 * o valor 0 (zero) se nenhuma tecla foi premida. // Retorna o valor 0
	 * (zero) se a tecla premida já foi retornada.
	 * 
	 * @return
	 */
	public char getKey() {
		char key;
		if ((key = pressedKey()) != 0 && key != keyPressed) {
			keyPressed = key;
			return key;
		}
		return 0;
	}

	/**
	 * // Se uma tecla foi premida retorna a tecla: '0'..'9','A'..'F' // Se
	 * nenhuma tecla foi premida espera durante o tempo indicado // em
	 * milisegundos que seja premida uma tecla. // Retorna o valor 0 (zero) se
	 * não for premida uma tecla durante // o tempo indicado.
	 * 
	 * @param timeout
	 * @return
	 */
	public char waitKey(long timeout) {
		char k;
		long elapsed = 0;
		long towait = (WAIT > timeout) ? timeout : WAIT;
		while (((k = this.getKey()) == 0) || (elapsed >= timeout)) {
			elapsed += towait;
			Time.sleep(towait);
		}
		return k;
	}

	public void setACK() {
		Kit.setBits(ACK_MASK);
	}

	public void unsetACK() {
		Kit.resetBits(ACK_MASK);
	}

	public static void main(String[] args) {

	}

}
