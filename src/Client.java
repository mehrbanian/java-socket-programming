import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	Socket clientSocket;
	Scanner inputReader = new Scanner(System.in); 
	String hostAddress;
	int port;
	
	BufferedReader reader;
	BufferedWriter writer;
	
	
	public Client(String host) {
		this.hostAddress = host;
	}
	
	public void closeConnection() {
		try {
			clientSocket.close();
			inputReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public void start(int _port) {
		this.port = _port;
		
		try {
			clientSocket = new Socket(hostAddress, port);
			System.out.println("Client connected!");

			OutputStream os = clientSocket.getOutputStream();
		    DataOutputStream outStream = new DataOutputStream(os);
			
			InputStream is = clientSocket.getInputStream();
		    DataInputStream inStream = new DataInputStream(is);
			
			String data = "";
			String response = "";
			
			while(!data.equals("bye")) {
				System.out.println("Enter your message (Type \"bye\" to quit): ");
				data = inputReader.nextLine();
				outStream.writeUTF(data);
				outStream.flush();
				System.out.println("Sending message to the server...");

				response = inStream.readUTF();
				System.out.println("Server response:\n" + response + "\n");
			}
			
			this.closeConnection();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Client client = new Client("localhost");
		client.start(1818);
	}

}
