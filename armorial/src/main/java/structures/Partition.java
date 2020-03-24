package structures;

import java.text.Normalizer;

import ressources.Partitions;

public class Partition {

	private Partitions partition;
	
	public Partition(String exp) {
		this.partition = null;
		this.SetPartition(exp);
	}
	
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
		// TODO : à compléter
		default:
			this.partition = null;
			break;
		}
	}
	
	public Partitions GetPartition() {
		return this.partition;
	}
}
