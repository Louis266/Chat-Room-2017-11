package socket_chat_room_1.client;

import java.io.IOException;
import java.net.Socket;

public class ClientChannel {

	private ClientForm form;
	private Socket socket;

	private ClientReceiver receiver;
	private ClientSender sender;

	public ClientChannel(ClientForm clientForm) {
		this.form = clientForm;
	}

	public void connect(String hostName, int portNumber) {
		try {
			socket = new Socket(hostName, portNumber);
		} catch (IOException e) {
		    form.showMessage("Failed to connect " + hostName + ":" + portNumber
                    + ".\n" + e);
		}

		sender = new ClientSender(this, socket);
		Thread senderThread = new Thread(sender);
		senderThread.start();

		receiver = new ClientReceiver(this, socket);
		Thread receiverThread = new Thread(receiver);
		receiverThread.start();
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
