package modèle;

import java.util.ArrayList;
import java.util.List;

import structures.Couleur;

/**
 * 	Classe contenant les informations et éléments d'un champ (arrière-plan) 
 * 
 * 	Attributs:
 * 	- Couleurs : couleur(s) de représentation
 *  - Partitionnement : divisions du champ en sous parties le cas échéant
 *  - Composition : composition du champ en de multiples sous éléments réguliers le cas échéant
 *  
 * 	Composition:
 * 	- Une ou plusieurs Charges
 *  
 */
public class Champ {

	private Couleur couleur;
	
	private List<Charge> charges;
	
	public Champ() {
		this.charges = new ArrayList<Charge>();
	}
	
	public void SetCouleur(Couleur nouvCoul) {
		this.couleur = nouvCoul;
	}
	
	public Couleur GetCouleur() {
		return this.couleur;
	}
	
	public List<Charge> GetCharges() {
		return this.charges;
	}
	
}
