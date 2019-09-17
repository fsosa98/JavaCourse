package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

public class StackDemo {
	
	public static void main(String[] args) {
		
		if (args.length != 1) {
			System.out.println("Broj argumenata mora biti 1.");
			System.exit(1);
		}
		
		String[] parts = (args[0].strip()).split("\\s+");
		ObjectStack stack = new ObjectStack();
		for (String part : parts) {
			try {
				int number = Integer.parseInt(part);
				stack.push(number);
			}
			catch(NumberFormatException exc) {
				if (stack.isEmpty()) {
					System.out.println("Unijeli ste krivi izraz.");
					System.exit(1);
				}
				int b = (int)stack.pop();
				
				if (stack.isEmpty()) {
					System.out.println("Unijeli ste krivi izraz.");
					System.exit(1);
				}
				int a = (int)stack.pop();
				
				if (part.equals("+")) {
					stack.push(a+b);
				}
				else if (part.equals("-")) {
					stack.push(a-b);
				}
				else if (part.equals("/")) {
					if (b == 0) {
						System.out.println("Dijeljenje s 0 nije dozvoljeno.");
						System.exit(1);
					}
					stack.push(a/b);
				}
				else if (part.equals("*")) {
					stack.push(a*b);
				}
				else if (part.equals("%")) {
					stack.push(a%b);
				}
				else {
					System.out.println("U izrazu postoji znak koji nije niti broj niti operand.");
					System.exit(1);
				}
			}
		}
		if (stack.size() == 1) {
			System.out.println("Expression evaluates to " + stack.pop() + ".");
		}
		else {
			System.out.println("Unijeli ste krivi izraz.");
		}
	}
}
