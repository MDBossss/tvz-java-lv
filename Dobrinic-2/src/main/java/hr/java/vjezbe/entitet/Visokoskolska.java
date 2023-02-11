package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.Arrays;

public interface Visokoskolska {
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti,Integer ocjenaPismenog,Integer ocjenaZavrsnog);

    default BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispiti){
        BigDecimal sum = BigDecimal.ZERO;
        for(Ispit ispit : ispiti){
            sum.add(new BigDecimal(ispit.getOcjena()));
        }
        return sum.divide(new BigDecimal(ispiti.length));
    }

    private Ispit[] filtrirajPolozeneIspite(Ispit[] ispiti){
        Ispit[] polozeniIspiti = new Ispit[0];
        for(Ispit ispit : ispiti){
            if(ispit.getOcjena() < 2){
                polozeniIspiti = Arrays.copyOf(polozeniIspiti,polozeniIspiti.length+1);
                polozeniIspiti[polozeniIspiti.length - 1] = ispit;
            }
        }
        return polozeniIspiti;
    }

    default Ispit[] filtrirajIspitePoStudentu(Ispit[] ispiti,Student student){
        Ispit[] studentoviIspiti = new Ispit[0];
        for(Ispit ispit: ispiti){
            if(ispit.getStudent().getJmbag() == student.getJmbag()){
                studentoviIspiti = Arrays.copyOf(studentoviIspiti,studentoviIspiti.length + 1);
                studentoviIspiti[studentoviIspiti.length - 1] = ispit;
            }
        }
        return studentoviIspiti;
    }

}
