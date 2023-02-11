package hr.java.vjezbe.util;

import hr.java.vjezbe.entitet.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Datoteke {
    private static final String PROFESORI_FILE = "dat\\profesori.txt";
    private static final String STUDENTI_FILE = "dat\\studenti.txt";
    private static final String PREDMETI_FILE = "dat\\predmeti.txt";
    private static final String ISPITI_FILE = "dat\\ispiti.txt";
    private static final String OBRAZOVNE_USTANOVE_FILE = "dat\\obrazovneUstanove.txt";
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d.M.yyyy.");
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("d.M.yyyy. H:mm");

    public static Map<Long, Profesor> getProfesori(){
        HashMap<Long,Profesor> profesori = new HashMap<>();

        try(Scanner scanner = new Scanner(new File(PROFESORI_FILE))){
            while(scanner.hasNextLine()){
                long id = scanner.nextLong();
                scanner.nextLine();

                var sifra = scanner.nextLine();
                var ime = scanner.nextLine();
                var prezime = scanner.nextLine();
                var titula = scanner.nextLine();

                Profesor profesor = new Profesor(id,ime,prezime,sifra,titula);
                profesori.put(profesor.getId(),profesor);
            }
        } catch(FileNotFoundException e){
            throw new RuntimeException("File not found");
        }
        return profesori;
    }

    public static Map<Long, Student> getStudenti(){
        HashMap<Long,Student> studenti = new HashMap<>();

        try(Scanner scanner = new Scanner(new File(STUDENTI_FILE))){
            while(scanner.hasNextLine()){
                long id = scanner.nextLong();
                scanner.nextLine();

                var ime = scanner.nextLine();
                var prezime = scanner.nextLine();
                var jmbag = scanner.nextLine();
                var datumRodenja = LocalDate.parse(scanner.nextLine(),DATE_FORMAT);

                Student student = new Student(id,ime,prezime,jmbag,datumRodenja);
                studenti.put(student.getId(),student);
            }
        }catch(FileNotFoundException e){
            throw new RuntimeException("File not found");
        }
        return studenti;
    }

    public static Map<Long, Predmet> getPredmeti(){
        HashMap<Long,Predmet> predmeti = new HashMap<>();
        Map<Long,Profesor> profesori = getProfesori();
        Map<Long,Student> sviStudenti = getStudenti();

        try(Scanner scanner = new Scanner(new File(PREDMETI_FILE))){
            while(scanner.hasNextLine()){
                long id = scanner.nextLong();
                scanner.nextLine();

                var sifra = scanner.nextLine();
                var naziv = scanner.nextLine();

                var brojEctsBodova = scanner.nextInt();
                scanner.nextLine();

                var nositelj = profesori.get(scanner.nextLong());
                scanner.nextLine();

                var studenti = mapGetMany(sviStudenti, parseIds(scanner.nextLine()));

                var predmet = new Predmet(id, sifra, naziv, brojEctsBodova, nositelj, new HashSet<>(studenti));
                predmeti.put(predmet.getId(), predmet);
            }
        }catch(FileNotFoundException e){
            throw new RuntimeException("File not found");
        }
        return predmeti;
    }

    public static Map<Long, Ispit> getIspiti(){
        HashMap<Long,Ispit> ispiti = new HashMap<>();
        Map<Long,Student> studenti = getStudenti();
        Map<Long,Predmet> predmeti = getPredmeti();

        try(Scanner scanner = new Scanner(new File(ISPITI_FILE))){
            while(scanner.hasNextLine()){
                long id = scanner.nextLong();
                scanner.nextLine();

                var predmet = predmeti.get(scanner.nextLong());
                scanner.nextLine();

                var student = studenti.get(scanner.nextLong());
                scanner.nextLine();

                var ocjena = Ocjena.parseOcjena(scanner.nextInt());
                scanner.nextLine();

                var datumIVrijeme = LocalDateTime.parse(scanner.nextLine(), DATE_TIME_FORMAT);
                var zgrada = scanner.nextLine();
                var dvorana = scanner.nextLine();

                var ispit = new Ispit(id, predmet, student, ocjena, datumIVrijeme, new Dvorana(zgrada, dvorana));
                ispiti.put(ispit.getId(), ispit);
            }
        }catch(FileNotFoundException e){
            throw new RuntimeException("File not found");
        }
        return ispiti;
    }

    public static Map<Long,ObrazovnaUstanova> getObrazovneUstanove(){
        HashMap<Long,ObrazovnaUstanova> obrazovneUstanove = new HashMap<>();
        Map<Long,Profesor> sviProfesori = getProfesori();
        Map<Long,Predmet> sviPredmeti = getPredmeti();
        Map<Long,Ispit> sviIspiti = getIspiti();

        try(Scanner scanner = new Scanner(new File(OBRAZOVNE_USTANOVE_FILE))){
            while(scanner.hasNextLine()){
                long id = scanner.nextLong();
                scanner.nextLine();

                var naziv = scanner.nextLine();
                var vrstaObrazovneUstanove = scanner.nextInt();
                scanner.nextLine();

                var predmeti = mapGetMany(sviPredmeti, parseIds(scanner.nextLine()));
                var profesori = mapGetMany(sviProfesori, parseIds(scanner.nextLine()));
                var ispiti = mapGetMany(sviIspiti, parseIds(scanner.nextLine()));

                var obrazovnaUstanova = switch (vrstaObrazovneUstanove) {
                    case 1 -> new VeleucilisteJave(id, naziv, predmeti, profesori, ispiti);
                    case 2 -> new FakultetRacunarstva(id, naziv, predmeti, profesori, ispiti);
                    default -> {
                        String message = String.format("Nedozvoljena vrijednost za obrazovnu ustanovu: %d", vrstaObrazovneUstanove);
                        throw new RuntimeException(message);
                    }
                };

                obrazovneUstanove.put(obrazovnaUstanova.getId(), obrazovnaUstanova);
            }
        }catch(FileNotFoundException e){
            throw new RuntimeException(("File not found"));
        }
        return obrazovneUstanove;
    }

    public static <TKey, TValue> List<TValue> mapGetMany(Map<TKey,TValue> map, List<TKey> keys){
        var values = new ArrayList<TValue>();

        for(var key : keys){
            values.add(map.get(key));
        }
        return values;
    }

    public static <T> List<Long> parseIds(String ids){
        return Arrays.stream(ids.split(" ")).map(Long::parseLong).collect(Collectors.toList());
    }

    public static Long getProfesorIDbyFullName(String fullName){
        Map<Long,Profesor> profesori = getProfesori();
        String[] split = fullName.split(" ");
        var firstName = split[0];
        var lastName = split[1];
        List<Long> foundID = new ArrayList<>();
        profesori.forEach((id,profesor) -> {
            if ((Objects.equals(profesor.getIme(), firstName)) && (Objects.equals(profesor.getPrezime(), lastName))){
                foundID.add(id);
            }
        });
        if(foundID.isEmpty()){
            return 0L;
        }
        else{
            return foundID.get(0);
        }
    }

    public static Long getPredmetIDbyName(String name){
        Map<Long,Predmet> predmeti = getPredmeti();
        List<Long> foundID = new ArrayList<>();
        predmeti.forEach((id,predmet) -> {
            if(Objects.equals(predmet.getNaziv(), name)){
                foundID.add(id);
            }
        });
        if(foundID.isEmpty()){
            return 0L;
        }
        else{
            return foundID.get(0);
        }
    }

    public static Long getStudentIDbyFullName(String fullName){
        Map<Long,Student> studenti = getStudenti();
        String[] split = fullName.split(" ");
        var firstName = split[0];
        var lastName = split[1];
        List<Long> foundID = new ArrayList<>();
        studenti.forEach((id,student) -> {
            if((Objects.equals(student.getIme(), firstName)) && (Objects.equals(student.getPrezime(), lastName))){
                foundID.add(id);
            }
        });
        if(foundID.isEmpty()){
            return 0L;
        }
        else{
            return foundID.get(0);
        }
    }

    public static void addProfesor(String sifra,String ime,String prezime,String titula){
        try {
            var output = new BufferedWriter(new FileWriter(PROFESORI_FILE, true));
            output.write(new Random().nextLong(10000) + "\n");
            output.write(sifra + "\n");
            output.write(ime + "\n");
            output.write(prezime + "\n");
            output.write(titula + "\n");
            output.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void addStudent(String ime,String prezime,String jmbag,LocalDate datumRodenja){
        try{
            var output = new BufferedWriter(new FileWriter(STUDENTI_FILE,true));
            output.write(new Random().nextLong(100000) + "\n");
            output.write(ime + "\n");
            output.write(prezime + "\n");
            output.write(jmbag + "\n");
            output.write(datumRodenja.format(DATE_FORMAT) + "\n");
            output.close();
        } catch(IOException e){
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }

    public static void addPredmet(String sifra,String naziv,String ects,String imePrezimeProfesora){
        try{
            var output = new BufferedWriter(new FileWriter(PREDMETI_FILE,true));
            output.write(new Random().nextLong(100000) + "\n");
            output.write(sifra + "\n");
            output.write(naziv + "\n");
            output.write(ects  + "\n");
            output.write(getProfesorIDbyFullName(imePrezimeProfesora).toString() + "\n");
            output.write("00010 00011\n");
            output.close();
        } catch(IOException e){
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }

    public static void addIspit(String nazivPredmeta,String imePrezimeStudenta,Ocjena ocjena,String datumVrijeme,String nazivDvorane){
        try{
            var output = new BufferedWriter(new FileWriter(ISPITI_FILE,true));
            output.write(new Random().nextLong(100000) + "\n");
            output.write(getPredmetIDbyName(nazivPredmeta).toString() + "\n");
            output.write(getStudentIDbyFullName(imePrezimeStudenta).toString() + "\n");
            output.write(ocjena.vrijednost + "\n");
            output.write(LocalDateTime.parse(datumVrijeme, DATE_TIME_FORMAT) + "\n");
            output.write("TVZ Vrbik 8" + "\n");
            output.write(nazivDvorane + "\n");
            output.close();

        } catch(IOException e){
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }
}
