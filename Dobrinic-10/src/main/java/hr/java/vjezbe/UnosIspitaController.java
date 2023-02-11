package hr.java.vjezbe;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Ocjena;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.Datoteke;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UnosIspitaController {

    @FXML
    private ChoiceBox<String> predmetChoiceBox;

    @FXML
    private ChoiceBox<String> studentChoiceBox;

    @FXML
    private ChoiceBox<Ocjena> ocjenaIspitaChoiceBox;

    @FXML
    private TextField datumVrijemeIspitaTextField;

    @FXML
    private Label successLabel;

    Map<Long, Predmet> predmetiMap;
    Map<Long, Student> studentiMap;

    public void initialize(){
        List<String> predmetiList = new ArrayList<>();
        List<String> studentiList = new ArrayList<>();

        try{
            predmetiMap = BazaPodataka.getPredmeti();
            studentiMap = BazaPodataka.getStudenti();

            predmetiMap.values().forEach(predmet -> {
                predmetiList.add(predmet.getNaziv());
            });

            studentiMap.values().forEach(student -> {
                studentiList.add(student.getIme() + " " + student.getPrezime());
            });

        }catch(BazaPodatakaException e){
            e.printStackTrace();
        }


        predmetChoiceBox.setItems(FXCollections.observableList(predmetiList));
        studentChoiceBox.setItems(FXCollections.observableList(studentiList));

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

        if(studentChoiceBox.getValue() == null){
            alertMessages.add("Odabir studenta je obavezan podatak!\n");
            invalidInput = true;
        }

        if(ocjenaIspitaChoiceBox.getValue() == null){
            alertMessages.add("Odabir ocjene je obavezan podatak!\n");
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
            try{
                BazaPodataka.addIspit(new Ispit(null,BazaPodataka.getPredmeti().get(BazaPodataka.getPredmetIDbyName(predmetChoiceBox.getValue())),BazaPodataka.getStudenti().get(BazaPodataka.getStudentIDbyFullName(studentChoiceBox.getValue())),ocjenaIspitaChoiceBox.getValue(), LocalDateTime.parse(datumVrijemeIspitaTextField.getText(), DateTimeFormatter.ofPattern("d.M.yyyy. H:mm"))));
                displaySuccess();
            }catch (BazaPodatakaException e){
                e.printStackTrace();
            }
            //Datoteke.addIspit(nazivDvoraneIspitaTextField.getText(),imeStudentaIspitaTextField.getText(),ocjenaIspitaChoiceBox.getValue(),datumVrijemeIspitaTextField.getText(),nazivDvoraneIspitaTextField.getText());
        }
    }
}
