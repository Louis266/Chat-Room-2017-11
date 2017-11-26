package socket_chat_room_1;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatChannel {
	private String id;
	private Server server;
	private Socket socket;
	List<String> history = new ArrayList<>();

	private ChatReceiver receiver;
	private ChatSender sender;

	public ChatChannel(String id, Server server, Socket socket) {
		super();
		this.id = id;
		this.server = server;
		this.socket = socket;
	}

	public void start() {
		sender = new ChatSender(this, socket);
		Thread senderThread = new Thread(sender);
		senderThread.start();

		receiver = new ChatReceiver(this, socket);
		Thread receiverThread = new Thread(receiver);
		receiverThread.start();
		// (new Thread(new ChatReceiver(this, socket))).start();

		server.broadcast(getUserName() + "[ENTERED]");;
	}

	public void stop() {
		sender.stop();
		receiver.stop();
	}

	public void send(String content) {
		sender.send(content);
	}

	public Server getServer() {
		return server;
	}

	public String getUserName() {
		return "[" + id + "]";
	}

}
