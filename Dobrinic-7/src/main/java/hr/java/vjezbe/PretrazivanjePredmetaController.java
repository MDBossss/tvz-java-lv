package hr.java.vjezbe;

import hr.java.vjezbe.entitet.NastavnaOsoba;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
        Map<Long, Predmet> predmetiMap = Datoteke.getPredmeti();
        predmetiList = predmetiMap.values().stream().toList();

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

        List<Predmet> filtriraniPredmeti = predmetiList.stream()
                .filter(p -> p.getSifra().contains(sifra))
                .filter(p -> p.getNaziv().contains(naziv))
                .filter(p -> p.getBrojEctsBodova().toString().contains(brojEctsBodova))
                .filter(p -> (p.getNositelj().getIme() + " " + p.getNositelj().getPrezime()).contains(nositelj))
                .toList();



        predmetTableView.setItems(FXCollections.observableList(filtriraniPredmeti));

    }
}
