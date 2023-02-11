package hr.java.vjezbe.entitet;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Student na tercijarnoj obrazovnoj ustanovi.
 */
public class Student extends Osoba {
    private String jmbag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(jmbag, student.jmbag) && Objects.equals(datumRodenja, student.datumRodenja);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag, datumRodenja);
    }

    private LocalDate datumRodenja;


    public Student(Long id, String ime, String prezime, String jmbag, LocalDate datumRodenja) {
        super(id, ime, prezime);
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
