package structures;

import java.awt.Color;
import java.text.Normalizer;

import analyse.TesteurExpression;

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
	
	/**
	 * Setter de la couleur en fonction de son expression
	 * @param exp, expression de la couleur
	 */
	public void SetCouleur(String exp) {
		// Normalisation de l'expression
		String n_exp = exp.toLowerCase();
		
		n_exp = n_exp.trim();
		
		// On retire la ponctuation de fin
		if(n_exp.charAt(n_exp.length() - 1) == '.' ||
			n_exp.charAt(n_exp.length() - 1) == ',' ||
			n_exp.charAt(n_exp.length() - 1) == ';') {
			n_exp = n_exp.substring(0, n_exp.length() - 1);
		}
		
		// Si "d'" on retire
		if(n_exp.length() > 2) {
			if(n_exp.substring(0, 2).equalsIgnoreCase("d'")) {
				n_exp = n_exp.substring(2);
			}
		}
		
		// Si "du" on retire
		if(n_exp.length() > 2) {
			if(n_exp.substring(0, 2).equalsIgnoreCase("du")) {
				n_exp = n_exp.substring(2);
			}
		}
		
		n_exp = Normalizer.normalize(n_exp, Normalizer.Form.NFD);
		n_exp = n_exp.replaceAll("[^\\p{ASCII}]", ""); // pas d'accents
		n_exp = n_exp.replaceAll(",", "");
				
		switch(n_exp) {
		case "azur":
			this.couleur = new Color(0, 0, 255);
			break;
		case "gueules":
			this.couleur = new Color(255, 0, 0);
			break;
		case "sable":
			this.couleur = new Color(0, 0, 0);
			break;
		case "sinople":
			this.couleur = new Color(0, 255, 0);
			break;
		case "pourpre":
			this.couleur = new Color(137, 57, 94);
			break;
		case "or":
			this.couleur = new Color(255, 241, 0);
			break;
		case "argent":
			this.couleur = new Color(231, 231, 231);
			break;
		case "orange":
			this.couleur = new Color(255, 128, 0);
			break;
		case "carnation":
			this.couleur = new Color(227, 197, 162);
			break;
		case "hermine":
			// Couleur/motifs, à définir
			// voir motifs/fourrure_hermine.png
			break;
		case "vair":
			// Couleur/motifs, à définir
			// voir motifs/fourrure_vair.png
			break;
		case "meme":
			// on récupère la dernière couleur analysée
			if(TesteurExpression.derniereCouleur != null) {
				Color c = TesteurExpression.derniereCouleur.getCouleur();
					if(c != null) {
						this.couleur = new Color(c.getRed(), c.getGreen(), c.getBlue());
						break;
					}
			}
			this.couleur = null;
			break;
		default:
			this.couleur = null;
			break;
		}
	}

	/**
	 * Getter de l'objet Color associé
	 * @return couleur
	 */
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
