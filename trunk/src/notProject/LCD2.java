package notProject;

import isel.leic.utils.*;

public class LCD2  {
	//Data - pin 0 -3
	//CS -pin 6
	
	static final int CS = 0x40;
	static final int DATA= 0x0F;
	static final int ENABLE = 0x80;
	
	/* Realiza a sequencia de inicialização para comunicação a 4 bits */
	public static void init() { 
		comunication(0x3, false);
		Time.sleep(5);
		comunication(0x3, false);
		Time.sleep(1);
		comunication(0x3, false);
		comunication(0x2, false);
		SendBits(0x28, false);
		SendBits(0x08, false);
		clear();
		SendBits(0x06, false);
	}
	
	/* Apaga todos os acaracteres do display */
	public static void clear() {
		SendBits(0x01, false);}
	
	/* Posiciona o cursor na linha (0..1) e coluna (0..15) indicadas */
	public static void posCursor(int line, int col) {  
		
		SendBits(0x80+ line*0x40 + col, false);
		
	}

	/* Acerta o tipo de cursor: Visivel ou invisivel; A piscar ou constante */
	public static void setCursor(boolean visible, boolean blinking) { }

	/* Escreve o caracter indicado no local do cursor 
	 * e o cursor avança para a proxima coluna */
	public static void write(char c) { }

	/* Escreve o texto indicado no local do cursor 
	 * e o cursor avança para a coluna seguinte */
	public static void write(String txt) {  }

	/* Indica se o texto escrito com writeLine() nas chamadas seguintes deve ou não ficar centrado na linha */
	public static void setCenter(boolean value){  }
	
	/* Escreve o texto indicado na linha indicada (0 ou 1).
	 * O resto da linha é preenchida com espaços.
	 * O texto fica centrado ou alinhado à esquerda, dependendo da última chamada a SetCenter()
	 */
	public static void writeLine(int line, String txt) {  } 
	
	
	public static void SendBits(int trama, boolean rs){
		comunication(trama>>4, rs);
		comunication(trama, rs);
	}
	
	private static void comunication(int trama, boolean rs){
//		Kit.setBits(ENABLE);
//		Kit.write(trama |( rs?CS:0), DATA|CS);
//		Kit.invertBits(ENABLE);
		
	}
	
	public static void main(String[] args) {
		init();
	}

}