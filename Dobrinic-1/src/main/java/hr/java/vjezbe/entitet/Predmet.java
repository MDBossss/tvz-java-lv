package hr.java.vjezbe.entitet;

public class Predmet {
    private String sifra;
    private String naziv;
    private Integer brojEctsBodova;
    private Profesor nositelj;
    private Integer brojStudenata;

    public Predmet(String sifra, String naziv, Integer brojEctsBodova, Profesor nositelj, Integer brojStudenata) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
        this.brojStudenata = brojStudenata;
    }

    public String getSifra() {
        return sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public Integer getBrojEctsBodova() {
        return brojEctsBodova;
    }

    public Profesor getNositelj() {
        return nositelj;
    }

    public Integer getBrojStudenata() {
        return brojStudenata;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setBrojEctsBodova(Integer brojEctsBodova) {
        this.brojEctsBodova = brojEctsBodova;
    }

    public void setNositelj(Profesor nositelj) {
        this.nositelj = nositelj;
    }

    public void setBrojStudenata(Integer brojStudenata) {
        this.brojStudenata = brojStudenata;
    }
}
