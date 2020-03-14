package analyse;

import mod�le.Blason;
import structures.Couleur;

/**
 * 	Permet l'analyse syntaxique d'une phrase de blasonnement, et produit 
 * 	la structure de donn�e correspondante
 *
 */
public class AnalyseurSyntaxique {

	private String[] expression;
	private String phrase;
	
	private int position;
	private int etat;
	
	private Blason blason;
	
	public AnalyseurSyntaxique(String phrase) {
		this.phrase = phrase;
		this.expression = phrase.split(" ");
		// On retire le point de fin de phrase si pr�sent
		if(this.expression[this.expression.length-1].contains(".")) {
			this.expression[this.expression.length-1] = this.expression[this.expression.length-1].substring(0, this.expression[this.expression.length-1].length() - 1);
		}
		this.position = 0;
		this.etat = 0;
		this.blason = new Blason(phrase);
		TesteurExpression.nouvPosition = 0;
	}
	
	public Blason GetBlason() {
		return this.blason;
	}
	
	public Blason Analyser() {
		System.out.println("Analyse de : " + this.phrase);
		
		// On analyse tant qu'il reste des expressions � analyser
		while(this.position < this.expression.length) {
			this.position = TesteurExpression.nouvPosition;
			this.AnalyserSuivant();
		}
		
		System.out.println("");
		
		return this.blason;
	}
	
	/**
	 * 	Permet � partir d'un �tat initial et de la position actuelle dans l'expression de d�terminer l'�tat suivant
	 * 	et le/les �l�ments structurels associ�s le cas �ch�ant.
	 */
	private void AnalyserSuivant( ) {
		
		this.position = TesteurExpression.nouvPosition;
		
		switch(this.etat) {
		case 0:	// �tat initial, �tats possibles : 2,4,5
			System.out.println("Etat 0");
			// Pas de partition : arme simple, champ simple (1->4)
			if(!TesteurExpression.EstUnePartition(this.expression, this.position)) {
				this.etat = 4;
				System.out.println("Arme simple, champ simple");
				this.blason.AddQuartier();
			}
			else {
				// champ partition� (5)
				if(TesteurExpression.EstUnePartitionChamp(this.expression, this.position)) {
					this.etat = 5;
					System.out.println("Arme simple, champ partition�");
					this.blason.AddQuartier();
				}
				// arme compos�e (2)
				else if(TesteurExpression.EstUnePartitionArme(this.expression, this.position)) {
					this.etat = 2;
					System.out.println("Arme compos�e");
				}
			}
			break;
		case 4: // Champ simple, �tat possible: 12, 13
			System.out.println("Etat 4");
			if(TesteurExpression.EstUneCouleur(this.expression, this.position)) {
				this.position = TesteurExpression.nouvPosition;
				Couleur couleur = new Couleur(this.expression[this.position-1]);
				this.blason.GetQuartierCourant().GetChamp().SetCouleur(couleur);
				// Champ plein 13
				if(TesteurExpression.EstPlein(this.expression, this.position)) {
					System.out.println("champ plein");
				}
				else if (TesteurExpression.EstUneCharge(this.expression, this.position)) {
					System.out.println("champ charg�");
				}
			}
			else {
				// ERREUR de syntaxe
				System.out.println("Erreur de syntaxe, �l�ment attendu : COULEUR");
			}
			this.etat = 99;
			break;
		case 99:
			this.position = this.expression.length;
			break;
		default:
			this.etat = 99;
			break;
		}
		
	}
	
	
}
