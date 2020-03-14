package mod�le;

/**
 * 	Classe repr�sentant un quartier, une partie d'un blason
 * 
 * 	Composition:
 * 	- Un Champ
 * 	- Une Charge si pr�cis� par "le tout"
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
