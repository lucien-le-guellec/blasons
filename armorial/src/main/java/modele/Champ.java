package modele;

import java.util.ArrayList;
import java.util.List;

import structures.Couleur;
import structures.Partition;

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

	private List<Couleur> couleurs;
	
	private List<Charge> charges;
	
	private Partition partition;
	
	public Champ() {
		this.charges = new ArrayList<Charge>();
		this.couleurs = new ArrayList<Couleur>();
		this.partition = null;
	}
	
	public void SetCouleurs(List<Couleur> nouvCouls) {
		this.couleurs = nouvCouls;
	}
	
	public List<Couleur> GetCouleurs() {
		return this.couleurs;
	}
	
	public List<Charge> GetCharges() {
		return this.charges;
	}
	
	public void SetPartition(Partition part) {
		this.partition = part;
	}
	
	public Partition GetPartition() {
		return this.partition;
	}
	
}
