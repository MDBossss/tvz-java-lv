package hr.java.vjezbe.niti;


import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatumRodenjaNit implements Runnable{


    @Override
    public void run() {
        Map<Long, Student> studentiMap;
        List<String> alertMessages = new ArrayList<>();

        try {
            studentiMap = BazaPodataka.getStudentwithBirthday();
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }

        if(!studentiMap.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cestitajte studentima rodendan!");
            alert.setHeaderText("Ovim studentima je rodendan!");

            StringBuilder message = new StringBuilder();

            studentiMap.forEach((key,student) -> {
                message.append(student.getIme() + " " + student.getPrezime() + "\n");
            });

            alert.setContentText(message.toString());
            alert.showAndWait();
        }
    }
}
