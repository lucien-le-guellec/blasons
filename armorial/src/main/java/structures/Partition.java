package structures;

import java.text.Normalizer;

import ressources.Partitions;

/**
 *	Classe associant une expression � la partition correspondante
 *
 */
public class Partition {

	private Partitions partition;
	
	public Partition(String exp) {
		this.partition = null;
		this.SetPartition(exp);
	}
	
	/**
	 * Setter de la partition en fonction de l'expression
	 * @param exp, expression � utiliser
	 */
	public void SetPartition(String exp) {
		// Normalisation de l'expression
		String n_exp = exp.toLowerCase();
		n_exp = Normalizer.normalize(n_exp, Normalizer.Form.NFD);
		n_exp = n_exp.replaceAll("[^\\p{ASCII}]", ""); // pas d'accents
				
		switch(n_exp) {
		case "parti":
			this.partition = Partitions.PARTI;
			break;
		case "coupe":
			this.partition = Partitions.COUPE;
			break;
		case "tranche":
			this.partition = Partitions.TRANCHE;
			break;
		case "taille":
			this.partition = Partitions.TAILLE;
			break;
		case "ecartele":
			this.partition = Partitions.ECARTELE;
			break;
		// TODO : � compl�ter
		default:
			this.partition = null;
			break;
		}
	}
	
	/**
	 * Getter de la partition
	 * @return partition
	 */
	public Partitions GetPartition() {
		return this.partition;
	}
}
