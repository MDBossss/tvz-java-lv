package hr.java.vjezbe.entitet;

/**
 * Nastavna osoba u tercijarnoj obrazovnoj ustanovi
 */
public abstract class NastavnaOsoba extends Osoba{

    private String sifra;

    public NastavnaOsoba(String ime, String prezime, String sifra) {
        super(ime, prezime);
        this.sifra = sifra;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }
}
