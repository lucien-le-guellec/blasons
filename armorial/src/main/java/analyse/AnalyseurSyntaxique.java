package analyse;

import modèle.Blason;

/**
 * 	Permet l'analyse syntaxique d'une phrase de blasonnement, et produit 
 * 	la structure de donnée correspondante
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
	 * 	Permet à partir d'un état initial et de la position actuelle dans l'expression de déterminer l'état suivant
	 * 	et le/les éléments structurels associés le cas échéant.
	 */
	private void AnalyserSuivant( ) {
		
		switch(this.etat) {
		case 0:	// état initial, états possibles : 1, 2
			// Pas de partition : arme simple (1)
			if(!TesteurExpression.EstUnePartition(this.expression, this.position)) {
				this.etat = 1;
				System.out.println("Arme simple");
			}
			else {
				// champ partitioné (5)
				if(TesteurExpression.EstUnePartitionChamp(this.expression, this.position)) {
					this.etat = 5;
					System.out.println("Champ partitioné");
				}
				// arme composée (2)
				else if(TesteurExpression.EstUnePartitionArme(this.expression, this.position)) {
					this.etat = 2;
					System.out.println("Arme composée");
				}
			}
			break;
		default:
			break;
		}
		
	}
	
	
}
