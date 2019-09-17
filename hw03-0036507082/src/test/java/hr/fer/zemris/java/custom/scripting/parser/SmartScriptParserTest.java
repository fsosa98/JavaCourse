package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.node.DocumentNode;

import static hr.fer.zemris.java.hw03.SmartScriptTester.*;

class SmartScriptParserTest {

	@Test
	void testParser1() {
		String document1 = loader("document1.txt");
		SmartScriptParser parser1 = null;
		try {
			parser1 = new SmartScriptParser(document1);
		} catch (SmartScriptParserException e) {
			fail();
			System.exit(1);
		} catch (Exception e) {
			fail("If this line ever executes, you have failed this class!");
			System.exit(1);
		}
		DocumentNode doc1 = parser1.getDocumentNode();
		String originalDocumentBody1 = createOriginalDocumentBody(doc1);

		SmartScriptParser parser2 = null;
		try {
			parser2 = new SmartScriptParser(originalDocumentBody1);
		} catch (SmartScriptParserException e) {
			fail();
			System.exit(1);
		} catch (Exception e) {
			fail("If this line ever executes, you have failed this class!");
			System.exit(1);
		}
		DocumentNode doc2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(doc2);

		assertEquals(originalDocumentBody1, originalDocumentBody2);
	}

	@Test
	void testParser2() {
		String document1 = loader("document2.txt");
		SmartScriptParser parser1 = null;
		try {
			parser1 = new SmartScriptParser(document1);
		} catch (SmartScriptParserException e) {
			fail();
			System.exit(1);
		} catch (Exception e) {
			fail("If this line ever executes, you have failed this class!");
			System.exit(1);
		}
		DocumentNode doc1 = parser1.getDocumentNode();
		String originalDocumentBody1 = createOriginalDocumentBody(doc1);

		SmartScriptParser parser2 = null;
		try {
			parser2 = new SmartScriptParser(originalDocumentBody1);
		} catch (SmartScriptParserException e) {
			fail();
			System.exit(1);
		} catch (Exception e) {
			fail("If this line ever executes, you have failed this class!");
			System.exit(1);
		}
		DocumentNode doc2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(doc2);

		assertEquals(originalDocumentBody1, originalDocumentBody2);
	}

	@Test
	void testParserNoEnd() {
		String document = loader("document3.txt");

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(document));

	}

	@Test
	void testParserText() {
		String document1 = loader("document4.txt");
		SmartScriptParser parser1 = null;
		try {
			parser1 = new SmartScriptParser(document1);
		} catch (SmartScriptParserException e) {
			fail();
			System.exit(1);
		} catch (Exception e) {
			fail("If this line ever executes, you have failed this class!");
			System.exit(1);
		}
		DocumentNode doc1 = parser1.getDocumentNode();
		String originalDocumentBody1 = createOriginalDocumentBody(doc1);

		SmartScriptParser parser2 = null;
		try {
			parser2 = new SmartScriptParser(originalDocumentBody1);
		} catch (SmartScriptParserException e) {
			fail();
			System.exit(1);
		} catch (Exception e) {
			fail("If this line ever executes, you have failed this class!");
			System.exit(1);
		}
		DocumentNode doc2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(doc2);

		assertEquals(originalDocumentBody1, originalDocumentBody2);

	}

	@Test
	void testParserForFail() {
		String document = loader("document5.txt");

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(document));

	}

	@Test
	void testParserNoTag1() {
		String document = loader("document6.txt");

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(document));

	}

	@Test
	void testParserNoTag2() {
		String document = loader("document7.txt");

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(document));

	}

	@Test
	void testParserText3() {
		String document1 = loader("document8.txt");
		SmartScriptParser parser1 = null;
		try {
			parser1 = new SmartScriptParser(document1);
		} catch (SmartScriptParserException e) {
			fail();
			System.exit(1);
		} catch (Exception e) {
			fail("If this line ever executes, you have failed this class!");
			System.exit(1);
		}
		DocumentNode doc1 = parser1.getDocumentNode();
		String originalDocumentBody1 = createOriginalDocumentBody(doc1);

		SmartScriptParser parser2 = null;
		try {
			parser2 = new SmartScriptParser(originalDocumentBody1);
		} catch (SmartScriptParserException e) {
			fail();
			System.exit(1);
		} catch (Exception e) {
			fail("If this line ever executes, you have failed this class!");
			System.exit(1);
		}
		DocumentNode doc2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(doc2);

		assertEquals(originalDocumentBody1, originalDocumentBody2);

	}

	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}
}
