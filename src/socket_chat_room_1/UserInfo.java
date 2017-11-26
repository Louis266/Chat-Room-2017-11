package socket_chat_room_1;

import java.io.Writer;
import java.util.ArrayList;

public class UserInfo {
	private String ID;
	private ArrayList<Writer> message;
	
	public UserInfo(){
		
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public ArrayList<Writer> getMessage() {
		return message;
	}

	public void addMessage(Writer message) {
		this.message.add(message);
	}
	
}
