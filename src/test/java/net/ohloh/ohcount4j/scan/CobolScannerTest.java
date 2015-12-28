package net.ohloh.ohcount4j.scan;

import static net.ohloh.ohcount4j.Entity.BLANK;
import static net.ohloh.ohcount4j.Entity.CODE;
import static net.ohloh.ohcount4j.Entity.COMMENT;

import java.io.File;

import net.ohloh.ohcount4j.Language;
import net.ohloh.ohcount4j.SourceFile;

import org.testng.annotations.Test;

public class CobolScannerTest extends AbstractBaseScannerTest {

    @Test
    public void basic() {
        assertLine(Language.COBOL, new Line(Language.COBOL, BLANK), "\n");
        assertLine(Language.COBOL, new Line(Language.COBOL, BLANK), "     \n");
        assertLine(Language.COBOL, new Line(Language.COBOL, BLANK), "\t\n");
        assertLine(Language.COBOL, new Line(Language.COBOL, CODE), "PERFORM UNTIL NOT ValidCharacter\n");
        assertLine(Language.COBOL, new Line(Language.COBOL, COMMENT), "* Line comment\n");
        assertLine(Language.COBOL, new Line(Language.COBOL, COMMENT), "*\n");
    }

    @Test
    public void eofHandling() {
        // Note lack of trailing \n in all cases below
        assertLine(Language.COBOL, new Line(Language.COBOL, BLANK), "     ");
        assertLine(Language.COBOL, new Line(Language.COBOL, BLANK), "\t");
        assertLine(Language.COBOL, new Line(Language.COBOL, CODE), "PERFORM UNTIL NOT ValidCharacter");
        assertLine(Language.COBOL, new Line(Language.COBOL, COMMENT), "* Line comment");
        assertLine(Language.COBOL, new Line(Language.COBOL, COMMENT), "*");
    }

    @Test
    public void sampleTest() {
        String code = "* Sample Program Written in Cobol\n"
                + "\n"
                + "PROCEDURE DIVISION.\n"
                + "Begin.\n"
                + "    DISPLAY \"Enter lower case character or digit. No data ends.\".\n"
                + "    ACCEPT Char.\n"
                + "    PERFORM UNTIL NOT ValidCharacter\n"
                + "        EVALUATE TRUE\n"
                + "           WHEN Vowel DISPLAY \"The letter \" Char \" is a vowel.\"\n"
                + "           WHEN Consonant DISPLAY \"The letter \" Char \" is a consonant.\"\n"
                + "           WHEN Digit DISPLAY Char \" is a digit.\"\n"
                + "           WHEN OTHER DISPLAY \"problems found\"\n"
                + "        END-EVALUATE\n"
                + "    END-PERFORM\n"
                + "    STOP RUN.;\n";

        Line[] expected = {
                new Line(Language.COBOL, COMMENT),
                new Line(Language.COBOL, BLANK),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
        };
        assertLines(Language.COBOL, expected, code);
    }

    @Test
    public void sampleTestWithSourceFile() throws Exception {
        SourceFile sourceFile = new SourceFile(new File(getSourceCodePath("cobol-1.cbl")));
        Line[] expected = {
                new Line(Language.COBOL, COMMENT),
                new Line(Language.COBOL, BLANK),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, CODE),
                new Line(Language.COBOL, BLANK)
        };
        assertLines(Language.COBOL, expected, sourceFile);
    }

}
