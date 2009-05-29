import sun.security.pkcs11.Secmod.DbMode;


public class AccessDb implements KitConstants{
	private static final int DEFAULT_SIZE=1000;
	private static int nbrUsers;
	private static User[] users;
	
	public AccessDb(){
		this(DEFAULT_SIZE);
	}
	
	public AccessDb(int maxSize){
		nbrUsers=0;
		users=new User[maxSize];
	}
	public int getDbSize(){
		return nbrUsers;
	}
	/**
	 * 
	 * @param u
	 * @return
	 * Pesquisa na DB o utilizador, retorna o indicie do array onde o utiliza se encontra,
	 * ou -1 caso o utilizador solicitado n�o se encontre.
	 */
	public int find(User u){
		for (int idx=0;idx<nbrUsers;idx++){
			if (users[idx].getUserId() == u.getUserId())
				return idx;
		}
		return -1;
	}
	/**
	 * 
	 * @param id
	 * @return
	 * 
	 * Pesquisa na DB o 'id' passado por argumento, retorna o indicie do array onde est� o
	 * utilizador com aquele 'id' ou -1 caso n�o exista.
	 */
	public int find(int id){
		for (int idx=0;idx<nbrUsers;idx++){
			if (users[idx].getUserId() == id)
				return idx;
		}
		return -1;
	}
	/**
	 * 
	 * @param u
	 * @return
	 * 
	 * Adiciona no array um novo utilizador.
	 * Retorna 'true' caso tenha sido introduzido, retorna 'false' se j� houver o utilizador ou
	 * se o array j� estiver totalmente preenchido.
	 */
	public boolean addUser(User u){
		if ((find(u) == -1)&&(nbrUsers<users.length)){
			users[nbrUsers++]=u;
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param u
	 * @return
	 * 
	 * Remove do array um utilizador.
	 * Retorna 'true' caso o utilizador seja eliminado, retorna false se o utilizador n�o existir.
	 */
	public boolean removeUser(User u){
		int pos;
		if ((pos=find(u)) != -1){
			users[pos]=users[nbrUsers];
			users[nbrUsers--]=null;
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param u
	 * @param m
	 * @return
	 * 
	 * Insere uma mensagem no determindad 'User', retorna 'false' caso o 'User' n�o exista. 
	 */
	public boolean insertMessage(User u,String m){
		int pos;
		if ((pos=find(u)) != -1){
			users[pos].setUserMessage(m);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * Faz a valida��o de um 'User'. Retorna o indicie da posi��o onde se encontra, ou -1 caso n�o exista.
	 */
	public int verifyUser(int id){
		return find(id);
	}

	/**
	 * 
	 * @param idx
	 * @return
	 * 
	 * Retorna o Nome Completo do 'User', dado o indicie passado por argumento.
	 */
	public String getUserFullName(int id){
		int idx=verifyUser(id);
		if (idx != -1)
			return users[idx].getUserName();
		return ERROR_USER_MESSAGE;
	}
	
	/**
	 * 
	 * @param idx
	 * @param pin
	 * @return
	 * 
	 * Verifica o pin do Utilizador, retorna true se o pin coincide, falso caso n�o verifique.
	 */
	public boolean verifyPin(int idx,int pin){
			if (users[find(idx)].getUserPin() == pin)
				return true;
			return false;		
	}
	/**
	 * 
	 * Lista os utilizadores existentes na tabela.
	 */
	public void list(){
		for (int i=0;i<nbrUsers;i++){
			users[i].toString();
		}
	}
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
