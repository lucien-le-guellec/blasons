package modele;

import java.util.ArrayList;
import java.util.List;

import structures.Partition;

/**
 * 	Classe englobante contenant les éléments d'un blason
 * 
 * 	Attributs:
 * 	- Ecu : forme générale du blason
 * 	- Partitionnement : le blason est-il composé d'un ou plusieurs quartiers ?
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
	
	public void AddQuartier() {
		this.quartiers.add(new Quartier());
	}
	
	public List<Quartier> GetQuartiers() {
		return this.quartiers;
	}
	
	public Quartier GetQuartierCourant() {
		return this.quartiers.get(ordreActuel-1);
	}
	
	public void SetOrdreActuel(int o) {
		this.ordreActuel = o;
	}

	public Partition GetPartitionnement() {
		return this.partitionnement;
	}
	
	public void SetPartitionnement(Partition p) {
		this.partitionnement = p;
	}
	
}
