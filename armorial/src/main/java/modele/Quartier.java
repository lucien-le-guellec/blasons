package modele;

/**
 * 	Classe représentant un quartier, une partie d'un blason
 * 
 * 	Composition:
 * 	- Un Champ
 * 	- Une Charge si précisé par "le tout"
 */
public class Quartier {
	
	private Champ champ;
	
	private Charge charge;
	
	public Quartier() {
		this.champ = new Champ();
		this.charge = null;
	}
	
	/**
	 * Getter du champ
	 * @return champ
	 */
	public Champ GetChamp() {
		return this.champ;
	}
	
	/**
	 * Setter de la charge
	 * @param c
	 */
	public void SetCharge(Charge c) {
		this.charge = c;
	}
	
	/**
	 * Getter dde la charge
	 * @return charge
	 */
	public Charge GetCharge() {
		return this.charge;
	}
}
