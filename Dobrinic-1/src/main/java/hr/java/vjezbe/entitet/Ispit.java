package hr.java.vjezbe.entitet;

import java.time.LocalDateTime;

public class Ispit {
    private Predmet predmet;
    private Student student;
    private Integer ocjena;
    private LocalDateTime datumIVrijeme;

    public Ispit(Predmet predmet, Student student, Integer ocjena, LocalDateTime datumIVrijeme) {
        this.predmet = predmet;
        this.student = student;
        this.ocjena = ocjena;
        this.datumIVrijeme = datumIVrijeme;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public Student getStudent() {
        return student;
    }

    public Integer getOcjena() {
        return ocjena;
    }

    public LocalDateTime getDatumIVrijeme() {
        return datumIVrijeme;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setOcjena(Integer ocjena) {
        this.ocjena = ocjena;
    }

    public void setDatumIVrijeme(LocalDateTime datumIVrijeme) {
        this.datumIVrijeme = datumIVrijeme;
    }
}
