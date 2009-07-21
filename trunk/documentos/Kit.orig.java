import isel.leic.usbio.UsbPort;
import isel.leic.utils.Time;

public class Kit {
        ...

	/* L� o input port para posterior consulta */
	public static void read() { ... }
	
	/* Consulta o valor representado pelos bits da mascara que foram previamente lidos com read()
	 * Retorna o valor correspondente dos bits com 0 para GND e 1 para Vcc
	 * Por exemplo retorna 0x0F se I0..I3 est�o a Vcc e I4..I7 est�o a GND */
	public static int getBits(int mask) { ... }
	
	/* Consulta o bit indicado pela mascara previamente lido com read()
	 * Retorna true se o bit indicado pela mascara est� a Vcc */ 
	public static boolean isBit(int mask) { ... }
	
	/* L� e consulta o valor representado pelos bits da mascara */
	public static int readBits(int mask) { read(); return getBits(mask); }
	
	/* L� e consulta o bit indicado pela mascara */
	public static boolean readBit(int mask) { read(); return isBit(mask); } 

	
	/* Coloca a Vcc os bits indicados pela mascara e os restantes bits mant�m o valor. */  
	public static void setBits(int mask) { ... }
	
	/* Coloca a GND os bits indicados pela mascara e os restantes bits mant�m o valor. */  
	public static void resetBits(int mask) { ... }
	
	/* Inverte o valor dos bits indicados pela mascara e os restantes bits mant�m o valor. */  
	public static void invertBits(int mask) { ... }
	
	/* Altera os valores dos bits indicados pela mascara para os bits correspondentes em �value� 1-Vcc 0-GND */
	public static void write(int values, int mask) { ... }
	
	public static void main(String[] args) {
		Kit.setBits(0xFF); 		// Coloca a Vcc todos os bits do output port   	->  ---- ----
		while( Kit.readBit(0x1) ); 	// Espera que o bit I0 fique a GND			   
		Kit.resetBits(0x0F);  		// Coloca a GND os 4 bits de menor peso		->  ---- ****
		while( ! Kit.readBit(0x1) ); 	// Espera que o bit I0 fique a Vcc
		Kit.invertBits(0xFF);  		// Inverte os valores dos bits do output port	->  **** ----
		for(int val=1;Kit.isBit(0x80);) {	// Enquanto entrada I7 a Vcc (ultima leitura)
			Kit.write(~val,0x0F);		// Escreve o valor nos 4 bits de menor peso
			Time.sleep(100);		// Espera 100 ms
			Kit.read(); 			// Le os bits do input port
			if (Kit.isBit(0x02)) {		// Se entrada I1 a Vcc (ultima leitura)
				if ((val<<=1) > 8) val=1;  	// Roda bits do valor para esquerda
			} else {
				if ((val>>=1) < 1) val=8;  	// Roda bits do valor para a direita
			}
		}
	}
}
