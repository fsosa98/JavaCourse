package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import hr.fer.zemris.java.hw03.prob1.LexerException;

class SmartScriptLexerTest {

	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}

	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(),
				"Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return
		// each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");

		SmartScriptToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

	@Test
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testNoActualContent() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");

		assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
	}

	@Test
	public void testTwoWords() {
		// Lets check for several words...
		SmartScriptLexer lexer = new SmartScriptLexer("  Štefanija\r\n\t Automobil   ");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.TEXT, "  Štefanija\r\n\t Automobil   "),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testEscape1() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example { bla } blu \\{$=1$}. Nothing interesting {=here}");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.TEXT,
						"Example { bla } blu \\{$=1$}. Nothing interesting {=here}"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testEscape2() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example \\{$=1$}. Now actually write one {$=1$}");

		checkToken(lexer.nextToken(),
				new SmartScriptToken(SmartScriptTokenType.TEXT, "Example \\{$=1$}. Now actually write one "));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, "{$"));

		lexer.setState(SmartScriptStates.TAG);

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAGNAME, "="));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, 1));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, "$}"));

		lexer.setState(SmartScriptStates.TEXT);

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
	}

	@Test
	public void testNullState() {
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer("").setState(null));
	}

	@Test
	public void testNotNullInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptStates.TAG);

		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testEmptyInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptStates.TAG);

		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(),
				"Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNextInExtended() {
		// Calling getToken once or several times after calling nextToken must return
		// each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptStates.TAG);

		SmartScriptToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

	@Test
	public void testRadAfterEOFInExtended() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptStates.TAG);

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testTokenTypes() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$= i i * @sin  \"0.000\" @decfmt $}");

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, "{$"));

		lexer.setState(SmartScriptStates.TAG);

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAGNAME, "="));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.OPERATOR, "*"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.FUNCTION, "@sin"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.STRING, "\"0.000\""));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.FUNCTION, "@decfmt"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, "$}"));

		lexer.setState(SmartScriptStates.TEXT);

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
	}

	@Test
	public void testWithBlanks() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$    FOR    sco_re            \"-1\"10 \"1\" $}");

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, "{$"));

		lexer.setState(SmartScriptStates.TAG);

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAGNAME, "FOR"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "sco_re"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.STRING, "\"-1\""));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, 10));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.STRING, "\"1\""));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, "$}"));

		lexer.setState(SmartScriptStates.TEXT);

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
	}

	@Test
	public void testVariableName1() {
		SmartScriptLexer lexer = new SmartScriptLexer("A7_bb");

		lexer.setState(SmartScriptStates.TAG);

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "A7_bb"));

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
	}

	@Test
	public void testVariableName2() {
		SmartScriptLexer lexer = new SmartScriptLexer("_a21");

		lexer.setState(SmartScriptStates.TAG);

		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testFunctionName() {
		SmartScriptLexer lexer = new SmartScriptLexer("@5da");

		lexer.setState(SmartScriptStates.TAG);

		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testNegativeNum() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i-1.35bbb\"1\" $}");

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, "{$"));

		lexer.setState(SmartScriptStates.TAG);

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAGNAME, "FOR"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.DOUBLE, -1.35));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "bbb"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.STRING, "\"1\""));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, "$}"));

		lexer.setState(SmartScriptStates.TEXT);

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
	}

	@Test
	public void testString() {
		SmartScriptLexer lexer = new SmartScriptLexer("A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}.");

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TEXT, "A tag follows "));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, "{$"));

		lexer.setState(SmartScriptStates.TAG);

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAGNAME, "="));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.STRING, "\"Joe \"Long\" Smith\""));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, "$}"));

		lexer.setState(SmartScriptStates.TEXT);

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TEXT, "."));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
	}

	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(SmartScriptLexer lexer, SmartScriptToken[] correctData) {
		int counter = 0;
		for (SmartScriptToken expected : correctData) {
			SmartScriptToken actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}

	private void checkToken(SmartScriptToken actual, SmartScriptToken expected) {
		String msg = "Token are not equal.";
		assertEquals(expected.getType(), actual.getType(), msg);
		assertEquals(expected.getValue(), actual.getValue(), msg);
	}

}
