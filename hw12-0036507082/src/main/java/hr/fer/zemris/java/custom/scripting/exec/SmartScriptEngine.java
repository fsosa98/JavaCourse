package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.node.DocumentNode;
import hr.fer.zemris.java.custom.scripting.node.EchoNode;
import hr.fer.zemris.java.custom.scripting.node.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.node.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.node.Node;
import hr.fer.zemris.java.custom.scripting.node.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class SmartScriptEngine is smart script engine class.
 * 
 * Users can execute.
 * 
 * @author Filip
 */
public class SmartScriptEngine {

	private DocumentNode documentNode;
	private RequestContext requestContext;
	private ObjectMultistack multistack = new ObjectMultistack();

	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variable = node.getVariable().asText();
			Integer start = ((ElementConstantInteger) node.getStartExpression()).getValue();
			Integer step = ((ElementConstantInteger) node.getStepExpression()).getValue();
			Integer end = ((ElementConstantInteger) node.getEndExpression()).getValue();

			multistack.push(variable, new ValueWrapper(start));
			while (((Integer) multistack.peek(variable).getValue()) <= end) {
				for (int i = 0; i < node.numberOfChildren(); i++) {
					Node nd = node.getChild(i);
					nd.accept(this);
				}
				multistack.peek(variable).add(step);
			}
			multistack.pop(variable);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> stack = new Stack<Object>();
			Element[] elements = node.getElements();
			for (Element e : elements) {
				if (e instanceof ElementConstantDouble) {
					stack.push(((ElementConstantDouble) e).getValue());
				}
				if (e instanceof ElementConstantInteger) {
					stack.push(((ElementConstantInteger) e).getValue());
				}
				if (e instanceof ElementVariable) {
					stack.push(multistack.peek(((ElementVariable) e).getName()).getValue());
				}
				if (e instanceof ElementOperator) {
					String sym = ((ElementOperator) e).getSymbol();
					ValueWrapper val1 = new ValueWrapper(stack.pop());
					Object val2 = stack.pop();
					switch (sym) {
					case "+":
						val1.add(val2);
						stack.push(val1.getValue());
						break;
					case "-":
						val1.subtract(val2);
						stack.push(val1.getValue());
						break;
					case "*":
						val1.multiply(val2);
						stack.push(val1.getValue());
						break;
					case "/":
						val1.divide(val2);
						stack.push(val1.getValue());
						break;
					default:
						throw new IllegalArgumentException();
					}
				}
				if (e instanceof ElementFunction) {
					String function = ((ElementFunction) e).getName();
					switch (function) {
					case "@sin":
						Object x = stack.pop();
						if (x instanceof Integer) {
							stack.push(Math.sin((Math.PI / 180.0) * (int) x));
						} else {
							stack.push(Math.sin((Math.PI / 180.0) * (double) x));
						}
						break;
					case "@decfmt":
						Object f = stack.pop();
						Object y = stack.pop();
						stack.push(new DecimalFormat(String.valueOf(f)).format((double) y));
						break;
					case "@dup":
						stack.push(stack.peek());
						break;
					case "@swap":
						Object a = stack.pop();
						Object b = stack.pop();
						stack.push(a);
						stack.push(b);
						break;
					case "@setMimeType":
						Object mimeType = stack.pop();
						requestContext.setMimeType(String.valueOf(mimeType));
						break;
					case "@paramGet":
						Object dv = stack.pop();
						Object name = stack.pop();
						String value = requestContext.getParameter((String) name);
						stack.push(value == null ? String.valueOf(dv) : value);
						break;
					case "@pparamGet":
						Object dv2 = stack.pop();
						Object name2 = stack.pop();
						String value2 = requestContext.getPersistentParameter((String) name2);
						stack.push(value2 == null ? String.valueOf(dv2) : value2);
						break;
					case "@pparamSet":
						Object name3 = stack.pop();
						Object value3 = stack.pop();
						requestContext.setPersistentParameter(String.valueOf(name3), String.valueOf(value3));
						break;
					case "@pparamDel":
						requestContext.removePersistentParameter(String.valueOf(stack.pop()));
						break;
					case "@tparamGet":
						Object dv4 = stack.pop();
						Object name4 = stack.pop();
						String value4 = requestContext.getTemporaryParameter(String.valueOf(name4));
						stack.push(value4 == null ? String.valueOf(dv4) : value4);
						break;
					case "@tparamSet":
						Object name5 = stack.pop();
						Object value5 = stack.pop();
						requestContext.setTemporaryParameter(String.valueOf(name5), String.valueOf(value5));
						break;
					case "@tparamDel":
						requestContext.removeTemporaryParameter(String.valueOf(stack.pop()));
						break;
					default:
						throw new IllegalArgumentException();
					}
				}
				if (e instanceof ElementString) {
					stack.push(((ElementString) e).getValue());
				}
			}
			Stack<Object> revStack = new Stack<Object>();
			while (!stack.isEmpty()) {
				revStack.push(stack.pop());
			}
			while (!revStack.isEmpty()) {
				try {
					requestContext.write(revStack.pop().toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {

			for (int i = 0; i < node.numberOfChildren(); i++) {
				Node nd = node.getChild(i);
				nd.accept(this);
			}
		}
	};

	/**
	 * Constructor with given parameters.
	 * 
	 * @param documentNode
	 * @param requestContext
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		Objects.requireNonNull(documentNode);
		Objects.requireNonNull(requestContext);
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Execute method.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
