package com.epa.testdriver;

public class Imprimir {

	
  public native void displayHelloWorld();

	    static {
	        System.loadLibrary("Imprimir");
	    }

}
