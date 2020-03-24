package modele;

import java.util.ArrayList;
import java.util.List;

import structures.Couleur;
import structures.Partition;

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
