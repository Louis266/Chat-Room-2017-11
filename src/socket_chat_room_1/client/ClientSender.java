package socket_chat_room_1.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientSender implements Runnable {
	/**
	 * set up all basic fields
	 */
	private ClientChannel channel;
	private Socket socket;
	private BlockingQueue<String> linesInWait = new LinkedBlockingQueue<>();//we use blocking queue to store all message to send
	/**
	 * this variable is assigned a volatile type in order to avoid conflicts when multiple threads 
	 * calling to alter it (when closing thread, we need to assign done as true).
	 */
	private volatile boolean done = false;
	/**constructor
	 * @param channel
	 * @param socket
	 */
	public ClientSender(ClientChannel channel, Socket socket) {
		this.channel = channel;
		this.socket = socket;
	}

	@Override
	public void run() {
		try(
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		) {
			String message;
			while(!done && (message = linesInWait.take()) != null){
				out.println(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * stop receiver thread by stopping the loop in the main logic
	 */
	public void stop() {
		this.done = true;//set thread's done as true
	}
	
	//add function: add the message to the queue
	public void send(String content){
		linesInWait.add(content);
	}
}
