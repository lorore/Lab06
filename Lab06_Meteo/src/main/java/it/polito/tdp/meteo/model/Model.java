package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private MeteoDAO meteoDAO;
	private List<Citta> listaCitta;
	//private int giorniConsecutivi;
	public int min;
	public int costo;
	private List<Citta> soluzione;
	public Model() {
		meteoDAO=new MeteoDAO();
	//	giorniConsecutivi=0;
		min=1000000000;
		costo=0;
	}
	
	
	public List<Citta> trovaSequenza(int mese){
		listaCitta= meteoDAO.getAllCitta(mese);
		
		List<Citta> parziale=new ArrayList<Citta>();
		
		calcolaSequenza(parziale, 0,0);
		
		return soluzione;
	}

	// of course you can change the String output with what you think works best
	public Map<String, Double> getUmiditaMedia(int mese) {
		return this.meteoDAO.getTempMedie(mese);
		
	}
	
	// of course you can change the String output with what you think works best
/*	public String trovaSequenza(int mese) {
		return "TODO!";
	}*/
	
	private void calcolaSequenza(List<Citta> parziale, int livello, int giorniConsecutivi) {
	
		if(livello==NUMERO_GIORNI_TOTALI) {
			if(this.ciSonoTutte(parziale)) {
				if(costo<min) {
					min=costo;
					soluzione=new ArrayList<Citta>(parziale);
					
				}
			}
		}	
		
		else if(giorniConsecutivi>0 && giorniConsecutivi<NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) {
			Citta c=parziale.get(parziale.size()-1);
			c.increaseCounter();
			
			int delta=c.getUmiditaDiQuestoGiorno(livello);
			costo+=delta;
			parziale.add(c);
			calcolaSequenza(parziale, livello+1, giorniConsecutivi+1);
			c.decreaseCounter();
			costo-=delta;
			parziale.remove(livello);
		}
		else {
			
		for(int i=0; i<listaCitta.size(); i++) {
			
			Citta c=listaCitta.get(i);
			
			
			
			if(c.getCounter()<NUMERO_GIORNI_CITTA_MAX) {
				if(livello>0 && !parziale.get(parziale.size()-1).equals(c)) {
					giorniConsecutivi=0;
					costo+=COST;
				}
			c.increaseCounter();
			
			
			int delta=c.getUmiditaDiQuestoGiorno(livello);
			costo+=delta;
			parziale.add(c);
			calcolaSequenza(parziale, livello+1, giorniConsecutivi+1);
			c.decreaseCounter();
			costo-=delta;
			if(livello>0 && !parziale.get(parziale.size()-2).equals(c)) {
				
				costo-=COST;
			}
			parziale.remove(livello);
			}
			//livello mi dice quanti elementi ci sono in parziale
			//livello mappa anche il giorno del mese: livello 0 giorno 1 , livello 1 giorno 2 e così via
		}
		
		}
	}
		
	
	
	private boolean ciSonoTutte(List<Citta> listaCitta) {
		for(Citta c: listaCitta) {
			if(c.getCounter()==0)
				return false;
		}
		return true;
	}
	
	

}
