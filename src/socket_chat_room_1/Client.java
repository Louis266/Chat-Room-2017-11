package socket_chat_room_1;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Client {
	/**
	 * set basic variables that get involved in GUI and socket communication
	 */
	JTextArea incoming;
	JTextField outgoing;
	JScrollPane scroller;
	BufferedReader reader;
	PrintWriter writer;
	Socket socket;
	
	/**
	 * this function is to set up GUI and begin the output thread
	 */
	private void start(){
		JFrame frame = new JFrame("Chat Client");
		JPanel panel = new JPanel();
		
		incoming = new JTextArea(15, 50);
		incoming.setEditable(false);
		incoming.setWrapStyleWord(true);
		incoming.setLineWrap(true);
		//set the scroller for the showing panel of the incoming text from server
		scroller = new JScrollPane(incoming);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//set up the text field for client to type text
		outgoing = new JTextField(20);
		//set up buttons
		JButton broadcastButton = new JButton("Broadcast");
		JButton stopButton = new JButton("Stop");
		JButton listButton = new JButton("List");
		JButton kickButton = new JButton("Kick");
		JButton statsButton = new JButton("Stats");
		broadcastButton.addActionListener(new BroadcastButtonListener() );
		stopButton.addActionListener(new StopButtonListener());
		listButton.addActionListener(new ListButtonListener());
		kickButton.addActionListener(new KickButtonListener());
		statsButton.addActionListener(new StatsButtonListener());
		 
		panel.add(scroller);
		panel.add(outgoing);
		panel.add(broadcastButton);
		panel.add(stopButton);
		panel.add(listButton);
		panel.add(kickButton);
		panel.add(statsButton);
		
		//set up the thread for client to keep reading text into the GUI
		Thread readerThread = new Thread(new Reader());
		readerThread.start();
		
		
		//set up the frame
		frame.getContentPane().add(BorderLayout.CENTER, panel);
		frame.setSize(400, 500);
		frame.setVisible(true);
		
	}//close start()
	
	/**
	 * this function set up socket, and get input string
	 */
	private void setUpNetwork(){
		try {
			socket = new Socket("127.0.0.1", 8888);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter( socket.getOutputStream());
			System.out.println("network setup succeed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//close setUpNetworking()
	
	
	/**
	 * following five buttons handle five different functions for the chat room
	 * 
	 * main idea for realizing these functions is to add number"1" or "2" or "3" or "4" or "5" at the start of every string we sent
	 * representing  different functions we intent to get, so the server will know what we want
	 * 
	 * 1st broadcast(): sent every client connected the information next
	 */
	public class BroadcastButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try{
				writer.println("1"+outgoing.getText());
				writer.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
			outgoing.setText("");
			outgoing.requestFocus();
		}
		
	}//close broadcast()
	/**
	 * 2nd stop(): to stop the connection between the server and the client whose id we sent next
	 */
	public class StopButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try{
				writer.println("2"+outgoing.getText());
				writer.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
			outgoing.setText("");
			outgoing.requestFocus();
			
		}
		
	}//close Stop()
	
	/**
	 * 3rd list(): to list all clients that are connected
	 */
	public class ListButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try{
				writer.println("3");
				writer.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
			outgoing.setText("");
			outgoing.requestFocus();
			
		}
		
	}//close list()
	
	/**
	 *4th kick(): to disconnect the client that has the id we input after
	 */
	public class KickButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			try{
				writer.println("4"+outgoing.getText());
				writer.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
			outgoing.setText("");
			outgoing.requestFocus();
		}
		
	}//close kick()
	
	/**
	 *5th stats(): to list all commands the client have ever inputed that has the id we input after
	 */
	public class StatsButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			try{
				writer.println("5"+outgoing.getText());
				writer.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
			outgoing.setText("");
			outgoing.requestFocus();
		}
		
	}//close stats()
	
	class Reader implements Runnable{

		@Override
		public void run() {
			String message = null;
			
			try{
				
				while((message = reader.readLine()) != null){
					incoming.append(message);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
			
		}//close run()
		
	}//close reader
	
	
	public static void main(String[] args){
		Client client = new Client();
		client.start();
		
	}//close main
}//close the class
