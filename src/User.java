
public class User {
	private String userName;
	private int userId;
	private int userPin;
	private String userMessage;
	private boolean hasMessage;
	
	public User(){
		this("", 0, 0,"");
	}
	
	public User(String n, int i, int p, String m){
		setUserId(i);
		setUserName(n);
		setUserPin(p);
		setUserMessage(m);
	}
	public void setUserName(String n){
		userName=n;
	}
	public void setUserId(int i){
		userId=i;
	}
	public void setUserPin(int p){
		userPin=p;
	}
	public void setUserMessage(String m){
		userMessage=m;
		hasMessage=true;
	}

	public String getUserName(){
		return  userName;
	}
	public int getUserId(){
		return userId;
	}
	public int getUserPin(){
		return userPin;
	}
	public String getUserMessage(){
		return userMessage;
	}
	@Override
	public String toString() {
		return getUserId()+" - "+getUserName()+" - "+getUserPin();
	}
}
