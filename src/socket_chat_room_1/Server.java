package socket_chat_room_1;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable {
	/**
	 * this array list is used to store all client's info
	 */
	private boolean done = false;
	Map<String, ChatChannel> channels = new HashMap<>();

	private long counter;

	// ArrayList clientWriter;
	/**
	 * this inner class is designed for the thread to handle different clients
	 * using different sockets
	 * 
	 * @author Louis
	 *
	 */

	/**
	 * this constructor will read in the input stream from the socket
	 * 
	 * @param clientSocket
	 *            for the reader
	 */

	/**
	 * this function is the main logic for the chat server
	 */

	public void start() {
		this.run();
		System.out.println("Server started on port 9999!");
	}// close start()


	/**
	 * this function will send all connected clients message it received
	 */
	public void broadcast(String message) {
		for (ChatChannel channel : channels.values()) {
			channel.send(message);
		}
	}

	public List<String> getHistory(String id) {
		return channels.get(id).history;
	}

	/**
	 * this function will list all connected clients' ID
	 */
	public List<String> getList() {
		List<String> result = new ArrayList<>();
		for (ChatChannel channel : channels.values()) {
			result.add(channel.getUserName());
		}
		return result;
	}

	/**
	 * this function will disconnect the client with the provided id
	 */
	public void kick(String id, String kickerName) {
		ChatChannel channel = channels.get(id);
		broadcast(channel.getUserName() + "[KICKED OUT BY]" + kickerName );
		channel.stop();
	}

	public static void main(String[] arg) {
		new Server().start();
	}

	@Override
	public void run() {
		try (ServerSocket server = new ServerSocket(9999);) {
			while (!done) {
				Socket clientSocket = server.accept();
				String id = String.valueOf(++counter);
				ChatChannel channel = new ChatChannel(id, this, clientSocket);
				this.channels.put(id, channel);
				channel.start();
			}
			for (ChatChannel cc : this.channels.values()) {
				cc.stop();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}// close run

	public void stop() {

	}
}// close server
