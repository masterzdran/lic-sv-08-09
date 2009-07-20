package Lic0809SV;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;
import Lic0809SV.LicConstants;

public class AccessDb{
	private static final int DEFAULT_SIZE = 1000;
	private static int maxsize;

	private int nbrUsers;
	private LinkedList<User> users;

	public AccessDb() {
		this(DEFAULT_SIZE);
	}

	public AccessDb(int maxSize) {
		nbrUsers = 0;
		maxsize = maxSize;
		users = new LinkedList<User>();
		open();
	}

	public int getDbSize() {
		return nbrUsers;
	}
	public boolean isFull(){
		return (nbrUsers==maxsize);
	}
	public boolean isEmpty(){
		return (nbrUsers==0);
	}
	private void open() {
		try {
			File file = new File(LicConstants.filePath);
			//System.out.print(file.getAbsolutePath());
			FileReader fr = new FileReader(file);
			BufferedReader bf = new BufferedReader(fr);
			String line;
			String name;
			int pin;
			int nbr;
			String msg;
			while ((line = bf.readLine()) != null) {
				Scanner lineField = new Scanner(line).useDelimiter(";");
				nbr = lineField.nextInt();
				name = lineField.next();
				pin = lineField.nextInt();
				msg = lineField.next();
				addUser(new User(name, nbr, pin, msg));
			}
			fr.close();
			bf.close();

		} catch (FileNotFoundException e) {
			System.out
					.println("DataBase file Not Found. Iniciate new DataBase.\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out
					.println("DataBase file with I/O Problems. Iniciate new DataBase.");
		} finally {

		}
	}

	public void save() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(LicConstants.filePath)));
			ListIterator<User> l = users.listIterator();
			while (l.hasNext()) {
				bw.write(l.next().exportUser());
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
	 * @return Pesquisa na DB o utilizador, retorna o indicie do array onde o
	 *         utiliza se encontra, ou -1 caso o utilizador solicitado não se
	 *         encontre.
	 */
	private int find(User u) {
		int idx = 0;
		ListIterator<User> l = users.listIterator();
		User x;
		while (l.hasNext()) {
			x = l.next();
			if (x.getUserId() == u.getUserId()) {
				return idx;
			}
			idx++;
		}
		return -1;
	}

	/**
	 * 
	 * @param id
	 * @return Pesquisa na DB o 'id' passado por argumento, retorna o indicie do
	 *         array onde está o utilizador com aquele 'id' ou -1 caso não
	 *         exista.
	 */
	private User find(int id) {
		int idx = 0;
		User tmp;
		ListIterator<User> l = users.listIterator();
		while (l.hasNext()) {
			tmp = l.next();
			if (tmp.getUserId() == id) {
				return tmp;
			}
			idx++;
		}
		return null;
	}

	/**
	 * 
	 * @param u
	 * @return Adiciona no array um novo utilizador. Retorna 'true' caso tenha
	 *         sido introduzido, retorna 'false' se já houver o utilizador ou se
	 *         o array já estiver totalmente preenchido.
	 */
	public boolean addUser(User u) {
		if (nbrUsers < maxsize) {

			if (users.isEmpty()) {
				users.add(u);
				nbrUsers++;
				return true;
			}
			ListIterator<User> x = users.listIterator();
			User tmp;
			int idx = 0;
			while (x.hasNext()) {
				tmp = x.next();
				if (u.getUserId() == tmp.getUserId())
					return false;
				if (u.getUserId() < tmp.getUserId()) {
					users.add(idx, u);
					nbrUsers++;
					return true;
				}
				idx++;
			}
			users.add(idx, u);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param u
	 * @return Remove do array um utilizador. Retorna 'true' caso o utilizador
	 *         seja eliminado, retorna false se o utilizador não existir.
	 */
	public boolean removeUser(User u) {
		int pos;
		if ((pos = find(u)) != -1) {
			users.remove(pos);
			nbrUsers--;
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param id
	 * @return Faz a validação de um 'User'. Retorna o indicie da posição onde
	 *         se encontra, ou -1 caso não exista.
	 */
	public User verifyUser(int id) {
		return find(id);
	}

	/**
	 * 
	 * Lista os utilizadores existentes na tabela.
	 */
	public void list() {
		ListIterator<User> l = users.listIterator();
		while (l.hasNext()) {
			System.out.println(l.next().toString());
		}
	}

	public static void main(String[] args) {
	}
}