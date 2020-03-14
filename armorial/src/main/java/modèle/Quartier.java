package modèle;

/**
 * 	Classe représentant un quartier, une partie d'un blason
 * 
 * 	Composition:
 * 	- Un Champ
 * 	- Une Charge si précisé par "le tout"
 */
public class Quartier {
	
	private Champ champ;
	
	public Quartier() {
		this.champ = new Champ();
	}
	
	public Champ GetChamp() {
		return this.champ;
	}
	
}
