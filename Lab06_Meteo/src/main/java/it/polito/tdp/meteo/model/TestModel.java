package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.List;
import java.time.*;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
	//	System.out.println(m.getUmiditaMedia(12));
		
	/*	System.out.println(m.trovaSequenza(5));
		
		List<String> prova=new ArrayList<String>();
		prova.add("c");
		prova.add("c");
		prova.add("c");
		System.out.println(prova);*/
		
		//LocalDate d=LocalDate.of(2013, 5, 13);
	
		
		
		System.out.println(m.trovaSequenza(3));
		System.out.println(m.min);
		
	//problema solo con 3
		
		System.out.println(m.getUmiditaMedia(3));
		

	}

}
