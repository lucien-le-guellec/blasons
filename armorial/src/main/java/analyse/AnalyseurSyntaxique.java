package analyse;

import java.util.ArrayList;
import java.util.List;

import modele.Blason;
import modele.Charge;
import ressources.Partitions;
import structures.Couleur;
import structures.Partition;

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
				// champ partition� (5->14)
				if(TesteurExpression.EstUnePartitionChamp(this.expression, this.position)) {
					this.etat = 14;
					System.out.println("Arme simple, champ partition�");
					
					// Ajout du champ partitionn�
					this.blason.AddQuartier();
					Partition part = new Partition(this.expression[this.position]);
					this.blason.GetQuartierCourant().GetChamp().SetPartition(part);
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
				
				Couleur c1 = new Couleur(this.expression[this.position-1]);
				List<Couleur> couleurs = new ArrayList<Couleur>();
				couleurs.add(c1);
				this.blason.GetQuartierCourant().GetChamp().SetCouleurs(couleurs);
				
				// Champ plein 13
				if(TesteurExpression.EstPlein(this.expression, this.position)) {
					System.out.println("champ plein");
				}
				else if (TesteurExpression.EstUneCharge(this.expression, this.position)) {
					
					// R�cup�ration de l'expression de la charge
					String chargeStr = "";
					while (this.position < TesteurExpression.nouvPosition) {
						chargeStr += this.expression[this.position] + " ";
						this.position++;
					}
					
					Charge charge = new Charge(chargeStr);
					this.blason.GetQuartierCourant().GetChamp().GetCharges().add(charge);
					
					System.out.println("champ charg� : " + charge.GetRepresentation());
				}
			}
			else {
				// ERREUR de syntaxe
				System.out.println("Erreur de syntaxe, �l�ment attendu : COULEUR");
			}
			this.etat = 99;
			break;
		case 14: // Champ partitionn�, �tat possible: 15
			System.out.println("Etat 14");
						
			// Une ou deux couleurs attendues
			if(TesteurExpression.EstUneCouleur(this.expression, this.position)) {
				this.position = TesteurExpression.nouvPosition;
				
				List<Couleur> couleurs = new ArrayList<Couleur>();
				Couleur c1 = new Couleur(this.expression[this.position-1]);
				couleurs.add(c1);
								
				// Deuxi�me couleur ?
				if(TesteurExpression.EstUneCouleur(this.expression, this.position)) {
					this.position = TesteurExpression.nouvPosition;
					
					Couleur c2 = new Couleur(this.expression[this.position-1]);
					couleurs.add(c2);
				}
				
				this.blason.GetQuartierCourant().GetChamp().SetCouleurs(couleurs);
				this.etat = 15;
			}
			else {
				// ERREUR de syntaxe
				System.out.println("Erreur de syntaxe, �l�ment attendu : COULEUR");
			}
			break;
		case 15: // Sortie de arme simple, etat possible: 3,32,99
			System.out.println("Etat 15");
			// Si le tout est color�
			if(TesteurExpression.EstLeToutCouleur(this.expression, this.position)) {
				this.position = TesteurExpression.nouvPosition;
				this.etat = 32;
			}
			
			// Si le tout est charg�
			// TODO : if(TesteurExpression.EstLeToutCharge(this.expression, this.position)) 
			
			this.etat = 99;
			break;
		case 32: // Sortie de le tout color�, �tat possible: 99
			System.out.println("Etat 32");
			if(TesteurExpression.EstUneCouleur(this.expression, this.position)) {
				this.position = TesteurExpression.nouvPosition;
				
				Couleur c1 = new Couleur(this.expression[this.position-1]);
				// TODO : toutes les charges de la couleur identifi�e
				
			}
			else if(TesteurExpression.EstDuMeme(this.expression, this.position)) {
				// TODO : interpr�tation � voir ??
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
