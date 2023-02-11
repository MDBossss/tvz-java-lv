package hr.java.vjezbe.niti;

import hr.java.vjezbe.MainApplication;
import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;

import java.util.*;

public class NajboljiStudentNit implements Runnable{

    @Override
    public void run() {
        List<Student> studentiList;
        List<Ispit> ispitiList;
        Map<Long, OptionalDouble> studentiProsijekMap = new HashMap<>();

        try {
            ispitiList = BazaPodataka.getIspiti().values().stream().toList();
            studentiList = BazaPodataka.getStudenti().values().stream().toList();
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }

        studentiList.forEach(student -> {
            ispitiList.forEach(ispit -> {
                if(Objects.equals(student.getId(), ispit.getStudent().getId())){
                    studentiProsijekMap.put(student.getId(),ispitiList.stream().mapToDouble(i -> i.getOcjena().vrijednost).average());
                }
            });
        });

        Long najboljiStudentID = studentiProsijekMap.entrySet().stream()
                .filter(entry -> entry.getValue().isPresent())
                .max(Comparator.comparingDouble(entry -> entry.getValue().getAsDouble()))
                .map(Map.Entry::getKey)
                .orElse(-1L);

        try {
            Student najboljiStudent = BazaPodataka.getFilteredStudenti(new Student(najboljiStudentID,null,null,null,null)).get(najboljiStudentID);
            MainApplication.getMainStage().setTitle(najboljiStudent.getIme() + " " + najboljiStudent.getPrezime() + " " + ispitiList.stream().filter(ispit -> ispit.getStudent().getId().equals(najboljiStudentID)).mapToDouble(i -> i.getOcjena().vrijednost).average().getAsDouble());
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }

    }
}
