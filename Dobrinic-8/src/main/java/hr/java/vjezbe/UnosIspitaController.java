package hr.java.vjezbe;

import hr.java.vjezbe.entitet.Ocjena;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.util.Datoteke;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UnosIspitaController {

    @FXML
    private ChoiceBox<String> predmetChoiceBox;

    @FXML
    private TextField imeStudentaIspitaTextField;

    @FXML
    private TextField prezimeStudentaIspitaTextField;

    @FXML
    private ChoiceBox<Ocjena> ocjenaIspitaChoiceBox;

    @FXML
    private TextField datumVrijemeIspitaTextField;

    @FXML
    private TextField nazivDvoraneIspitaTextField;
    @FXML
    private Label successLabel;

    Map<Long, Predmet> predmetiMap = Datoteke.getPredmeti();

    public void initialize(){
        List<String> predmetiList = new ArrayList<>();
        predmetiMap.values().forEach(predmet -> {
            predmetiList.add(predmet.getNaziv());
        });
        predmetChoiceBox.setItems(FXCollections.observableList(predmetiList));

        ocjenaIspitaChoiceBox.setItems(FXCollections.observableList(Arrays.stream(Ocjena.values()).toList()));
    }

    public void displaySuccess(){
        successLabel.setText("Uspješno dodan ispit!");
    }

    public void unesiIspit(){
        List<String> alertMessages = new ArrayList<>();
        boolean invalidInput = false;

        if(predmetChoiceBox.getValue() == null){
            alertMessages.add("Odabir predmeta je obavezan podatak!\n");
            invalidInput = true;
        }

        if(imeStudentaIspitaTextField.getText().isEmpty()){
            alertMessages.add("Ime studenta je obavezan podatak!\n");
            invalidInput = true;
        }

        if(prezimeStudentaIspitaTextField.getText().isEmpty()){
            alertMessages.add("Prezime studenta je obavezan podatak!\n");
            invalidInput = true;
        }

        if(ocjenaIspitaChoiceBox.getValue() == null){
            alertMessages.add("Odabir ocjene je obavezan podatak!\n");
            invalidInput = true;
        }

        if(nazivDvoraneIspitaTextField.getText().isEmpty()){
            alertMessages.add("Naziv dvorane je obavezan podatak!\n");
            invalidInput = true;
        }

        if(datumVrijemeIspitaTextField.getText().isEmpty()){
            alertMessages.add("Datum i vrijeme ispita je obavezan podatak!\n");
            invalidInput = true;
        }

        if(invalidInput){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Neispravan unos podataka!");
            alert.setHeaderText("Molimo ispravite sljedeće pogreške:");

            StringBuilder message = new StringBuilder();
            for (String alertMessage : alertMessages) {
                message.append(alertMessage);
            }
            alert.setContentText(message.toString());
            alert.showAndWait();
        }
        else{
            //pozivanje metode koja prima argumente kao u txt datoteci i zapisuje
            Datoteke.addIspit(nazivDvoraneIspitaTextField.getText(),imeStudentaIspitaTextField.getText(),ocjenaIspitaChoiceBox.getValue(),datumVrijemeIspitaTextField.getText(),nazivDvoraneIspitaTextField.getText());
            displaySuccess();
        }
    }
}
