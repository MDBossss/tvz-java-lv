package hr.java.vjezbe;

import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.util.Datoteke;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UnosPredmetaController {

    @FXML
    private TextField sifraPredmetaTextField;
    @FXML
    private TextField nazivPredmetaTextField;
    @FXML
    private TextField brojEctsTextField;
    @FXML
    private ChoiceBox<String> nositeljChoiceBox;
    @FXML
    private Label successLabel;

    Map<Long, Profesor> profesoriMap = Datoteke.getProfesori();

    public void initialize(){
        List<String> profesoriList = new ArrayList<>();
        profesoriMap.values().forEach(profesor ->{
            profesoriList.add(profesor.getIme() + " " + profesor.getPrezime());
        });

        nositeljChoiceBox.setItems(FXCollections.observableList(profesoriList));
    }

    public void displaySuccess(){
        successLabel.setText("Uspješno dodan predmet!");
    }

    public void unesiPredmet(){
        List<String> alertMessages = new ArrayList<>();
        boolean invalidInput = false;

        if(sifraPredmetaTextField.getText().isEmpty()){
            alertMessages.add("Šifra predmeta je obavezan podatak!\n");
            invalidInput = true;
        }

        if(nazivPredmetaTextField.getText().isEmpty()){
            alertMessages.add("Naziv predmeta je obavezan podatak!\n");
            invalidInput = true;
        }

        if(brojEctsTextField.getText().isEmpty()){
            alertMessages.add("Broj ECTS bodova je obavezan podatak!\n");
            invalidInput = true;
        }

        if(nositeljChoiceBox.getValue() == null){
            alertMessages.add("Odabir nositelja je obavezan podatak!\n");
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
            Datoteke.addPredmet(sifraPredmetaTextField.getText(),nazivPredmetaTextField.getText(),brojEctsTextField.getText(),nositeljChoiceBox.getValue());
            displaySuccess();
        }
    }
}
