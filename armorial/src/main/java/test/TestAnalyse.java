package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import analyse.AnalyseurSyntaxique;

public class TestAnalyse {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void TestAnalyse() {
		AnalyseurSyntaxique a = new AnalyseurSyntaxique("D'or aux trois écussons de gueules.");
		a.Analyser();
		
		a = new AnalyseurSyntaxique("De gueules plein.");
		a.Analyser();
		
		a = new AnalyseurSyntaxique("Écartelé d'or et de gueules.");
		a.Analyser();
	}

}
