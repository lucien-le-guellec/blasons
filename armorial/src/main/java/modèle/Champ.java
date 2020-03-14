package mod�le;

import java.util.ArrayList;
import java.util.List;

import structures.Couleur;

/**
 * 	Classe contenant les informations et �l�ments d'un champ (arri�re-plan) 
 * 
 * 	Attributs:
 * 	- Couleurs : couleur(s) de repr�sentation
 *  - Partitionnement : divisions du champ en sous parties le cas �ch�ant
 *  - Composition : composition du champ en de multiples sous �l�ments r�guliers le cas �ch�ant
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
