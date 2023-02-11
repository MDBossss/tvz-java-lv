package hr.java.vjezbe.entitet;

public class Profesor {
    private String sifra;
    private String ime;
    private String prezime;
    private String titula;

    public Profesor(String sifra, String ime, String prezime, String titula) {
        this.sifra = sifra;
        this.ime = ime;
        this.prezime = prezime;
        this.titula = titula;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }

    public String getSifra() {
        return sifra;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getTitula() {
        return titula;
    }
}
