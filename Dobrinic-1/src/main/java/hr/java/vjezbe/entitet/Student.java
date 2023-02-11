package hr.java.vjezbe.entitet;

import java.time.LocalDate;

public class Student {
    private String ime;
    private String prezime;
    private String jmbag;
    private LocalDate datumRodenja;


    public Student(String ime, String prezime, String jmbag, LocalDate datumRodenja) {
        this.ime = ime;
        this.prezime = prezime;
        this.jmbag = jmbag;
        this.datumRodenja = datumRodenja;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }

    public void setDatumRodenja(LocalDate datumRodenja) {
        this.datumRodenja = datumRodenja;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getJmbag() {
        return jmbag;
    }

    public LocalDate getDatumRodenja() {
        return datumRodenja;
    }

}
