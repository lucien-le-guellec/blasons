package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import structures.Couleur;

public class TestStructures {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCouleurs() {
		Couleur c1 = new Couleur("azur");
		assertEquals(c1.getCouleur().getRed(), 0);
		assertEquals(c1.getCouleur().getGreen(), 0);
		assertEquals(c1.getCouleur().getBlue(), 255);
		
		Couleur c2 = new Couleur("AzUr");
		assertEquals(c1.getCouleur(), c2.getCouleur());
		
		c1 = new Couleur("orangé");
		assertEquals(c1.getCouleur().getRed(), 255);
		assertEquals(c1.getCouleur().getGreen(), 128);
		assertEquals(c1.getCouleur().getBlue(), 0);
		
		c2 = new Couleur("orange");
		assertEquals(c1.getCouleur(), c2.getCouleur());
		
		c2 = new Couleur("oRaNgé");
		assertEquals(c1.getCouleur(), c2.getCouleur());
	}

}
