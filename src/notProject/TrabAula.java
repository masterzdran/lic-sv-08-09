package notProject;

import isel.leic.usbio.UsbPort;
import isel.leic.utils.Time;


public class TrabAula {
	/**
	 * Variáveis/Constantes de Sistema
	 */
	private static final int START = 0;
	private static final int SLEEP = 250; // n. de milisegundos do Sleep
	private static final int QUITMASK = 0x80;// Define o Bit de Saida
	private static final int DIRECTIONMASK = 0x01;// Define a Direcção
	private static final int LASTBITMASK = 0x80;// Indica Qual o último Bit a
												// ser mostrado
	private static final int FIRSTBITMASK = 0x01;// Indica Qual o primeiro Bit a
													// ser mostrado
	private static final int RIGHT = 0x00; // Define um valor para Shiftar para
											// a Dir.
	private static final int LEFT = 0x01; // Define um valor para Shiftar para a
											// Esq.
	private static final int BITSHIFTED = 1; // N. de Bits a shiftar
	private static int value = 0x00; // Valor a ser mostrado no Kit

	/**
	 * Método a ser substituido pelo método da Classe USBPort
	 */
	private static void sleep() {
		Time.sleep(SLEEP);
	}

	/**
	 * Método que shifta o valor para a esquerda.
	 */
	private static void shiftLeft() {
		value = value << BITSHIFTED;
		if (value > LASTBITMASK) {
			value = FIRSTBITMASK;
		} else if (value == 0) {
			value = FIRSTBITMASK;
		}
	}

	/**
	 * Método que shifta o valor para a direita.
	 */
	private static void shiftRight() {
		value = value >> BITSHIFTED;
		if (value == 0) {
			value = LASTBITMASK;
		} // controla o valor zero para não "pendurar" no valor Zero.
	}

	/**
	 * 
	 * @return
	 * Retorna um inteiro recebido pelo KIT
	 */
	private static int getInput() {
		return UsbPort.in();
	}

	/**
	 * Método que move os LEDs para um lado ou para o outro.
	 */
	public static void move() {
		if ((DIRECTIONMASK & getInput()) == LEFT)
			shiftLeft();
		else
			shiftRight();
	}

	/**
	 * Substituir o print pelo método da Classe USBPort que coloca o valor nas
	 * saídas do KIT.
	 */
	public static void show() {
		UsbPort.out(value);
		sleep();
	}

	public static void show(int i) {
		UsbPort.out(i);
		sleep();
	}

	public static void main(String[] args) {
		show(START);
		do {
			move();
			show();

		} while ((getInput() & value) != QUITMASK);

		System.out.println("\nEnded!\n");
	}

}
