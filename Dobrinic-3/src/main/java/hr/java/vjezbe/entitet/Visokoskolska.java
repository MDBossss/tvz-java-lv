package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Interface za smjerove na visokoskolskoj razini
 */
public interface Visokoskolska {
    /**
     * Racuna konacnu ocjenu iz danih istpita. Nije dozvoljena negativna ocjena.
     * @param ispiti Ispiti iz kojih se uzimaju ocjene. Ocekuje se filtriran popis ispita za jednog studenta.
     * @param ocjenaPismenog Ocjena dobivena za zavrsni reda.
     * @param ocjenaZavrsnog Konacna ocjena studenta.
     * @return Konacna ocjena studenta.
     */
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, Integer ocjenaPismenog, Integer ocjenaZavrsnog);

    /**
     * Racuna prosjecnu ocjenu iz danih ispita. Nije dozvoljena negativna ocjena.
     * @param ispiti Ispiti iz kojih se uzimaju ocjene. Ocekuje se filtriran popis ispita za jednog studenta.
     * @return Prosjecna ocjena studenta na ispitima
     * @throws NemoguceOdreditiProsjekStudentaException Ako se pronade ispit s negativnom ocjenom
     */
    default BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispiti) throws NemoguceOdreditiProsjekStudentaException {
        BigDecimal sum = BigDecimal.ZERO;
        int count = 0;
        for (Ispit ispit : ispiti) {
            if (ispit.getOcjena() == 1) {
                throw new NemoguceOdreditiProsjekStudentaException("Ucenik " + ispit.getStudent().getIme() + " " + ispit.getStudent().getPrezime() + " ima ocjenu nedovoljan iz predmeta " + ispit.getPredmet().getNaziv());
            } else {
                sum = sum.add(BigDecimal.valueOf(ispit.getOcjena()));
                count++;
            }
        }
        return sum.divide(BigDecimal.valueOf(count));
    }


    /**
     * Izbacuje ispite s negativnom ocjenom
     * @param ispiti Ispiti iz kojih se uzimaju ocjene
     * @return Filtrirani ispiti.
     */
    private Ispit[] filtrirajPolozeneIspite(Ispit[] ispiti) {
        Ispit[] polozeniIspiti = new Ispit[0];
        for (Ispit ispit : ispiti) {
            if (ispit.getOcjena() > 1) {
                polozeniIspiti = Arrays.copyOf(polozeniIspiti, polozeniIspiti.length + 1);
                polozeniIspiti[polozeniIspiti.length - 1] = ispit;
            }
        }
        return polozeniIspiti;
    }

    /**
     * Vraca sve ispite koje je pisao odreden student
     * @param ispiti Svi ispiti
     * @param student Student prema kojem se filtriraju ispiti
     * @return Filtrirani ispiti
     */
    default Ispit[] filtrirajIspitePoStudentu(Ispit[] ispiti, Student student) {
        Ispit[] studentoviIspiti = new Ispit[0];
        for (Ispit ispit : ispiti) {
            if (ispit.getStudent().getJmbag() == student.getJmbag()) {
                studentoviIspiti = Arrays.copyOf(studentoviIspiti, studentoviIspiti.length + 1);
                studentoviIspiti[studentoviIspiti.length - 1] = ispit;
            }
        }
        return studentoviIspiti;
    }

}
