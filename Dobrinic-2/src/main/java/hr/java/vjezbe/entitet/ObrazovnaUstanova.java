package hr.java.vjezbe.entitet;

public abstract class ObrazovnaUstanova {
    private String naziv;
    private Predmet[] predmeti;
    private NastavnaOsoba[] profesori;
    private Student[] studenti;
    private Ispit[] ispiti;

    abstract public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina);

    public ObrazovnaUstanova(String naziv, Predmet[] predmeti, NastavnaOsoba[] profesori, Student[] studenti, Ispit[] ispiti) {
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

    public Predmet[] getPredmeti() {
        return predmeti;
    }

    public void setPredmeti(Predmet[] predmeti) {
        this.predmeti = predmeti;
    }

    public NastavnaOsoba[] getProfesori() {
        return profesori;
    }

    public void setProfesori(NastavnaOsoba[] profesori) {
        this.profesori = profesori;
    }

    public Student[] getStudenti() {
        return studenti;
    }

    public void setStudenti(Student[] studenti) {
        this.studenti = studenti;
    }

    public Ispit[] getIspiti() {
        return ispiti;
    }

    public void setIspiti(Ispit[] ispiti) {
        this.ispiti = ispiti;
    }
}
