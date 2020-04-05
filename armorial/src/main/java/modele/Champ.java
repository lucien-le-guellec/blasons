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
 *  - Partition : divisions du champ en sous parties le cas échéant
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
	
	/**
	 * Setter des couleurs
	 * @param nouvCouls, nouvelles couleurs à utiliser
	 */
	public void SetCouleurs(List<Couleur> nouvCouls) {
		this.couleurs = nouvCouls;
	}
	
	/**
	 * Getter des couleurs
	 * @return couleurs
	 */
	public List<Couleur> GetCouleurs() {
		return this.couleurs;
	}
	
	/**
	 * Getter des charges
	 * @return charges
	 */
	public List<Charge> GetCharges() {
		return this.charges;
	}
	
	/**
	 * Setter de la partition
	 * @param part
	 */
	public void SetPartition(Partition part) {
		this.partition = part;
	}
	
	/**
	 * Getter de la partition
	 * @return partition
	 */
	public Partition GetPartition() {
		return this.partition;
	}
	
}
