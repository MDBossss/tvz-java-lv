package hr.java.vjezbe.entitet;

/**
 * Interface za ispite se odrzavaju online
 */
public sealed interface Online permits Ispit{
    public default String nazivSoftwarea(String naziv){
        return naziv;
    }
}
