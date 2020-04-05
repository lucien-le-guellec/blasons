package modele;

import java.util.ArrayList;
import java.util.List;

import structures.Couleur;

/**
 * 	Classe représentant une charge, un élément décoratif et ses informations
 * 
 * 	Attributs:
 *  - Expression : expression décrivant la charge
 * 	- Représentation : motif représentant graphiquement la charge
 * 	- Emplacement : emplacement par rapport à son contenant
 *  - Nombre : nombre de motifs composant la charge
 *  - Couleurs : couleurs de la charge
 * 
 * 	Composition:
 * 	- Une ou plusieurs Charges (une charge peut être chargée)
 */
public class Charge {
	
	protected String representation;
	protected String exp;
	protected String emplacement;
	
	protected int nombre;
	
	protected List<Couleur> couleurs;
	
	public Charge(String expression) {
		this.SetExpression(expression);
		this.nombre = 0;
		this.couleurs = new ArrayList<Couleur>();
	}
	
	/**
	 * Setter de l'expression
	 * Normalise l'expression
	 * @param exp
	 */
	public void SetExpression(String exp) {
		exp = exp.trim();
		
		// On retire la ponctuation de fin
		if(exp.charAt(exp.length() - 1) == '.' ||
			exp.charAt(exp.length() - 1) == ',' ||
			exp.charAt(exp.length() - 1) == ';') {
			exp = exp.substring(0, exp.length() - 1);
		}
		
		// On retire le/les articles
		String premier = exp.split(" ")[0];
		while(premier.equalsIgnoreCase("à") ||
			premier.equalsIgnoreCase("au") ||
			premier.equalsIgnoreCase("aux") ||
			premier.equalsIgnoreCase("la") ||
			premier.equalsIgnoreCase("le") ||
			premier.equalsIgnoreCase("des") ||
			premier.equalsIgnoreCase("de")) {
			exp = exp.substring(premier.length()+1, exp.length());
			premier = exp.split(" ")[0];
		}
		
		if(exp.substring(0, 2).equalsIgnoreCase("l'")) {
			exp = exp.substring(2, exp.length());
		}
				
		this.exp = exp.trim();
	}
	
	/**
	 * Getter de l'expression
	 * @return exp
	 */
	public String GetExpression() {
		return this.exp;
	}
	
	/**
	 * Setter de la representation
	 * @param rep
	 */
	public void SetRepresentation(String rep) {
		this.representation = rep.trim();
	}
	
	/**
	 * Getter de la représentation
	 * @return representation
	 */
	public String GetRepresentation() {
		return this.representation;
	}
	
	/**
	 * Getter du nombre
	 * @return nombre
	 */
	public int GetNombre() {
		return this.nombre;
	}
	
	/**
	 * Setter du nombre
	 * @param nb
	 */
	public void SetNombre(int nb) {
		this.nombre = nb;
	}
	
	/**
	 * Getter des couleurs
	 * @return couleurs
	 */
	public List<Couleur> GetCouleurs() {
		return this.couleurs;
	}

}
