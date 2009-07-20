package licFinal;

public class LcdProtocol implements KitConstants {
	private static final int bits2Shift = 4;

	// private static Kit ourKit;

	/**
	 * Cria a sequencia de inicio do Protocolo
	 */
	private static void initProtocol() {
		Kit.setBits(RxC_MASK | RxD_MASK);
		fullDelay();
	}

	/**
	 * Cria sequencia de Start.
	 */
	private static void setStart() {
		Kit.invertBits(RxD_MASK);
		delay();
	}

	/**
	 * Verifica se o Kit está pronto a envia a sequencia de Dados.
	 * 
	 * @return
	 */
	private static boolean isReady() {
		return Kit.readBit(RxRDY_MASK);
	}

	/**
	 * Faz reset à sequencia de dados.
	 */
	private static void resetProtocol() {
		Kit.setBits(RxD_MASK);
	}

	/**
	 * Temporizador que pára o acesso ao Kit.
	 */
	private static void delay() {
		Kit.sleep(HALFCLOCK_MASK);
	}

	/**
	 * Temporizador que pára o acesso ao Kit.
	 */
	private static void fullDelay() {
		Kit.sleep(CLOCK_MASK);
	}

	/**
	 * Envia a sequencia de dados para ser mostrado no LCD.
	 * 
	 * @param rs
	 * @param value
	 */
	public static void sendBits(int rs, int value) {
		initProtocol();
		if (isReady()) {
			setStart();
			sendBit(rs);

			for (int count = bits2Shift; count != 0; count -= 1) {
				sendBit(value);
				value = value >> SHIFT_BITS_MASK;
			}

		}
		// if(!isReady()){
		// sendBits(rs, value);
		// }
	}

	/**
	 * Envia os dados, segundo o protocolo, pelo Output port.
	 * 
	 * @param value
	 */
	private static void sendBit(int value) {
		Kit.invertBits(RxC_MASK);

		// Kit.write(value,RxD_MASK);
		if ((value & RxD_MASK) == 1)
			Kit.setBits(RxD_MASK);
		else
			Kit.resetBits(RxD_MASK);
		delay();
		Kit.setBits(RxC_MASK);
		delay();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
