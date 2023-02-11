package hr.java.vjezbe.glavna;


import hr.java.vjezbe.entitet.*;

import hr.java.vjezbe.iznimke.PostojiViseNajmladihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Glavna klasa s metodom main i pomocnim metodama
 */
public class Glavna {

    public static final int BROJ_PROFESORA = 2;
    public static final int BROJ_PREDMETA = 2;
    public static final int BROJ_STUDENTA = 2;
    public static final int BROJ_ISPITA = 2;
    public static final int BROJ_ASISTENATA = 3;
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);


    /**
     * Pocetak programa
     * @param args Argumenti iz komandne linije
     */
    public static void main(String[] args){

        Scanner input = new Scanner(System.in);
        logger.info("Pocetak programa");

        drugaVjezba(input);

    }

    /**
     * Ucitava novog profesora
     * @param input Scanner s kojim se citaju podaci
     * @return novi Profesor objekt sa ucitanim podacima
     */
    static Profesor unesiProfesora(Scanner input){

        System.out.print("Unesite šifru profesora: ");
        String tempSifra = input.nextLine();
        System.out.print("Unesite ime profesora: ");
        String tempIme = input.nextLine();
        System.out.print("Unesite prezime profesora: ");
        String tempPrezime = input.nextLine();
        System.out.print("Unesite titulu profesora: ");
        String tempTitula = input.nextLine();

        return new Profesor.ProfesorBuilder().setIme(tempIme).setPrezime(tempPrezime).setSifra(tempSifra).setTitulaIzaImena(tempTitula).createProfesor();
    }

    /**
     * Ucitava novi predmet
     * @param input Scanner s kojim se citaju podaci
     * @param profesori polje profesora koji mogu predavati predmet
     * @return novi Predmet objekt sa ucitanim podacima
     */
    static Predmet unesiPredmet(Scanner input, NastavnaOsoba[] profesori){
        boolean validInput;

        System.out.print("Unesite šifru predmeta: ");
        String tempSifra = input.nextLine();

        System.out.print("Unesite naziv predmeta: ");
        String tempNaziv = input.nextLine();

        validInput = false;
        Integer tempBrojEctsBodova = null;
        do{
            try{
                System.out.print("Unesite broj ECTS bodova za predmet '" + tempNaziv + "': ");
                tempBrojEctsBodova = input.nextInt();
                input.nextLine();
                validInput = true;
            }
            catch(InputMismatchException e){
                System.out.println("Neispravno unesena vrijednost!");
                logger.error("Neispravno unesena vrijednost!",e);
                input.nextLine();
            }
        }while(!validInput);



        System.out.println("Odaberite profesora: ");
        for(int i=0;i< profesori.length;i++){
            System.out.println((i+1) + ". " + profesori[i].getIme() + " " + profesori[i].getPrezime());
        }
        NastavnaOsoba tempNositelj = null;
        validInput = false;
        do{
            try{
                System.out.print("Odabir >> ");
                int profesorIndex = input.nextInt() - 1;
                input.nextLine();
                tempNositelj = profesori[profesorIndex];
                validInput = true;
            }
            catch(InputMismatchException e){
                System.out.println("Neispravno unesena vrijednost!");
                input.nextLine();
                logger.error(e.getMessage(),e);
            }
        }while(!validInput);

        Integer tempBrojStudenata = null;
        validInput = false;
        do{
            try{
                System.out.print("Unesite broj studenata za predmet '" + tempNaziv + "': ");
                tempBrojStudenata = input.nextInt();
                input.nextLine();
                validInput = true;
            }
            catch(InputMismatchException e){
                System.out.println("Neispravno unesena vrijednost!");
                input.nextLine();
                logger.error(e.getMessage(),e);
            }
        }while(!validInput);


        Asistent[] tempAsistenti = new Asistent[BROJ_ASISTENATA];
        System.out.println("Unesite tri asistenta: ");
        for(int i=0;i<BROJ_ASISTENATA;i++){
            tempAsistenti[i] = unesiAsistenta(input);
        }

        return new Predmet(tempSifra,tempNaziv,tempBrojEctsBodova,tempNositelj,tempBrojStudenata,tempAsistenti);
    }

    /**
     * Ucitava novog studenta
     * @param input Scanner s kojim se citaju podaci
     * @return novi Student objekt sa ucitanim podacima
     */
    static Student unesiStudenta(Scanner input){
        System.out.print("Unesite ime studenta: ");
        String tempIme = input.nextLine();

        System.out.print("Unesite prezime studenta: ");
        String tempPrezime = input.nextLine();

        LocalDate tempDatumRodenja = null;
        boolean validInput = false;
        do{
            try{
                System.out.print("Unesite datum rodenja studenta " + tempPrezime + " " + tempIme + " u formatu (dd.MM.yyyy.):" );
                String stringTempDatumRodenja = input.nextLine();
                tempDatumRodenja = LocalDate.parse(stringTempDatumRodenja, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
                validInput = true;
            }
            catch(DateTimeException e){
                System.out.println("Neispravno upisan datum!");
                logger.error("Neispravno upisan datum!",e);
            }
        }
        while(!validInput);


        System.out.print("Unesite JMBAG studenta: " + tempPrezime + " " + tempIme + ": ");
        String tempJmbag = input.nextLine();

        return new Student(tempIme,tempPrezime,tempJmbag,tempDatumRodenja);
    }

    /**
     * Ucitava novi ispit
     * @param input Scanner s kojim se citaju podaci
     * @param predmeti  polje predmeta od kojih jedan biramo za ispit
     * @param studenti polje  studenta od kojih jedan biramo za ispit
     * @return
     */
    static Ispit unesiIspit(Scanner input,Predmet[] predmeti,Student[] studenti){
        boolean validInput = false;

        System.out.println("Odaberite predmet: ");
        for(int i=0;i<BROJ_PREDMETA;i++){
            System.out.println((i+1) + ". " + predmeti[i].getNaziv());
        }

        Predmet tempPredmet = null;
        do{
            try{
                System.out.print("Odabir >> ");
                int predmetIndex = input.nextInt() - 1 ;
                input.nextLine();
                tempPredmet = predmeti[predmetIndex];
                validInput = true;
            }
            catch(InputMismatchException e){
                System.out.println("Neispravno unesena vrijednost!");
                input.nextLine();
                logger.error(e.getMessage(),e);
            }
        }while(!validInput);


        System.out.println("Odaberite studenta: ");
        for(int i=0;i<BROJ_STUDENTA;i++){
            System.out.println((i+1) + ". " + studenti[i].getIme() + " " + studenti[i].getPrezime());
        }

        Student tempStudent = null;
        validInput = false;
        do{
            try{
                System.out.print("Odabir >> ");
                int studentIndex = input.nextInt() - 1;
                input.nextLine();
                tempStudent = studenti[studentIndex];
                validInput = true;
            }
            catch (InputMismatchException e){
                System.out.println("Neispravno unesena vrijednost!");
                input.nextLine();
                logger.error(e.getMessage(),e);
            }
        }while(!validInput);


        Dvorana tempDvorana = unesiteDvoranu(input);

        Integer tempOcjena = null;
        validInput = false;
        do{
            try{
                System.out.print("Unesite ocjenu na ispitu (1-5): ");
                tempOcjena = input.nextInt();
                input.nextLine();
                validInput = true;
            }
            catch(InputMismatchException e){
                System.out.println("Neispravno unesena vrijednost!");
                input.nextLine();
                logger.error(e.getMessage(),e);
            }
        }while(!validInput);


        LocalDateTime tempDatumIVrijeme = null;
        validInput = false;
        do{
            try{
                System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyyTHH:mm): ");
                String stringTempDatumIVrijeme = input.nextLine();
                tempDatumIVrijeme = LocalDateTime.parse(stringTempDatumIVrijeme, DateTimeFormatter.ofPattern("dd.MM.yyyy'T'HH:mm"));
                validInput = true;
            }
            catch(InputMismatchException e){
                System.out.println("Neispravno unesen datum!");
            }
        }while(!validInput);



        return new Ispit(tempPredmet,tempStudent,tempOcjena,tempDatumIVrijeme,tempDvorana);
    }

    /**
     * Unosi dvoranu u kojoj se pise ispit
     * @param input Scanner s kojim se citaju podaci
     * @return objekt Dvorana sa ucitanim podacima
     */
    static Dvorana unesiteDvoranu(Scanner input){
        System.out.print("Unesite naziv dvorane: ");
        String tempNaziv = input.nextLine();

        System.out.print("Unesite zgradu dvorane: ");
        String tempZgrada = input.nextLine();

        return new Dvorana(tempNaziv,tempZgrada);
    }

    //dodavanje studenata u predmet zadatak 9.

    /**
     * Dodaje studente sa ispita u predmet
     * @param ispiti Svi ispite koji su pisani
     * @param predmeti Predemt za koji trazimo ispite
     */
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

    /**
     * Unosi obrazovnu ustanovu sa svim dijelovima ustanove
     * @param input Scanner s kojim unosimo podatke
     * @return objekt Ustanova sa svim unesenim podacima
     */
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

        Integer odabirUstanove = null;
        boolean validInput = false;
        do{
            try{
                System.out.println("Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti: \n (1 - Veleučilište Jave, 2 - Fakultet računarstva):");
                odabirUstanove = input.nextInt();
                input.nextLine();
                validInput = true;
            }
            catch(InputMismatchException e){
                System.out.println("Neispravno unesena vrijednost!");
                input.nextLine();
                logger.error(e.getMessage(),e);
            }
        }while(!validInput);


        System.out.print("Unesite naziv obrazovne ustanove: ");
        String tempNaziv = input.nextLine();

        if(odabirUstanove == 1){
            return new VeleucilisteJave(tempNaziv,predmeti,profesori,studenti,ispiti);
        }
        else{
            return new FakultetRacunarstva(tempNaziv,predmeti,profesori,studenti,ispiti);
        }
    }

    /**
     * Odreduje ispite sa studentovim imenom
     * @param ispiti polje svih ispita
     * @param student student za kojeg zelimo izdvojiti ispite
     * @return polje ispita koje je pisao predati student
     */
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

    /**
     * Unosi podatke o asistentima
     * @param input Scanner sa kojim unosimo podatke
     * @return objekt Asistent sa unesenim podacima
     */
    static Asistent unesiAsistenta(Scanner input){
        System.out.print("Unesite ime: ");
        String tempIme = input.nextLine();

        System.out.print("Unesite prezime: ");
        String tempPrezime = input.nextLine();

        System.out.print("Unesite sifru: ");
        String tempSifra = input.nextLine();

        return new Asistent(tempIme,tempPrezime,tempSifra);
    }

    /**
     * Provjerava ima li neki od ispita jedinicu
     * @param ispiti ispite koje zelimo provjeriti
     * @return true ako jedan od ispita ima jedinicu
     */
    static boolean imaJedinicu(Ispit[] ispiti){
        boolean imaJedinicu = false;
        for(Ispit ispit : ispiti){
            if(ispit.getOcjena() == 1){
                imaJedinicu = true;
                break;
            }
        }
        return imaJedinicu;
    }

    /**
     * Ispisuje asistente na predmetima
     * @param obrazovneUstanove obrazovna ustanova za koju ispisujemo asistente na predmetima
     */
    static void ispisiPredmet(ObrazovnaUstanova[] obrazovneUstanove){
        for (ObrazovnaUstanova obrazovnaUstanova : obrazovneUstanove) {
            Predmet[] predmeti = obrazovnaUstanova.getPredmeti();
            System.out.println(obrazovnaUstanova.getNaziv() + " asistenti na predmetima: ");
            for (Predmet predmet : predmeti) {
                System.out.println(predmet.getNaziv() + ": ");
                for (Asistent asistent : predmet.getAsistenti()) {
                    System.out.println(asistent.getIme() + " " + asistent.getPrezime() + " " + asistent.getSifra());
                }
            }
        }
    }

    /**
     * Naredbe koje prate upute druge vjezbe
     * @param input Scanner sa kojim unosimo podatke
     */
    static void drugaVjezba(Scanner input){
        boolean validInput = false;

        Integer brojUstanova = null;
        do{
            try{
                System.out.print("Unesite broj obrazovnih ustanova: ");
                brojUstanova = input.nextInt();
                input.nextLine();
                validInput = true;
            }
            catch(InputMismatchException e){
                System.out.println("Neispravno unesena vrijednost!");
                input.nextLine();
                logger.error(e.getMessage(),e);
            }
        }while(!validInput);

        ObrazovnaUstanova[] obrazovneUstanove = new ObrazovnaUstanova[brojUstanova];

        for(int j=0;j<brojUstanova;j++){
            System.out.println("Unesite " + (j+1) + " obrazovnu ustanovu:" );
            obrazovneUstanove[j] = unesiObrazovnuUstanovu(input);


            for(Student student : obrazovneUstanove[j].getStudenti()){
                Ispit[] studentoviIspiti = odrediStudentoveIspite(obrazovneUstanove[j].getIspiti(),student);

                if(imaJedinicu(studentoviIspiti)){
                    System.out.println("Student ima jedinicu i nema ocjena zavrsnog rada!");
                    logger.error("Student ima jedinicu i nema ocjena zavrsnog rada!");
                    break;
                }

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

                Student rektor = null;
                try {
                    rektor = ((FakultetRacunarstva) obrazovneUstanove[j]).odrediStudentaZaRektorovuNagradu();
                    System.out.println("Student koji je osvojio rektorovu nagradu je: " + rektor.getIme() + " " +rektor.getPrezime() + " JMBAG: " + rektor.getJmbag());
                } catch (PostojiViseNajmladihStudenataException e){
                    logger.error(e.getMessage(),e);
                }
            }
        }
        //ISPISIVANJE ASISTENTA IZ PREDMETA
        ispisiPredmet(obrazovneUstanove);
    }







}
