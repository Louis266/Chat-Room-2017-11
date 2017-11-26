package socket_chat_room_1;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatSender implements Runnable {
	private ChatChannel channel;
	private Socket socket;
	private BlockingQueue<String> linesInWait = new LinkedBlockingQueue();
	private volatile boolean done = false;

	public ChatSender(ChatChannel channel, Socket socket) {
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

	public void stop() {
		this.done = true;
	}
	
	
	public void send(String content){
		linesInWait.add(content);
	}
}
