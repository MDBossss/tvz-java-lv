package hr.java.vjezbe;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class UnosStudenataController {

    @FXML
    private TextField imeStudentaTextField;
    @FXML
    private TextField prezimeStudentaTextField;
    @FXML
    private TextField jmbagStudentaTextField;
    @FXML
    private DatePicker datumRodenjaStudentaTextField;
    @FXML
    private Label successLabel;

    public void displaySucccess(){
        successLabel.setText("Uspješno dodan student!");
    }

    public void unesiStudenta(){
        List<String> alertMessages = new ArrayList<>();
        boolean invalidInput = false;

        if(imeStudentaTextField.getText().isEmpty()){
            alertMessages.add("Ime studenta je obavezan podatak!\n");
            invalidInput = true;
        }

        if(prezimeStudentaTextField.getText().isEmpty()){
            alertMessages.add("Prezime studenta je obavezan podatak!\n");
            invalidInput = true;
        }

        if(jmbagStudentaTextField.getText().isEmpty()){
            alertMessages.add("JMBAG studenta je obavezan podatak!\n");
            invalidInput = true;
        }

        if(datumRodenjaStudentaTextField.getValue() == null){
            alertMessages.add("Datum rođenja studenta je obavezan podatak!\n");
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
            //zapis u bazu podataka
            try{
                BazaPodataka.addStudent(new Student(null,imeStudentaTextField.getText(),prezimeStudentaTextField.getText(),jmbagStudentaTextField.getText(),datumRodenjaStudentaTextField.getValue()));
                displaySucccess();
            }catch(BazaPodatakaException e){
                e.printStackTrace();
            }

        }
    }
}
