package structures;

import java.awt.Color;
import java.text.Normalizer;

/**
 *	Classe associant une expression à la couleur correspondante
 *
 */
public class Couleur {
	
	private Color couleur;
	
	public Couleur(String exp) {
		this.couleur = null;
		this.SetCouleur(exp);
	}
	
	public void SetCouleur(String exp) {
		// Normalisation de l'expression
		String n_exp = exp.toLowerCase();
		
		// Si "d'" on retire
		if(n_exp.length() > 2) {
			if(n_exp.substring(0, 2).equalsIgnoreCase("d'")) {
				n_exp = n_exp.substring(2);
			}
		}
		
		n_exp = Normalizer.normalize(n_exp, Normalizer.Form.NFD);
		n_exp = n_exp.replaceAll("[^\\p{ASCII}]", ""); // pas d'accents
		n_exp = n_exp.replaceAll(",", "");
		
		switch(n_exp) {
		case "azur":
			couleur = new Color(0, 0, 255);
			break;
		case "gueules":
			couleur = new Color(255, 0, 0);
			break;
		case "sable":
			couleur = new Color(0, 0, 0);
			break;
		case "sinople":
			couleur = new Color(0, 255, 0);
			break;
		case "pourpre":
			couleur = new Color(137, 57, 94);
			break;
		case "or":
			couleur = new Color(255, 241, 0);
			break;
		case "argent":
			couleur = new Color(231, 231, 231);
			break;
		case "orange":
			couleur = new Color(255, 128, 0);
			break;
		case "carnation":
			couleur = new Color(227, 197, 162);
			break;
		case "hermine":
			// Couleur/motifs, à définir
			break;
		case "vair":
			// Couleur/motifs, à définir
			break;
		default:
			couleur = null;
			break;
		}
	}

	public Color getCouleur() {
		return this.couleur;
	}
	
	public String toString() {
		if (this.couleur != null) {
			return "(" + this.couleur.getRed() + "," + this.couleur.getGreen() + "," + this.couleur.getBlue() + ")";
		}
		
		return null;
	}
	
}
