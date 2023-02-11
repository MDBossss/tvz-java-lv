package hr.java.vjezbe;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.NastavnaOsoba;
import hr.java.vjezbe.entitet.Predmet;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PretrazivanjePredmetaController {

    @FXML
    private TextField sifraPredmetaTextField;
    @FXML
    private TextField nazivPredmetTextField;
    @FXML
    private TextField brojEctsBodovaTextField;
    @FXML
    private TextField nositeljTextField;

    @FXML
    private TableView<Predmet> predmetTableView;
    @FXML
    private TableColumn<Predmet,String> sifraPredmetaTableColumn;
    @FXML
    private TableColumn<Predmet,String> nazivPredmetaTableColumn;
    @FXML
    private TableColumn<Predmet,String> brojEctsBodovaPredmetaTableColumn;
    @FXML
    private TableColumn<Predmet,String> nositeljPredmetaTableColumn;

    private List<Predmet> predmetiList;

    public void initialize(){
        try{
            Map<Long, Predmet> predmetiMap = BazaPodataka.getPredmeti();
            predmetiList = predmetiMap.values().stream().toList();
        }catch(BazaPodatakaException e){
            e.printStackTrace();
        }

        sifraPredmetaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSifra()));
        nazivPredmetaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNaziv()));
        brojEctsBodovaPredmetaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBrojEctsBodova().toString()));
        nositeljPredmetaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNositelj().getIme() + " " + cellData.getValue().getNositelj().getPrezime()));

        predmetTableView.setItems(FXCollections.observableList(predmetiList));
    }

    public void filtrirajPredmete(){
        String sifra = sifraPredmetaTextField.getText();
        String naziv = nazivPredmetTextField.getText();
        String brojEctsBodova = brojEctsBodovaTextField.getText();
        String nositelj = nositeljTextField.getText();


        if(sifra.isEmpty())
            sifra = null;
        if(naziv.isEmpty())
            naziv = null;
        if(brojEctsBodova.isEmpty())
            brojEctsBodova = null;
        if(nositelj.isEmpty())
            nositelj = null;

        try{
            predmetTableView.setItems(FXCollections.observableList(BazaPodataka.getFilteredPredmeti(new Predmet(null,sifra,naziv,brojEctsBodova == null ? null : Integer.valueOf(brojEctsBodova),nositelj == null ? null : BazaPodataka.getProfesorbyFullName(nositelj),null)).values().stream().toList()));
        }catch(BazaPodatakaException e){
            e.printStackTrace();
        }


    }
}
