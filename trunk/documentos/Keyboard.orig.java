
public class Keyboard  {
        // Mascaras das liga��es ao input port.
	private static final int KEY_VAL_MASK = 0x40;
	private static final int KEY_DAT_MASK = 0x0F;


	// Se uma tecla est� premida retorna a tecla: '0'..'9','A'..'F'
	// Retorna o valor 0 (zero) se nenhuma tecla est� premida.
	public static char pressedKey() { ... }

	// Se uma tecla foi premida retorna a tecla: '0'..'9','A'..'F'
	// Retorna o valor 0 (zero) se nenhuma tecla foi premida.
	// Retorna o valor 0 (zero) se a tecla premida j� foi retornada. 
	public static char getKey() { ... }
	
	// Se uma tecla foi premida retorna a tecla: '0'..'9','A'..'F'
	// Se nenhuma tecla foi premida espera durante o tempo indicado
	//  em milisegundos que seja premida uma tecla.
	// Retorna o valor 0 (zero) se n�o for premida uma tecla durante
	//  o tempo indicado.
	public static char waitKey(long timeout) { ... }
}
