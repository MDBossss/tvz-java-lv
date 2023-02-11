package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

/**
 * Bazna klasa za obrazovne ustanove
 */

public abstract class ObrazovnaUstanova {
    private String naziv;
    private List<Predmet> predmeti;
    private List<NastavnaOsoba> profesori;
    private List<Student> studenti;
    private List<Ispit> ispiti;

    abstract public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina);

    public ObrazovnaUstanova(String naziv, List<Predmet> predmeti, List<NastavnaOsoba> profesori, List<Student> studenti, List<Ispit> ispiti) {
        this.naziv = naziv;
        this.predmeti = predmeti;
        this.profesori = profesori;
        this.studenti = studenti;
        this.ispiti = ispiti;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public List<Predmet> getPredmeti() {
        return predmeti;
    }

    public void setPredmeti(List<Predmet> predmeti) {
        this.predmeti = predmeti;
    }

    public List<NastavnaOsoba> getProfesori() {
        return profesori;
    }

    public void setProfesori(List<NastavnaOsoba> profesori) {
        this.profesori = profesori;
    }

    public List<Student> getStudenti() {
        return studenti;
    }

    public void setStudenti(List<Student> studenti) {
        this.studenti = studenti;
    }

    public List<Ispit> getIspiti() {
        return ispiti;
    }

    public void setIspiti(List<Ispit> ispiti) {
        this.ispiti = ispiti;
    }
}

