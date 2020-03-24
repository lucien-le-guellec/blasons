package test;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import analyse.AnalyseurSyntaxique;
import modele.Blason;
import ressources.Partitions;

public class TestAnalyse {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void TestAnalyseur() {
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("D'or aux trois écussons de gueules.");
		a.Analyser();
		Blason b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		Color c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur or
		assertEquals(c.getGreen(), 241);
		assertEquals(c.getBlue(), 0);
		
		a = new AnalyseurSyntaxique("De gueules plein.");
		a.Analyser();
		b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur gueules
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
		assertEquals(b.GetQuartierCourant().GetChamp().GetCharges().size(), 0); // Aucune charge (plein)
		
		a = new AnalyseurSyntaxique("Écartelé d'or et de gueules.");
		a.Analyser();
		b = a.GetBlason();
		assertEquals(b.GetQuartiers().size(), 1); // 1 quartier
		assertEquals(b.GetQuartierCourant().GetChamp().GetPartition().GetPartition(), Partitions.ECARTELE); // écartelé
		assertEquals(b.GetQuartierCourant().GetChamp().GetCouleurs().size(), 2); // 2 couleurs
		c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(0).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur 1: or
		assertEquals(c.getGreen(), 241);
		assertEquals(c.getBlue(), 0);
		c = b.GetQuartierCourant().GetChamp().GetCouleurs().get(1).getCouleur();
		assertEquals(c.getRed(), 255); // Couleur 2: gueules
		assertEquals(c.getGreen(), 0);
		assertEquals(c.getBlue(), 0);
	}

}
