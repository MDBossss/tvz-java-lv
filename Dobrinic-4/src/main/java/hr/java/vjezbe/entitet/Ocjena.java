package hr.java.vjezbe.entitet;

public enum Ocjena {
    ODLICAN(5),
    VRLO_DOBAR(4),
    DOBAR(3),
    DOVOLJAN(2),
    NEDOVOLJAN(1);

    public final int vrijednost;
    Ocjena(int vrijednost) {
        this.vrijednost = vrijednost;
    }
}
