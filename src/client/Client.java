package client;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Client {
	public static void main(String[] args) throws SocketException {
	DatagramSocket dc = new DatagramSocket();
	ClientReceive cr = new ClientReceive(dc);
	cr.start();
	ClientSend cs = new ClientSend(dc);
	cs.start();
	}
}