package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientSend extends Thread  {
	
	DatagramSocket dc;
	
	public ClientSend(DatagramSocket dc) {
		this.dc = dc;
		
	}
	
	public void run() {
		
		try {
			System.out.println("tapez votre pseudo :");
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String s= in.readLine();
			while (!s.startsWith("##")) {
				System.out.println("connectez-vous!");
				 s= in.readLine();
				}
			DatagramPacket dp = new DatagramPacket(s.getBytes(),s.getBytes().length,InetAddress.getByName("127.0.0.1"),9876);
			
			
		    dc.send(dp);
		    
			while(true) {
			
				s= in.readLine();
		    dp = new DatagramPacket(s.getBytes(),s.getBytes().length,InetAddress.getByName("127.0.0.1"),9876);
		
			
		    dc.send(dp);
		   
			}
	
}
	catch(IOException e) {
			e.printStackTrace();
		}
	}
	

	
}	








//dp = new DatagramPacket(s.getBytes(),s.getBytes().length,InetAddress.getByName("172.20.10.6"),9876);


//DatagramPacket dp = new DatagramPacket(s.getBytes(),s.getBytes().length,InetAddress.getByName("172.20.10.6"),9876);