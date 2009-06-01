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
	private  int readInput;
	private  int writeOutput;
	
	/**
	 * Construtor da Class Kit. 
	 * Inicia os valores dos portos de Input e OutPut.
	 */
	public Kit(){
		readInput=0x00;
		writeOutput=0x00;
		write(writeOutput, EIGHTBITS);
	}

	/** 
	 * Lê o input port para posterior consulta 
	 * */
	public void read() {
		readInput =~( InputPort.in());
	}

	/**
	 * Consulta o valor representado pelos bits da mascara que foram previamente
	 * lidos com read() Retorna o valor correspondente dos bits com 0 para GND e
	 * 1 para Vcc Por exemplo retorna 0x0F se I0..I3 estão a Vcc e I4..I7 estão
	 * a GND
	 */
	public  int getBits(int mask) {
		return (readInput&mask);
	}

	/**
	 * Consulta o bit indicado pela mascara previamente lido com read() Retorna
	 * true se o bit indicado pela mascara está a Vcc
	 */
	public  boolean isBit(int mask) {
		return ((readInput&mask)==mask);
	}

	/** 
	 * Lê e consulta o valor representado pelos bits da mascara 
	 * */
	public  int readBits(int mask) {
		read();
		return getBits(mask);
	}

	/** 
	 * Lê e consulta o bit indicado pela mascara 
	 * */
	public  boolean readBit(int mask) {
		read();
		return isBit(mask);
	}
	/**
	 * Pára o acesso aos portos do Kit, durante alguns milisegundos.
	 */
	public  void sleep(){
		Time.sleep(SLEEP);
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
	public  void setBits(int mask) {
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
	public  void resetBits(int mask) {
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
	public  void invertBits(int mask) {
		writeOutput= writeOutput ^ mask;
		write(writeOutput,EIGHTBITS);
	}

	/**
	 * Altera os valores dos bits indicados pela mascara para os bits
	 * correspondentes em 'value' 1-Vcc 0-GND
	 */
	public  void write(int values, int mask) {
		writeOutput=values&mask;
		UsbPort.out(~writeOutput);
	}

	public static void main(String[] args) {

	}
}
