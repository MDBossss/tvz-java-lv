package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interface za smjerove na visokoskolskoj razini
 */
public interface Visokoskolska {
    /**
     * Racuna konacnu ocjenu iz danih istpita. Nije dozvoljena negativna ocjena.
     *
     * @param ispiti         Ispiti iz kojih se uzimaju ocjene. Ocekuje se filtriran popis ispita za jednog studenta.
     * @param ocjenaPismenog Ocjena dobivena za zavrsni reda.
     * @param ocjenaZavrsnog Konacna ocjena studenta.
     * @return Konacna ocjena studenta.
     */
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> ispiti, Integer ocjenaPismenog, Integer ocjenaZavrsnog);

    /**
     * Racuna prosjecnu ocjenu iz danih ispita. Nije dozvoljena negativna ocjena.
     *
     * @param ispiti Ispiti iz kojih se uzimaju ocjene. Ocekuje se filtriran popis ispita za jednog studenta.
     * @return Prosjecna ocjena studenta na ispitima
     * @throws NemoguceOdreditiProsjekStudentaException Ako se pronade ispit s negativnom ocjenom
     */
    default BigDecimal odrediProsjekOcjenaNaIspitima(List<Ispit> ispiti) throws NemoguceOdreditiProsjekStudentaException {
        BigDecimal sum = BigDecimal.ZERO;
        int count = 0;
        for (Ispit ispit : ispiti) {
            if (ispit.getOcjena() == Ocjena.NEDOVOLJAN) {
                throw new NemoguceOdreditiProsjekStudentaException("Ucenik " + ispit.getStudent().getIme() + " " + ispit.getStudent().getPrezime() + " ima ocjenu nedovoljan iz predmeta " + ispit.getPredmet().getNaziv());
            } else {
                sum = sum.add(BigDecimal.valueOf(ispit.getOcjena().vrijednost));
                count++;
            }
        }
        return sum.divide(BigDecimal.valueOf(count));
    }


    /**
     * Izbacuje ispite s negativnom ocjenom
     *
     * @param ispiti Ispiti iz kojih se uzimaju ocjene
     * @return Filtrirani ispiti.
     */
    private List<Ispit> filtrirajPolozeneIspite(List<Ispit> ispiti) {
        return ispiti.stream().filter(ispit -> ispit.getOcjena().vrijednost > Ocjena.NEDOVOLJAN.vrijednost).collect(Collectors.toList());
    }

    /**
     * Vraca sve ispite koje je pisao odreden student
     *
     * @param ispiti  Svi ispiti
     * @param student Student prema kojem se filtriraju ispiti
     * @return Filtrirani ispiti
     */
    default List<Ispit> filtrirajIspitePoStudentu(List<Ispit> ispiti, Student student) {

        return ispiti.stream().filter(ispit -> ispit.getStudent().getJmbag() == student.getJmbag()).collect(Collectors.toList());
    }

    default List<Ispit> filtrirajOvogodisnjeIspite(List<Ispit> ispiti,Integer godina){
        return ispiti.stream().filter(ispit -> ispit.getDatumIVrijeme().getYear() == godina).collect(Collectors.toList());
    }
}
