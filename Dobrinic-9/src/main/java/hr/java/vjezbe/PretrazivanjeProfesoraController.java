package hr.java.vjezbe;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Map;

public class PretrazivanjeProfesoraController {

    @FXML
    private TextField imeProfesoraTextField;
    @FXML
    private TextField prezimeProfesoraTextField;
    @FXML
    private TextField sifraProfesoraTextField;
    @FXML
    private TextField titulaProfesoraTextField;
    @FXML
    private TableView<Profesor> profesorTableView;
    @FXML
    private TableColumn<Profesor,String> sifraProfesoraTableColumn;
    @FXML
    private TableColumn<Profesor,String> prezimeProfesoraTableColumn;
    @FXML
    private TableColumn<Profesor,String> imeProfesoraTableColumn;
    @FXML
    private TableColumn<Profesor,String> titulaProfesoraTableColumn;

    private List<Profesor> profesoriList;

    public void initialize(){
        try{
            Map<Long, Profesor> profesoriMap = BazaPodataka.getProfesori();
            profesoriList = profesoriMap.values().stream().toList();
        }catch (BazaPodatakaException e){
            e.printStackTrace();
        }


        sifraProfesoraTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSifra()));
        prezimeProfesoraTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrezime()));
        imeProfesoraTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIme()));
        titulaProfesoraTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulaIzaImena()));

        profesorTableView.setItems(FXCollections.observableList(profesoriList));
    }

    public void filtrirajProfesore(){
        String sifra = sifraProfesoraTextField.getText();
        String prezime = prezimeProfesoraTextField.getText();
        String ime = imeProfesoraTextField.getText();
        String titula = titulaProfesoraTextField.getText();

        if(ime.isEmpty())
            ime = null;
        if(prezime.isEmpty())
            prezime = null;
        if(sifra.isEmpty())
            sifra = null;
        if(titula.isEmpty())
            titula = null;

        try{
            profesorTableView.setItems(FXCollections.observableList(BazaPodataka.getFilteredProfesori(new Profesor(null,ime,prezime,sifra,titula)).values().stream().toList()));
        }catch(BazaPodatakaException e){
            e.printStackTrace();
        }

    }
}
