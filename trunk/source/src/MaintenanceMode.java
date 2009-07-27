/*
 * 
 * 31401 - Nuno Cancelo 
 * 31900 - José Guilherme
 * 33595 - Nuno Sousa
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MaintenanceMode {
	private AccessDb db;
	private String adminPassword;
	private boolean au = false;

	/**
	 * Construtor
	 * @param ourDB
	 */
	public MaintenanceMode(AccessDb ourDB) {
		db = ourDB;
		getPassword();
	}
	/**
	 * Atribui Password
	 * @param pw
	 */
	private void setPassword(String pw) {
		adminPassword = pw;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					LicConstants.configFile)));
			bw.write(adminPassword + ";");
			bw.close();
		} catch (IOException e) {
			showMessage(LicConstants.configFile + " file with I/O problems.");
		}
	}
	/**
	 * Retorna Password
	 */
	private void getPassword() {
		try {
			BufferedReader bR = new BufferedReader(new FileReader(new File(
					LicConstants.configFile)));
			String line;
			String pw;
			if ((line = bR.readLine()) != null) {
				Scanner lineField = new Scanner(line).useDelimiter(";");
				pw = lineField.next();
				adminPassword = pw;
			}
			bR.close();
		} catch (FileNotFoundException e) {
			showMessage(LicConstants.configFile + " not found.");
		} catch (IOException e) {
			showMessage(LicConstants.configFile + " file with I/O problems.");
		}

	}
	/**
	 * Altera Password
	 */
	private void changePwd() {
		clearScreen();

		showMessage("Maintenace Mode");
		showMessage("Main Menu");
		showMessage("Change Password");

		showMessage("Insert New Password: ");
		char[] newPwd = System.console().readPassword();
		showMessage("Confirm New Password: ");
		char[] confirmNewPwd = System.console().readPassword();
		if (verify(newPwd, confirmNewPwd)) {
			String pw = "";
			for (int i = 0; i < newPwd.length; i++) {
				pw += newPwd[i];
			}
			setPassword(pw);
			showMessage("Password changed successfully");
			return;
		}
		showMessage("Password missmatch!");
	}
	/**
	 * Verifica se está bloqueado
	 * @return
	 */
	public boolean isLocked() {
		return Kit.readBit(LicConstants.KEY_LOCK_MASK);
	}
	/**
	 * Verifica Password
	 * @param pw
	 * @return
	 */
	private boolean verify(char[] pw) {
		if (adminPassword.length() != pw.length)
			return false;
		for (int i = 0; i < adminPassword.length(); i++) {
			if (pw[i] != adminPassword.charAt(i))
				return false;
		}
		return true;
	}
	/**
	 * Compara Passwords
	 * @param pw1
	 * @param pw2
	 * @return
	 */
	private boolean verify(char[] pw1, char[] pw2) {
		if (pw1.length != pw2.length)
			return false;
		for (int i = 0; i < pw1.length; i++) {
			if (pw1[i] != pw2[i])
				return false;
		}
		return true;
	}
	/**
	 * Verifica se está autorizado
	 * @return
	 */
	private boolean auth() {
		if (!au) {
			clearScreen();
			showMessage("Enter password:");
			char[] p = System.console().readPassword();
			if (verify(p)) {
				return au = true;
			} else {
				return au = false;
			}
		}
		return true;
	}
	/**
	 * Mode de Manutenção
	 */
	private void maintenaceMainMenu() {

		clearScreen();
		showMessage("Maintenace Mode");
		showMessage("Main Menu:");
		showMessage("[1] - Add New User");
		showMessage("[2] - Remove User");
		showMessage("[3] - Associate Message to User");
		showMessage("[4] - Search User");
		showMessage("[5] - List Users");
		showMessage("[6] - Exit");
		showMessage("Enter your option (1-6):");

		Scanner option = new Scanner(System.in);

		try {
			switch (option.nextInt()) {
			case 1:
				addUserMenu();
				break;
			case 2:
				removeUserMenu();
				break;
			case 3:
				addMessageUser();
				break;
			case 4:
				searchUserMenu();
				break;
			case 5:
				listUsersMenu();
				break;
			case 6:
				mainMenu();
				break;
			default:
				mainMenu();
				break;
			}
		} catch (Exception e) {
			maintenaceMainMenu();
		}

	}

	/**
	 * Pede userId
	 * @param m
	 * @return
	 */
	private int askUserId(String m){
		showMessage(m);
		return new Scanner(System.in).nextInt();
	}
	/**
	 * Pede username
	 * @param m
	 * @return
	 */
	private String askUserName(String m){
		showMessage(m);
		return new Scanner(System.in).nextLine();
	}
	/**
	 * Mostra Mensagem
	 * @param m
	 */
	private void showMessage(String m){
		System.out.println(m);
	}
	/**
	 * Pede Pin
	 * @param m
	 * @return
	 */
	private int askUserPin(String m){
		showMessage(m);
		return new Scanner(System.in).nextInt();
	}
	/**
	 * Pede Mensagem
	 * @param m
	 * @return
	 */
	private String askUserMessage(String m ){
		showMessage(m);
		return new Scanner(System.in).nextLine();
	}
	/**
	 * Pede Confirmação
	 * @param s
	 * @return
	 */
	private String confirmation(String s){
		showMessage(s);
		String option;
		do{
			option=new Scanner(System.in).next();
			
		}while (!(option.equalsIgnoreCase("yes") || option.equalsIgnoreCase("y")||option.equalsIgnoreCase("no") || option.equalsIgnoreCase("n"))); 
		return option;
	}
	/**
	 * Verifica opção
	 * @param o
	 * @return
	 */
	private boolean option(String o){
		return (o.equals("yes") || o.equals("y"));
	}
	/**
	 * Adiciona user
	 */
	public void addUserMenu() {
		clearScreen();

		showMessage("Maintenace Mode");
		showMessage("Main Menu");
		showMessage("[1] - Add New User");

		int userId = askUserId("User Id: ");
		String userName = askUserName("User Name: ");
		int userPin = askUserPin("User Pin: ");
		String userMsg = askUserMessage("Associate User Message:");
		User user = new User(userName, userId, userPin, userMsg);
		user.toString();
		String option = confirmation("Confirm data? (Yes,No)");

		if (option(option)){
				if (db.addUser(user))
					showMessage("User added successfully!");
				else
					showMessage("UserId already exists or DataBase is full!");
		}else{				
			showMessage("Insertion not Confirmed!");
				
		}
		option = confirmation("Add Another User? (Yes,No)");
		if(option(option)){
			addUserMenu();
		}else{
			maintenaceMainMenu();
		}
	}
	/**
	 * remove utilizador
	 */
	public void removeUserMenu() {
		clearScreen();
		showMessage("Maintenace Mode");
		showMessage("Main Menu");
		showMessage("[2] - Remove User");

		int userId = askUserId("User Id: ");

		showMessage(db.verifyUser(userId).toString());
		String option = confirmation("Confirm data? (Yes,No)");

		if (option(option)){
			if (db.removeUser(db.verifyUser(userId))) {
				showMessage("User removed!");
			} else {
				showMessage("Error Ocorred!");
			}
		} else {
			showMessage("Remove Operation cancelled!");
		}

		option = confirmation("Remove Another User? (Yes,No)");

		if (option(option))
			removeUserMenu();
		 else 
			maintenaceMainMenu();
		
	}
	/**
	 * Adiciona mensagem
	 */
	public void addMessageUser() {
		clearScreen();
		showMessage("Maintenace Mode");
		showMessage("Main Menu");
		showMessage("[3] - Associate Message to User");
		int userId = askUserId("User Id: ");

		showMessage(db.verifyUser(userId).toString());
		String userMsg = askUserMessage("Associate User Message:");

		String option = confirmation("Confirm data? (Yes,No)");

		if (option(option)){
			db.verifyUser(userId).setUserMessage(userMsg);
			showMessage("Message added!");
		} else{
			showMessage("Operation cancelled!");
		}

		option = confirmation("Add Message to Another User? (Yes,No)");

		if (option(option))
			addMessageUser();
		else
			maintenaceMainMenu();

	}
	/**
	 * Procura utilizador
	 */
	public void searchUserMenu() {
		clearScreen();
		showMessage("Maintenace Mode");
		showMessage("Main Menu");
		showMessage("[4] - Search User");
		int userId = askUserId("User Id: ");
		User u;
		if ((u = db.verifyUser(userId)) != null)
			showMessage(u.toString());
		else
			showMessage("User not Found!");

		String option = confirmation("Search Another User? (Yes,No)");
		if (option(option))
			searchUserMenu();
		else
			maintenaceMainMenu();
	}
	/**
	 * Lista Utilizadores
	 */
	public void listUsersMenu() {
		clearScreen();
		showMessage("Maintenace Mode");
		showMessage("Main Menu");
		showMessage("[5] - List Users");
		db.list();
		showMessage("Enter 'C' to continue...");
		Scanner listUser = new Scanner(System.in);
		listUser.next();
		maintenaceMainMenu();
	}
	/**
	 * Limpa o ecrâ
	 */
	public void clearScreen() {
		for (int i = 0; i < 50; i++) {
			showMessage("");
		}
	}
	/**
	 * Sai do modo de manutenção
	 */
	public void exit() {
		clearScreen();
		db.save();
		showMessage("Exit!");

	}
	/**
	 * Menu Principal
	 */
	public void mainMenu() {
		clearScreen();
		/*
		 * Comentado porque:
		 * metodos utilizados não funcionam no Eclipse.
		 * na linha de comandos dá erro com a classe Time.
		 * */
//		if (!au) {
//			if (!auth())
//				mainMenu();
//		}
		Scanner newOption = new Scanner(System.in);
		showMessage("Main Menu");
		showMessage("[1] - Change Password (Disabled)");
		showMessage("[2] - Maintenace Mode");
		showMessage("[3] - Exit");
		showMessage("Option: ");
		int option = newOption.nextInt();

		switch (option) {
		case 1:
//			changePwd();
//			mainMenu();
			break;
		case 2:
			maintenaceMainMenu();
			break;
		case 3:
			exit();
			break;
		default:
			mainMenu();
			break;
		}

	}

	public static void main(String[] args) {
		AccessDb a = new AccessDb();
		MaintenanceMode m = new MaintenanceMode(a);
		m.mainMenu();
	}

}
