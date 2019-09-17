package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.node.DocumentNode;
import hr.fer.zemris.java.custom.scripting.node.EchoNode;
import hr.fer.zemris.java.custom.scripting.node.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.node.Node;
import hr.fer.zemris.java.custom.scripting.node.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class SmartScriptTester is used for testing SmartScriptParser.
 * 
 * @author Filip
 */
public class SmartScriptTester {

	/**
	 * This is main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Wrong number of arguments");
			System.exit(1);
		}
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		} catch (IOException exc) {
			System.out.println("Wrong path.");
			System.exit(1);
		}

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like original
													// content of docBody
	}

	/**
	 * This method creates string from given DocumentNode.
	 * 
	 * @param document given DocumentNode
	 * @return string
	 */
	public static String createOriginalDocumentBody(DocumentNode document) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < document.numberOfChildren(); i++) {
			Node node = document.getChild(i);
			if (node instanceof TextNode) {
				sb = textNode(sb, (TextNode) node);
			}
			if (node instanceof EchoNode) {
				sb = echoNode(sb, (EchoNode) node);
			}
			if (node instanceof ForLoopNode) {
				forNode(sb, (ForLoopNode) node);
			}
		}
		return new String(sb);
	}

	/**
	 * Helper method for createOriginalDocumentBody.
	 * 
	 * @param sb   StringBuilder
	 * @param node given node
	 * @return StringBuilder with appended elements
	 */
	public static StringBuilder forNode(StringBuilder sb, ForLoopNode node) {
		sb.append("{$FOR");
		sb.append(" ").append(node.getVariable().asText()).append(" ").append(node.getStartExpression().asText())
				.append(" ").append(node.getEndExpression().asText());
		if (node.getStepExpression() != null) {
			sb.append(" ").append(node.getStepExpression().asText());
		}
		sb.append("$}");
		for (int i = 0; i < node.numberOfChildren(); i++) {
			Node nd = node.getChild(i);
			if (nd instanceof TextNode) {
				sb = textNode(sb, (TextNode) nd);
			}
			if (nd instanceof EchoNode) {
				sb = echoNode(sb, (EchoNode) nd);
			}
			if (nd instanceof ForLoopNode) {
				forNode(sb, (ForLoopNode) nd);
			}
		}
		sb.append("{$END$}");
		return sb;
	}

	/**
	 * Helper method for createOriginalDocumentBody.
	 * 
	 * @param sb   StringBuilder
	 * @param node given node
	 * @return StringBuilder with appended elements
	 */
	public static StringBuilder textNode(StringBuilder sb, TextNode node) {
		sb.append(node.getText());
		return sb;
	}

	/**
	 * Helper method for createOriginalDocumentBody.
	 * 
	 * @param sb   StringBuilder
	 * @param node given node
	 * @return StringBuilder with appended elements
	 */
	public static StringBuilder echoNode(StringBuilder sb, EchoNode node) {
		sb.append("{$=");
		Element[] elements = node.getElements();
		for (Element e : elements) {
			sb.append(" ").append(e.asText());
		}
		sb.append("$}");
		return sb;
	}

}
