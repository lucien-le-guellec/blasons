package analyse;

import structures.Couleur;
import structures.Partition;

/**
 * 	Classe utilitaire fournissant des outils permettant de déterminer si
 * 	des expressions font références à des structures connues.
 */
public class TesteurExpression {
	
	public static int nouvPosition = 0;

	/**
	 * Test de couleur, 
	 * @param exp, expression complète
	 * @param position, position de l'élément à analyser dans l'expression
	 * @return true si une couleur est reconnue, false sinon
	 */
	public static boolean EstUneCouleur(String[] exp, int position) {
		
		exp = exp.clone();
		
		// Si "et" on avance
		if(exp[position].equalsIgnoreCase("et")) {
			position += 1;
		}
		
		// Si "de" on avance
		if(exp[position].equalsIgnoreCase("de")) {
			position += 1;
		}
		
		// Si "d'" on retire
		if(exp[position].length() > 2) {
			if(exp[position].substring(0, 2).equalsIgnoreCase("d'")) {
				exp[position] = exp[position].substring(2);
			}
		}
		
		// Test de couleur
		Couleur test = new Couleur(exp[position]);
		
		if (test.getCouleur() != null) {
			TesteurExpression.nouvPosition = position + 1;
			return true;
		}
		
		return false;
	}

	/**
	 * Test si l'expression courante est "du même"
	 * @param exp, expression complète
	 * @param position, position de l'élément à analyser dans l'expression
	 * @return true l'exp est "du même"
	 */
	public static boolean EstDuMeme(String[] exp, int position) {
		
		exp = exp.clone();
		
		// Si "du" on avance
		if(exp[position].equalsIgnoreCase("du")) {
			position += 1;
			// Si "même" on avance
			if(exp[position].equalsIgnoreCase("même")) {
				position += 1;
				TesteurExpression.nouvPosition = position + 1;
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Test de partition
	 * @param exp, expression complète
	 * @param position, position de l'élément à analyser dans l'expression
	 * @return true si une partition est reconnue, false sinon
	 */
	public static boolean EstUnePartition(String[] exp, int position) {
		exp = exp.clone();
		
		// On enlève les potentiels indésirables
		if(exp[position].contains(":") || exp[position].contains(",") || exp[position].contains(";")) {
			exp[position].substring(0, exp[position].length() - 1);
		}
		
		// Test de partition
		Partition test = new Partition(exp[position]);
		
		if (test.GetPartition() != null) {
			TesteurExpression.nouvPosition = position + 1;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Test de partition relative à une arme
	 * @param exp, expression complète
	 * @param position, position de l'élément à analyser dans l'expression
	 * @return true si une partition est reconnue, false sinon
	 */
	public static boolean EstUnePartitionArme(String[] exp, int position) {
		exp = exp.clone();
		
		// Si ce n'est pas une partition de couleur, c'est une partition d'arme
		return !TesteurExpression.EstUnePartitionChamp(exp, position);
	}
	
	/**
	 * Test de partition relative à un champ
	 * @param exp, expression complète
	 * @param position, position de l'élément à analyser dans l'expression
	 * @return true si une partition est reconnue, false sinon
	 */
	public static boolean EstUnePartitionChamp(String[] exp, int position) {
		exp = exp.clone();
		
		// Test de partition
		if(!TesteurExpression.EstUnePartition(exp, position)) {
			return false;
		}
		
		int pos = TesteurExpression.nouvPosition;
				
		// Si l'élément suivant est une couleur, alors c'est une partition de champ
		boolean coul = TesteurExpression.EstUneCouleur(exp, position+1);
		
		// On réinitialise la position actuelle à l'élément de partition analysé
		TesteurExpression.nouvPosition = pos;
		
		return coul;
	}
	
	/**
	 * Test de "plein"
	 * @param exp, expression complète
	 * @param position, position de l'élément à analyser dans l'expression
	 * @return true l'exp "plein" est trouvée 
	 */
	public static boolean EstPlein(String[] exp, int position) {
		exp = exp.clone();
		
		if (exp[position].equalsIgnoreCase("plein")) {
			TesteurExpression.nouvPosition = position + 1;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Test de charge
	 * @param exp, expression complète
	 * @param position, position de l'élément à analyser dans l'expression
	 * @return true l'exp est une charge
	 */
	public static boolean EstUneCharge(String[] exp, int position) {
		exp = exp.clone();
		
		return false;
	}
	
	/**
	 * Test si l'expression courante est "le tout" coloré
	 * @param exp, expression complète
	 * @param position, position de l'élément à analyser dans l'expression
	 * @return true l'exp est "le tout" coloré
	 */
	public static boolean EstLeToutCouleur(String[] exp, int position) {
		
		exp = exp.clone();
		
		// Si "le" on avance
		if(exp[position].equalsIgnoreCase("le")) {
			position += 1;
			// Si "tout" on avance
			if(exp[position].equalsIgnoreCase("tout")) {
				position += 1;
				TesteurExpression.nouvPosition = position + 1;
				return true;
			}
		}
		
		return false;
	}
	
}
