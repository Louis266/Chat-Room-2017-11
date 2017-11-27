package socket_chat_room_1;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

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
		try (Scanner in = new Scanner(socket.getInputStream(), "UTF-8");) {
			while (!done && in.hasNextLine()) {
				String message = in.nextLine();
				channel.history.add(message);
				String[] parts = message.split(":", 2);
				switch (parts[0]) {
				case Commands.ECHO:
					channel.send(parts[1]);
					break;
				case Commands.BROADCAST:
					channel.getServer().broadcast(channel.getUserName() + parts[1]);
					break;
				case Commands.KICK:
					if (parts.length > 1 
							&& channel.getServer().channels.containsKey(parts[1].trim())) {
						channel.getServer().kick(parts[1], channel.getUserName());
					}
					break;
				case Commands.LIST:
					List<String> result = channel.getServer().getList();
					for (String s : result) {
						channel.send(s);
					}
					break;
				case Commands.STATS:
					List<String> history = channel.getServer().getHistory(parts[1].trim());
					for (String h : history) {
						channel.send("[STATS]"
								+ channel.getServer().channels.get(parts[1].trim()).getUserName()
								+ h);
					}
					break;
				case Commands.STOP:
					channel.getServer().broadcast(channel.getUserName() + "[LEFT]");
					channel.stop();
					break;
				default:
					channel.send("[ERROR] INVALIED COMMAND: " + message);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		channel.getServer().channels.remove(channel.getId());
	}

	public void stop() {
		this.done = true;
	}

}
