package modele;

import java.util.ArrayList;
import java.util.List;

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
	
	private List<Quartier> quartiers;
	
	public Blason(String phrase) {
		this.phrase = phrase;
		this.quartiers = new ArrayList<Quartier>();
	}
	
	public void AddQuartier() {
		this.quartiers.add(new Quartier());
	}
	
	public List<Quartier> GetQuartiers() {
		return this.quartiers;
	}
	
	public Quartier GetQuartierCourant() {
		return this.quartiers.get(this.quartiers.size()-1);
	}

}
