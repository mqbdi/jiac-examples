package org.jython.book.chap10_1;
import org.python.util.PythonInterpreter;


final class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PythonInterpreter i = new PythonInterpreter();
//		>>> from org.jython.book.chap10_1 import Beach
//		>>> beach = Beach("Cocoa Beach","Cocoa Beach")
//		>>> beach.getName()
//		>>> print beach.getName()
		i.exec("from org.jython.book.chap10_1 import Beach");
		i.exec("beach = Beach(\"Cocoa Beach\",\"Cocoa Beach\")");
//		i.exec("beach.getName()");
		i.exec("print beach.getName()");
	}
	
}
