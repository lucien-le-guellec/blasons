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

public class TestAnalyse {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void Test1() {
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("D'or aux trois écussons de gueules.");
		a.Analyser();
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur or
		assertEquals(c.getGreen(), 241);
		assertEquals(c.getBlue(), 0);
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetRepresentation(), "trois écussons de gueules");
	}
	
	@Test
	public void Test2() {
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("D'azur au lion d'argent.");
		a.Analyser();
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur azur
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 255);
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetRepresentation(), "lion d'argent");
	}
	
	@Test
	public void Test3() {
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("De gueules plein.");
		a.Analyser();
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur gueules
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
		assertEquals(b.GetQuartierCourant().GetChamp().GetCharges().size(), 0); // Aucune charge (plein)
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 0); // 0 charge
	}
	
	@Test
	public void Test4() {
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("De gueules à trois coquilles d'or.");
		a.Analyser();
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur gueules
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetRepresentation(), "trois coquilles d'or");
	}
	
	@Test
	public void Test5() {
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("De gueules au sautoir d'or.");
		a.Analyser();
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur gueules
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetRepresentation(), "sautoir d'or");
	}
	
	@Test
	public void Test6() {
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("Écartelé d'or et de gueules.");
		a.Analyser();
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
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 0); // 0 charge
	}
	
	@Test
	public void Test7() {
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("De sable à une fasce d'or.");
		a.Analyser();
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur sable
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetRepresentation(), "une fasce d'or");
	}
	
	@Test
	public void Test8() {
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("D'azur à l'aigle d'or.");
		a.Analyser();
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur azur
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 255);
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetRepresentation(), "aigle d'or");
	}
	
	@Test
	public void Test9() {
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("De sable, au lion d'argent.");
		a.Analyser();
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur azur
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetRepresentation(), "lion d'argent");
	}
	
	@Test
	public void Test10() {
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("D'argent à un écureuil de gueules.");
		a.Analyser();
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 231); // Couleur argent
		assertEquals(c.getGreen(), 231);
		assertEquals(c.getBlue(), 231);
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetRepresentation(), "un écureuil de gueules");
	}
	
	@Test
	public void Test11() {
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("D'azur au chef de sinople.");
		a.Analyser();
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur azur
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 255);
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetRepresentation(), "chef de sinople");
	}
	
	@Test
	public void Test101() {
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("D'azur au chevron d'argent, accompagné de trois roses du même.");
		a.Analyser();
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 0); // Couleur azur
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 255);
		List<Charge> cs = b.GetQuartierCourant().GetChamp().GetCharges();
		assertEquals(cs.size(), 1); // 1 charge
		assertEquals(cs.get(0).GetRepresentation(), "chevron d'argent");
	}

}
