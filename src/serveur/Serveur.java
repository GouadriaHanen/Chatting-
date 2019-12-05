package serveur;


import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import java.util.ArrayList;
import java.util.List;

import model.Historique;
import model.Salon;
import model.Utilisateur;

public class Serveur {
    static List<Utilisateur> listUtilisateur = new ArrayList<>();
	static List<Historique> listHistorique = new ArrayList<>();
	static List<Salon> listSalon = new ArrayList<>();
	
   public Serveur() {
		
	}


public static void main (String [] args) throws IOException {
	DatagramSocket dc = new DatagramSocket(9876);
	    DatagramPacket p;
		while(true) {
			byte[] data = new byte[1024];
		    String feedback = "";
		    String forward = "";
		    Historique h = new Historique();
		    Utilisateur u=new Utilisateur();
		    Salon s = new Salon();
		    int exists = 0; 
			DatagramPacket dp = new DatagramPacket(data,data.length);
			
			dc.receive(dp);
			
			String message = new String(dp.getData(),0,dp.getLength());
			if(message.startsWith("##")) {
				
				for(int i=0; i<listUtilisateur.size(); i++) {
					System.out.println(listUtilisateur.get(i).getPseudo()+message.substring(2,message.length()));
					if(listUtilisateur.get(i).getPseudo().equals(message.substring(2))) {
						exists = 1;
					}
				}
				if(exists==0) {
					 u = new Utilisateur(message.substring(2),dp.getPort(),dp.getAddress(),"connecté");
					listUtilisateur.add(u);
					feedback="bienvenue";
					System.out.println(u);
				}
				else {
					feedback = "Ce pseudo existe déjà!";
				}
				p = new DatagramPacket(feedback.getBytes(),feedback.getBytes().length,dp.getAddress(),dp.getPort());
				dc.send(p);
				
			}
			else if(message.startsWith("#LIST")) {
				for (int i=0 ; i<listUtilisateur.size();i++) {
					if(listUtilisateur.get(i).getStatus().equals("connecté") && (listUtilisateur.get(i).getPort()!=(dp.getPort()))) {
				feedback = feedback +" " +listUtilisateur.get(i).getPseudo();
				System.out.println(dp.getPort());
				}
			}
				p = new DatagramPacket(feedback.getBytes(),feedback.getBytes().length,dp.getAddress(),dp.getPort());
				dc.send(p);
				
		}
			else if(message.startsWith("#STATUS")) {
				String[] chaines = message.split("#");
				
				for (int i=0 ; i<listUtilisateur.size();i++) {
					if(listUtilisateur.get(i).getPort()==(dp.getPort())){
						listUtilisateur.get(i).setStatus(chaines[2]);
						feedback=listUtilisateur.get(i).getStatus();
						System.out.println("nouveau status : "+feedback);
						p = new DatagramPacket(feedback.getBytes(),feedback.getBytes().length,dp.getAddress(),dp.getPort());
						dc.send(p);
						break;
					}
				
				}
				
			}
			else if(message.startsWith("#HISTO")) {
				System.out.println("in");
				for (int j=0 ; j<listUtilisateur.size();j++) {
					
					
					if(listUtilisateur.get(j).getPort()==dp.getPort()) {
						u = listUtilisateur.get(j);
					}
					
					}
				for (int i=0 ; i<listHistorique.size();i++) {
						if(u.getPseudo().equals(listHistorique.get(i).getPreceiver())|| u.getPseudo().equals(listHistorique.get(i).getPsender())){
							String sender=listHistorique.get(i).getPsender();
							String receiver = listHistorique.get(i).getPreceiver();
							String msg = listHistorique.get(i).getMessages();
							h.setPsender(sender);
							h.setPreceiver(receiver);
							h.setMessages(msg);
							feedback = feedback+ "Sender : "+h.getPsender()+" Receiver : "+h.getPreceiver()+" message : "+h.getMessages();
						System.out.println(h);
						System.out.println(u);
						System.out.println(feedback);
						}
					}
				if(feedback.isEmpty()) {
					feedback="vous n'avez pas d'historique";
				}
					p = new DatagramPacket(feedback.getBytes(),feedback.getBytes().length,dp.getAddress(),dp.getPort());
					dc.send(p);
                    
						
					

				
			}
			else if(message.startsWith("#*")) {
				for (int i=0 ; i<listUtilisateur.size();i++) {
					if(listUtilisateur.get(i).getPort()==(dp.getPort())){
						listUtilisateur.get(i).setPseudo(message.substring(2)) ;
						feedback="votre nouveau pseudo est : "+listUtilisateur.get(i).getPseudo();
						break;
						
					}
				}
				p = new DatagramPacket(feedback.getBytes(),feedback.getBytes().length,dp.getAddress(),dp.getPort());
				dc.send(p);
			}
			else if(message.startsWith("@#")) {
				String[] chaines = message.split("@#");
				for (int i=0 ; i<listUtilisateur.size();i++) {
					if(listUtilisateur.get(i).getPort()==dp.getPort()) {
						h.setPsender(listUtilisateur.get(i).getPseudo());
						h.setPreceiver(chaines[1]);
						h.setMessages(chaines[2]);
						break;
					}
				}
				for (int i=0 ; i<listUtilisateur.size();i++) {
					if(listUtilisateur.get(i).getPseudo().equals(chaines[1])) {
						u=listUtilisateur.get(i);
						forward=h.getPreceiver()+" Vous avez un message de "+h.getPsender()+" : " +h.getMessages();
						p = new DatagramPacket(forward.getBytes(),forward.getBytes().length,u.getAddress(),u.getPort());
						dc.send(p);
						listHistorique.add(h);
						System.out.println(h);
						break;
						}
					
				
				}
				
					
				
			}
			else if(message.startsWith("#SALONS")) {
				for (int i=0 ; i<listSalon.size();i++) {
				feedback = feedback +" " +listSalon.get(i).getTitre();
				System.out.println(dp.getPort());
				}
				p = new DatagramPacket(feedback.getBytes(),feedback.getBytes().length,dp.getAddress(),dp.getPort());
				dc.send(p);
			}
			else if(message.startsWith("#SALON")){
				String[] chaines = message.split("#");
				for(int i =0; i<listSalon.size();i++) {
					if(listSalon.get(i).getTitre().equals(chaines[2])) {
						feedback = "le nom de salon que vous avez choisit existe déjà";
						break;
					}
					
					
				}
				if(feedback.isEmpty()) {
					s.setTitre(chaines[2]);	
					listSalon.add(s);
					feedback="salon ajouté avec succès";
				}
				
				p = new DatagramPacket(feedback.getBytes(),feedback.getBytes().length,dp.getAddress(),dp.getPort());
				dc.send(p);
			}
			else if(message.startsWith("#>")) {
				for(int i =0; i<listSalon.size();i++) {
					if(listSalon.get(i).getTitre().equals(message.substring(2,message.length()))) {
						s=listSalon.get(i);
						exists=1;
						break;
					}
					
				}
				for (int i=0 ; i<listUtilisateur.size();i++) {
					if(listUtilisateur.get(i).getPort()==dp.getPort() && exists==1) {
					
						u=listUtilisateur.get(i);
						s.getSalonUtilisateur().add(u);
						feedback ="Vous faites partie du Salon "+message.substring(2,message.length());
						break;
					}
					
				}
				if(feedback.isEmpty()) {
					
						feedback="ce salon n'existe pas";
				}
				p = new DatagramPacket(feedback.getBytes(),feedback.getBytes().length,dp.getAddress(),dp.getPort());
				dc.send(p);
			}
			else if(message.startsWith("#USERS")) {
				String [] chaines = message.split("#");
				for(int i =0; i<listSalon.size();i++) {
					if(listSalon.get(i).getTitre().equals(chaines[2])){
						s=listSalon.get(i);
						feedback="existe";
						break;
					}
					
				}
				if(!feedback.isEmpty()) {
					if(!s.getSalonUtilisateur().isEmpty()) {
						feedback="";
				for (int i=0;i<s.getSalonUtilisateur().size();i++) {
					feedback=feedback+s.getSalonUtilisateur().get(i).getPseudo();
				}
					}
					else {
						feedback="ce salon est vide";
					}
				
				}
				p = new DatagramPacket(feedback.getBytes(),feedback.getBytes().length,dp.getAddress(),dp.getPort());
				dc.send(p);
			}
			else if(message.startsWith("@>")) {
				String [] chaines = message.split("@>");
				for (int i=0;i<listSalon.size();i++) {
					if(listSalon.get(i).getTitre().equals(chaines[1])){
						s=listSalon.get(i);
						feedback="okay";
						break;
					}
					
				}
				if(feedback.isEmpty()) {
					
						feedback="ce salon n'existe pas";
						
				}
				else {
					feedback="";
				for (int i=0 ; i<listUtilisateur.size();i++) {
					if(listUtilisateur.get(i).getPort()==dp.getPort()) {
						u=listUtilisateur.get(i);
						
					}
				}
				for (int i=0;i<s.getSalonUtilisateur().size();i++) {
					if(u.getPort()!=s.getSalonUtilisateur().get(i).getPort())
					feedback =  "Sender : "+u.getPseudo()+" Receiver : "+s.getSalonUtilisateur().get(i).getPseudo()+" message : "+chaines[2];
					p = new DatagramPacket(feedback.getBytes(),feedback.getBytes().length,s.getSalonUtilisateur().get(i).getAddress(),s.getSalonUtilisateur().get(i).getPort());
					dc.send(p);
					
				}
				}
			}
			else {
				feedback="Vérifiez votre syntax!";
				p = new DatagramPacket(feedback.getBytes(),feedback.getBytes().length,dp.getAddress(),dp.getPort());
				dc.send(p);
			}
			}
	
	}
}


