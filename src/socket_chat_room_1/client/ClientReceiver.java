package socket_chat_room_1.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientReceiver implements Runnable {

	private ClientChannel channel;
	private Socket socket;

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
				channel.getForm().receive(message);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		this.done = true;
	}

}
