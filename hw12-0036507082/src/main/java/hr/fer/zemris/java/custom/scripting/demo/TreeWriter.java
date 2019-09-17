package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.node.DocumentNode;
import hr.fer.zemris.java.custom.scripting.node.EchoNode;
import hr.fer.zemris.java.custom.scripting.node.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.node.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.node.Node;
import hr.fer.zemris.java.custom.scripting.node.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Class TreeWriter is used for writing tree.
 * 
 * @author Filip
 */
public class TreeWriter {

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
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}

	/**
	 * Class WriterVisitor is inner static visitor class.
	 * 
	 * @author Filip
	 */
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print("{$FOR " + node.getVariable().asText() + " " + node.getStartExpression().asText() + " "
					+ node.getEndExpression().asText());

			if (node.getStepExpression() != null) {
				System.out.print(" " + node.getStepExpression().asText());
			}
			System.out.print("$}");
			for (int i = 0; i < node.numberOfChildren(); i++) {
				Node nd = node.getChild(i);
				nd.accept(this);
			}
			System.out.print("{$END$}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print("{$=");
			Element[] elements = node.getElements();
			for (Element e : elements) {
				System.out.print(" " + e.asText());
			}
			System.out.print("$}");
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {

			for (int i = 0; i < node.numberOfChildren(); i++) {
				Node nd = node.getChild(i);
				nd.accept(this);
			}
		}

	}
}
