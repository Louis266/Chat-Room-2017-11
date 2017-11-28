package socket_chat_room_1.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientReceiver implements Runnable {

	/**
	 * set up all basic fields
	 * @author caiyihua
	 */
	private ClientChannel channel;
	private Socket socket;

	/**
	 * this variable is assigned a volatile type in order to avoid conflicts when multiple threads 
	 * calling to alter it (when closing thread, we need to assign done as true).
	 */
	private volatile boolean done = false;

	public ClientReceiver(ClientChannel channel, Socket socket) {
		this.channel = channel;
		this.socket = socket;
	}

	@Override
	public void run() {
		try (Scanner in = new Scanner(socket.getInputStream(), "UTF-8");) {
			while (!done && in.hasNextLine()) {
				String message = in.nextLine();
				channel.getForm().receive(message);//handle messages in this channel's form, so it's easy to return result to the UI

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		this.done = true;
	}

}
