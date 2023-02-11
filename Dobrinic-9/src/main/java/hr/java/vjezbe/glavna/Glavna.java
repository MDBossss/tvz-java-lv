package hr.java.vjezbe.glavna;//package hr.java.vjezbe.glavna;
//
//
//import hr.java.vjezbe.entitet.*;
//
//import hr.java.vjezbe.iznimke.PostojiViseNajmladihStudenataException;
//import hr.java.vjezbe.sortiranje.StudentSorter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.time.DateTimeException;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * Glavna klasa s metodom main i pomocnim metodama
// */
//public class Glavna {
//
//    public static final int BROJ_PROFESORA = 2;
//    public static final int BROJ_PREDMETA = 3;
//    public static final int BROJ_STUDENTA = 3;
//    public static final int BROJ_ISPITA = 2;
//    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
//
//
//    /**
//     * Pocetak programa
//     * @param args Argumenti iz komandne linije
//     */
//    public static void main(String[] args){
//
//        Scanner input = new Scanner(System.in);
//        logger.info("Pocetak programa");
//
//
//
//    }
//
//    /**
//     * Ucitava novog profesora
//     * @param input Scanner s kojim se citaju podaci
//     * @return novi Profesor objekt sa ucitanim podacima
//     */
//    static Profesor unesiProfesora(Scanner input){
//
//        System.out.print("Unesite šifru profesora: ");
//        String tempSifra = input.nextLine();
//        System.out.print("Unesite ime profesora: ");
//        String tempIme = input.nextLine();
//        System.out.print("Unesite prezime profesora: ");
//        String tempPrezime = input.nextLine();
//        System.out.print("Unesite titulu profesora: ");
//        String tempTitula = input.nextLine();
//
//        return new Profesor.ProfesorBuilder().setIme(tempIme).setPrezime(tempPrezime).setSifra(tempSifra).setTitulaIzaImena(tempTitula).createProfesor();
//    }
//
//    /**
//     * Ucitava novi predmet
//     * @param input Scanner s kojim se citaju podaci
//     * @param profesori polje profesora koji mogu predavati predmet
//     * @return novi Predmet objekt sa ucitanim podacima
//     */
//    static Predmet unesiPredmet(Scanner input, List<NastavnaOsoba> profesori, List<Student> studenti){
//        boolean validInput;
//
//        System.out.print("Unesite šifru predmeta: ");
//        String tempSifra = input.nextLine();
//
//        System.out.print("Unesite naziv predmeta: ");
//        String tempNaziv = input.nextLine();
//
//        validInput = false;
//        Integer tempBrojEctsBodova = null;
//        do{
//            try{
//                System.out.print("Unesite broj ECTS bodova za predmet '" + tempNaziv + "': ");
//                tempBrojEctsBodova = input.nextInt();
//                input.nextLine();
//                validInput = true;
//            }
//            catch(InputMismatchException e){
//                System.out.println("Neispravno unesena vrijednost!");
//                logger.error("Neispravno unesena vrijednost!",e);
//                input.nextLine();
//            }
//        }while(!validInput);
//
//
//
//        System.out.println("Odaberite profesora: ");
//        for(int i=0;i< profesori.size();i++){
//            System.out.println((i+1) + ". " + profesori.get(i).getIme() + " " + profesori.get(i).getPrezime());
//        }
//        NastavnaOsoba tempNositelj = null;
//        validInput = false;
//        do{
//            try{
//                System.out.print("Odabir >> ");
//                int profesorIndex = input.nextInt() - 1;
//                input.nextLine();
//                tempNositelj = profesori.get(profesorIndex);
//                validInput = true;
//            }
//            catch(InputMismatchException e){
//                System.out.println("Neispravno unesena vrijednost!");
//                input.nextLine();
//                logger.error(e.getMessage(),e);
//            }
//        }while(!validInput);
//
//        System.out.println("Odaberite studente,za kraj unosa upisite 0: ");
//        for(int i=0;i< studenti.size();i++){
//            System.out.println((i+1) + ". " + studenti.get(i).getIme() + " " + studenti.get(i).getPrezime());
//        }
//        Set<Student> tempStudenti = new HashSet<>();
//
//        boolean loopInput = true;
//        while((loopInput) || (tempStudenti.size() < 1)){
//            try{
//                System.out.print("Odabir>> ");
//                int odabir = input.nextInt();
//                if((odabir == 0) && (tempStudenti.size() < 1)){
//                    System.out.println("Unesite barem jednog studenta!");
//                }
//                else if(odabir == 0){
//                    loopInput = false;
//                    input.nextLine();
//                }
//                else{
//                    tempStudenti.add(studenti.get(odabir-1));
//                }
//            }
//            catch(InputMismatchException e){
//                System.out.println("Neispravno unesena vrijednost!");
//                input.nextLine();
//                logger.error(e.getMessage(),e);
//            }
//        }
//
//
//
//
//        return new Predmet(tempSifra,tempNaziv,tempBrojEctsBodova,tempNositelj,tempStudenti);
//    }
//
//    /**
//     * Ucitava novog studenta
//     * @param input Scanner s kojim se citaju podaci
//     * @return novi Student objekt sa ucitanim podacima
//     */
//    static Student unesiStudenta(Scanner input){
//        System.out.print("Unesite ime studenta: ");
//        String tempIme = input.nextLine();
//
//        System.out.print("Unesite prezime studenta: ");
//        String tempPrezime = input.nextLine();
//
//        LocalDate tempDatumRodenja = null;
//        boolean validInput = false;
//        do{
//            try{
//                System.out.print("Unesite datum rodenja studenta " + tempPrezime + " " + tempIme + " u formatu (dd.MM.yyyy.):" );
//                String stringTempDatumRodenja = input.nextLine();
//                tempDatumRodenja = LocalDate.parse(stringTempDatumRodenja, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
//                validInput = true;
//            }
//            catch(DateTimeException e){
//                System.out.println("Neispravno upisan datum!");
//                logger.error("Neispravno upisan datum!",e);
//            }
//        }
//        while(!validInput);
//
//
//        System.out.print("Unesite JMBAG studenta: " + tempPrezime + " " + tempIme + ": ");
//        String tempJmbag = input.nextLine();
//
//        return new Student(tempIme,tempPrezime,tempJmbag,tempDatumRodenja);
//    }
//
//    /**
//     * Ucitava novi ispit
//     * @param input Scanner s kojim se citaju podaci
//     * @param predmeti  polje predmeta od kojih jedan biramo za ispit
//     * @param studenti polje  studenta od kojih jedan biramo za ispit
//     * @return
//     */
//    static Ispit unesiIspit(Scanner input,List<Predmet> predmeti,List<Student> studenti){
//        boolean validInput = false;
//
//        System.out.println("Odaberite predmet: ");
//        for(int i=0;i<BROJ_PREDMETA;i++){
//            System.out.println((i+1) + ". " + predmeti.get(i).getNaziv());
//        }
//
//        Predmet tempPredmet = null;
//        do{
//            try{
//                System.out.print("Odabir >> ");
//                int predmetIndex = input.nextInt() - 1 ;
//                input.nextLine();
//                tempPredmet = predmeti.get(predmetIndex);
//                validInput = true;
//            }
//            catch(InputMismatchException e){
//                System.out.println("Neispravno unesena vrijednost!");
//                input.nextLine();
//                logger.error(e.getMessage(),e);
//            }
//        }while(!validInput);
//
//
//        System.out.println("Odaberite studenta: ");
//        for(int i=0;i<BROJ_STUDENTA;i++){
//            System.out.println((i+1) + ". " + studenti.get(i).getIme() + " " + studenti.get(i).getPrezime());
//        }
//
//        Student tempStudent = null;
//        validInput = false;
//        do{
//            try{
//                System.out.print("Odabir >> ");
//                int studentIndex = input.nextInt() - 1;
//                input.nextLine();
//                tempStudent = studenti.get(studentIndex);
//                validInput = true;
//            }
//            catch (InputMismatchException e){
//                System.out.println("Neispravno unesena vrijednost!");
//                input.nextLine();
//                logger.error(e.getMessage(),e);
//            }
//        }while(!validInput);
//
//
//        Dvorana tempDvorana = unesiteDvoranu(input);
//
//        Integer tempOcjena = null;
//        validInput = false;
//        do{
//            try{
//                System.out.print("Unesite ocjenu na ispitu (1-5): ");
//                tempOcjena = input.nextInt();
//                input.nextLine();
//                validInput = true;
//            }
//            catch(InputMismatchException e){
//                System.out.println("Neispravno unesena vrijednost!");
//                input.nextLine();
//                logger.error(e.getMessage(),e);
//            }
//        }while(!validInput);
//
//
//        LocalDateTime tempDatumIVrijeme = null;
//        validInput = false;
//        do{
//            try{
//                System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyyTHH:mm): ");
//                String stringTempDatumIVrijeme = input.nextLine();
//                tempDatumIVrijeme = LocalDateTime.parse(stringTempDatumIVrijeme, DateTimeFormatter.ofPattern("dd.MM.yyyy'T'HH:mm"));
//                validInput = true;
//            }
//            catch(InputMismatchException e){
//                System.out.println("Neispravno unesen datum!");
//            }
//        }while(!validInput);
//
//
//
//        return new Ispit(tempPredmet,tempStudent,tempOcjena,tempDatumIVrijeme,tempDvorana);
//    }
//
//    /**
//     * Unosi dvoranu u kojoj se pise ispit
//     * @param input Scanner s kojim se citaju podaci
//     * @return objekt Dvorana sa ucitanim podacima
//     */
//    static Dvorana unesiteDvoranu(Scanner input){
//        System.out.print("Unesite naziv dvorane: ");
//        String tempNaziv = input.nextLine();
//
//        System.out.print("Unesite zgradu dvorane: ");
//        String tempZgrada = input.nextLine();
//
//        return new Dvorana(tempNaziv,tempZgrada);
//    }
//
//    //dodavanje studenata u predmet zadatak 9.
//
//    /**
//     * Dodaje studente sa ispita u predmet
//     * @param ispiti Svi ispite koji su pisani
//     * @param predmeti Predemt za koji trazimo ispite
//     */
//    static void dodajStudenteuPredmet(List<Ispit> ispiti,List<Predmet> predmeti){
//        for(Predmet predmet : predmeti){
//            Set<Student> tempStudenti = new HashSet<>();
//            for(Ispit ispit : ispiti){
//                if(ispit.getPredmet().getNaziv() == predmet.getNaziv()){
//                    tempStudenti.add(ispit.getStudent());
//                }
//            }
//            predmet.setStudenti(tempStudenti);
//        }
//    }
//
//    /**
//     * Unosi obrazovnu ustanovu sa svim dijelovima ustanove
//     * @param input Scanner s kojim unosimo podatke
//     * @return objekt Ustanova sa svim unesenim podacima
//     */
//    static ObrazovnaUstanova unesiObrazovnuUstanovu(Scanner input){
//        List<NastavnaOsoba> profesori = new ArrayList<>();
//        List<Predmet> predmeti = new ArrayList<>();
//        List<Student> studenti = new ArrayList<>();
//        List<Ispit> ispiti = new ArrayList<>();
//
//
//        for(int i=0;i<BROJ_PROFESORA;i++){
//            System.out.println("Unesite " + (i+1) + " profesora: ");
//            profesori.add(unesiProfesora(input));
//        }
//
//        for(int i=0;i<BROJ_STUDENTA;i++){
//            System.out.println("Unesite " + (i+1) + " studenta: ");
//            studenti.add(unesiStudenta(input));
//        }
//
//        for(int i=0;i<BROJ_PREDMETA;i++){
//            System.out.println("Unesite " + (i+1) + " predmet: ");
//            predmeti.add(unesiPredmet(input,profesori,studenti));
//        }
//
//
//        for(int i=0;i<BROJ_ISPITA;i++){
//            System.out.println("Unesite " + (i+1) + ". ispitni rok: ");
//            ispiti.add(unesiIspit(input,predmeti,studenti));
//        }
//
//        dodajStudenteuPredmet(ispiti,predmeti);
//
//
//        //ispis studenata sa ocijenom 5
//        ispiti.forEach(ispit -> {
//            if(ispit.getOcjena() == Ocjena.ODLICAN.vrijednost){
//                System.out.println("Student " + ispit.getStudent().getIme() + " " + ispit.getStudent().getPrezime() + " je ostvario ocjenu 'izvrstan' na predmetu '" + ispit.getPredmet().getNaziv() + "'");
//            }
//        });
////        for(int i=0;i<BROJ_ISPITA;i++){
////            if(ispiti.get(i).getOcjena() == Ocjena.ODLICAN.vrijednost){
////                System.out.println("Student " + ispiti.get(i).getStudent().getIme() + " " + ispiti.get(i).getStudent().getPrezime() + " je ostvario ocjenu 'izvrstan' na predmetu '" + ispiti.get(i).getPredmet().getNaziv() + "'");
////            }
////        }
//
//        Integer odabirUstanove = null;
//        boolean validInput = false;
//        do{
//            try{
//                System.out.println("Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti: \n (1 - Veleučilište Jave, 2 - Fakultet računarstva):");
//                odabirUstanove = input.nextInt();
//                input.nextLine();
//                validInput = true;
//            }
//            catch(InputMismatchException e){
//                System.out.println("Neispravno unesena vrijednost!");
//                input.nextLine();
//                logger.error(e.getMessage(),e);
//            }
//        }while(!validInput);
//
//
//        System.out.print("Unesite naziv obrazovne ustanove: ");
//        String tempNaziv = input.nextLine();
//
//        if(odabirUstanove == 1){
//            return new VeleucilisteJave(tempNaziv,predmeti,profesori,studenti,ispiti);
//        }
//        else{
//            return new FakultetRacunarstva(tempNaziv,predmeti,profesori,studenti,ispiti);
//        }
//    }
//
//    /**
//     * Odreduje ispite sa studentovim imenom
//     * @param ispiti polje svih ispita
//     * @param student student za kojeg zelimo izdvojiti ispite
//     * @return polje ispita koje je pisao predati student
//     */
//    static List<Ispit> odrediStudentoveIspite(List<Ispit> ispiti,Student student){
//        return ispiti.stream().filter(ispit -> ispit.getStudent().getJmbag() == student.getJmbag()).collect(Collectors.toList());
//    }
//
//    /**
//     * Provjerava ima li neki od ispita jedinicu
//     * @param ispiti ispite koje zelimo provjeriti
//     * @return true ako jedan od ispita ima jedinicu
//     */
//    static boolean imaJedinicu(List<Ispit> ispiti){
//        boolean imaJedinicu = false;
//        for(Ispit ispit : ispiti){
//            if(ispit.getOcjena() == Ocjena.NEDOVOLJAN.vrijednost){
//                imaJedinicu = true;
//                break;
//            }
//        }
//        return imaJedinicu;
//    }
//
//
//    /**
//     * Naredbe koje prate upute druge vjezbe
//     * @param input Scanner sa kojim unosimo podatke
//     */
//    static void drugaVjezba(Scanner input){
//        boolean validInput = false;
//
//        Integer brojUstanova = null;
//        do{
//            try{
//                System.out.print("Unesite broj obrazovnih ustanova: ");
//                brojUstanova = input.nextInt();
//                input.nextLine();
//                validInput = true;
//            }
//            catch(InputMismatchException e){
//                System.out.println("Neispravno unesena vrijednost!");
//                input.nextLine();
//                logger.error(e.getMessage(),e);
//            }
//        }while(!validInput);
//
//        Sveuciliste<ObrazovnaUstanova> sveuciliste = new Sveuciliste<>();
//
//        for(int j=0;j<brojUstanova;j++){
//            System.out.println("Unesite " + (j+1) + " obrazovnu ustanovu:" );
//            ObrazovnaUstanova tempObrazovnaUstanova = unesiObrazovnuUstanovu(input);
//            sveuciliste.dodajObrazovnuUstanovu(tempObrazovnaUstanova);
//
//
//            for(Student student : tempObrazovnaUstanova.getStudenti()){
//                List<Ispit> studentoviIspiti = odrediStudentoveIspite(tempObrazovnaUstanova.getIspiti(),student);
//
//                if(imaJedinicu(studentoviIspiti)){
//                    System.out.println("Student ima jedinicu i nema ocjena zavrsnog rada!");
//                    logger.error("Student ima jedinicu i nema ocjena zavrsnog rada!");
//                    break;
//                }
//
//                System.out.print("Unesite ocjenu zavrsnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ": ");
//                Integer tempZavrsni = input.nextInt();
//                input.nextLine();
//
//                System.out.print("Unesite ocjenu obranze zavrsnog rada za studenta :" + student.getIme() + " " + student.getPrezime() + ": ");
//                Integer tempObrana = input.nextInt();
//                input.nextLine();
//
//                if(tempObrazovnaUstanova instanceof VeleucilisteJave){
//                    Integer konacnaOcjena = ((VeleucilisteJave) tempObrazovnaUstanova).izracunajKonacnuOcjenuStudijaZaStudenta(studentoviIspiti,tempObrana,tempZavrsni).intValue();
//                    System.out.println("Konacna ocjena studija studenta " + student.getIme() + " " + student.getPrezime() + " je" + konacnaOcjena);
//                }
//                if(tempObrazovnaUstanova instanceof FakultetRacunarstva){
//                    Integer konacnaOcjena = ((FakultetRacunarstva) tempObrazovnaUstanova).izracunajKonacnuOcjenuStudijaZaStudenta(studentoviIspiti,tempObrana,tempZavrsni).intValue();
//                    System.out.println("Konacna ocjena studija studenta " + student.getIme() + " " + student.getPrezime() + " je" + konacnaOcjena);
//                }
//            }
//
//            if(tempObrazovnaUstanova instanceof  VeleucilisteJave){
//                Student student = tempObrazovnaUstanova.odrediNajuspjesnijegStudentaNaGodini(2022);
//                if(student != null){
//                    System.out.println("Najbolji student 2022. godine je " + student.getIme() + " " +student.getPrezime() + " JMBAG: " + student.getJmbag());
//                }
//                else{
//                    System.out.println("Nema studenta sa te godine");
//                }
//            }
//
//            if(tempObrazovnaUstanova instanceof  FakultetRacunarstva){
//                Student student = tempObrazovnaUstanova.odrediNajuspjesnijegStudentaNaGodini(2022);
//                if(student != null){
//                    System.out.println("Najbolji student 2022. godine je " + student.getIme() + " " +student.getPrezime() + " JMBAG: " + student.getJmbag());
//                }
//                else{
//                    System.out.println("Nema studenta sa te godine!");
//                }
//
//                Student rektor = null;
//                try {
//                    rektor = ((FakultetRacunarstva) tempObrazovnaUstanova).odrediStudentaZaRektorovuNagradu();
//                    System.out.println("Student koji je osvojio rektorovu nagradu je: " + rektor.getIme() + " " +rektor.getPrezime() + " JMBAG: " + rektor.getJmbag());
//                } catch (PostojiViseNajmladihStudenataException e){
//                    logger.error(e.getMessage(),e);
//                }
//            }
//        }
//
//        var sortiraneUstanove = sveuciliste
//                .getObrazovneUstanove()
//                .stream()
//                .sorted((a,b) -> {
//                    int studentCompare = b.getStudenti().size() - a.getStudenti().size();
//                    return studentCompare != 0 ? studentCompare : a.getNaziv().compareTo(b.getNaziv());
//            }).toList();
//
//    }
//
//    /**
//     * Ispisuje sve studenete koju se nalaze na predanom predmetu
//     * @param predmet predmet na kojem ispisujemo studente
//     */
//    static void ispisiStudenteNaPredmetu(Predmet predmet){
//        System.out.println("==========Ispis studenata na predmetu " + predmet.getNaziv() +"=============");
//        predmet
//                .getStudenti()
//                .stream()
//                .sorted(new StudentSorter())
//                .toList()
//                .forEach(student -> System.out.println(student.getPrezime() + " " + student.getIme() + " " + student.getJmbag()));
////        for(Student student : predmet.getStudenti().stream().sorted(new StudentSorter()).toList()){
////            System.out.println(student.getPrezime() + " " + student.getIme() + " " + student.getJmbag());
////        }
//    }
//
//    /**
//     * ispisuje predmete od svih profesora
//     * @param nositelji mapa koja ima kljuc vrijednost profesora i vrijednost listu predmeta tog profesora
//     */
//    private static void ispisiPredmeteNositelja(Map<Profesor,List<Predmet>> nositelji){
//        nositelji.forEach((nositelj,predmeti) -> {
//            System.out.println(nositelj.getIme() + " " + nositelj.getPrezime() + " je nositelj predmeta: \n");
//            for(Predmet predmet : predmeti){
//                System.out.println(predmet.getNaziv());
//            }
//        });
//    }
//
//    /**
//     * Kod koji rjesava cetrvtu laboratorijsku vjezbu
//     * @param input
//     */
//    static void cetvrtaVjezba(Scanner input){
//        Map<Profesor,List<Predmet>> nositelji = new HashMap<>();
//        List<NastavnaOsoba> profesori = new ArrayList<>();
//        List<Predmet> predmeti = new ArrayList<>();
//        List<Student> studenti = new ArrayList<>();
//
//
//        for(int i=0;i<BROJ_PROFESORA;i++){
//            profesori.add(unesiProfesora(input));
//        }
//
//        for(int i=0;i<BROJ_STUDENTA;i++){
//            studenti.add(unesiStudenta(input));
//        }
//
//        for(int i=0;i<BROJ_PREDMETA;i++){
//            Predmet tempPredmet = unesiPredmet(input,profesori,studenti);
//            predmeti.add(tempPredmet);
//            NastavnaOsoba nositelj = tempPredmet.getNositelj();
//
//            if(nositelji.containsKey(nositelj)){
//                nositelji.get(nositelj).add(tempPredmet);
//            }
//            else{
//                nositelji.put((Profesor) nositelj,new ArrayList<>() {{add(tempPredmet);}});
//            }
//        }
//
//        if(profesori.stream().allMatch(nositelji::containsKey) && profesori.stream().anyMatch(p -> {
//            List<Predmet> lista = nositelji.get(p);
//            return lista != null && lista.size() > 1;
//        })){
//            ispisiPredmeteNositelja(nositelji);
//        }
//        else{
//            System.out.println("Greška pro unosu! Svaki profesor mora biti nostielj barem jednog predmeta i barem jedan mora biti nositelj vise od jednog predmeta!");
//        }
//
//
//
//        for(Predmet predmet : predmeti){
//            ispisiStudenteNaPredmetu(predmet);
//        }
//    }
//
//
//
//
//}
