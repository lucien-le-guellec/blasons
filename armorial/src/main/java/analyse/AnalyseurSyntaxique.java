package analyse;

import modèle.Blason;
import structures.Couleur;

/**
 * 	Permet l'analyse syntaxique d'une phrase de blasonnement, et produit 
 * 	la structure de donnée correspondante
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
		// On retire le point de fin de phrase si présent
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
		
		// On analyse tant qu'il reste des expressions à analyser
		while(this.position < this.expression.length) {
			this.position = TesteurExpression.nouvPosition;
			this.AnalyserSuivant();
		}
		
		System.out.println("");
		
		return this.blason;
	}
	
	/**
	 * 	Permet à partir d'un état initial et de la position actuelle dans l'expression de déterminer l'état suivant
	 * 	et le/les éléments structurels associés le cas échéant.
	 */
	private void AnalyserSuivant( ) {
		
		this.position = TesteurExpression.nouvPosition;
		
		switch(this.etat) {
		case 0:	// état initial, états possibles : 2,4,5
			System.out.println("Etat 0");
			// Pas de partition : arme simple, champ simple (1->4)
			if(!TesteurExpression.EstUnePartition(this.expression, this.position)) {
				this.etat = 4;
				System.out.println("Arme simple, champ simple");
				this.blason.AddQuartier();
			}
			else {
				// champ partitioné (5)
				if(TesteurExpression.EstUnePartitionChamp(this.expression, this.position)) {
					this.etat = 5;
					System.out.println("Arme simple, champ partitioné");
					this.blason.AddQuartier();
				}
				// arme composée (2)
				else if(TesteurExpression.EstUnePartitionArme(this.expression, this.position)) {
					this.etat = 2;
					System.out.println("Arme composée");
				}
			}
			break;
		case 4: // Champ simple, état possible: 12, 13
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
					System.out.println("champ chargé");
				}
			}
			else {
				// ERREUR de syntaxe
				System.out.println("Erreur de syntaxe, élément attendu : COULEUR");
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
