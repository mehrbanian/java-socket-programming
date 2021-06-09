import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	int port;
	ServerSocket serverSocket;
	Socket incoming;
	
	
	private void sleep(int time) {
        try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			incoming.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start(int _port) {
		port = _port;

		try {
			serverSocket = new ServerSocket(port);
			incoming = serverSocket.accept();
			System.out.println("Client/Server connection stablished!");

			OutputStream os = incoming.getOutputStream();
		    DataOutputStream outStream = new DataOutputStream(os);
			
			InputStream is = incoming.getInputStream();
		    DataInputStream inStream = new DataInputStream(is);
			
			String clientMsg = "";

			while(!clientMsg.equals("bye")) {
				System.out.println("Waiting for client message...");
				clientMsg = inStream.readUTF();
				System.out.println("Message recieved: " + clientMsg);

				outStream.writeUTF("echo: " + clientMsg);
				outStream.flush();
				System.out.println("Response has been sent!\n");
				sleep(500);
			}
			
			System.out.println("Shutting down the Server...");
			this.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.start(1818);
	}

}
