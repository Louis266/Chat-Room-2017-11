package socket_chat_room_1;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable {
	/**
	 * this array list is used to store all client's info
	 */
	ArrayList<UserInfo> clients;
	private boolean done = false;
	private Map<String, ChatChannel> channels = new HashMap<>();

	private long counter;

	// ArrayList clientWriter;
	/**
	 * this inner class is designed for the thread to handle different clients
	 * using different sockets
	 * 
	 * @author Louis
	 *
	 */
	class clientHandler implements Runnable {
		BufferedReader reader;
		Socket socket;

		/**
		 * this constructor will read in the input stream from the socket
		 * 
		 * @param clientSocket
		 *            for the reader
		 */
		public clientHandler(Socket clientSocket) {
			try {
				socket = clientSocket;
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {

			String message;
			Character instruction;

			try {
				message = reader.readLine();
				while (message != null) {
					instruction = message.charAt(0);// read the first character
													// as the instruction code
					if (instruction.equals("1")) {
						broadcast(message.substring(0, message.length()));
					}
					if (instruction.equals("2")) {
						// stop()
					}
					if (instruction.equals("3")) {
						// list()
					}
					if (instruction.equals("4")) {
						// kick()
					}
					if (instruction.equals("5")) {
						// stats()
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}// close run

	}// close inner class

	/**
	 * this function is the main logic for the chat server
	 */

	public void start() {
		this.run();
	}// close start()


	/**
	 * this function will send all connected clients message it received
	 */
	public void broadcast(String message) {
		Iterator<UserInfo> it = clients.iterator();
		PrintWriter writer;
		int i = 0;
		while (it.hasNext()) {
			try {
				UserInfo user = clients.get(i);
				Iterator it2 = user.getMessage().iterator();
				while (it2.hasNext()) {
					writer = (PrintWriter) it2.next();
					writer.println(message);
					writer.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * this function will stop the connection between the server and the client
	 * with the ID given followed
	 */
	public void terminate() {

	}

	/**
	 * this function will list all connected clients' ID
	 */
	public void list() {

	}

	/**
	 * this function will disconnect the client with the provided id
	 */
	public void kick() {

	}

	public static void main(String[] arg) {
		new Server().start();
	}

	@Override
	public void run() {
		try (ServerSocket server = new ServerSocket(9999);) {
			while (!done) {
				Socket clientSocket = server.accept();
				String id = String.valueOf(counter++);
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
