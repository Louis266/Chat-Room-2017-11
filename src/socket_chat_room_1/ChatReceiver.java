package socket_chat_room_1;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ChatReceiver implements Runnable {

	/**
	 * set up all basic fields
	 * @author caiyihua
	 */
	private ChatChannel channel;//a channel for the thread
	private Socket socket;//a socket this thread is responsible of
	/**
	 * this variable is assigned a volatile type in order to avoid conflicts when multiple threads 
	 * calling to alter it (when closing thread, we need to assign done as true).
	 */
	private volatile boolean done = false;//set up the boolean variable 

	public ChatReceiver(ChatChannel channel, Socket socket) {
		this.channel = channel;
		this.socket = socket;
	}//constructor

	@Override
	public void run() {
		try (Scanner in = new Scanner(socket.getInputStream(), "UTF-8");) {//using scanner to read input stream
			while (!done && in.hasNextLine()) {//start loop when there's a new line to read
				String message = in.nextLine();//assign a new variable to store the message input
				channel.history.add(message);//add this message to the list of this thread's channel
				String[] parts = message.split(":", 2);//split the message in to at most two parts dividing by":" (take the first one if multiple appears)
				switch (parts[0]) {//switch the first part of the message (command)
				case Commands.ECHO://echo: print the message to the client
					channel.send(parts[1]);//when called upon, channel will send the part of message to the channel itself
					break;
				case Commands.BROADCAST://broadcast: send message to all clients
					channel.getServer().broadcast(channel.getUserName() + parts[1]);
					//find channel's server and call its broadcast function using channel's id and message as parameter
					break;
				case Commands.KICK://kick: disconnect client with the provided id
					if (parts.length > 1 
							&& channel.getServer().channels.containsKey(parts[1].trim())) {
						//make sure the command is valid
						channel.getServer().kick(parts[1], channel.getUserName());
						//call kick function 
					}
					break;
				case Commands.LIST://list: list all connected clients
					List<String> result = channel.getServer().getList();//assign a new list of the channel
					for (String s : result) {
						channel.send(s);//loop and send results to the client
					}
					break;
				case Commands.STATS://stats: give back all history commands
					List<String> history = channel.getServer().getHistory(parts[1].trim());
					for (String h : history) {
						channel.send(h);//loop through all values and send out all history message
					}
					break;
				case Commands.STOP://stop: disconnect the connection of this channel
					channel.getServer().broadcast(channel.getUserName() + "[LEFT]");//broadcast the info
					channel.stop();//call stop function
					break;
				default:
					channel.send("[ERROR] INVALIED COMMAND: " + message);//if command can not be resolved, than print the information
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//stop receiver thread by stopping the loop in the main logic
	public void stop() {
		this.done = true;//set thread's done as true
	}

}
