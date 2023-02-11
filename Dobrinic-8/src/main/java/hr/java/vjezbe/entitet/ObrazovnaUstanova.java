package hr.java.vjezbe.entitet;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Bazna klasa za obrazovne ustanove
 */

public abstract class ObrazovnaUstanova extends Entitet{
    private String naziv;
    private List<Predmet> predmeti;
    private List<Profesor> profesori;
    private List<Ispit> ispiti;

    abstract public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina);

    public ObrazovnaUstanova(Long id, String naziv, List<Predmet> predmeti, List<Profesor> profesori, List<Ispit> ispiti) {
        super(id);
        this.naziv = naziv;
        this.predmeti = predmeti;
        this.profesori = profesori;
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

    public List<Profesor> getProfesori() {
        return profesori;
    }

    public void setProfesori(List<Profesor> profesori) {
        this.profesori = profesori;
    }


    public List<Ispit> getIspiti() {
        return ispiti;
    }

    public void setIspiti(List<Ispit> ispiti) {
        this.ispiti = ispiti;
    }

    public List<Ispit> getOdlicneIspite(){
        return ispiti.stream().filter(ispit -> ispit.getOcjena() == Ocjena.ODLICAN).collect(Collectors.toList());
    }
}

