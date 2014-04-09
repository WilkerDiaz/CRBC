/**
 * 
 */
package com.beco.sinccompieretda.modelo;


/**
 * @author aavila
 *
 */
public class Dupla {
	private int element1;
	private int element2;
	public Dupla(){}

	public Dupla(int elem1, int elem2){
		setObjects(elem1,elem2);
	}
	
	public void setObjects(int elem1, int elem2){
		element1 = elem1;
		element2 = elem2;
	}
	
	public int first(){
		return element1;
	}
	
	public int second(){
		return element2;
	}
}
