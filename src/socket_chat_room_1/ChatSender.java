package socket_chat_room_1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatSender implements Runnable {
	/**
	 * set up all basic fields
	 */
	private ChatChannel channel;//a channel for thread
	private Socket socket;//socket for the thread
	private BlockingQueue<String> linesInWait = new LinkedBlockingQueue<>();//we use blocking queue to store all message to send
	/**
	 * this variable is assigned a volatile type in order to avoid conflicts when multiple threads 
	 * calling to alter it (when closing thread, we need to assign done as true).
	 */
	private volatile boolean done = false;//boolean variable to keep the loop running

	public ChatSender(ChatChannel channel, Socket socket) {
		this.channel = channel;
		this.socket = socket;
	}

	@Override
	public void run() {
		try(
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);//get user's input
		) {
			String message;//assign a new string variable 
			while(!done && (message = linesInWait.take()) != null){//if linesInWait is not empty
				out.println(message);//send the message to the writer
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
