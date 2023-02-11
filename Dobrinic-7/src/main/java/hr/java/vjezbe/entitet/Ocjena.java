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

    public static Ocjena parseOcjena(int ocjena) {
        return switch (ocjena) {
            case 1 -> NEDOVOLJAN;
            case 2 -> DOVOLJAN;
            case 3 -> DOBAR;
            case 4 -> VRLO_DOBAR;
            case 5 -> ODLICAN;
            default ->
            {
                String message = String.format("Nedozvoljena vrijednost za ocjenu: %d", ocjena);
                throw new RuntimeException(message);
            }
        };
    }
}
