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
	
	public String GetExpression() {
		return this.exp;
	}
	
	public void SetRepresentation(String rep) {
		this.representation = rep.trim();
	}
	
	public String GetRepresentation() {
		return this.representation;
	}
	
	public int GetNombre() {
		return this.nombre;
	}
	
	public void SetNombre(int nb) {
		this.nombre = nb;
	}
	
	public List<Couleur> GetCouleurs() {
		return this.couleurs;
	}

}
