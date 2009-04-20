import isel.leic.utils.Time;

/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */

public class LCD {
	/*
	 * Os pinos do LCD devem ficar ligados da seguinte forma:
	 * Pinos 1, 3 e 5 – GND (alimentação, R/W e controlo de brilho)
	 * Pino 2 – Vcc (alimentação)
	 * Pinos 4 e 6 – bits D6 e D7 do output port do Kit (CS e Enable)
	 * Pinos 11..14 – bits D0..D3 do output port do Kit (Dados)
	 * 
	 * O programa de teste deve começar por escrever “ISEL” centrado na primeira linha do LCD 
	 * e “LIC 2009” centrado na segunda.
	 * Por cada transição ascendente detectada em I0 do input port é trocado o texto entre as 
	 * duas linhas. Quando a entrada I7 do input port for colocada a GND, o programa pára 
	 * escrevendo na primeira linha “transitions=” alinhado à esquerda seguido do número de 
	 * transições detectadas em I0. Na segunda linha aparecerá “Bye” alinhado à esquerda e o 
	 * cursor na forma de bloco a piscar deve ficar a seguir ao “Bye”.
	 * --------------------------------------------------------------------------------------
	 * RS 			-> Input -> (DnC)
	 * 				0 - Instruction register (for write) Busy flag:address counter (for read)
	 * 				1 - Data register (for write and read)
	 * R/!W			-> Input ->
	 * 				Selects read or write.
	 * 				0: Write
	 * 				1: Read
	 * E			-> Input ->
	 * 				Starts data read/write
	 * DB4 to DB7	-> Input/Output ->
	 * 				Four high order bidirectional tristate data bus
	 * 				pins. Used for data transfer and receive between
	 * 				the MPU and the HD44780U. DB7 can be used
	 * 				as a busy flag.
	 * DB0 to DB3	-> Input/Output ->
	 * 				Four low order bidirectional tristate data bus pins.
	 * 				Used for data transfer and receive between the
	 * 				MPU and the HD44780U.
	 * 				These pins are not used during 4-bit operation.
	 * 				
	 *  				  
	 * --------------------------------------------------------------------------------------
	 * Busy Flag (BF)
	 * When the busy flag is 1, the HD44780U is in the internal operation mode, and the next 
	 * instruction will not be accepted. When RS = 0 and R/W = 1 (Table 1), the busy flag is
	 * output to DB7. The next instruction must be written after ensuring that the busy flag
	 * is 0.
	 * 
	 * Address Counter (AC)
	 * The address counter (AC) assigns addresses to both DDRAM and CGRAM. When an address of
	 * an instruction is written into the IR, the address information is sent from the IR to
	 * the AC. Selection of either DDRAM or CGRAM is also determined concurrently by the 
	 * instruction.
	 * After writing into (reading from) DDRAM or CGRAM, the AC is automatically incremented
	 * by 1 (decremented by 1). The AC contents are then output to DB0 to DB6 when RS = 0 and
	 * R/W = 1 (Table 1).
	 * 
	 * Register Selection
	 * RS				R/W 			Operation
	 * 0				0				IR write as an internal operation (display clear, etc.)
	 * 0				1				Read busy flag (DB7) and address counter (DB0 to DB6)
	 * 1				0				DR write as an internal operation (DR to DDRAM or CGRAM)
	 * 1				1				DR read as an internal operation (DDRAM or CGRAM to DR)
	 * 
	 */
	
	
	/* ****************** Mascaras ****************** */
	private static final int RS_MASK			=	0x40;	//PIN 4
	private static final int ENABLE_MASK		=	0x80;	//PIN 6
	private static final int DATA_MASK			=	0x0F;	//PIN 11-14, DB4-7
	private static final int NIBBLE_SHIFT_MASK	=	4;		//N. Bits a serem deslocados
	/* ****************** Mascaras Controlo****************** */
	private static final int CLEAR_MASK			=	0x01;		//Clear do Display
	private static final int RETURN_HOME_MASK	=	0x02;	//Set Position 0 on AC(address Counter)
	private static final int CURSOR_ON_MASK  	=	0x0A;	//Cursor Activo
	private static final int CURSOR_OFF_MASK	=	0x08;	//Cursor InActivo
	private static final int DISPLAY_ON_MASK  	=	0x0D;	//Display Activo
	private static final int DISPLAY_OFF_MASK	=	0x08;	//Display InActivo

