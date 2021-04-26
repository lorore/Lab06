/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.meteo;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import java.time.*;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxMese"
    private ChoiceBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnUmidita"
    private Button btnUmidita; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcola"
    private Button btnCalcola; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaSequenza(ActionEvent event) {
    		txtResult.clear();
    		if(boxMese.getValue()==null) {
    			txtResult.setText("Non è stato inserito un mese");
    			return;
    		}
    		
    		int mese=boxMese.getValue();
    		
    		List<Citta> result=this.model.trovaSequenza(mese);
    		if(result==null) {
    			txtResult.setText("Ci sono stati problemi nel dataBase");
    			return;
    		}
    		StringBuilder sb=new StringBuilder();
    		for(Citta c: result) {
    			sb.append(String.format("%-10s", c.getNome()));
    			sb.append("\n");
    		}
    		this.txtResult.appendText(sb.toString());
    }

    @FXML
    void doCalcolaUmidita(ActionEvent event) {
    	txtResult.clear();
    		if(boxMese.getValue()==null) {
    			txtResult.setText("Non è stato inserito un mese");
    			return;
    		}
    		
    		int mese=boxMese.getValue();
    		Map<String, Double> result=this.model.getUmiditaMedia(mese);
    		if(result==null) {
    			txtResult.setText("Ci sono stati problemi nel dataBase");
    			return;
    		}
    		StringBuilder sb=new StringBuilder();
    		for(String s: result.keySet()) {
    			sb.append(String.format("%-10s ", s));
    			sb.append(String.format("%5f", result.get(s)));
    			sb.append("\n");
    		}
    	this.txtResult.appendText(sb.toString());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model=model;
	/*	boxMese.getItems().add(Month.JANUARY);
		boxMese.getItems().add(Month.FEBRUARY);
		boxMese.getItems().add(Month.MARCH);
		boxMese.getItems().add(Month.APRIL);
		boxMese.getItems().add(Month.MAY);
		boxMese.getItems().add(Month.JUNE);
		boxMese.getItems().add(Month.JULY);
		boxMese.getItems().add(Month.AUGUST);
		boxMese.getItems().add(Month.SEPTEMBER);
		boxMese.getItems().add(Month.OCTOBER);
		boxMese.getItems().add(Month.NOVEMBER);
		boxMese.getItems().add(Month.DECEMBER);*/
		
		for(int i=1; i<13;i++) {
			boxMese.getItems().add(i);
		}
		
		
		
	}
}

