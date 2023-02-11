package hr.java.vjezbe.entitet;

/**
 * Dvorana u kojoj se moze odrzavati nastava i ispiti
 * @param naziv Naziv prostorije
 * @param zgrada Naziv zgrade u kojoj se nalazi prostorija
 */
public record Dvorana(String naziv,String zgrada) {
    public Dvorana(String naziv,String zgrada){
        this.naziv = naziv;
        this.zgrada = zgrada;
    }
}
