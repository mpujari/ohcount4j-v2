package net.ohloh.ohcount4j.scan;

import org.testng.annotations.Test;

import net.ohloh.ohcount4j.scan.JavaScriptScanner;
import static net.ohloh.ohcount4j.Entity.*;
import net.ohloh.ohcount4j.Language;

public class JavaScriptScannerTest extends BaseScannerTest {

	@Test
	public void basic() {
		assertLine(new JavaScriptScanner(), new Line(Language.JAVASCRIPT, BLANK),   "\n");
		assertLine(new JavaScriptScanner(), new Line(Language.JAVASCRIPT, BLANK),   "     \n");
		assertLine(new JavaScriptScanner(), new Line(Language.JAVASCRIPT, BLANK),   "\t\n");
		assertLine(new JavaScriptScanner(), new Line(Language.JAVASCRIPT, CODE),    "function() {};\n");
		assertLine(new JavaScriptScanner(), new Line(Language.JAVASCRIPT, COMMENT), "/* comment */\n");
		assertLine(new JavaScriptScanner(), new Line(Language.JAVASCRIPT, CODE),    "function() {}; /* with comment */\n");
	}

	@Test
	public void eofHandling() {
		// Note lack of trailing \n in all cases below
		assertLine(new JavaScriptScanner(), new Line(Language.JAVASCRIPT, BLANK),   "     ");
		assertLine(new JavaScriptScanner(), new Line(Language.JAVASCRIPT, BLANK),   "\t");
		assertLine(new JavaScriptScanner(), new Line(Language.JAVASCRIPT, CODE),    "function() {};");
		assertLine(new JavaScriptScanner(), new Line(Language.JAVASCRIPT, COMMENT), "/* comment */");
		assertLine(new JavaScriptScanner(), new Line(Language.JAVASCRIPT, CODE),    "function() {}; /* with comment */");
	}

	@Test
	public void escapedCharsInStrings() {
		// A literal newline character embedded within a one-line string should not be
		// incorrectly counted as two lines of code
		assertLine(new JavaScriptScanner(), new Line(Language.JAVASCRIPT, CODE),
				"var str = \"a newline literal \\n in a string\";");
	}

	@Test
	public void helloWorld() {
		String code
			= "/* Hello World */\n"
			+ "\n"
			+ "$(document).ready(function() {\n"
			+ "\talert(\"Hello, world!\\n\");\n"
			+ "});\n";

		Line[] expected = {
			new Line(Language.JAVASCRIPT, COMMENT),
			new Line(Language.JAVASCRIPT, BLANK),
			new Line(Language.JAVASCRIPT, CODE),
			new Line(Language.JAVASCRIPT, CODE),
			new Line(Language.JAVASCRIPT, CODE)
		};
		assertLines(new JavaScriptScanner(), expected, code);
	}
}
