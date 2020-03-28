package modele;

/**
 * 	Classe repr�sentant une charge, un �l�ment d�coratif et ses informations
 * 
 * 	Attributs:
 * 	- Repr�sentation : motif repr�sentant graphiquement la charge
 * 	- Emplacement : emplacement par rapport � son contenant
 * 
 * 	Composition:
 * 	- Une ou plusieurs Charges (une charge peut �tre charg�e)
 */
public class Charge {
	
	protected String representation;
	protected String emplacement;
	
	public Charge(String expression) {
		this.SetRepresentation(expression);
	}
	
	public void SetRepresentation(String exp) {
		exp = exp.trim();
		
		// On retire la ponctuation de fin
		if(exp.charAt(exp.length() - 1) == '.' ||
			exp.charAt(exp.length() - 1) == ',' ||
			exp.charAt(exp.length() - 1) == ';') {
			exp = exp.substring(0, exp.length() - 1);
		}
		
		// On retire le/les articles
		String premier = exp.split(" ")[0];
		while(premier.equalsIgnoreCase("�") ||
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
				
		this.representation = exp;
	}
	
	public String GetRepresentation() {
		return this.representation;
	}

}
