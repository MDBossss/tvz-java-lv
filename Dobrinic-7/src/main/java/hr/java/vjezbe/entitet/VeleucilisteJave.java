package hr.java.vjezbe.entitet;

//import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.glavna.GlavnaDatoteke;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;


/**
 * Visokoslolska obrazovna ustanova za Javu
 */
public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska {
    public VeleucilisteJave(Long id, String naziv, List<Predmet> predmeti, List<Profesor> profesori, List<Ispit> ispiti) {
        super(id, naziv, predmeti, profesori, ispiti);
    }


    private static final Logger logger = LoggerFactory.getLogger(GlavnaDatoteke.class);
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> ispiti, Integer ocjenaPismenog, Integer ocjenaZavrsnog){
        try {
            return odrediProsjekOcjenaNaIspitima(ispiti)
                    .multiply(BigDecimal.TWO)
                    .add(BigDecimal.valueOf(ocjenaZavrsnog))
                    .add(BigDecimal.valueOf(ocjenaPismenog))
                    .divide(BigDecimal.valueOf(4));
        } catch (NemoguceOdreditiProsjekStudentaException e) {
            System.out.println("Student " + ispiti.get(0).getStudent().getIme() + " " + ispiti.get(0).getStudent().getPrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek nedovoljan 1! ");
            logger.warn("Student " + ispiti.get(0).getStudent().getIme() + " " + ispiti.get(0).getStudent().getPrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek nedovoljan 1! ",e);
            return BigDecimal.valueOf(1);
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
        return ispiti.stream().filter(ispit -> ispit.getOcjena() == Ocjena.ODLICAN).toList().size();
    }

}
