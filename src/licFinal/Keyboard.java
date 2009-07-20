package licFinal;

/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - Jos� Guilherme
 * 33595 - Nuno Sousa
 * 
 */
public class Keyboard implements KitConstants {
	private static char keyPressed = 0;
	private static int time = 1;

	public static void resetFifo() {
		while (Kit.readBit((KEY_VAL_MASK))) {
			ACK();
		}
	}

	/**
	 * // Se uma tecla est� premida retorna a tecla: '0'..'9','A'..'F' //
	 * Retorna o valor 0 (zero) se nenhuma tecla est� premida.
	 * 
	 * @return
	 */
	public static char pressedKey() {
		setACK();
		if (Kit.readBit((KEY_VAL_MASK))) {
			char key = KEYS[Kit.getBits(KEY_DAT_MASK)];
			Kit.sleep(50);
			ACK();

			return key;
		}

		keyPressed = 0;
		return 0;
	}

	/**
	 * // Se uma tecla foi premida retorna a tecla: '0'..'9','A'..'F' // Retorna
	 * o valor 0 (zero) se nenhuma tecla foi premida. // Retorna o valor 0
	 * (zero) se a tecla premida j� foi retornada.
	 * 
	 * @return
	 */
	public static char getKey() {
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
	 * n�o for premida uma tecla durante // o tempo indicado.
	 * 
	 * @param timeout
	 * @return
	 */
	public static char waitKey(long timeout) {
		char k;
		long elapsed = 0;
		long towait = (WAIT > timeout) ? timeout : WAIT;
		while (((k = getKey()) == 0) && (elapsed < timeout)) {
			elapsed += towait;
			Kit.sleep(towait);
		}
		return k;
	}

	public static void setACK() {
		Kit.setBits(ACK_MASK);
	}

	public static void unsetACK() {
		Kit.resetBits(ACK_MASK);
	}

	public static void ACK() {
		unsetACK();
		Kit.sleep(time);
		setACK();
	}

	public static void main(String[] args) {
		while (true) {
			System.out.print(waitKey(2500) + " ");
			Kit.sleep(100);
		}
	}

}
