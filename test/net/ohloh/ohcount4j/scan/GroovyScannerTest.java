package net.ohloh.ohcount4j.scan;

import org.testng.annotations.Test;

import static net.ohloh.ohcount4j.Entity.*;
import net.ohloh.ohcount4j.Language;

public class GroovyScannerTest extends BaseScannerTest {

	@Test
	public void basic() {
		assertLine(new GroovyScanner(), new Line(Language.GROOVY, BLANK),   "\n");
		assertLine(new GroovyScanner(), new Line(Language.GROOVY, BLANK),   "     \n");
		assertLine(new GroovyScanner(), new Line(Language.GROOVY, BLANK),   "\t\n");
		assertLine(new GroovyScanner(), new Line(Language.GROOVY, CODE),    "def name='World'; println \"Hello $name!\"\n");
		assertLine(new GroovyScanner(), new Line(Language.GROOVY, COMMENT), "/* Block Comment */\n");
		assertLine(new GroovyScanner(), new Line(Language.GROOVY, COMMENT), "// Line comment\n");
		assertLine(new GroovyScanner(), new Line(Language.GROOVY, COMMENT), "//\n");
		assertLine(new GroovyScanner(), new Line(Language.GROOVY, CODE),    "def name='World'; println \"Hello $name!\" // with comment\n");
	}

	@Test
	public void eofHandling() {
		// Note lack of trailing \n in all cases below
		assertLine(new GroovyScanner(), new Line(Language.GROOVY, BLANK),   "     ");
		assertLine(new GroovyScanner(), new Line(Language.GROOVY, BLANK),   "\t");
		assertLine(new GroovyScanner(), new Line(Language.GROOVY, CODE),    "def name='World'; println \"Hello $name!\"");
		assertLine(new GroovyScanner(), new Line(Language.GROOVY, COMMENT), "/* Block Comment */");
		assertLine(new GroovyScanner(), new Line(Language.GROOVY, COMMENT), "// Line comment");
		assertLine(new GroovyScanner(), new Line(Language.GROOVY, COMMENT), "//");
		assertLine(new GroovyScanner(), new Line(Language.GROOVY, CODE),    "def name='World'; println \"Hello $name!\" // with comment");
	}

	@Test
	public void helloWorld() {
		String code
			= "/* Groovy Style\n"
			+ "\n"
			+ "Hello World Program*/\n"
			+ "class Greet {\n"
			+ "	  def name\n"
			+ "	  Greet(who) { name = who[0].toUpperCase() +\n"
			+ "	                      who[1..-1] }\n"
			+ "	  def salute() { println \"Hello $name!\" }\n"
			+ "}\n"
			+ "\n"
			+ "g = new Greet('world')  // create object\n"
			+ "g.salute()              // output \"Hello World!\";\n";



		Line[] expected = {
			new Line(Language.GROOVY, COMMENT),
			new Line(Language.GROOVY, BLANK),
			new Line(Language.GROOVY, COMMENT),
			new Line(Language.GROOVY, CODE),
			new Line(Language.GROOVY, CODE),
			new Line(Language.GROOVY, CODE),
			new Line(Language.GROOVY, CODE),
			new Line(Language.GROOVY, CODE),
			new Line(Language.GROOVY, CODE),
			new Line(Language.GROOVY, BLANK),
			new Line(Language.GROOVY, CODE),
			new Line(Language.GROOVY, CODE)
		};
		assertLines(new GroovyScanner(), expected, code);
	}

}
