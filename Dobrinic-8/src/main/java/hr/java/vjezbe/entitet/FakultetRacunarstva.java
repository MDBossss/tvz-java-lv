package hr.java.vjezbe.entitet;

//import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.glavna.GlavnaDatoteke;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Diplomska obrazovna ustanova za racunarstvo
 */
public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski{
    public FakultetRacunarstva(Long id, String naziv, List<Predmet> predmeti, List<Profesor> profesori, List<Ispit> ispiti) {
        super(id, naziv, predmeti, profesori, ispiti);
    }


    private static final Logger logger = LoggerFactory.getLogger(GlavnaDatoteke.class);
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> ispiti, Integer ocjenaPismenog, Integer ocjenaDiplomskog){
        try {
            return odrediProsjekOcjenaNaIspitima(ispiti)
                    .multiply(BigDecimal.valueOf(3))
                    .add(BigDecimal.valueOf(ocjenaDiplomskog))
                    .add(BigDecimal.valueOf(ocjenaPismenog))
                    .divide(BigDecimal.valueOf(5));
        } catch (NemoguceOdreditiProsjekStudentaException e) {
            System.out.println("Student " + ispiti.get(0).getStudent().getIme() + " " + ispiti.get(0).getStudent().getPrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek nedovoljan 1! ");
            logger.warn("Student " + ispiti.get(0).getStudent().getIme() + " " + ispiti.get(0).getStudent().getPrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek nedovoljan 1! ",e);
            return BigDecimal.ONE;
        }
    }
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina){
        List<Ispit> ovogodisnjiIspiti = filtrirajOvogodisnjeIspite(this.getIspiti(), godina);

        HashSet<Student> studenti = new HashSet<>();
        Student najboljiStudent = null;
        int najboljiBrojIzvrsnihOcjena = 0;

        for (var i : ovogodisnjiIspiti) {
            studenti.add(i.getStudent());
        }

        for (var s : studenti) {
            int brojIzvrsnihOcjena = odrediBrojIzvrsnihOcjena(filtrirajIspitePoStudentu(ovogodisnjiIspiti, s));

            if (brojIzvrsnihOcjena > najboljiBrojIzvrsnihOcjena) {
                najboljiStudent = s;
                najboljiBrojIzvrsnihOcjena = brojIzvrsnihOcjena;
            }
        }

        return najboljiStudent;
    }

    private int odrediBrojIzvrsnihOcjena(List<Ispit> ispiti){
        return ispiti.stream().filter(ispit -> ispit.getOcjena() == Ocjena.ODLICAN).collect(Collectors.toList()).size();
    }

    @Override
    public Student odrediStudentaZaRektorovuNagradu() throws PostojiViseNajmladihStudenataException {
        HashSet<Student> studenti = new HashSet<>();

        for (var i : this.getIspiti()) {
            studenti.add(i.getStudent());
        }

        Student najboljiStudent = null;
        BigDecimal najboljiProsjek = new BigDecimal(0);

        for (var s : studenti) {
            BigDecimal prosjek;
            try {
                prosjek = odrediProsjekOcjenaNaIspitima((List<Ispit>) filtrirajIspitePoStudentu(this.getIspiti(), s));
            } catch (NemoguceOdreditiProsjekStudentaException e) {
                logger.info(String.format("Student %s %s zbog negativne ocjene na jednom od ispita ima prosjek 'nedovoljan (1)'!", s.getIme(), s.getPrezime()), e);
                continue;
            }

            int usporedbaDobi = najboljiStudent != null
                    ? s.getDatumRodenja().compareTo(najboljiStudent.getDatumRodenja())
                    : 1;

            if (prosjek.compareTo(najboljiProsjek) >= 0 || prosjek.equals(najboljiProsjek) && usporedbaDobi > 0) {
                najboljiStudent = s;
                najboljiProsjek = prosjek;
            } else if (usporedbaDobi == 0) {
                String najmladjiStudenti = String.format("%s %s, %s %s",
                        najboljiStudent.getIme(),
                        najboljiStudent.getPrezime(),
                        s.getIme(),
                        s.getPrezime());

                System.out.println("Prona??eno je vi??e najmla??ih studenata: " + najmladjiStudenti);
                logger.error("Prona??eno je vi??e najmla??ih studenata: " + najmladjiStudenti);

                throw new PostojiViseNajmladihStudenataException(najmladjiStudenti);
            }
        }

        return najboljiStudent;
    }
}
