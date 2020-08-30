package api.embedded.translator;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.junit.Test;

public class AppControllerTest {

	@Test
	public void testGetTranslation() throws HttpException, IOException {
		AppController tester = new AppController();
		String name = "charizard";
		tester.getTranslation(name);
		//System.out.println( tester.getTranslation(description) );
	}
	@Test
	public void testGetTranslation_1() throws HttpException, IOException {
		AppController tester = new AppController();
		String name = "...";
		tester.getTranslation(name);
		//System.out.println( tester.getTranslation("...") );
	}

	@Test
	public void testGetShakespearean() throws IOException {
		AppController tester = new AppController();
		String description = "Spits fire that\nis hot enough to\nmelt boulders.\fKnown to cause\nforest fires\nunintentionally.";
		tester.getShakespearean(description);
		//System.out.println( tester.getShakespearean(description) );
	}

	@Test
	public void testGetPokemon() throws IOException {
		AppController tester = new AppController();		
		String pokemon = "...";
		tester.getPokemon(pokemon);

	}
	@Test
	public void testGetPokemon_1() throws IOException {
		AppController tester = new AppController();		
		String pokemon = "charizard";
		tester.getPokemon(pokemon);
	}

}
