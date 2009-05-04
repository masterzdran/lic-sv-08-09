import isel.leic.utils.Time;

public class KitProtocol {
	private static final int CLOCK_MASK = 1000;
	private static final int HALFCLOCK_MASK = CLOCK_MASK / 2;
	private static final int RxRDY_MASK = 0x08;
	private static final int RxC_MASK = 0x02;
	private static final int RxD_MASK = 0x01;
	private static final int SHIFT_BITS_MASK = 1;
	private static final int VALUE_MASK = 0x0F;
	private static int	bits2Shift=5;
	private static int count;
	private static boolean ready=true;

	/*
	 * Inicia o protocolo
	 */
	private static void initProtocol() {
		System.out.print("Inicio do Protocolo\n");
		Kit.setBits(RxC_MASK |RxD_MASK);
		count=bits2Shift;
		fullDelay();
		System.out.print("--------------------------------\n");
	}

	private static void setStart() {
		System.out.print("Inicio do Start\n");
		Kit.setBits(RxC_MASK);
		Kit.invertBits(RxD_MASK);
		delay();
		System.out.print("++++++++++++++++++++++++++++++++\n");
	}

	private static boolean isReady() {
		//return Kit.readBit(RxRDY_MASK);
		return ready;
	}

	private static void resetProtocol() {
		Kit.resetBits(RxC_MASK | RxD_MASK);
	}

	private static void delay() {
		Time.sleep(HALFCLOCK_MASK);
	}

	private static void fullDelay() {
		Time.sleep(CLOCK_MASK);
	}

	public static void sendBits(int rs, int value) {
		value = (VALUE_MASK & value);
		boolean rsSent = false;
		System.out.print("Inicio da Comunicação\n");
		initProtocol();
		while (isReady()) {
			setStart();
			ready=false;
			while (!isReady() && (count!= 0)) {
				if (!rsSent) {
					System.out.print("RS"+(rs&RxD_MASK)+"\n");
					sendBit(rs);
					rsSent = true;
					count-=1;
				}
				System.out.print("Dados"+(value&RxD_MASK)+"\n");
				sendBit(value);
				value = value >> SHIFT_BITS_MASK;
				count-=1;
			}
			initProtocol();
		}
		ready=true;
		System.out.print("Fim da Comunicação\n");
	}

	public static void sendBit(int value) {
		Kit.write(value,RxD_MASK);
		Kit.setBits(RxC_MASK);
		delay();
		Kit.invertBits(RxC_MASK);
		delay();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a=2;
		while(a>0){
			System.out.print("Sending Bits\n");
			sendBits(0x01, 0x05);
			a--;
		}
	}

}
