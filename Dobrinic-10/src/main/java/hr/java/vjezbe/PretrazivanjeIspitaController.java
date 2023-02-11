package hr.java.vjezbe;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Ocjena;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
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
    private TableView<Ispit> ispitTableView;

    @FXML
    private TableColumn<Ispit,String> nazivPredmetaIspitaTableColumn;
    @FXML
    private TableColumn<Ispit,String> imePrezimeStudentaIspitaTableColumn;
    @FXML
    private TableColumn<Ispit,String> ocjenaIspitaTableColumn;
    @FXML
    private TableColumn<Ispit,String> datumIspitaTableColumn;

    private List<Ispit> ispitiList;

    public void initialize(){
        try{
            Map<Long,Ispit> ispitiMap = BazaPodataka.getIspiti();
            ispitiList = ispitiMap.values().stream().toList();
        }catch(BazaPodatakaException e){
            e.printStackTrace();
        }


        nazivPredmetaIspitaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPredmet().getNaziv()));
        imePrezimeStudentaIspitaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getIme() + " " + cellData.getValue().getStudent().getPrezime()));
        ocjenaIspitaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getOcjena().vrijednost)));
        datumIspitaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDatumIVrijeme().toString()));

        ispitTableView.setItems(FXCollections.observableList(ispitiList));
    }

    public void filtrirajIspite(){
        String nazivPredmeta = nazivPredmetaIspitaTextField.getText();
        String imePrezimeStudenta = imePrezimeStudentaIspitaTextField.getText();
        String ocjena = ocjenaIspitaTextField.getText();
        String datumIspita = datumIspitaTextField.getText();


        if(nazivPredmeta.isEmpty())
            nazivPredmeta = null;
        if(imePrezimeStudenta.isEmpty())
            imePrezimeStudenta = null;
        if(ocjena.isEmpty())
            ocjena = null;
        if(datumIspita.isEmpty())
            datumIspita = null;

        try{
            ispitTableView.setItems(FXCollections.observableList(BazaPodataka.getFilteredIspiti(new Ispit(null,nazivPredmeta == null ? null : BazaPodataka.getPredmeti().get(BazaPodataka.getPredmetIDbyName(nazivPredmeta)),imePrezimeStudenta == null ? null : BazaPodataka.getStudenti().get(BazaPodataka.getStudentIDbyFullName(imePrezimeStudenta)), ocjena == null ? null : Ocjena.parseOcjena(Integer.valueOf(ocjena)), datumIspita == null ? null : LocalDateTime.parse(datumIspita, DateTimeFormatter.ofPattern("d.M.yyyy. H:mm")))).values().stream().toList()));
        }catch(BazaPodatakaException e){
            e.printStackTrace();
        }


    }
}