	private static final int BLINK_ON_MASK		=	0x09;	//Blink Activo
	private static final int BLINK_OFF_MASK		=	0x08;	//Blink InActivo
	private static final int SHIFT_MASK			=	0x04;	//Move Cursor e Shifta Conteudo (2 nibbles)
	/* ****************** Tempo de Resposta ****************** */
	private static final int MAXTIMERISE		=	350; 	// in miliseconds
	private static final int MAXTIMEFALL		=	400; 	// in miliseconds
	
	/* ****************** Controlo ****************** */
	private static boolean isCentered 		=	false;
	private static boolean isVisible 		=	false;
	private static boolean isBlinking 		= 	false;
	private static int 	cursorPosition		=	0;
	/* ********************************************** */
	private static int instruction=0x00;
	
	/* Realiza a sequencia de inicialização para comunicação a 4 bits */
	public static void init() {
	}

	/* Apaga todos os acaracteres do display */
	public static void clear() {
	}

	/* Posiciona o cursor na linha (0..1) e coluna (0..15) indicadas */
	public static void posCursor(int line, int col) {
		cursorPosition=0x40*line+col;
	}

	/* Acerta o tipo de cursor: Visivel ou invisivel; A piscar ou constante 
	 * 
	 * Cursor/Blink Control Circuit
	 * The cursor/blink control circuit generates the cursor or character blinking.
	 * The cursor or the blinking will
	 * appear with the digit located at the display data RAM (DDRAM) address set in the
	 * address counter (AC).
	 * 
	 * */
	public static void setCursor(boolean visible, boolean blinking) {
		isVisible=true;
		isBlinking=true;
		instruction=(visible?CURSOR_ON_MASK:CURSOR_OFF_MASK)|(blinking?BLINK_ON_MASK:BLINK_OFF_MASK);
	}

	/*
	 * Escreve o caracter indicado no local do cursor e o cursor avança para a
	 * proxima coluna
	 */
	public static void write(char c) {
		
	}

	/*
	 * Escreve o texto indicado no local do cursor e o cursor avança para a
	 * coluna seguinte
	 */
	public static void write(String txt) {
		for (int i=0;i<txt.length();i++){
			write(txt.charAt(i));
		}
	}

	/*
	 * Indica se o texto escrito com writeLine() nas chamadas seguintes deve ou
	 * não ficar centrado na linha
	 */
	public static void setCenter(boolean value) {
		isCentered = value;
	}

	/*
	 * Escreve o texto indicado na linha indicada (0 ou 1). O resto da linha é
	 * 	 texto fica centrado ou alinhado à esquerda,
	 * dependendo da última chamada a SetCenter()
	 */
	public static void writeLine(int line, String txt) {
		//clear();
		int pos=(isCentered)?(16-txt.length())/2:0;
		posCursor(line, pos);
		write(txt);
	}
	
	private static void instrRegister(){
//		RS=0;
//		RW=0;
	}
	private static void addrCounter(){
//		RS=0;
//		RW=1;
	}
	private static void dataRegister(){
//		RS=1;
//		RW=1;
	}
	
	public static void main(String args[]){
		Time.sleep(20);
		Kit.write(0x83);
		Kit.write(0x03);
		Time.sleep(10);
		Kit.write(0x83);
		Kit.write(0x03);
		Time.sleep(10);
		Kit.write(0x83);
		Kit.write(0x03);
		
		Kit.write(0x82);
		Kit.write(0x02);	
		Time.sleep(10);
		Kit.write(0x82);
		Kit.write(0x02);
		Kit.write(0x88);
		Kit.write(0x08);
		
		Time.sleep(10);
		Kit.write(0x80);
		Kit.write(0x00);
		Kit.write(0x88);
		Kit.write(0x08);
		
		Time.sleep(10);
		Kit.write(0x80);
		Kit.write(0x00);
		Kit.write(0x81);
		Kit.write(0x01);
		
		Time.sleep(10);
		Kit.write(0x80);
		Kit.write(0x00);
		Kit.write(0x87);
		Kit.write(0x07);
		
		Kit.write(ENABLE_MASK|0x00);
		Kit.write(0x00);
		Kit.write(ENABLE_MASK|0x0E);
		Kit.write(0x0E);
		Kit.write(ENABLE_MASK|0x00);
		Kit.write(0x00);
		Kit.write(ENABLE_MASK|0x06);
		Kit.write(0x06);
		
		
		Kit.write(ENABLE_MASK|RS_MASK|('A'>>SHIFT_MASK));
		Kit.write(RS_MASK|('A'>>SHIFT_MASK));
		Kit.write(ENABLE_MASK|RS_MASK|('A'&0x0F));
		//Kit.resetBits(ENABLE_MASK);
		Kit.write(RS_MASK|('A'&0x0F));
		
		
	}

}
