package analyse;

import mod�le.Blason;

/**
 * 	Permet l'analyse syntaxique d'une phrase de blasonnement, et produit 
 * 	la structure de donn�e correspondante
 *
 */
public class AnalyseurSyntaxique {

	private String[] expression;
	
	private int position;
	private int etat;
	
	private Blason blason;
	
	public AnalyseurSyntaxique(String expression) {
		this.expression = expression.split(" ");
		this.position = 0;
		this.etat = 0;
		this.blason = new Blason();
	}
	
	public Blason Analyser() {
		this.AnalyserSuivant();
		return this.blason;
	}
	
	/**
	 * 	Permet � partir d'un �tat initial et de la position actuelle dans l'expression de d�terminer l'�tat suivant
	 * 	et le/les �l�ments structurels associ�s le cas �ch�ant.
	 */
	private void AnalyserSuivant( ) {
		
		switch(this.etat) {
		case 0:	// �tat initial, �tats possibles : 1, 2
			// Pas de partition : arme simple (1)
			if(!TesteurExpression.EstUnePartition(this.expression, this.position)) {
				this.etat = 1;
				System.out.println("Arme simple");
			}
			else {
				// champ partition� (5)
				if(TesteurExpression.EstUnePartitionChamp(this.expression, this.position)) {
					this.etat = 5;
					System.out.println("Champ partition�");
				}
				// arme compos�e (2)
				else if(TesteurExpression.EstUnePartitionArme(this.expression, this.position)) {
					this.etat = 2;
					System.out.println("Arme compos�e");
				}
			}
			break;
		default:
			break;
		}
		
	}
	
	
}
