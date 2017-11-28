package socket_chat_room_1.client;

import java.io.IOException;
import java.net.Socket;

public class ClientChannel {
	/**
	 * basic fields for a chat channel
	 * @author caiyihua
	 */
	private ClientForm form;//store its form
	private Socket socket;//store socket

	private ClientReceiver receiver;//store its receiver
	private ClientSender sender;//store its sender

	public ClientChannel(ClientForm clientForm) {
		this.form = clientForm;//constructor that give channel a form
	}

	/**
	 * try to connect to the host's port
	 * @param hostName
	 * @param portNumber
	 * @return
	 */
	public boolean connect(String hostName, int portNumber) {
		try {
			socket = new Socket(hostName, portNumber);//set up a new socket connection
		} catch (IOException e) {
		    form.showMessage("Failed to connect " + hostName + ":" + portNumber
                    + ".\n" + e);//if not able to set up a connection than show message "not able to set up a connection"
		    return false;
		}
		//start a sender thread
		sender = new ClientSender(this, socket);
		Thread senderThread = new Thread(sender);
		senderThread.start();//start a sender thread
		//start a receiver thread
		receiver = new ClientReceiver(this, socket);
		Thread receiverThread = new Thread(receiver);
		receiverThread.start();
		return true;
	}

	public void stop() {
		sender.stop();
		receiver.stop();
	}

	public void send(String content) {
		sender.send(content);
	}

	public ClientForm getForm() {
		return form;
	}

}
