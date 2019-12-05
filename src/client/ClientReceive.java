package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientReceive extends Thread{
	DatagramSocket dc;

	public ClientReceive(DatagramSocket dc) {
		this.dc = dc;
	}

	public void setDc(DatagramSocket dc) {
		this.dc = dc;
	}
	
	public void run() {
		while (true) {
			byte[] data = new byte [1024];
			DatagramPacket dp = new DatagramPacket(data,data.length);
		try {
		
			dc.receive(dp);
			String message = new String (dp.getData(),0,dp.getLength());
			System.out.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		
	}
	
}
