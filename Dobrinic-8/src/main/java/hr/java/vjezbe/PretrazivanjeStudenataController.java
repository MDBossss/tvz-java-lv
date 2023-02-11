package hr.java.vjezbe;

import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class PretrazivanjeStudenataController {

    @FXML
    private TextField imeStudentaTextField;
    @FXML
    private TextField prezimeStudentaTextField;
    @FXML
    private TextField jmbagStudentaTextField;
    @FXML
    private DatePicker datumRodenjaStudentaDatePicker;
    @FXML
    private TableView<Student> studentTableView;

    @FXML
    private TableColumn<Student,String> jmbagStudentaTableColumn;

    @FXML
    private TableColumn<Student,String> imeStudentaTableColumn;

    @FXML
    private TableColumn<Student,String> prezimeStudentaTableColumn;

    @FXML
    private TableColumn<Student,String> datumRodenjaStudentaTableColumn;

    private List<Student> studentiList;

    public void initialize(){
        Map<Long,Student> studentiMap = Datoteke.getStudenti();
        studentiList = studentiMap.values().stream().toList();

        jmbagStudentaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJmbag()));
        imeStudentaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIme()));
        prezimeStudentaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrezime()));
        datumRodenjaStudentaTableColumn.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            property.setValue(cellData.getValue().getDatumRodenja().format(formatter));
            return property;
        });

        studentTableView.setItems(FXCollections.observableList(studentiList));
    }

    public void filtrirajStudente(){
        String ime = imeStudentaTextField.getText();
        String prezime = prezimeStudentaTextField.getText();
        String jmbag = jmbagStudentaTextField.getText();
        LocalDate datum = datumRodenjaStudentaDatePicker.getValue();

        List<Student> filtriraniStudenti = studentiList.stream()
                .filter(s -> s.getJmbag().contains(jmbag))
                .filter(s -> s.getIme().contains(ime))
                .filter(s -> s.getPrezime().contains(prezime))
                .toList();

        if(datum != null){
            filtriraniStudenti = filtriraniStudenti.stream().filter(s -> s.getDatumRodenja().equals(datum)).toList();
        }

        studentTableView.setItems(FXCollections.observableList(filtriraniStudenti));

    }

}
