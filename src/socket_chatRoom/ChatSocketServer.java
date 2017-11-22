package socket_chatRoom;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ChatSocketServer {

		private ServerSocket serverSocket = null;
		private Socket socket = null;
		private InputStream inStream = null;
		private OutputStream outStream= null;
		
		public ChatSocketServer(){}
		
		public void createSocket(){
			try{
				@SuppressWarnings("resource")
				ServerSocket serverSocket = new ServerSocket(7777);
				
				while(true){
					socket = serverSocket.accept();
					inStream = socket.getInputStream();
					outStream = socket.getOutputStream();
					String key = socket.getInetAddress().getHostAddress()+":"+socket.getPort();
					System.out.println("Connected"+key);
					createReadThread();
					createWriteThread();
				}
			
			}catch(IOException io){
				io.printStackTrace();
			}
		}
		
		public void createReadThread() {
			Thread readThread = new Thread() {
				public void run() {
					while (socket.isConnected()) {
						try {
							byte[] readBuffer = new byte[200];
							int num = inStream.read(readBuffer);
							if (num > 0) {
								byte[] arrayBytes = new byte[num];
								System.arraycopy(readBuffer, 0, arrayBytes, 0, num);
								String recvedMessage = new String(arrayBytes, "UTF-8");
								System.out.println("Received message :" + recvedMessage);
							} else {
								notify();
							};
							//System.arraycopy();

							} catch (SocketException se) {
							System.exit(0);

						} catch (IOException i) {
							i.printStackTrace();
						}

					}
				}
			};
			readThread.setPriority(Thread.MAX_PRIORITY);
			readThread.start();
			}
		
		public void createWriteThread() {
			Thread writeThread = new Thread() {
				public void run() {

					while (socket.isConnected()) {
						try {
							BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
							sleep(100);
							String typedMessage = inputReader.readLine();
							if (typedMessage != null && typedMessage.length() > 0) {
								synchronized (socket) {
									outStream.write(typedMessage.getBytes("UTF-8"));
									sleep(100);
								}
							}/* else {
								notify();
							}*/
							;
							//System.arraycopy();

						} catch (IOException i) {
							i.printStackTrace();
						} catch (InterruptedException ie) {
							ie.printStackTrace();
						}

					}
			}
			};
			writeThread.setPriority(Thread.MAX_PRIORITY);
			writeThread.start();

			}

		public static void main(String[] args) {
			ChatSocketServer chatServer = new ChatSocketServer();
			chatServer.createSocket();
		}
		
}