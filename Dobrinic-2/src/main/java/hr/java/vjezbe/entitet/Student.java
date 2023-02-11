package hr.java.vjezbe.entitet;

import java.time.LocalDate;

public class Student extends Osoba {
    private String jmbag;
    private LocalDate datumRodenja;


    public Student(String ime, String prezime, String jmbag, LocalDate datumRodenja) {
        super(ime, prezime);
        this.jmbag = jmbag;
        this.datumRodenja = datumRodenja;
    }

    public String getJmbag() {
        return jmbag;
    }

    public LocalDate getDatumRodenja() {
        return datumRodenja;
    }

    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }

    public void setDatumRodenja(LocalDate datumRodenja) {
        this.datumRodenja = datumRodenja;
    }
}
