import isel.leic.utils.Time;
import isel.leic.usbio.InputPort;
import isel.leic.usbio.OutputPort;

public class Kit {
	private static final int VCCMASK=0xFF;
	private static final int GNDMASK=0X00;
	private static final int SLEEP=250;
	private static int readInput;
	private static int writeOutput;

	
	public static void show(int value) {
		OutputPort.out(value);
	}
	/* Lê o input port para posterior consulta */
	public static void read() {
		readInput = InputPort.in();
		writeOutput=readInput;
	}

	/*
	 * Consulta o valor representado pelos bits da mascara que foram previamente
	 * lidos com read() Retorna o valor correspondente dos bits com 0 para GND e
	 * 1 para Vcc Por exemplo retorna 0x0F se I0..I3 estão a Vcc e I4..I7 estão
	 * a GND
	 */
	public static int getBits(int mask) {
		return (readInput&mask);
	}

	/*
	 * Consulta o bit indicado pela mascara previamente lido com read() Retorna
	 * true se o bit indicado pela mascara está a Vcc
	 */
	public static boolean isBit(int mask) {
		return ((readInput&mask)==mask);
	}

	/* Lê e consulta o valor representado pelos bits da mascara */
	public static int readBits(int mask) {
		read();
		return getBits(mask);
	}

	/* Lê e consulta o bit indicado pela mascara */
	public static boolean readBit(int mask) {
		read();
		return isBit(mask);
	}

	/*
	 * Coloca a Vcc os bits indicados pela mascara e os restantes bits mantêm o
	 * valor.
	 * 
	 *  R | M | M + R  
	 * ---------------
	 *  0 | 0 |   0    <- Mantém
	 *  0 | 1 |   1    <- Altera
	 *  1 | 0 |   1    <- Mantém
	 *  1 | 1 |   1    <- Altera
	 * 
	 * Output=readInput+Mask
	 */
	public static void setBits(int mask) {
		writeOutput=writeOutput|mask;
		show(writeOutput);/*2 delete*/
	}

	/*
	 * Coloca a GND os bits indicados pela mascara e os restantes bits mantêm o
	 * valor.
	 * 
	 *  R | ~M | M * R  
	 * ---------------
	 *  0 | 1  |   0    <- Altera (acaba por manter)
	 *  0 | 0  |   0    <- Mantém
	 *  1 | 1  |   0    <- Altera
	 *  1 | 0  |   1    <- Mantém
	 * 
	 * Output=~Mask&readInput
	 */
	public static void resetBits(int mask) {
		writeOutput=~mask & writeOutput;
		show(writeOutput);/*2 delete*/
	}

	/*
	 * Inverte o valor dos bits indicados pela mascara e os restantes bits
	 * mantêm o valor.
	 * 
	 *  R | M | M ^ R  
	 * ---------------
	 *  0 | 0 |   0    <- Mantém
	 *  0 | 1 |   1    <- Altera
	 *  1 | 0 |   1    <- Mantém
	 *  1 | 1 |   0    <- Altera
	 * 
	 * Output=readInput^Mask
	 */
	public static void invertBits(int mask) {
		writeOutput= writeOutput ^ mask;
		show(writeOutput);/*2 delete*/
	}

	/*
	 * Altera os valores dos bits indicados pela mascara para os bits
	 * correspondentes em ´value´ 1-Vcc 0-GND
	 */
	public static void write(int values, int mask) {
		OutputPort.out(values&mask);
	}

	public static void main(String[] args) {
		Kit.write(0x00, 0xFF);
		Kit.setBits(0xFF); // Coloca a Vcc todos os bits do output port -> ----
							// ----
		while (Kit.readBit(0x1))
			; // Espera que o bit I0 fique a GND
			Kit.resetBits(0x0F); // Coloca a GND os 4 bits de menor peso -> ----
								// ****
		while (!Kit.readBit(0x1))
			; // Espera que o bit I0 fique a Vcc
			Kit.invertBits(0xFF); // Inverte os valores dos bits do output port ->
				// **** ----
		for (int val = 1; Kit.isBit(0x80);) { // Enquanto entrada I7 a Vcc
												// (ultima leitura)
			Kit.write(~val, 0x0F); // Escreve o valor nos 4 bits de menor peso
			Time.sleep(SLEEP); // Espera 100 ms
			Kit.read(); // Le os bits do input port
			if (Kit.isBit(0x02)) { // Se entrada I1 a Vcc (ultima leitura)
				if ((val <<= 1) > 8)
					val = 1; // Roda bits do valor para esquerda
			} else {
				if ((val >>= 1) < 1)
					val = 8; // Roda bits do valor para a direita
			}
		}
	}
}
