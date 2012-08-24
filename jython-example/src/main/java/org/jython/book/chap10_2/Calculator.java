package org.jython.book.chap10_2;

/**
 * Java calculator class that contains two simple methods
 */
public class Calculator {
	
	public Calculator() {
		
	}
	
	public double calculateTip(double cost, double tipPercentage) {
		return cost * tipPercentage;
	}
	
	public double calculateTax(double cost, double taxPercentage) {
		return cost * taxPercentage;
	}
	
}
