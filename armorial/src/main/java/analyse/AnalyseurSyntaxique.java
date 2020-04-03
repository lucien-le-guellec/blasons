package analyse;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import modele.Blason;
import modele.Charge;
import structures.Couleur;
import structures.Partition;

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
			this.AnalyserSuivant();
			this.position = TesteurExpression.nouvPosition;
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
				// champ partitioné (5->14)
				if(TesteurExpression.EstUnePartitionChamp(this.expression, this.position)) {
					this.etat = 14;
					System.out.println("Arme simple, champ partitioné");
					
					// Ajout du champ partitionné
					this.blason.AddQuartier();
					Partition part = new Partition(this.expression[this.position]);
					this.blason.GetQuartierCourant().GetChamp().SetPartition(part);
				}
				// arme composée (2->6)
				else if(TesteurExpression.EstUnePartitionArme(this.expression, this.position)) {
					System.out.println("Arme composée");
					Partition partition = new Partition(this.expression[this.position]);
					if(partition.GetPartition() != null) {
						this.blason.SetPartitionnement(partition);
					}
					else {
						System.out.println("Partitionnement d'arme non identifié : " + this.expression[this.position]);
					}
					this.etat = 6;
				}
			}
			break;
		case 4: // Champ simple, état possible: 12, 13
			System.out.println("Etat 4");
			System.out.println(this.expression[this.position]);
			if(TesteurExpression.EstUneCouleur(this.expression, this.position)) {
				this.position = TesteurExpression.nouvPosition;
				
				Couleur c1 = new Couleur(this.expression[this.position-1]);
				List<Couleur> couleurs = new ArrayList<Couleur>();
				couleurs.add(c1);
				this.blason.GetQuartierCourant().GetChamp().SetCouleurs(couleurs);
				
				// Champ plein 13
				if(TesteurExpression.EstPlein(this.expression, this.position)) {
					System.out.println("champ plein");
					// On passe à l'élément suivant
					TesteurExpression.nouvPosition++;
					// TODO : Le tout chargé/coloré ?
					break;
				}
				else if (TesteurExpression.EstUneCharge(this.expression, this.position)) {
					
					// Récupération de l'expression de la charge
					String chargeStr = "";
					while (this.position < TesteurExpression.nouvPosition) {
						chargeStr += this.expression[this.position] + " ";
						this.position++;
					}
					
					Charge charge = new Charge(chargeStr);
					// Analyse de la charge
					AnalyserCharge(chargeStr, charge);
					this.blason.GetQuartierCourant().GetChamp().GetCharges().add(charge);
					
					System.out.println("champ chargé : " + charge.GetExpression());
					
					// Nouveaux quartiers
					if(this.position+1 < this.expression.length) {
						if(TesteurExpression.EstUnOrdre(this.expression, this.position+1)) {
							this.etat = 6;
						}
					}
				}
			}
			// Accompagné d'une charge
			else if(TesteurExpression.EstAccompagne(this.expression, this.position)) {
				this.position = TesteurExpression.nouvPosition;
				if (TesteurExpression.EstUneCharge(this.expression, this.position)) {
					// Récupération de l'expression de la charge
					String chargeStr = "";
					while (this.position < TesteurExpression.nouvPosition) {
						chargeStr += this.expression[this.position] + " ";
						this.position++;
					}
					
					Charge charge = new Charge(chargeStr);
					// Analyse de la charge
					AnalyserCharge(chargeStr, charge);
					this.blason.GetQuartierCourant().GetChamp().GetCharges().add(charge);
					
					System.out.println("accompagné de : " + charge.GetExpression());
				}
			}
			else {
				// ERREUR de syntaxe
				System.out.println("Erreur de syntaxe.");
				this.etat = 99;
			}
			break;
		case 6: // Arme composée, états possibles : ordre(7) ou sortie (3, 32, 99)
			if(TesteurExpression.EstUnOrdre(this.expression, this.position)) {
				// Ordre (7)
				List<Integer> ordres = new ArrayList<Integer>();
				
				// Premier ordre
				// Si ":" on avance
				if(this.expression[this.position].equalsIgnoreCase(":")) {
					this.position++;
				}
				
				// Si "en","au","aux" on avance
				if(this.expression[this.position].equalsIgnoreCase("en") ||	
						this.expression[this.position].equalsIgnoreCase("au") ||
						this.expression[this.position].equalsIgnoreCase("aux")) {
					this.position++;
				}
				
				// Si "," accolée on retire
				if(this.expression[position].length() > 1) {
					if(this.expression[position].charAt(1) == ',') {
						this.expression[position] = this.expression[position].substring(0, 1);
					}
				}
				
				if(this.expression[this.position].equalsIgnoreCase("1") ||
						this.expression[this.position].equalsIgnoreCase("premier")) {
					ordres.add(1);
				}
				else if(this.expression[this.position].equalsIgnoreCase("2") ||
						this.expression[this.position].equalsIgnoreCase("deuxieme") ||
						this.expression[this.position].equalsIgnoreCase("second")) {
					ordres.add(2);
				}
				else if(this.expression[this.position].equalsIgnoreCase("3") ||
						this.expression[this.position].equalsIgnoreCase("trosieme")) {
					ordres.add(3);
				}
				else if(this.expression[this.position].equalsIgnoreCase("4") ||
						this.expression[this.position].equalsIgnoreCase("quatrieme")) {
					ordres.add(4);
				}
				else {
					System.out.println("Ordre non identifié : " + this.expression[this.position]);
					this.etat = 99;
				}
				
				this.position++;
				
				// Second ordre
				// Si "et", "&" on avance
				if(this.expression[position].equalsIgnoreCase("et") ||
						this.expression[position].equalsIgnoreCase("&")) {
					this.position++;
					
					// Si "en","au","aux" on avance
					if(this.expression[this.position].equalsIgnoreCase("en") ||	
							this.expression[this.position].equalsIgnoreCase("au") ||
							this.expression[this.position].equalsIgnoreCase("aux")) {
						this.position++;
					}
						
					// Si "," accolée on retire
					if(this.expression[position].length() > 1) {
						if(this.expression[position].charAt(1) == ',') {
							this.expression[position] = this.expression[position].substring(0, 1);
						}
					}
						
					if(this.expression[this.position].equalsIgnoreCase("2") ||
							this.expression[this.position].equalsIgnoreCase("deuxieme") ||
							this.expression[this.position].equalsIgnoreCase("second")) {
						ordres.add(2);
					}
					else if(this.expression[this.position].equalsIgnoreCase("3") ||
							this.expression[this.position].equalsIgnoreCase("trosieme")) {
						ordres.add(3);
					}
					else if(this.expression[this.position].equalsIgnoreCase("4") ||
							this.expression[this.position].equalsIgnoreCase("quatrieme")) {
						ordres.add(4);
					}
					else {
						System.out.println("Second ordre non identifié : " + this.expression[this.position]);
						this.etat = 99;
					}
					
					this.position++;
				}
				
				System.out.println("Ordres : " + ordres.toString());
				
				// L'ordre est déterminé
				int dernierOrdre = ordres.get(ordres.size()-1);
				// Création des quartiers
				while(dernierOrdre > this.blason.GetQuartiers().size()) {
					this.blason.AddQuartier();
				}
				
				// Gestion de l'ordre secondaire
				if (ordres.size() > 1) {
					// L'ordre secondaire fait référence à l'ordre principal
					this.blason.GetQuartiers().set(ordres.get(1)-1, this.blason.GetQuartiers().get(ordres.get(0)-1));
				}
				
				// On analyse l'expression de l'arme
				this.blason.SetOrdreActuel(ordres.get(0));
				this.etat = 4;
				TesteurExpression.nouvPosition = this.position;
				System.out.println("pos : " + this.position);
			}
			else {
				// Le "tout" (3 ou 32) ou sortie (99)
				this.etat = 99;
			}
			break;
		case 14: // Champ partitionné, état possible: 15
			System.out.println("Etat 14");
						
			// Une ou deux couleurs attendues
			if(TesteurExpression.EstUneCouleur(this.expression, this.position)) {
				this.position = TesteurExpression.nouvPosition;
				
				List<Couleur> couleurs = new ArrayList<Couleur>();
				Couleur c1 = new Couleur(this.expression[this.position-1]);
				couleurs.add(c1);
								
				// Deuxième couleur ?
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
				System.out.println("Erreur de syntaxe, élément attendu : COULEUR");
			}
			break;
		case 15: // Sortie de arme simple, etat possible: 3,32,99
			System.out.println("Etat 15");
			// Si le tout est coloré
			if(TesteurExpression.EstLeToutCouleur(this.expression, this.position)) {
				this.position = TesteurExpression.nouvPosition;
				this.etat = 32;
			}
			
			// Si le tout est chargé
			// TODO : if(TesteurExpression.EstLeToutCharge(this.expression, this.position)) 
			
			this.etat = 99;
			break;
		case 32: // Sortie de le tout coloré, état possible: 99
			System.out.println("Etat 32");
			if(TesteurExpression.EstUneCouleur(this.expression, this.position)) {
				this.position = TesteurExpression.nouvPosition;
				
				Couleur c1 = new Couleur(this.expression[this.position-1]);
				// TODO : toutes les charges de la couleur identifiée
				
			}
			else if(TesteurExpression.EstDuMeme(this.expression, this.position)) {
				// TODO : interprétation à voir ??
			}
			else {
				System.out.println("Erreur de syntaxe, élément attendu : COULEUR");
			}
			this.etat = 99;
			break;
		case 99:
			TesteurExpression.nouvPosition = this.expression.length;
			break;
		default:
			this.etat = 99;
			break;
		}
		
	}

	
	/**
	 * Analyse l'expression décrivant une charge pour en déterminer la représentation, nombre, couleur(s)...
	 */
	private void AnalyserCharge(String chargeStr, Charge charge) {
		System.out.println("Analyse de la charge : " + chargeStr);
		
		String[] exp = chargeStr.split(" ");
		
		// Correspondance entre mot et nombre (un/une = 1)
		String[] nombresStr = {"un", "une", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf", "dix", "onze",
								"douze", "treize", "quatorze", "quinze", "seize", "dix-sept", "dix-huit", "dix-neuf", "vingt"};
		
		boolean meuble = false;
		boolean piece = false;
		
		int position = 0;
		
		// Pièce (16) si article défini (18), meuble (17) si article indéfini (24)
		switch(exp[0].toLowerCase()) {
		case "à":
			// Article défini: pièce
			if(exp[1].equalsIgnoreCase("la")) {
				charge.SetNombre(1);
				piece = true;
				position = 2;
			}
			else if (exp[1].substring(0, 2).equalsIgnoreCase("l'")) {
				charge.SetNombre(1);
				piece = true;
				position = 1;
				// On retire le "l'" 
				exp[1] = exp[1].substring(2, exp[1].length());
			}
			// Nombre: meuble
			else {
				// Nombre attendu
				int index = ArrayUtils.indexOf(nombresStr, exp[1]);
				if(index != -1) {
					if(index == 0) {
						index = 1;
					}
					meuble = true;
					position = 2;
					charge.SetNombre(index);
				}
			}
			break;
		case "au":
			// Piece
			charge.SetNombre(1);
			piece = true;
			position = 1;
			break;
		case "aux":
			// Pièce, nombre attendu
			int index = ArrayUtils.indexOf(nombresStr, exp[1]);
			if(index != 1) {
				if(index == 0) {
					index = 1;
				}
				piece = true;
				position = 2;
				charge.SetNombre(index);
			}
			break;
		case "de":
			// Meuble
			// Nombre attendu
			index = ArrayUtils.indexOf(nombresStr, exp[1]);
			if(index != -1) {
				if(index == 0) {
					index = 1;
				}
				meuble = true;
				position = 2;
				charge.SetNombre(index);
			}
			break;
		default:
			// Meuble pour "d'un" ou "d'une"
			if(exp[0].equalsIgnoreCase("d'un") || exp[0].equalsIgnoreCase("d'une")) {
				meuble = true;
				position = 1;
				charge.SetNombre(1);
			}
			break;
		}
		
		if(piece) {
			System.out.println("Pièce identifiée");
		}
		else if(meuble) {
			System.out.println("Meuble identifié");
		}
		else {
			System.out.println("Charge non identifiée");
		}
		
		// Récupération de la dénomination, on va jusqu'à la couleur
		Couleur c = null;
		int testeurPos = TesteurExpression.nouvPosition;
		int origine = position;
		while(c == null && position < exp.length) {
			if(TesteurExpression.EstUneCouleur(exp, position)) {
				// On récupère la couleur
				c = new Couleur(exp[TesteurExpression.nouvPosition - 1]);
				charge.GetCouleurs().add(c);
				
				String repCharge = "";
				for(int i = origine; i < position; i++) {
					repCharge += exp[i] + " ";
				}
								
				charge.SetRepresentation(repCharge);
				
				break;
			}
			position++;
		}
		
		// On réinitialise la position du testeur
		TesteurExpression.nouvPosition = testeurPos;
		
	}
	
}

















