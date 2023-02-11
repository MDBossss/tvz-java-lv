package hr.java.vjezbe;

import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class PretrazivanjeIspitaController {
    @FXML
    private TextField nazivPredmetaIspitaTextField;
    @FXML
    private TextField imePrezimeStudentaIspitaTextField;
    @FXML
    private TextField ocjenaIspitaTextField;
    @FXML
    private TextField datumIspitaTextField;
    @FXML
    private TextField nazivDvoraneIspitaTextField;
    @FXML
    private TableView<Ispit> ispitTableView;

    @FXML
    private TableColumn<Ispit,String> nazivPredmetaIspitaTableColumn;
    @FXML
    private TableColumn<Ispit,String> imePrezimeStudentaIspitaTableColumn;
    @FXML
    private TableColumn<Ispit,String> ocjenaIspitaTableColumn;
    @FXML
    private TableColumn<Ispit,String> datumIspitaTableColumn;
    @FXML
    private TableColumn<Ispit,String> nazivDvoraneIspitaTableColumn;

    private List<Ispit> ispitiList;

    public void initialize(){
        Map<Long,Ispit> ispitiMap = Datoteke.getIspiti();
        ispitiList = ispitiMap.values().stream().toList();

        nazivPredmetaIspitaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPredmet().getNaziv()));
        imePrezimeStudentaIspitaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getIme() + " " + cellData.getValue().getStudent().getPrezime()));
        ocjenaIspitaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOcjena().toString()));
        datumIspitaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDatumIVrijeme().toString()));
        nazivDvoraneIspitaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDvorana().naziv()));

        ispitTableView.setItems(FXCollections.observableList(ispitiList));
    }

    public void filtrirajIspite(){
        String nazivPredmeta = nazivPredmetaIspitaTextField.getText();
        String imePrezimeStudenta = imePrezimeStudentaIspitaTextField.getText();
        String ocjena = ocjenaIspitaTextField.getText();
        String datumIspita = datumIspitaTextField.getText();
        String nazivDvorane = nazivDvoraneIspitaTextField.getText();

        List<Ispit> filtriraniIspiti = ispitiList.stream()
                .filter(i -> i.getPredmet().getNaziv().contains(nazivPredmeta))
                .filter(i -> (i.getStudent().getIme() + " " + i.getStudent().getPrezime()).contains(imePrezimeStudenta))
                .filter(i -> i.getOcjena().toString().contains(ocjena))
                .filter(i -> i.getDatumIVrijeme().toString().contains(datumIspita))
                .filter(i -> i.getDvorana().naziv().contains(nazivDvorane))
                .toList();

        ispitTableView.setItems(FXCollections.observableList(filtriraniIspiti));

    }
}
