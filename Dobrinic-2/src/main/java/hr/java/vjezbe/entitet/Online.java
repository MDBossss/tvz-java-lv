package hr.java.vjezbe.entitet;

public sealed interface Online permits Ispit{
    public default String nazivSoftwarea(String naziv){
        return naziv;
    }
}
