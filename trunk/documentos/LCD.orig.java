import isel.leic.utils.*;

public class LCD {
	
	/* Realiza a sequencia de inicialização para comunicação a 4 bits */
	public static void init() { ...	}
	
	/* Apaga todos os acaracteres do display */
	public static void clear() { ... }
	
	/* Posiciona o cursor na linha (0..1) e coluna (0..15) indicadas */
	public static void posCursor(int line, int col) { ... }

	/* Acerta o tipo de cursor: Visivel ou invisivel; A piscar ou constante */
	public static void setCursor(boolean visible, boolean blinking) { ... }

	/* Escreve o caracter indicado no local do cursor 
	 * e o cursor avança para a proxima coluna */
	public static void write(char c) { ... }

	/* Escreve o texto indicado no local do cursor 
	 * e o cursor avança para a coluna seguinte */
	public static void write(String txt) { ... }

	/* Indica se o texto escrito com writeLine() nas chamadas seguintes deve ou não ficar centrado na linha */
	public static void setCenter(boolean value){ ... }
	
	/* Escreve o texto indicado na linha indicada (0 ou 1).
	 * O resto da linha é preenchida com espaços.
	 * O texto fica centrado ou alinhado à esquerda, dependendo da última chamada a SetCenter()
	 */
	public static void writeLine(int line, String txt) { ... }

}
