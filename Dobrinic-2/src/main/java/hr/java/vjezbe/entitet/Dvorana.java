package hr.java.vjezbe.entitet;

public record Dvorana(String naziv,String zgrada) {
    public Dvorana(String naziv,String zgrada){
        this.naziv = naziv;
        this.zgrada = zgrada;
    }
}
