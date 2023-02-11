package hr.java.vjezbe.glavna;


import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Glavna {

    public static final int BROJ_PROFESORA = 2;
    public static final int BROJ_PREDMETA = 3;
    public static final int BROJ_STUDENTA = 2;
    public static final int BROJ_ISPITA = 1;


    public static void main(String[] args){
        Profesor[] profesori = new Profesor[BROJ_PROFESORA];
        Predmet[] predmeti = new Predmet[BROJ_PREDMETA];
        Student[] studenti = new Student[BROJ_STUDENTA];
        Ispit[] ispiti = new Ispit[BROJ_ISPITA];

        Scanner input = new Scanner(System.in);

        for(int i=0;i<BROJ_PROFESORA;i++){
            System.out.println("Unesite " + (i+1) + " profesora: ");
            profesori[i] = unesiProfesora(input);
        }

        for(int i=0;i<BROJ_PREDMETA;i++){
            System.out.println("Unesite " + (i+1) + " predmet: ");
            predmeti[i] = unesiPredmet(input,profesori);
        }

        for(int i=0;i<BROJ_STUDENTA;i++){
            System.out.println("Unesite " + (i+1) + " studenta: ");
            studenti[i] = unesiStudenta(input);
        }

        for(int i=0;i<BROJ_ISPITA;i++){
            System.out.println("Unesite " + (i+1) + ". ispitni rok: ");
            ispiti[i] = unesiIspit(input,predmeti,studenti);
        }

        //ispis studenata sa ocijenom 5
        for(int i=0;i<BROJ_ISPITA;i++){
            if(ispiti[i].getOcjena() == 5){
                System.out.println("Student " + ispiti[i].getStudent().getIme() + " " + ispiti[i].getStudent().getPrezime() + " je ostvario ocjenu 'izvrstan' na predmetu '" + ispiti[i].getPredmet().getNaziv() + "'");
            }
        }

    }

    static Profesor unesiProfesora(Scanner input){

        System.out.print("Unesite šifru profesora: ");
        String tempSifra = input.nextLine();
        System.out.print("Unesite ime profesora: ");
        String tempIme = input.nextLine();
        System.out.print("Unesite prezime profesora: ");
        String tempPrezime = input.nextLine();
        System.out.print("Unesite titulu profesora: ");
        String tempTitula = input.nextLine();


        Profesor tempProfesor = new Profesor(tempSifra,tempIme,tempPrezime,tempTitula);

        return tempProfesor;
    }

    static Predmet unesiPredmet(Scanner input,Profesor[] profesori){
        System.out.print("Unesite šifru predmeta: ");
        String tempSifra = input.nextLine();

        System.out.print("Unesite naziv predmeta: ");
        String tempNaziv = input.nextLine();

        System.out.print("Unesite broj ECTS bodova za predmet '" + tempNaziv + "': ");
        Integer tempBrojEctsBodova = input.nextInt();
        input.nextLine();

        System.out.println("Odaberite profesora: ");
        for(int i=0;i<BROJ_PROFESORA;i++){
            System.out.println((i+1) + ". " + profesori[i].getIme() + " " + profesori[i].getPrezime());
        }
        System.out.print("Odabir >> ");
        Integer profesorIndex = input.nextInt() - 1;
        input.nextLine();
        Profesor tempNositelj = profesori[profesorIndex];

        System.out.print("Unesite broj studenata za predmet '" + tempNaziv + "': ");
        Integer tempBrojStudenata = input.nextInt();
        input.nextLine();


        Predmet tempPredmet = new Predmet(tempSifra,tempNaziv,tempBrojEctsBodova,tempNositelj,tempBrojStudenata);

        return tempPredmet;
    }

    static Student unesiStudenta(Scanner input){
        System.out.print("Unesite ime studenta: ");
        String tempIme = input.nextLine();

        System.out.print("Unesite prezime studenta: ");
        String tempPrezime = input.nextLine();

        System.out.print("Unesite datum rodenja studenta " + tempPrezime + " " + tempIme + " u formatu (dd.MM.yyyy.):" );
        String stringTempDatumRodenja = input.nextLine();
        LocalDate tempDatumRodenja = LocalDate.parse(stringTempDatumRodenja, DateTimeFormatter.ofPattern("dd.MM.yyyy."));

        System.out.print("Unesite JMBAG studenta: " + tempPrezime + " " + tempIme + ": ");
        String tempJmbag = input.nextLine();

        Student tempStudent = new Student(tempIme,tempPrezime,tempJmbag,tempDatumRodenja);

        return tempStudent;
    }

    static Ispit unesiIspit(Scanner input,Predmet[] predmeti,Student[] studenti){
        System.out.println("Odaberite predmet: ");
        for(int i=0;i<BROJ_PREDMETA;i++){
            System.out.println((i+1) + ". " + predmeti[i].getNaziv());
        }
        System.out.print("Odabir >> ");
        Integer predmetIndex = input.nextInt() - 1 ;
        input.nextLine();
        Predmet tempPredmet = predmeti[predmetIndex];

        System.out.println("Odaberite studenta: ");
        for(int i=0;i<BROJ_STUDENTA;i++){
            System.out.println((i+1) + ". " + studenti[i].getIme() + " " + studenti[i].getPrezime());
        }
        System.out.print("Odabir >> ");
        Integer studentIndex = input.nextInt() - 1;
        input.nextLine();
        Student tempStudent = studenti[studentIndex];

        System.out.print("Unesite ocjenu na ispitu (1-5): ");
        Integer tempOcjena = input.nextInt();
        input.nextLine();

        System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyyTHH:mm): ");
        String stringTempDatumIVrijeme = input.nextLine();
        LocalDateTime tempDatumIVrijeme = LocalDateTime.parse(stringTempDatumIVrijeme, DateTimeFormatter.ofPattern("dd.MM.yyyy'T'HH:mm"));



        Ispit tempIspit = new Ispit(tempPredmet,tempStudent,tempOcjena,tempDatumIVrijeme);

        return tempIspit;
    }





}
