package socket_chat_room_1;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable {
	/**
	 * we choose to use a map to store all channel objects;
	 * also we use a counter to store id of channels
	 * @author caiyihua
	 */
	private boolean done = false;
	Map<String, ChatChannel> channels = new HashMap<>();
	private long counter;
	
	/**
	 * start server
	 */
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
	public void start() {
		this.run();
		System.out.println("Server started on port 9999!");
	}

<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
	/**
	 * this function will send all connected clients message it received
	 */
	public void broadcast(String message) {
		for (ChatChannel channel : channels.values()) {
			channel.send(message);
		}
	}
	/**
	 * this function will get all commands sent by the client identified by @param id
	 * it will return a list
	 */
	public List<String> getHistory(String id) {
		return channels.get(id).history;
	}

	/**
	 * this function will list all connected clients' id stored in a list
	 */
	public List<String> getList() {
		List<String> result = new ArrayList<>();
		for (ChatChannel channel : channels.values()) {
			result.add(channel.getUserName());
		}
		return result;
	}

	/**
	 * this function will disconnect the client with the provided id and broadcast a string stating the situation
	 */
	public void kick(String id, String kickerName) {
		ChatChannel channel = channels.get(id);
		broadcast(channel.getUserName() + "[KICKED OUT BY]" + kickerName );
		channel.stop();
	}

	/**
	 * main
	 */
	public static void main(String[] arg) {
		new Server().start();
	}

	/**
	 * this is the run method of a server thread, including main logic for a server
	 */
	@Override
	public void run() {
		try (ServerSocket server = new ServerSocket(9999);) {//listen to port 9999 of the localhost
			while (!done) {
				Socket clientSocket = server.accept();//accept a connection
				String id = String.valueOf(++counter);//set up its id
				ChatChannel channel = new ChatChannel(id, this, clientSocket);
				//create a new instance of channel to store the socket and handle information transition
				
				this.channels.put(id, channel);//add new channel to the map using id as key
				channel.start();//start this new channel logic
			}
			//when server closes, close all channels' connection in a map
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
