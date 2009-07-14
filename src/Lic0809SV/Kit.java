package Lic0809SV;
import isel.leic.utils.Time;
import isel.leic.usbio.InputPort;
import isel.leic.usbio.OutputPort;
import isel.leic.usbio.UsbPort;
/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */
public class Kit implements KitConstants{
	private static  int readInput;
	private static int writeOutput;
	
	/**
	 * Construtor da Class Kit. 
	 * Inicia os valores dos portos de Input e OutPut.
	 */
	public Kit(){
		//readInput=0x00;
		//writeOutput=0x00;
		//write(writeOutput, EIGHTBITS);
	}

	/** 
	 * Lê o input port para posterior consulta 
	 * */
	public static void read() {
		readInput =~( InputPort.in());
	}

	/**
	 * Consulta o valor representado pelos bits da mascara que foram previamente
	 * lidos com read() Retorna o valor correspondente dos bits com 0 para GND e
	 * 1 para Vcc Por exemplo retorna 0x0F se I0..I3 estão a Vcc e I4..I7 estão
	 * a GND
	 */
	public  static int getBits(int mask) {
		return (readInput&mask);
	}

	/**
	 * Consulta o bit indicado pela mascara previamente lido com read() Retorna
	 * true se o bit indicado pela mascara está a Vcc
	 */
	public  static boolean isBit(int mask) {
		return ((readInput&mask)==mask);
	}

	/** 
	 * Lê e consulta o valor representado pelos bits da mascara 
	 * */
	public  static int readBits(int mask) {
		read();
		return getBits(mask);
	}

	/** 
	 * Lê e consulta o bit indicado pela mascara 
	 * */
	public static  boolean readBit(int mask) {
		read();
		return isBit(mask);
	}

	/**
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
		write(writeOutput,EIGHTBITS);
	}

	/**
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
	public static  void resetBits(int mask) {
		writeOutput=~mask & writeOutput;
		write(writeOutput,EIGHTBITS);
	}

	/**
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
		write(writeOutput,EIGHTBITS);
	}

	/**
	 * Altera os valores dos bits indicados pela mascara para os bits
	 * correspondentes em 'value' 1-Vcc 0-GND
	 */
	public static void write(int values, int mask) {
		writeOutput=values&mask;
		UsbPort.out(~writeOutput);
	}

	public static void main(String[] args) {

	}
}
