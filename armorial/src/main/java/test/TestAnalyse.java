package test;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import analyse.AnalyseurSyntaxique;
import modele.Blason;
import modele.Charge;
import ressources.Partitions;
import structures.Couleur;

public class TestAnalyse {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void Test1() {
		System.out.println("Test 1");
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("D'or aux trois écussons de gueules.");
		a.Analyser();
		
		// Général
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur or
		assertEquals(c.getGreen(), 241);
		assertEquals(c.getBlue(), 0);
		
		// Charges
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "trois écussons de gueules");
		assertEquals(cs.get(0).GetNombre(), 3);
		assertEquals(cs.get(0).GetRepresentation(), "écussons");
		List<Couleur> cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur gueules
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
	}
	
	@Test
	public void Test2() {
		System.out.println("Test 2");
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("D'azur au lion d'argent.");
		a.Analyser();
		
		// Général
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur azur
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 255);
		
		// Charges
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "lion d'argent");
		assertEquals(cs.get(0).GetNombre(), 1);
		assertEquals(cs.get(0).GetRepresentation(), "lion");
		List<Couleur> cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 231); // Couleur argent
		assertEquals(c.getGreen(), 231);
		assertEquals(c.getBlue(), 231);
	}
	
	@Test
	public void Test3() {
		System.out.println("Test 3");
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("De gueules plein.");
		a.Analyser();
		
		// Général
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur gueules
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
		assertEquals(b.GetQuartierCourant().GetChamp().GetCharges().size(), 0); // Aucune charge (plein)
		
		// Charges
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 0); // 0 charge
	}
	
	@Test
	public void Test4() {
		System.out.println("Test 4");
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("De gueules à trois coquilles d'or.");
		a.Analyser();
		
		// Général
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur gueules
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
		
		// Charges
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "trois coquilles d'or");
		assertEquals(cs.get(0).GetNombre(), 3);
		assertEquals(cs.get(0).GetRepresentation(), "coquilles");
		List<Couleur> cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur or
		assertEquals(c.getGreen(), 241);
		assertEquals(c.getBlue(), 0);
	}
	
	@Test
	public void Test5() {
		System.out.println("Test 5");
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("De gueules au sautoir d'or.");
		a.Analyser();
		
		// Général
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur gueules
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
		
		// Charges
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "sautoir d'or");
		assertEquals(cs.get(0).GetNombre(), 1);
		assertEquals(cs.get(0).GetRepresentation(), "sautoir");
		List<Couleur> cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur or
		assertEquals(c.getGreen(), 241);
		assertEquals(c.getBlue(), 0);
	}
	
	@Test
	public void Test6() {
		System.out.println("Test 6");
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("Écartelé d'or et de gueules.");
		a.Analyser();
		
		// Général
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		assertEquals(b.GetQuartierCourant().GetChamp().GetPartition().GetPartition(), Partitions.ECARTELE); // écartelé
		System.out.println(b.GetQuartierCourant().GetChamp().GetCouleurs().size());
		assertEquals(b.GetQuartierCourant().GetChamp().GetCouleurs().size(), 2); // 2 couleurs
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur 1: or
		assertEquals(c.getGreen(), 241);
		assertEquals(c.getBlue(), 0);
		c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(1).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur 2: gueules
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
		
		// Charges
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 0); // 0 charge
	}
	
	@Test
	public void Test7() {
		System.out.println("Test 7");
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("De sable à une fasce d'or.");
		a.Analyser();
		
		// Général
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur sable
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
		
		// Charges
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "une fasce d'or");
		assertEquals(cs.get(0).GetNombre(), 1);
		assertEquals(cs.get(0).GetRepresentation(), "fasce");
		List<Couleur> cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur or
		assertEquals(c.getGreen(), 241);
		assertEquals(c.getBlue(), 0);
	}
	
	@Test
	public void Test8() {
		System.out.println("Test 8");
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("D'azur à l'aigle d'or.");
		a.Analyser();
		
		// Général
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur azur
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 255);
		
		// Charges
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "aigle d'or");
		assertEquals(cs.get(0).GetNombre(), 1);
		assertEquals(cs.get(0).GetRepresentation(), "aigle");
		List<Couleur> cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur or
		assertEquals(c.getGreen(), 241);
		assertEquals(c.getBlue(), 0);
	}
	
	@Test
	public void Test9() {
		System.out.println("Test 9");
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("De sable, au lion d'argent.");
		a.Analyser();
		
		// Général
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur azur
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
		
		// Charges
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "lion d'argent");
		assertEquals(cs.get(0).GetNombre(), 1);
		assertEquals(cs.get(0).GetRepresentation(), "lion");
		List<Couleur> cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 231); // Couleur argent
		assertEquals(c.getGreen(), 231);
		assertEquals(c.getBlue(), 231);
	}
	
	@Test
	public void Test10() {
		System.out.println("Test 10");
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("D'argent à un écureuil de gueules.");
		a.Analyser();
		
		// Général
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 231); // Couleur argent
		assertEquals(c.getGreen(), 231);
		assertEquals(c.getBlue(), 231);
		
		// Charges
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "un écureuil de gueules");
		assertEquals(cs.get(0).GetNombre(), 1);
		assertEquals(cs.get(0).GetRepresentation(), "écureuil");
		List<Couleur> cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur gueules
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
	}
	
	@Test
	public void Test11() {
		System.out.println("Test 11");
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("D'azur au chef de sinople.");
		a.Analyser();
		
		// Général
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur azur
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 255);
		
		// Charges
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "chef de sinople");
		assertEquals(cs.get(0).GetNombre(), 1);
		assertEquals(cs.get(0).GetRepresentation(), "chef");
		List<Couleur> cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur sinople
		assertEquals(c.getGreen(), 255);
		assertEquals(c.getBlue(), 0);
	}
	
	@Test
	public void Test101() {
		System.out.println("Test 101");
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("D'azur au chevron d'argent, accompagné de trois roses du même.");
		a.Analyser();
		
		// Général
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur azur
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 255);
		
		// Charges
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 2); // 2 charges
		assertEquals(cs.get(0).GetExpression(), "chevron d'argent");
		assertEquals(cs.get(0).GetNombre(), 1);
		assertEquals(cs.get(0).GetRepresentation(), "chevron");
		List<Couleur> cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 231); // Couleur argent
		assertEquals(c.getGreen(), 231);
		assertEquals(c.getBlue(), 231);
		assertEquals(cs.get(1).GetExpression(), "trois roses du même");
		assertEquals(cs.get(1).GetNombre(), 3);
		assertEquals(cs.get(1).GetRepresentation(), "roses");
		cls = cs.get(1).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 231); // Couleur argent (même)
		assertEquals(c.getGreen(), 231);
		assertEquals(c.getBlue(), 231);
	}

	@Test
	public void Test202() {
		System.out.println("Test 202");
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("Coupé : au premier de gueules aux trois étoiles d'argent rangées en fasce, au second d'argent à la rivière de sinople en fasce.");
		a.Analyser();
		
		// Général
		Blason b = a.GetBlason();
		assertEquals(b.GetPartitionnement().GetPartition(), Partitions.COUPE);
		assertEquals(b.GetQuartiers().size(), 2); // 2 quartiers
		
		// 1er quartier
		Color c = b.GetQuartiers().get(0).GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur gueules
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
		// Charge
		List<Charge> cs = b.GetQuartiers().get(0).GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "trois étoiles d'argent rangées en fasce");
		assertEquals(cs.get(0).GetNombre(), 3);
		assertEquals(cs.get(0).GetRepresentation(), "étoiles");
		List<Couleur> cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 231); // Couleur argent
		assertEquals(c.getGreen(), 231);
		assertEquals(c.getBlue(), 231);
		
		// second quartier
		c = b.GetQuartiers().get(1).GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 231); // Couleur argent
		assertEquals(c.getGreen(), 231);
		assertEquals(c.getBlue(), 231);
		// Charge
		cs = b.GetQuartiers().get(1).GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "rivière de sinople en fasce");
		assertEquals(cs.get(0).GetNombre(), 1);
		assertEquals(cs.get(0).GetRepresentation(), "rivière");
		cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur sinople
		assertEquals(c.getGreen(), 255);
		assertEquals(c.getBlue(), 0);
	}

	@Test
	public void Test203() {
		System.out.println("Test 203");
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("Parti au 1 d'azur à la bande d'or chargé de trois cosses de pois de sinople, au 2 d'argent à la fasce de sinople accompagnée de trois trèfles du même.");
		a.Analyser();
		
		// Général
		Blason b = a.GetBlason();
		assertEquals(b.GetPartitionnement().GetPartition(), Partitions.PARTI);
		assertEquals(b.GetQuartiers().size(), 2); // 2 quartiers
		
		// 1er quartier
		Color c = b.GetQuartiers().get(0).GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur azur
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 255);
		// Charge
		List<Charge> cs = b.GetQuartiers().get(0).GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "bande d'or chargé de trois cosses de pois de sinople");
		assertEquals(cs.get(0).GetNombre(), 1);
		assertEquals(cs.get(0).GetRepresentation(), "bande");
		List<Couleur> cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur or
		assertEquals(c.getGreen(), 241);
		assertEquals(c.getBlue(), 0);
		
		// second quartier
		c = b.GetQuartiers().get(1).GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 231); // Couleur argent
		assertEquals(c.getGreen(), 231);
		assertEquals(c.getBlue(), 231);
		// Charges
		cs = b.GetQuartiers().get(1).GetChamp().GetCharges();
		assertEquals(cs.size(), 2); // 2 charges
		// 1ere
		assertEquals(cs.get(0).GetExpression(), "fasce de sinople");
		assertEquals(cs.get(0).GetNombre(), 1);
		assertEquals(cs.get(0).GetRepresentation(), "fasce");
		cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur sinople
		assertEquals(c.getGreen(), 255);
		assertEquals(c.getBlue(), 0);
		// 2eme
		assertEquals(cs.get(1).GetExpression(), "trois trèfles du même");
		assertEquals(cs.get(1).GetNombre(), 3);
		assertEquals(cs.get(1).GetRepresentation(), "trèfles");
		cls = cs.get(1).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur sinople
		assertEquals(c.getGreen(), 255);
		assertEquals(c.getBlue(), 0);
	}
	
	@Test
	public void Test205() {
		System.out.println("Test 205");
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("Ecartelé : aux 1 et 4, d'azur, à une coquille d'argent ; aux 2 et 3, d'argent, à une branche de fougère de sinople posée en pal.");
		a.Analyser();
		
		// Général
		Blason b = a.GetBlason();
		assertEquals(b.GetPartitionnement().GetPartition(), Partitions.ECARTELE);
		assertEquals(b.GetQuartiers().size(), 4); // 4 quartiers
		assertEquals(b.GetQuartiers().get(0), b.GetQuartiers().get(3)); // Premier quartier identique au quatrième
		assertEquals(b.GetQuartiers().get(1), b.GetQuartiers().get(2)); // Deuxième quartier identique au troisième
		
		// 1er quartier
		Color c = b.GetQuartiers().get(0).GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur azur
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 255);
		// Charge
		List<Charge> cs = b.GetQuartiers().get(0).GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "une coquille d'argent");
		assertEquals(cs.get(0).GetNombre(), 1);
		assertEquals(cs.get(0).GetRepresentation(), "coquille");
		List<Couleur> cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 231); // Couleur argent
		assertEquals(c.getGreen(), 231);
		assertEquals(c.getBlue(), 231);
		
		// second quartier
		c = b.GetQuartiers().get(1).GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 231); // Couleur argent
		assertEquals(c.getGreen(), 231);
		assertEquals(c.getBlue(), 231);
		// Charge
		cs = b.GetQuartiers().get(1).GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "une branche de fougère de sinople posée en pal");
		assertEquals(cs.get(0).GetNombre(), 1);
		assertEquals(cs.get(0).GetRepresentation(), "branche de fougère");
		cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur sinople
		assertEquals(c.getGreen(), 255);
		assertEquals(c.getBlue(), 0);
	}
	
	@Test
	public void Test204() {
		System.out.println("Test 204");
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("Coupé : au premier de gueules aux trois étoiles d'argent rangées en fasce, au second d'argent à la rivière de sinople en fasce.");
		a.Analyser();
		
		// Général
		Blason b = a.GetBlason();
		assertEquals(b.GetPartitionnement().GetPartition(), Partitions.COUPE);
		assertEquals(b.GetQuartiers().size(), 2); // 2 quartiers
		
		// 1er quartier
		Color c = b.GetQuartiers().get(0).GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur gueules
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
		// Charge
		List<Charge> cs = b.GetQuartiers().get(0).GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "trois étoiles d'argent rangées en fasce");
		assertEquals(cs.get(0).GetNombre(), 3);
		assertEquals(cs.get(0).GetRepresentation(), "étoiles");
		List<Couleur> cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 231); // Couleur argent
		assertEquals(c.getGreen(), 231);
		assertEquals(c.getBlue(), 231);
		
		// second quartier
		c = b.GetQuartiers().get(1).GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 231); // Couleur argent
		assertEquals(c.getGreen(), 231);
		assertEquals(c.getBlue(), 231);
		// Charge
		cs = b.GetQuartiers().get(1).GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "rivière de sinople en fasce");
		assertEquals(cs.get(0).GetNombre(), 1);
		assertEquals(cs.get(0).GetRepresentation(), "rivière");
		cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur sinople
		assertEquals(c.getGreen(), 255);
		assertEquals(c.getBlue(), 0);
	}
	
	@Test
	public void Test206() {
		System.out.println("Test 206");
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("Écartelé : aux 1 et 4 d'azur au lévrier courant de sable ; aux 2 et 3 de sable au lion d'or morné.");
		a.Analyser();
		
		// Général
		Blason b = a.GetBlason();
		assertEquals(b.GetPartitionnement().GetPartition(), Partitions.ECARTELE);
		assertEquals(b.GetQuartiers().size(), 4); // 4 quartiers
		assertEquals(b.GetQuartiers().get(0), b.GetQuartiers().get(3)); // Premier quartier identique au quatrième
		assertEquals(b.GetQuartiers().get(1), b.GetQuartiers().get(2)); // Deuxième quartier identique au troisième
		
		// 1er quartier
		Color c = b.GetQuartiers().get(0).GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur azur
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 255);
		// Charge
		List<Charge> cs = b.GetQuartiers().get(0).GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "lévrier courant de sable");
		assertEquals(cs.get(0).GetNombre(), 1);
		assertEquals(cs.get(0).GetRepresentation(), "lévrier courant");
		List<Couleur> cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur sable
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
		
		// second quartier
		c = b.GetQuartiers().get(1).GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur sable
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
		// Charge
		cs = b.GetQuartiers().get(1).GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetExpression(), "lion d'or morné");
		assertEquals(cs.get(0).GetNombre(), 1);
		assertEquals(cs.get(0).GetRepresentation(), "lion");
		cls = cs.get(0).GetCouleurs();
		assertEquals(cls.size(), 1); // 1 couleur
		c = cls.get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur or
		assertEquals(c.getGreen(), 241);
		assertEquals(c.getBlue(), 0);
	}
}
