package analyse;

import structures.Couleur;
import structures.Partition;

/**
 * 	Classe utilitaire fournissant des outils permettant de d�terminer si
 * 	des expressions font r�f�rences � des structures connues.
 */
public class TesteurExpression {
	
	public static int nouvPosition = 0;
	
	public static Couleur derniereCouleur = null;

	/**
	 * Test de couleur, 
	 * @param exp, expression compl�te
	 * @param position, position de l'�l�ment � analyser dans l'expression
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
		
		// Si "du" on avance
		if(exp[position].equalsIgnoreCase("du")) {
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
			TesteurExpression.derniereCouleur = test;
			return true;
		}
		
		return false;
	}

	/**
	 * Test si l'expression courante est "du m�me"
	 * @param exp, expression compl�te
	 * @param position, position de l'�l�ment � analyser dans l'expression
	 * @return true l'exp est "du m�me"
	 */
	public static boolean EstDuMeme(String[] exp, int position) {
		
		exp = exp.clone();
		
		// Si "du" on avance
		if(exp[position].equalsIgnoreCase("du")) {
			position += 1;
			// Si "m�me" on avance
			if(exp[position].equalsIgnoreCase("m�me")) {
				position += 1;
				TesteurExpression.nouvPosition = position + 1;
				return true;
			}
		}
		
		return false;
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
		
		if (test.GetPartition() != null) {
			TesteurExpression.nouvPosition = position + 1;
			return true;
		}
		
		return false;
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
		
		int pos = TesteurExpression.nouvPosition;
				
		// Si l'�l�ment suivant est une couleur, alors c'est une partition de champ
		boolean coul = TesteurExpression.EstUneCouleur(exp, position+1);
		
		// On r�initialise la position actuelle � l'�l�ment de partition analys�
		TesteurExpression.nouvPosition = pos;
		
		return coul;
	}
	
	/**
	 * Test de "plein"
	 * @param exp, expression compl�te
	 * @param position, position de l'�l�ment � analyser dans l'expression
	 * @return true l'exp "plein" est trouv�e 
	 */
	public static boolean EstPlein(String[] exp, int position) {
		exp = exp.clone();
		
		
		
		if (exp[position].equalsIgnoreCase("plein") ||
			exp[position].equalsIgnoreCase("plein.")) {
			TesteurExpression.nouvPosition = position + 1;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Test de charge
	 * @param exp, expression compl�te
	 * @param position, position de l'�l�ment � analyser dans l'expression
	 * @return true l'exp est une charge, d�fini la position � la fin de la charge
	 */
	public static boolean EstUneCharge(String[] exp, int position) {
		exp = exp.clone();
		
		int origine = position;
		
		if(TesteurExpression.EstUnePiece(exp, position) ||
			TesteurExpression.EstUnMeuble(exp, position)) {
			position = TesteurExpression.nouvPosition;
			
			// Atteint une ',' ';' '.' ou la fin
			while(position < exp.length) {
				if (exp[position].charAt(exp[position].length()-1) == ';' ||
					exp[position].charAt(exp[position].length()-1) == '.') {
					TesteurExpression.nouvPosition = position + 1;
					return true;
				}
				else if(exp[position].charAt(exp[position].length()-1) == ',') {
					position++;
					if(exp[position].equalsIgnoreCase("accompagn�") ||
						exp[position].equalsIgnoreCase("accompagn�s") ||
						exp[position].equalsIgnoreCase("accompagn�e") ||
						exp[position].equalsIgnoreCase("accompagn�es") ||
						exp[position].equalsIgnoreCase("et")) {
						TesteurExpression.nouvPosition = position;
						return true;
					}
				}
				
				position++;
			}
			TesteurExpression.nouvPosition = position;
			return true;
		}
		
		// On ne trouve pas l'expression, on se replace au d�but
		TesteurExpression.nouvPosition = origine;
		
		return false;
	}
	
	/**
	 * Test de piece
	 * @param exp, expression compl�te
	 * @param position, position de l'�l�ment � analyser dans l'expression
	 * @return true l'exp est une piece, la position est d�finie apr�s l'article
	 */
	public static boolean EstUnePiece(String[] exp, int position) {
		exp = exp.clone();
		
		// Doit commencer par 'au', 'aux', '� la' ou '� l''
		if (exp[position].equalsIgnoreCase("au")) {
			position++;
			TesteurExpression.nouvPosition = position;
			return true;
		}
		else if (exp[position].equalsIgnoreCase("aux")) {
			position++;
			if (exp[position].equalsIgnoreCase("deux") || 
				exp[position].equalsIgnoreCase("trois") ||
				exp[position].equalsIgnoreCase("quatre")) {
				TesteurExpression.nouvPosition = position;
				return true;
			}
		}
		else if (exp[position].equalsIgnoreCase("�") || exp[position].equalsIgnoreCase("a")) {
			position++;
			if (exp[position].equalsIgnoreCase("la") ||
				exp[position].substring(0, 2).equalsIgnoreCase("l'")) {
				TesteurExpression.nouvPosition = position;
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Test de meuble
	 * @param exp, expression compl�te
	 * @param position, position de l'�l�ment � analyser dans l'expression
	 * @return true l'exp est un meuble, la position est d�finie apr�s l'article
	 */
	public static boolean EstUnMeuble(String[] exp, int position) {
		exp = exp.clone();
		
		// Doit commencer par '�/de/d' un/une/deux/trois...'
		if (exp[position].equalsIgnoreCase("�") || 
			exp[position].equalsIgnoreCase("a") ||
			exp[position].equalsIgnoreCase("de")) {
			position++;
			if (exp[position].equalsIgnoreCase("un") ||
				exp[position].equalsIgnoreCase("une") ||
				exp[position].equalsIgnoreCase("deux") ||
				exp[position].equalsIgnoreCase("trois") ||
				exp[position].equalsIgnoreCase("quatre") ||
				exp[position].equalsIgnoreCase("cinq") ||
				exp[position].equalsIgnoreCase("six") ||
				exp[position].equalsIgnoreCase("sept") ||
				exp[position].equalsIgnoreCase("huit") ||
				exp[position].equalsIgnoreCase("neuf") ||
				exp[position].equalsIgnoreCase("dix") ) {
				TesteurExpression.nouvPosition = position;
				return true;
			}
		}
		else if (exp[position].substring(0, 2).equalsIgnoreCase("d'")) {
			position++;
			if (exp[position].equalsIgnoreCase("un") ||
				exp[position].equalsIgnoreCase("une")) {
				TesteurExpression.nouvPosition = position;
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Test si l'expression courante est "le tout" color�
	 * @param exp, expression compl�te
	 * @param position, position de l'�l�ment � analyser dans l'expression
	 * @return true l'exp est "le tout" color�
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
	
	/**
	 * Test si l'expression courante est une expression d'accompagnement
	 * @param exp, expression compl�te
	 * @param position, position de l'�l�ment � analyser dans l'expression
	 * @return true l'exp est un accompagnement
	 */
	public static boolean EstAccompagne(String[] exp, int position) {
		exp = exp.clone();
		
		if(exp[position].equalsIgnoreCase("accompagn�") ||
				exp[position].equalsIgnoreCase("accompagn�s") ||
				exp[position].equalsIgnoreCase("accompagn�e") ||
				exp[position].equalsIgnoreCase("accompagn�es") ||
				exp[position].equalsIgnoreCase("et")) {
				TesteurExpression.nouvPosition = position+1;
				return true;
		}
		
		return false;
	}
	
}


