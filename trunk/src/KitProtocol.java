import isel.leic.utils.Time;

public class KitProtocol {
	private static final int CLOCK_MASK = 2000;
	private static final int HALFCLOCK_MASK = CLOCK_MASK / 2;
	private static final int RxRDY_MASK = 0x08;
	private static final int RxC_MASK = 0x02;
	private static final int RxD_MASK = 0x01;
	private static final int SHIFT_BITS_MASK = 1;
	private static final int VALUE_MASK = 0xF0;

	/*
	 * Inicia o protocolo
	 */
	private static void initProtocol() {
		Kit.setBits(RxC_MASK);
		delay();
		Kit.setBits(RxD_MASK);
		delay();
	}

	private static boolean isReady() {
		return Kit.readBit(RxRDY_MASK);
	}

	private static void setStart() {
		Kit.setBits(RxC_MASK);
		delay();
		Kit.invertBits(RxD_MASK);
		delay();
	}

	private static void resetProtocol() {
		Kit.setBits(0xff);
		// Kit.invertBits(0xff);
	}

	private static void delay() {
		Time.sleep(HALFCLOCK_MASK);
	}

	public static void sendBits(int rs, int value) {
		value=(0x0f&value)|0x80 ;
		boolean rsSent=false;
		int i=0;
		System.out.print("Inicio da Comunicação\n");
		
		while (isReady()) {
			delay();
			
			setStart();
			while (!isReady() && (value != 0x08)) {
				if(!rsSent){
					System.out.print("RS-"+(value & RxD_MASK) + "-\n");
					sendBit(rs& RxD_MASK);
					rsSent=true;
					}
				System.out.print("Dados-"+(value & RxD_MASK) + "-\n");
					sendBit(value & RxD_MASK);
					value = value >> 1;
					
			}

		}
		System.out.print("Fim da Comunicação\n");
	}

	public static void sendBit(int value) {
		Kit.setBits(RxC_MASK);
		delay();
		Kit.write(0xFF | value, RxD_MASK);
		delay();
		Kit.invertBits(RxC_MASK);
		delay();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		resetProtocol();
		initProtocol();
		sendBits(0x01, 0x05);
	}

}
