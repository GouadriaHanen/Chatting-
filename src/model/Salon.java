package model;

import java.util.ArrayList;
import java.util.List;

public class Salon {
String titre;
List<Utilisateur> SalonUtilisateur= new ArrayList<>();
public Salon(String titre, List<Utilisateur> salonUtilisateur) {
	super();
	this.titre = titre;
	SalonUtilisateur = salonUtilisateur;
}
public Salon() {
	super();
}
public String getTitre() {
	return titre;
}
public void setTitre(String titre) {
	this.titre = titre;
}
public List<Utilisateur> getSalonUtilisateur() {
	return SalonUtilisateur;
}
public void setSalonUtilisateur(List<Utilisateur> salonUtilisateur) {
	SalonUtilisateur = salonUtilisateur;
}
@Override
public String toString() {
	return "Salon [titre=" + titre + ", SalonUtilisateur=" + SalonUtilisateur + "]";
}


}
