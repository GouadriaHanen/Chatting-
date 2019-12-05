package model;


import java.net.InetAddress;

public class Utilisateur {

String pseudo;
int port;
InetAddress address;
String Status;
public Utilisateur(String pseudo, int port, InetAddress address, String status) {
	this.pseudo = pseudo;
	this.port = port;
	this.address = address;
	Status = status;
}

public Utilisateur() {

}
public String getPseudo() {
	return pseudo;
}
public void setPseudo(String pseudo) {
	this.pseudo = pseudo;
}
public int getPort() {
	return port;
}
public void setPort(int port) {
	this.port = port;
}
public InetAddress getAddress() {
	return address;
}
public void setAddress(InetAddress address) {
	this.address = address;
}
public String getStatus() {
	return Status;
}
public void setStatus(String status) {
	Status = status;
}
@Override
public String toString() {
	return "Utilisateur [pseudo=" + pseudo + ", port=" + port + ", address=" + address + ", Status=" + Status + "]";
}


}
