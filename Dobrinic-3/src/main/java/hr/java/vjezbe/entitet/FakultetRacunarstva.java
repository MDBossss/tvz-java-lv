package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Diplomska obrazovna ustanova za racunarstvo
 */
public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski{
    public FakultetRacunarstva(String naziv, Predmet[] predmeti, NastavnaOsoba[] profesori, Student[] studenti, Ispit[] ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, Integer ocjenaPismenog, Integer ocjenaDiplomskog){
        try {
            return odrediProsjekOcjenaNaIspitima(ispiti)
                    .multiply(BigDecimal.valueOf(3))
                    .add(BigDecimal.valueOf(ocjenaDiplomskog))
                    .add(BigDecimal.valueOf(ocjenaPismenog))
                    .divide(BigDecimal.valueOf(5));
        } catch (NemoguceOdreditiProsjekStudentaException e) {
            System.out.println("Student " + ispiti[0].getStudent().getIme() + " " + ispiti[0].getStudent().getPrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek nedovoljan 1! ");
            logger.warn("Student " + ispiti[0].getStudent().getIme() + " " + ispiti[0].getStudent().getPrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek nedovoljan 1! ",e);
            return BigDecimal.ONE;
        }
    }
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina){
        //get popis svih ispita i svih studenata
        Ispit[] ispiti = this.getIspiti();
        Student[] studenti = this.getStudenti();
        Integer[] brojOdlicnihOcjena = new Integer[studenti.length];
        for(int i=0;i<studenti.length;i++){
            Arrays.fill(brojOdlicnihOcjena,0);
            //trazimo ispite od danog studenta te godine te brojimo odlicne ocijene
            for(Ispit ispit : ispiti){
                if(ispit.getDatumIVrijeme().getYear() == godina){
                    if(ispit.getStudent().getJmbag() == studenti[i].getJmbag()){
                        if(ispit.getOcjena() == 5){
                            brojOdlicnihOcjena[i] ++;
                        }
                    }
                }
            }
        }

        //trazimo studenta sa najvise odlicnih i vracamo ga
        Integer najviseOdlicnih = brojOdlicnihOcjena[0];
        Integer najviseOdlicnihIndex = 0;
        for(int i=0;i< studenti.length;i++){
            if(brojOdlicnihOcjena[i] > najviseOdlicnih){
                najviseOdlicnih = brojOdlicnihOcjena[i];
                najviseOdlicnihIndex = i;
            }
        }

        return studenti[najviseOdlicnihIndex];
    }
    @Override
    public Student odrediStudentaZaRektorovuNagradu() throws PostojiViseNajmladihStudenataException {
        //get popis svih ispita i svih studenata
        Ispit[] ispiti = this.getIspiti();
        Student[] studenti = this.getStudenti();
        //stvaranje polja sa identicnom velicinom za ocjene svakom studenta i brojac na koliko je ispita student bio
        //potrebno za racunanje srednje vrijednosti svakog od studenta
        Double[] ocjeneStudenata = new Double[studenti.length];
        Integer[] brojIspita = new Integer[studenti.length];
        for(int i=0;i<studenti.length;i++){
            Arrays.fill(ocjeneStudenata,0);
            //trazimo ispite od danog studenta te godine te zbrajamo sve ocjene i brojimo kolicinu ispita
            for(Ispit ispit : ispiti){
                if(ispit.getStudent().getJmbag() == studenti[i].getJmbag()){
                    ocjeneStudenata[i] += ispit.getOcjena().doubleValue();
                    brojIspita[i] ++;
                }
            }
        }
        //racunamo prosijek za svakog od studenta u polju
        Double[] prosijeciOcjenaStudenata = new Double[studenti.length];
        for(int i=0;i< studenti.length;i++){
            prosijeciOcjenaStudenata[i] = ocjeneStudenata[i] / brojIspita[i];
        }

        //trazimo studenta sa najboljim prosijekom i vracamo ga
        Double bestProsjek = prosijeciOcjenaStudenata[0];
        int bestProsjekIndex = 0;
        for(int i=0;i< studenti.length;i++){
            //ako je prosijek bolji, stavi ga svejedno
            if(prosijeciOcjenaStudenata[i] > bestProsjek){
                bestProsjek = prosijeciOcjenaStudenata[i];
                bestProsjekIndex = i;
            }
            //ako je prosijek isti, provjeri koji je od dvoje mladi
            if(prosijeciOcjenaStudenata[i] == bestProsjek){
                if(studenti[i].getDatumRodenja().isBefore(studenti[bestProsjekIndex].getDatumRodenja())){
                    bestProsjek = prosijeciOcjenaStudenata[i];
                    bestProsjekIndex = i;
                }
                //ako su rodeni isti dan baci exception
                if(studenti[i].getDatumRodenja().compareTo(studenti[bestProsjekIndex].getDatumRodenja()) == 0){
                    System.out.println("Studenti " + studenti[i].getIme() + studenti[i].getPrezime() + ", " + studenti[bestProsjekIndex].getIme() + " " + studenti[bestProsjekIndex].getPrezime() + " imaju isti prosijek i isti dan rodenja!");
                    logger.error("Studenti " + studenti[i].getIme() + studenti[i].getPrezime() + ", " + studenti[bestProsjekIndex].getIme() + " " + studenti[bestProsjekIndex].getPrezime() + " imaju isti prosijek i isti dan rodenja!");
                    throw new PostojiViseNajmladihStudenataException("Studenti " + studenti[i].getIme() + studenti[i].getPrezime() + ", " + studenti[bestProsjekIndex].getIme() + " " + studenti[bestProsjekIndex].getPrezime() + " imaju isti prosijek i isti dan rodenja!");
                }
            }
        }

        return studenti[bestProsjekIndex];
    }
}
