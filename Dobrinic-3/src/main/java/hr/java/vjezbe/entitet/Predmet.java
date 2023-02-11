package hr.java.vjezbe.entitet;

/**
 * Bazni predmet koji se izvodi u tercijarnoj obrazovnoj ustanovi
 */
public class Predmet {
    private String sifra;
    private String naziv;
    private Integer brojEctsBodova;
    private NastavnaOsoba nositelj;
    private Integer brojStudenata;
    private Student[] studenti;
    private Asistent[] asistenti;

    public Predmet(String sifra, String naziv, Integer brojEctsBodova, NastavnaOsoba nositelj, Integer brojStudenata, Asistent[] asistenti) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
        this.brojStudenata = brojStudenata;
        this.asistenti = asistenti;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getBrojEctsBodova() {
        return brojEctsBodova;
    }

    public void setBrojEctsBodova(Integer brojEctsBodova) {
        this.brojEctsBodova = brojEctsBodova;
    }

    public NastavnaOsoba getNositelj() {
        return nositelj;
    }

    public void setNositelj(NastavnaOsoba nositelj) {
        this.nositelj = nositelj;
    }

    public Integer getBrojStudenata() {
        return brojStudenata;
    }

    public void setBrojStudenata(Integer brojStudenata) {
        this.brojStudenata = brojStudenata;
    }

    public Student[] getStudenti() {
        return studenti;
    }

    public void setStudenti(Student[] studenti) {
        this.studenti = studenti;
    }

    public Asistent[] getAsistenti() {
        return asistenti;
    }

    public void setAsistenti(Asistent[] asistenti) {
        this.asistenti = asistenti;
    }
}
