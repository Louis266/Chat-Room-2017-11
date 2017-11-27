package socket_chat_room_1;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatChannel {
	/**
	 * basic fields for a chat channel
	 * @author caiyihua
	 */
	private String id;//channel id
	private Server server;//channel's server 
	private Socket socket;//channel's socket
	List<String> history = new ArrayList<>();//a list that stores all previous commands

	private ChatReceiver receiver;//a receiver thread for this channel
	private ChatSender sender;//a sender thread for this channel

	public ChatChannel(String id, Server server, Socket socket) {
		super();
		this.id = id;
		this.server = server;
		this.socket = socket;
	}//constructor

	/**
	 * start a channel for a connection
	 */
	public void start() {
		sender = new ChatSender(this, socket);
		Thread senderThread = new Thread(sender);
		senderThread.start();
		//set up a sender thread
		receiver = new ChatReceiver(this, socket);
		Thread receiverThread = new Thread(receiver);
		receiverThread.start();
		//set up a receiver thread

		server.broadcast(getUserName() + "[ENTERED]");;
		//broadcast the login info for a connection
	}

	/**
	 * this function stops a connection by stopping its sender and receiver thread 
	 */
	public void stop() {
		sender.stop();
		receiver.stop();
	}

	/**
	 * this function sent a message using sender thread
	 * @param content
	 */
	public void send(String content) {
		sender.send(content);
	}

	/**
	 * this function returns a channel's server
	 * @return
	 */
	public Server getServer() {
		return server;
	}
	
	/**
	 * this function returns a channel's name, which is formed by its id
	 * @return
	 */
	public String getUserName() {
		return "[" + id + "]";
	}

}
