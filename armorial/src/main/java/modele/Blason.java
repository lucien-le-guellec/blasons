package modele;

import java.util.ArrayList;
import java.util.List;

import structures.Partition;

/**
 * 	Classe englobante contenant les éléments d'un blason
 * 
 * 	Attributs:
 * 	- Partitionnement : le blason est-il composé d'un ou plusieurs quartiers ?
 *  - OrdreActuel : 1 si l'arme est simple, valeur du quartier courant si l'arme est composée
 *  - Phrase : phrase de blasonnement décrivant ce blason
 * 
 * 	Composition:
 * 	- Un ou plusieurs Quartiers
 */
public class Blason {
	
	private String phrase;
	
	private Partition partitionnement;
	
	private List<Quartier> quartiers;
	
	private int ordreActuel;
	
	public Blason(String phrase) {
		this.phrase = phrase;
		this.quartiers = new ArrayList<Quartier>();
		this.partitionnement = null;
		this.ordreActuel = 1;
	}
	
	/**
	 * Ajoute un nouveau quartier vierge
	 */
	public void AddQuartier() {
		this.quartiers.add(new Quartier());
	}
	
	/**
	 * Getter des quartiers
	 * @return quartiers, la liste actuelle des quartiers du blason
	 */
	public List<Quartier> GetQuartiers() {
		return this.quartiers;
	}
	
	/**
	 * Getter du quartier courant en fonction de l'ordre actuel
	 * @return quartier, le quartier courant
	 */
	public Quartier GetQuartierCourant() {
		return this.quartiers.get(ordreActuel-1);
	}
	
	/**
	 * Setter de l'ordre actuel, permet de définir le quartier courant
	 * @param o, ordre actuel
	 */
	public void SetOrdreActuel(int o) {
		this.ordreActuel = o;
	}

	/**
	 * Getter du partitionnement
	 * @return partitionnement
	 */
	public Partition GetPartitionnement() {
		return this.partitionnement;
	}
	
	/**
	 * Setter du partitionnement
	 * @param p, partition à définir
	 */
	public void SetPartitionnement(Partition p) {
		this.partitionnement = p;
	}
	
}
