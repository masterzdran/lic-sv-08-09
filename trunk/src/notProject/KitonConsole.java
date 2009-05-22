package notProject;
public class KitonConsole {
	/**
	 * Vari�veis/Constantes de Sistema
	 */
	private static final int SLEEP = 250;
	private static final int QUITMASK=0x80;
	private static final int DIRECTIONMASK=0x01;
	private static final int LASTBITMASK=0x80;
	private static final int FIRSTBITMASK=0x01;
	private static final int LEFT=0x00;
	private static final int RIGHT=0x01;
	private static final int BITSHIFTED=1;
	private static int value = 0x00;
	private static int inpValue=0x00;
	
	/** 
	 * M�todo a ser substituido pelo m�todo da Classe USBPort 
	 */
	private static void sleep() {
		try {
			Thread.sleep(SLEEP);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Substituir o print pelo m�todo da Classe USBPort que coloca o valor nas
	 * sa�das do KIT.
	 */
	private static void show() {
		System.out.printf("%8s|\r", Integer.toBinaryString(value));
		sleep();
	}
	/**
	 * M�todo que shifta o valor para a esquerda.
	 */
	private static void shiftLeft() {
		value = value << BITSHIFTED;
		if(value == LASTBITMASK ) {setDirection(LEFT);}
		else if(value == 0){value=FIRSTBITMASK;}//controla o valor zero para n�o "pendura" no valor Zero.
	}
	/**
	 * M�todo que shifta o valor para a direita.
	 */
	private static void shiftRight() {
		value = value >> BITSHIFTED;
		if(value == FIRSTBITMASK) {setDirection(RIGHT);}
		else if(value == 0){value=LASTBITMASK;} //controla o valor zero para n�o "pendura" no valor Zero.
	}

	private static void setDirection(int i){
		inpValue=i;
	}

	
	/**
	 * M�todo que move os LEDs para um lado ou para o outro.
	 */
	public static void move(){
		if ((DIRECTIONMASK&inpValue) == DIRECTIONMASK)
		//if (direction =='L')
			shiftLeft();
		else
			shiftRight();
	}
	
	
	public static void main(String[] args) {
		while ( (QUITMASK&inpValue) != QUITMASK) {
			show();
			move();
		}
		System.out.println("\nEnded!\n");
	}

}
