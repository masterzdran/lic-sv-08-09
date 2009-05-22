
public class Maintenance {
	private User[] userDb;
	private int userDbSize;
	
	public Maintenance(User[] u, int s){
		userDb=u;
		userDbSize=s;
	}
	public boolean addUser(User u){
		if ((find(u) == -1)&&(userDbSize<userDb.length)){
			userDb[userDbSize++]=u;
			return true;
		}
		return false;
	}
	public boolean removeUser(User u){
		int pos;
		if ((pos=find(u)) != -1){
			userDb[pos]=userDb[userDbSize];
			userDb[userDbSize--]=null;
			return true;
		}
		return false;
	}
	public boolean insertMessage(User u,String m){
		int pos;
		if ((pos=find(u)) != -1){
			userDb[pos].setUserMessage(m);
			return true;
		}
		return false;
	}
	public int find(User u){
		for (int idx=0;idx<userDbSize;idx++){
			if (userDb[idx].getUserId() == u.getUserId())
				return idx;
		}
		return -1;
	}
}
