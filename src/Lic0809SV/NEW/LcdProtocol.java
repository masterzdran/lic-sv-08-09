package Lic0809SV;
import isel.leic.utils.Time;
import Lic0809SV.LicConstants;

public class LcdProtocol{
	private int	bits2Shift;
	private Kit ourKit;
	
	public LcdProtocol(){
		bits2Shift=4;
		ourKit=new Kit();
	}
	/**
	 * Cria a sequencia de inicio do Protocolo
	 */
	private  void initProtocol() {
		ourKit.setBits(LicConstants.RxC_MASK |LicConstants.RxD_MASK);
		fullDelay();
	}

	/**
	 * Cria sequencia de Start.
	 */
	private  void setStart() {
		ourKit.invertBits(LicConstants.RxD_MASK);
		delay();
	}
	/**
	 * Verifica se o Kit está pronto a envia a sequencia de Dados.
	 * @return
	 */
	private  boolean isReady() {
		return ourKit.readBit(LicConstants.RxRDY_MASK);
	}

	/**
	 * Faz reset à sequencia de dados.
	 */
	private  void resetProtocol() {
		ourKit.setBits(LicConstants.RxD_MASK);
	}

	/**
	 * Temporizador que pára o acesso ao Kit.
	 */
	private  void delay() {
		Time.sleep(LicConstants.HALFCLOCK_MASK);
	}
	/**
	 * Temporizador que pára o acesso ao Kit.
	 */
	private  void fullDelay() {
		Time.sleep(LicConstants.CLOCK_MASK);
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
				value = value >> LicConstants.SHIFT_BITS_MASK;
			}

		}
	}

	/**
	 * Envia os dados, segundo o protocolo, pelo Output port.
	 * @param value
	 */
	private  void sendBit(int value) {
		ourKit.invertBits(LicConstants.RxC_MASK);
		ourKit.write(value,LicConstants.RxD_MASK);
		delay();
		ourKit.setBits(LicConstants.RxC_MASK);
		delay();
	}

	/**
	 * @param args
	 */
	public  void main(String[] args) {

	}

}
