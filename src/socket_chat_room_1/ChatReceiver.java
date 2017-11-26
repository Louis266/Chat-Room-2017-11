package socket_chat_room_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatReceiver implements Runnable {

	private ChatChannel channel;
	private Socket socket;

	private volatile boolean done = false;

	public ChatReceiver(ChatChannel channel, Socket socket) {
		this.channel = channel;
		this.socket = socket;
	}

	@Override
	public void run() {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			String message;
			while (!done && (message = in.readLine()) != null) {
				String[] parts = message.split(":", 1);
				switch (parts[0]) {
				case Commands.ECHO:
					channel.send(parts[1]);
					break;
				case Commands.BROADCAST:
					break;
				case Commands.KICK:
					break;
				case Commands.LIST:
					break;
				case Commands.STATS:
					break;
				case Commands.STOP:
					break;
				default:
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		this.done = true;
	}

}
