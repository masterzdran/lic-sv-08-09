import isel.leic.utils.Time;

public class KitProtocol {
	private static final int CLOCK_MASK = 1;
	private static final int HALFCLOCK_MASK = CLOCK_MASK / 2;
	private static final int RxRDY_MASK = 0x01;					//To be read from input Port
	private static final int RxC_MASK = 0x02;					//To be written in Output Port
	private static final int RxD_MASK = 0x01;					//To be written in Output Port
	private static final int SHIFT_BITS_MASK = 1;
	private static final int VALUE_MASK = 0x0F;
	private static int	bits2Shift=5;
	private static int count;
	private static boolean rsSent;


	/*
	 * Inicia o protocolo
	 */
	private static void initProtocol() {
		//count=bits2Shift;
		Kit.setBits(RxC_MASK |RxD_MASK);
		fullDelay();
	}

	private static void setStart() {
		Kit.invertBits(RxD_MASK);
		rsSent=false;
		delay();
	}

	private static boolean isReady() {
		return Kit.readBit(RxRDY_MASK);
	}

	private static void resetProtocol() {
		Kit.setBits(RxD_MASK);
	}

	private static void delay() {
		Time.sleep(HALFCLOCK_MASK);
	}

	private static void fullDelay() {
		Time.sleep(CLOCK_MASK);
	}

	public static void sendBits(int rs, int value) {
	//	value = (VALUE_MASK & value);
		
		initProtocol();
		if (isReady()) {
			setStart();
			sendBit(rs);
			
//			for (count=bits2Shift;!isReady()&&count!=0;count-=1){
			for (count=bits2Shift-1;count!=0;count-=1){
//				if (!rsSent) {
//					sendBit(rs);
//					rsSent = true;
//					continue;
//				}
				sendBit(value);
				value = value >> SHIFT_BITS_MASK;
			}

		}
	}

	private static void sendBit(int value) {
		Kit.invertBits(RxC_MASK);
		Kit.write(value,RxD_MASK);
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
