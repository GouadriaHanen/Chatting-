package model;

import java.util.List;

public class Historique {
	
	String psender ,preceiver;
	String messages;
	public Historique(String psender, String preceiver, String messages) {
		this.psender = psender;
		this.preceiver = preceiver;
		this.messages = messages;
	}

	
	public Historique() {
		super();
	}
	public String getPsender() {
		return psender;
	}
	public void setPsender(String psender) {
		this.psender = psender;
	}
	public String getPreceiver() {
		return preceiver;
	}
	public void setPreceiver(String preceiver) {
		this.preceiver = preceiver;
	}
	public String getMessages() {
		return messages;
	}
	public void setMessages(String messages) {
		this.messages = messages;
	}
	public String toString() {
		return "Historique [psender=" + psender + ", preceiver=" + preceiver + ", messages=" + messages + "]";
	}
}
