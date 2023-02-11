package hr.java.vjezbe;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.Datoteke;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class UnosProfesoraController {

    @FXML
    private TextField imeProfesoraTextField;
    @FXML
    private TextField prezimeProfesoraTextField;
    @FXML
    private TextField sifraProfesoraTextField;
    @FXML
    private TextField titulaProfesoraTextField;
    @FXML
    private Label successLabel;

    public void displaySuccess(){
        successLabel.setText("Uspješno dodan profesor!");
    }

    public void unesiProfesora(){
        List<String> alertMessages = new ArrayList<>();
        boolean invalidInput = false;

        if(imeProfesoraTextField.getText().isEmpty()){
            alertMessages.add("Ime profesora je obavezan podatak!\n");
            invalidInput = true;
        }

        if(prezimeProfesoraTextField.getText().isEmpty()){
            alertMessages.add("Prezime profesora je obavezan podatak!\n");
            invalidInput = true;
        }

        if(sifraProfesoraTextField.getText().isEmpty()){
            alertMessages.add("Šifra profesora je obavezan podatak!\n");
            invalidInput = true;
        }

        if(titulaProfesoraTextField.getText().isEmpty()){
            alertMessages.add("Titula profesora je obavezan podatak!\n");
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
            //zapis u txt datoteku
            try{
                BazaPodataka.addProfesor(new Profesor(null,imeProfesoraTextField.getText(),prezimeProfesoraTextField.getText(),sifraProfesoraTextField.getText(),titulaProfesoraTextField.getText()));
                displaySuccess();
            }catch(BazaPodatakaException e){
                e.printStackTrace();
            }
        }
    }
}
