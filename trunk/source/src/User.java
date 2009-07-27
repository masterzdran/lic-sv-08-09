/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */
public class User {
	private String userName;
	private int userId;
	private int userPin;
	private String userMessage;
	/**
	 * Construtor
	 * @param n
	 * @param i
	 * @param p
	 * @param m
	 */
	public User(String n, int i, int p, String m) {
		setUserId(i);
		setUserName(n);
		setUserPin(p);
		setUserMessage(m);
	}
	/**
	 * Construtor
	 * @param n
	 * @param i
	 * @param p
	 */
	public User(String n, int i, int p) {
		setUserId(i);
		setUserName(n);
		setUserPin(p);
		setUserMessage("");
	}
	/**
	 * Atribui username
	 * @param n
	 */
	public void setUserName(String n) {
		userName = n;
	}
	/**
	 * Atribui userid
	 * @param i
	 */
	public void setUserId(int i) {
		userId = i;
	}
	/**
	 * Atribui userpin
	 * @param p
	 */
	public void setUserPin(int p) {
		userPin = p;
	}
	/**
	 * Atribui Mensagem
	 * @param m
	 */
	public void setUserMessage(String m) {
			userMessage = m;
	}
	/**
	 * Remove mensagem
	 */
	public void removeUserMessage() {
		setUserMessage(" ");
	}
	/**
	 * Verifica se tem mensagem
	 * @return
	 */
	public boolean hasMessage() {
		return (!userMessage.trim().equals(""));
	}
	/**
	 * Retorna username
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * Retorna userid
	 * @return
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * Retorna userpin
	 * @return
	 */
	public int getUserPin() {
		return userPin;
	}
	/**
	 * Retorna message
	 * @return
	 */
	public String getUserMessage() {
		return userMessage;
	}
	/**
	 * Exporta user
	 * @return
	 */
	public String exportUser() {
		return getUserId() + ";" + getUserName() + ";" + getUserPin() + ";"
				+ getUserMessage() + " \n";
	}

	@Override
	/**
	 * Mostra os dados do utilizador
	 */
	public String toString() {
		String output = "+---------------------------------------------------+\n";
		output += "+User Name    - " + getUserName() + "\n";
		output += "+User Id      - " + getUserId() + "\n";
		output += "+User PIN     - " + getUserPin() + "\n";
		output += "+User Message - " + getUserMessage() + "\n";
		output += "+---------------------------------------------------+\n";
		return output;
	}
}
