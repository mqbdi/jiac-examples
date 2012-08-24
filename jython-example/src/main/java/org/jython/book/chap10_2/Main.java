package org.jython.book.chap10_2;
import org.python.util.PythonInterpreter;


final class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PythonInterpreter i = new PythonInterpreter();
		i.execfile("src/main/java/org/jython/book/chap10_2/JythonCalc.py");
		i.cleanup();
	}
	
}
