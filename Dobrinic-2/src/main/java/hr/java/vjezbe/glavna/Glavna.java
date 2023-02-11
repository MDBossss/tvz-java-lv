package hr.java.vjezbe.glavna;


import hr.java.vjezbe.entitet.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class Glavna {

    public static final int BROJ_PROFESORA = 2;
    public static final int BROJ_PREDMETA = 2;
    public static final int BROJ_STUDENTA = 2;
    public static final int BROJ_ISPITA = 2;
    public static final int BROJ_ASISTENATA = 3;


    public static void main(String[] args){

        Scanner input = new Scanner(System.in);

        System.out.print("Unesite broj obrazovnih ustanova: ");
        Integer brojUstanova = input.nextInt();
        input.nextLine();
        ObrazovnaUstanova[] obrazovneUstanove = new ObrazovnaUstanova[brojUstanova];

        for(int j=0;j<brojUstanova;j++){
            System.out.println("Unesite " + (j+1) + " obrazovnu ustanovu:" );
            obrazovneUstanove[j] = unesiObrazovnuUstanovu(input);


            for(Student student : obrazovneUstanove[j].getStudenti()){
                Ispit[] studentoviIspiti = odrediStudentoveIspite(obrazovneUstanove[j].getIspiti(),student);

                System.out.print("Unesite ocjenu zavrsnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ": ");
                Integer tempZavrsni = input.nextInt();
                input.nextLine();

                System.out.print("Unesite ocjenu obranze zavrsnog rada za studenta :" + student.getIme() + " " + student.getPrezime() + ": ");
                Integer tempObrana = input.nextInt();
                input.nextLine();

                if(obrazovneUstanove[j] instanceof VeleucilisteJave){
                    Integer konacnaOcjena = ((VeleucilisteJave) obrazovneUstanove[j]).izracunajKonacnuOcjenuStudijaZaStudenta(studentoviIspiti,tempObrana,tempZavrsni).intValue();
                    System.out.println("Konacna ocjena studija studenta " + student.getIme() + " " + student.getPrezime() + " je" + konacnaOcjena);
                }
                if(obrazovneUstanove[j] instanceof FakultetRacunarstva){
                    Integer konacnaOcjena = ((FakultetRacunarstva) obrazovneUstanove[j]).izracunajKonacnuOcjenuStudijaZaStudenta(studentoviIspiti,tempObrana,tempZavrsni).intValue();
                    System.out.println("Konacna ocjena studija studenta " + student.getIme() + " " + student.getPrezime() + " je" + konacnaOcjena);
                }
            }

            if(obrazovneUstanove[j] instanceof  VeleucilisteJave){
                Student student = obrazovneUstanove[j].odrediNajuspjesnijegStudentaNaGodini(2022);
                System.out.println("Najbolji student 2022. godine je " + student.getIme() + " " +student.getPrezime() + " JMBAG: " + student.getJmbag());
            }

            if(obrazovneUstanove[j] instanceof  FakultetRacunarstva){
                Student student = obrazovneUstanove[j].odrediNajuspjesnijegStudentaNaGodini(2022);
                System.out.println("Najbolji student 2022. godine je " + student.getIme() + " " +student.getPrezime() + " JMBAG: " + student.getJmbag());

                Student rektor = ((FakultetRacunarstva) obrazovneUstanove[j]).odrediStudentaZaRektorovuNagradu();
                System.out.println("Student koji je osvojio rektorovu nagradu je: " + rektor.getIme() + " " +rektor.getPrezime() + " JMBAG: " + rektor.getJmbag());
            }
        }
            //ISPISIVANJE ASISTENTA IZ PREDMETA
            ispisiPredmet(obrazovneUstanove);

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


//        Profesor tempProfesor = new Profesor.Builder(tempIme,tempPrezime).sifra(tempSifra).titula(tempTitula).build();
        Profesor tempProfesor = new Profesor.ProfesorBuilder().setIme(tempIme).setPrezime(tempPrezime).setSifra(tempSifra).setTitulaIzaImena(tempTitula).createProfesor();

        return tempProfesor;
    }

    static Predmet unesiPredmet(Scanner input, NastavnaOsoba[] profesori){
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
        NastavnaOsoba tempNositelj = profesori[profesorIndex];

        System.out.print("Unesite broj studenata za predmet '" + tempNaziv + "': ");
        Integer tempBrojStudenata = input.nextInt();
        input.nextLine();

        Asistent[] tempAsistenti = new Asistent[BROJ_ASISTENATA];
        System.out.println("Unesite tri asistenta: ");
        for(int i=0;i<BROJ_ASISTENATA;i++){
            tempAsistenti[i] = unesiAsistenta(input);
        }


        Predmet tempPredmet = new Predmet(tempSifra,tempNaziv,tempBrojEctsBodova,tempNositelj,tempBrojStudenata,tempAsistenti);

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

        Dvorana tempDvorana = unesiteDvoranu(input);

        System.out.print("Unesite ocjenu na ispitu (1-5): ");
        Integer tempOcjena = input.nextInt();
        input.nextLine();

        System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyyTHH:mm): ");
        String stringTempDatumIVrijeme = input.nextLine();
        LocalDateTime tempDatumIVrijeme = LocalDateTime.parse(stringTempDatumIVrijeme, DateTimeFormatter.ofPattern("dd.MM.yyyy'T'HH:mm"));


        Ispit tempIspit = new Ispit(tempPredmet,tempStudent,tempOcjena,tempDatumIVrijeme,tempDvorana);

        return tempIspit;
    }

    static Dvorana unesiteDvoranu(Scanner input){
        System.out.print("Unesite naziv dvorane: ");
        String tempNaziv = input.nextLine();

        System.out.print("Unesite zgradu dvorane: ");
        String tempZgrada = input.nextLine();

        Dvorana tempDvorana = new Dvorana(tempNaziv,tempZgrada);
        return tempDvorana;
    }

    //dodavanje studenata u predmet zadatak 9.
    static void dodajStudenteuPredmet(Ispit[] ispiti,Predmet[] predmeti){
        for(Predmet predmet : predmeti){
            Student[] tempStudenti = new Student[1];
            for(Ispit ispit : ispiti){
                if(ispit.getPredmet().getNaziv() == predmet.getNaziv()){
                    tempStudenti = Arrays.copyOf(tempStudenti,tempStudenti.length+1);
                    tempStudenti[tempStudenti.length - 1] = ispit.getStudent();
                }
            }
            predmet.setStudenti(tempStudenti);
        }
    }

    static ObrazovnaUstanova unesiObrazovnuUstanovu(Scanner input){
        NastavnaOsoba[] profesori = new NastavnaOsoba[BROJ_PROFESORA];
        Predmet[] predmeti = new Predmet[BROJ_PREDMETA];
        Student[] studenti = new Student[BROJ_STUDENTA];
        Ispit[] ispiti = new Ispit[BROJ_ISPITA];


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

        dodajStudenteuPredmet(ispiti,predmeti);


        //ispis studenata sa ocijenom 5
        for(int i=0;i<BROJ_ISPITA;i++){
            if(ispiti[i].getOcjena() == 5){
                System.out.println("Student " + ispiti[i].getStudent().getIme() + " " + ispiti[i].getStudent().getPrezime() + " je ostvario ocjenu 'izvrstan' na predmetu '" + ispiti[i].getPredmet().getNaziv() + "'");
            }
        }

        System.out.println("Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti: \n (1 - Veleučilište Jave, 2 - Fakultet računarstva):");
        Integer odabirUstanove = input.nextInt();
        input.nextLine();

        System.out.print("Unesite naziv obrazovne ustanove: ");
        String tempNaziv = input.nextLine();

        if(odabirUstanove == 1){
            return new VeleucilisteJave(tempNaziv,predmeti,profesori,studenti,ispiti);
        }
        else{
            return new FakultetRacunarstva(tempNaziv,predmeti,profesori,studenti,ispiti);
        }
    }

    static Ispit[] odrediStudentoveIspite(Ispit[] ispiti,Student student){
        Ispit[] studentoviIspiti = new Ispit[1];
        for(Ispit ispit : ispiti){
            if(ispit.getStudent().getJmbag() == student.getJmbag()){
                Arrays.copyOf(studentoviIspiti,studentoviIspiti.length+1);
                studentoviIspiti[studentoviIspiti.length - 1] = ispit;
            }
        }
        return studentoviIspiti;
    }

    static Asistent unesiAsistenta(Scanner input){
        System.out.print("Unesite ime: ");
        String tempIme = input.nextLine();

        System.out.print("Unesite prezime: ");
        String tempPrezime = input.nextLine();

        System.out.print("Unesite sifru: ");
        String tempSifra = input.nextLine();

        Asistent tempAsistent = new Asistent(tempIme,tempPrezime,tempSifra);

        return tempAsistent;
    }

    static void ispisiPredmet(ObrazovnaUstanova[] obrazovneUstanove){
        for(int i=0;i< obrazovneUstanove.length;i++){
            Predmet[] predmeti = obrazovneUstanove[i].getPredmeti();
            System.out.println(obrazovneUstanove[i].getNaziv() + " asistenti na predmetima: ");
            for(Predmet predmet : predmeti){
                System.out.println(predmet.getNaziv() + ": ");
                for(Asistent asistent : predmet.getAsistenti()){
                    System.out.println(asistent.getIme() + " " + asistent.getPrezime() + " " +asistent.getSifra());
                }
            }
        }
    }







}
