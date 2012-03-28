package net.ohloh.ohcount4j.detect;

import net.ohloh.ohcount4j.Language;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import net.ohloh.ohcount4j.SourceFile;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ExtnBResolverTest {

	private ExtnBResolver r;

	@BeforeTest()
	public void setup() {
		this.r = new ExtnBResolver();
	}

	@Test
	public void canResolvetest() {
		assertFalse(r.canResolve(Language.RUBY));
		assertTrue(r.canResolve(Language.CLASSIC_BASIC));
		assertTrue(r.canResolve(Language.STRUCTURED_BASIC));
		assertTrue(r.canResolve(Language.LIMBO));
	}

	@Test
	public void structuredBasicByDefaultTest() throws IOException {
		assertEquals(Language.STRUCTURED_BASIC, r.resolve(new SourceFile("foo.b", "")));
	}

	@Test
	public void classicBasicExamplesTest() throws IOException {
		assertEquals(Language.CLASSIC_BASIC, r.resolve(new SourceFile("foo.b", "10 PRINT 'HELLO WORLD'")));
		assertEquals(Language.CLASSIC_BASIC, r.resolve(new SourceFile("foo.b", "  100 REM")));
	}

	@Test
	public void structuredBasicExamplesTest() throws IOException {
		assertEquals(Language.STRUCTURED_BASIC, r.resolve(new SourceFile("foo.b", "PRINT 'HELLO WORLD'")));
		assertEquals(Language.STRUCTURED_BASIC, r.resolve(new SourceFile("foo.b", "    REM")));
	}

	@Test
	public void limboExamplesTest() throws IOException {
		assertEquals(Language.LIMBO, r.resolve(new SourceFile("foo.b", "implement Foo;")));
		assertEquals(Language.LIMBO, r.resolve(new SourceFile("foo.b", "include \"foo.m\";")));
	}
}