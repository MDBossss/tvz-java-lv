package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;


/**
 * Visokoslolska obrazovna ustanova za Javu
 */
public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska {
    public VeleucilisteJave(String naziv, Predmet[] predmeti, NastavnaOsoba[] profesori, Student[] studenti, Ispit[] ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, Integer ocjenaPismenog, Integer ocjenaZavrsnog){
        try {
            return odrediProsjekOcjenaNaIspitima(ispiti)
                    .multiply(BigDecimal.TWO)
                    .add(BigDecimal.valueOf(ocjenaZavrsnog))
                    .add(BigDecimal.valueOf(ocjenaPismenog))
                    .divide(BigDecimal.valueOf(4));
        } catch (NemoguceOdreditiProsjekStudentaException e) {
            System.out.println("Student " + ispiti[0].getStudent().getIme() + " " + ispiti[0].getStudent().getPrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek nedovoljan 1! ");
            logger.warn("Student " + ispiti[0].getStudent().getIme() + " " + ispiti[0].getStudent().getPrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek nedovoljan 1! ",e);
            return BigDecimal.valueOf(1);
        }
    }
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina){
        //get popis svih ispita i svih studenata
        Ispit[] ispiti = this.getIspiti();
        Student[] studenti = this.getStudenti();
        //stvaranje polja sa identicnom velicinom za ocjene svakom studenta i brojac na koliko je ispita student bio
        //potrebno za racunanje srednje vrijednosti svakog od studenta
        Double[] ocjeneStudenata = new Double[studenti.length];
        Integer[] brojIspita = new Integer[studenti.length];
        for(int i=0;i<studenti.length;i++){
            Arrays.fill(ocjeneStudenata,0.0);
            Arrays.fill(brojIspita, 0);
            //trazimo ispite od danog studenta te godine te zbrajamo sve ocjene i brojimo kolicinu ispita
            for(Ispit ispit : ispiti){
                if(ispit.getDatumIVrijeme().getYear() == godina){
                    if(ispit.getStudent().getJmbag() == studenti[i].getJmbag()){
                        ocjeneStudenata[i] += ispit.getOcjena().doubleValue();
                        brojIspita[i] ++;
                    }
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
        Integer bestProsjekIndex = 0;
        for(int i=0;i< studenti.length;i++){
            if(prosijeciOcjenaStudenata[i] >= bestProsjek){
                bestProsjek = prosijeciOcjenaStudenata[i];
                bestProsjekIndex = i;
            }
        }

        return studenti[bestProsjekIndex];
    }

}
