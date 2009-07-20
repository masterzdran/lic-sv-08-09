

public class User {
	private String userName;
	private int userId;
	private int userPin;
	private String userMessage;
	private boolean hasMessage;

	public User(String n, int i, int p, String m) {
		setUserId(i);
		setUserName(n);
		setUserPin(p);
		setUserMessage(m);
	}

	public User(String n, int i, int p) {
		setUserId(i);
		setUserName(n);
		setUserPin(p);
		setUserMessage("");
	}

	public void setUserName(String n) {
		userName = n;
	}

	public void setUserId(int i) {
		userId = i;
	}

	public void setUserPin(int p) {
		userPin = p;
	}

	public void setUserMessage(String m) {
		userMessage = m;
		hasMessage = true;
	}

	public void removeUserMessage() {
		setUserMessage("");
		hasMessage = false;
	}

	public boolean hasMessage() {
		return hasMessage;
	}

	public String getUserName() {
		return userName;
	}

	public int getUserId() {
		return userId;
	}

	public int getUserPin() {
		return userPin;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public String exportUser() {
		return getUserId() + ";" + getUserName() + ";" + getUserPin() + ";"
				+ getUserMessage() + " \n";
	}

	@Override
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
