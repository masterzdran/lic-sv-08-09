import isel.leic.utils.Time;
/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */

public class KitProtocol implements KitConstants{
	private int	bits2Shift;
	private Kit ourKit;
	
	public KitProtocol(){
		bits2Shift=4;
		ourKit=new Kit();
	}
	/**
	 * Cria a sequencia de inicio do Protocolo
	 */
	private  void initProtocol() {
		ourKit.setBits(RxC_MASK |RxD_MASK);
		fullDelay();
	}

	/**
	 * Cria sequencia de Start.
	 */
	private  void setStart() {
		ourKit.invertBits(RxD_MASK);
		delay();
	}
	/**
	 * Verifica se o Kit está pronto a envia a sequencia de Dados.
	 * @return
	 */
	private  boolean isReady() {
		return ourKit.readBit(RxRDY_MASK);
	}

	/**
	 * Faz reset à sequencia de dados.
	 */
	private  void resetProtocol() {
		ourKit.setBits(RxD_MASK);
	}

	/**
	 * Temporizador que pára o acesso ao Kit.
	 */
	private  void delay() {
		Time.sleep(HALFCLOCK_MASK);
	}
	/**
	 * Temporizador que pára o acesso ao Kit.
	 */
	private  void fullDelay() {
		Time.sleep(CLOCK_MASK);
	}
	/**
	 * Envia a sequencia de dados para ser mostrado no LCD. 
	 * @param rs
	 * @param value
	 */
	public  void sendBits(int rs, int value) {
		initProtocol();
		if (isReady()) {
			setStart();
			sendBit(rs);
			
			for (int count=bits2Shift;count!=0;count-=1){
				sendBit(value);
				value = value >> SHIFT_BITS_MASK;
			}

		}
	}

	/**
	 * Envia os dados, segundo o protocolo, pelo Output port.
	 * @param value
	 */
	private  void sendBit(int value) {
		ourKit.invertBits(RxC_MASK);
		ourKit.write(value,RxD_MASK);
		delay();
		ourKit.setBits(RxC_MASK);
		delay();
	}

	/**
	 * @param args
	 */
	public  void main(String[] args) {

	}

}
