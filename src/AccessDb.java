import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AccessDb implements KitConstants{
	private static final int DEFAULT_SIZE=1000;
	private static final String filePath="D:/ISEL/Projectos/Lic/lic-sv-08-09/bin/users.txt";
	private static int nbrUsers;
	private static User[] users;
	
	
	public AccessDb(){
		this(DEFAULT_SIZE);
	}
	
	public AccessDb(int maxSize){
		nbrUsers=0;
		users=new User[maxSize];
		open();
	}
	public int getDbSize(){
		return nbrUsers;
	}
	
	private void open(){
		try {
			File file=new File(filePath);
			FileReader fr=new FileReader(file);
			BufferedReader bf=new BufferedReader(fr);
			String line;
			String name;
			int pin;
			int nbr;
			String msg;
			while ((line=bf.readLine()) != null){
				Scanner lineField=new Scanner(line).useDelimiter(";");
				nbr=lineField.nextInt();
				name=lineField.next();
				pin=lineField.nextInt();
				msg=lineField.next();
				addUser(new User(name,nbr,pin,msg));
			}
			fr.close();
			bf.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("DataBase file Not Found. Iniciate new DataBase.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("DataBase file with I/O Problems. Iniciate new DataBase.");
		}finally{
			
		}
	}

	
	public void close(){
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter(new File(filePath)));
			for (int i=0;i<nbrUsers;i++){
				bw.write(users[i].exportUser()+"\n");
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public User find(int id){
		for (int idx=0;idx<nbrUsers;idx++){
			if (users[idx].getUserId() == id)
				return users[idx];
		}
		return null;
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
			users[pos]=users[nbrUsers-1];
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
	public User verifyUser(int id){
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
		User user=verifyUser(id);
		if (user != null)
			return user.getUserName();
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
			if (find(idx).getUserPin() == pin)
				return true;
			return false;		
	}
	/**
	 * 
	 * Lista os utilizadores existentes na tabela.
	 */
	public void list(){
		for (int i=0;i<nbrUsers;i++){
			System.out.println(users[i].toString());
		}
	}
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AccessDb a=new AccessDb();
		a.list();
		a.close();

	}

}
