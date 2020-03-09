package analyse;

import structures.Couleur;
import structures.Partition;

/**
 * 	Classe utilitaire fournissant des outils permettant de d�terminer si
 * 	des expressions font r�f�rences � des structures connues.
 */
public class TesteurExpression {

	/**
	 * Test de couleur, 
	 * @param exp, expression compl�te
	 * @param position, position de l'�l�ment � analyser dans l'expression
	 * @return true si une couleur est reconnue, false sinon
	 */
	public static boolean EstUneCouleur(String[] exp, int position) {
		
		exp = exp.clone();
		
		// Si "de" on avance
		if(exp[position].toLowerCase() == "de") {
			position += 1;
		}
		
		// Si "d'" on retire
		if(exp[position].toLowerCase().substring(1, 3) == "d'") {
			exp[position] = exp[position].substring(2);
		}
		
		// Test de couleur
		Couleur test = new Couleur(exp[position]);
		
		return test != null;
	}
	
	/**
	 * Test de partition
	 * @param exp, expression compl�te
	 * @param position, position de l'�l�ment � analyser dans l'expression
	 * @return true si une partition est reconnue, false sinon
	 */
	public static boolean EstUnePartition(String[] exp, int position) {
		exp = exp.clone();
		
		// On enl�ve les potentiels ind�sirables
		if(exp[position].contains(":") || exp[position].contains(",") || exp[position].contains(";")) {
			exp[position].substring(0, exp[position].length() - 1);
		}
		
		// Test de partition
		Partition test = new Partition(exp[position]);
		
		return test.GetPartition() != null;
	}
	
	/**
	 * Test de partition relative � une arme
	 * @param exp, expression compl�te
	 * @param position, position de l'�l�ment � analyser dans l'expression
	 * @return true si une partition est reconnue, false sinon
	 */
	public static boolean EstUnePartitionArme(String[] exp, int position) {
		exp = exp.clone();
		
		// Si ce n'est pas une partition de couleur, c'est une partition d'arme
		return !TesteurExpression.EstUnePartitionChamp(exp, position);
	}
	
	/**
	 * Test de partition relative � un champ
	 * @param exp, expression compl�te
	 * @param position, position de l'�l�ment � analyser dans l'expression
	 * @return true si une partition est reconnue, false sinon
	 */
	public static boolean EstUnePartitionChamp(String[] exp, int position) {
		exp = exp.clone();
		
		// Test de partition
		if(!TesteurExpression.EstUnePartition(exp, position)) {
			return false;
		}
				
		// Si l'�l�ment suivant est une couleur, alors c'est une partition de champ
		return TesteurExpression.EstUneCouleur(exp, position+1);
	}
	
}
