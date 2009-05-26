/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */
public interface KitConstants {
	/*
	 * Constantes Referentes ao KitProtocol
	 * */
	public  final int CLOCK_MASK = 1;
	public  final int HALFCLOCK_MASK = CLOCK_MASK / 2;
	public  final int RxRDY_MASK = 0x80;					//To be read from input Port
	public  final int RxC_MASK = 0x02;					//To be written in Output Port
	public  final int RxD_MASK = 0x01;					//To be written in Output Port
	public  final int SHIFT_BITS_MASK = 1;
	public  final int KEY_VAL_MASK = 0x40;
	public  final int KEY_DAT_MASK = 0x0F;
	public  final char[] KEYS ={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	public  final long WAIT=200;

	/*
	 * Constantes Referentes ao LCD
	 * */
	/* ****************** Mascaras ****************** */
	public  final int RS_MASK			=	0x40;	    //PIN 4
	public  final int ENABLE_MASK		=	0x80;	    //PIN 8
	public  final int DATA_MASK			=	0x0F;	    //PIN 11-14, DB4-7
	public  final int NIBBLE_SHIFT_MASK	=	4;		    //N. Bits a serem deslocados
	/* ****************** Mascaras Controlo****************** */
	public  final int CLEAR_MASK			=	0x01;		//Clear do Display
	public  final int ENTRY_SET_MASK		=	0x06;		//Clear do Display
	public  final int RETURN_HOME_MASK	=	0x02;	//Set Position 0 on AC(address Counter)
	public  final int CURSOR_ON_MASK  	=	0x0A;	//Cursor Activo
	public  final int CURSOR_OFF_MASK	=	0x08;	//Cursor InActivo
	public  final int DISPLAY_ON_MASK  	=	0x0C;	//Display Activo
	public  final int DISPLAY_OFF_MASK	=	0x08;	//Display InActivo

	public  final int BLINK_ON_MASK		=	0x09;	//Blink Activo
	public  final int BLINK_OFF_MASK		=	0x08;	//Blink InActivo
	public  final int SHIFT_MASK			=	0x04;	//Move Cursor e Shifta Conteudo (2 nibbles)
	public  final int ADDR_COUNTER_MASK	=	0x80;	//Move Cursor para a Posição
	public  final int DISPLAY_SIZE_MASK	=	16;		//Tamanho do Display
	public  final int VALUE_MASK			=	0x0F;	//Mascara para os Valores a serem escritos
	
	/* ****************** Tempo de Resposta ****************** */
	public  final int MAXTIMERISE		=	350; 	// in miliseconds
	public  final int MAXTIMEFALL		=	400; 	// in miliseconds
	
	/*
	 * Constantes Referentes ao Kit
	 * */
	public final int SLEEP=1;
	public final int EIGHTBITS=0xFF;
	

}
