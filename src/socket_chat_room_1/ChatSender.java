package socket_chat_room_1;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChatSender implements Runnable {
	private ChatChannel channel;
	private Socket socket;
	private Queue<String> linesInWait = new ConcurrentLinkedQueue();
	private volatile boolean done = false;

	public ChatSender(ChatChannel channel, Socket socket) {
		this.channel = channel;
		this.socket = socket;
	}

	@Override
	public void run() {
		try(PrintWriter out = new PrintWriter( new OutputStreamWriter(socket.getOutputStream()))){
			String message;
			while(!done && (message = linesInWait.poll()) != null){
				out.print(message);
			}
		} catch (IOException e) {
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
