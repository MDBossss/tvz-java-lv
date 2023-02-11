package hr.java.vjezbe.entitet;

import java.util.HashSet;
import java.util.Set;

/**
 * Bazni predmet koji se izvodi u tercijarnoj obrazovnoj ustanovi
 */
public class Predmet extends Entitet{
    private String sifra;
    private String naziv;
    private Integer brojEctsBodova;
    private NastavnaOsoba nositelj;
    private Set<Student> studenti;

    public Predmet(Long id, String sifra, String naziv, Integer brojEctsBodova, NastavnaOsoba nositelj, Set<Student> studenti) {
        super(id);
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
        this.studenti = studenti;
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


    public Set<Student> getStudenti() {
        return studenti;
    }

    public void setStudenti(Set<Student> studenti) {
        this.studenti = studenti;
    }
}


