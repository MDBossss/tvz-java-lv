package hr.java.vjezbe.baza;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.BazaPodatakaException;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class BazaPodataka {

    private static final String DATABASE_FILE = "dat\\database.properties";

    //==========UTILS==========

    private static Connection connectToDatabase() throws SQLException, IOException{
        Properties properties = new Properties();
        properties.load(new FileReader(DATABASE_FILE));

        String urlBazePodataka = properties.getProperty("bazaPodatakaUrl");
        String korisnickoIme = properties.getProperty("korisnickoIme");
        String lozinka = properties.getProperty("lozinka");

        return DriverManager.getConnection(urlBazePodataka,korisnickoIme,lozinka);
    }

    public static Set<Student> getStudentsByPredmetID(Long ID) throws BazaPodatakaException{
        Set<Student> studentSet = new HashSet<>();
        Map<Long,Student> studentMap = getStudenti();

        try(Connection connection = connectToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM PREDMET_STUDENT");

            while(rs.next()){
                Long predmetID = rs.getLong("PREDMET_ID");
                Long studentID = rs.getLong("STUDENT_ID");

                if(predmetID.equals(ID)){
                    studentSet.add(studentMap.get(studentID));
                }
            }

        }catch(SQLException | IOException e){
            throw new BazaPodatakaException();
        }
        return studentSet;
    }

    public static Profesor getProfesorbyFullName(String fullName) throws BazaPodatakaException{
        try{
            Map<Long,Profesor> profesoriMap = getProfesori();
            String[] split = fullName.split(" ");
            var firstName = split[0];
            var lastName = split[1];
            List<Profesor> foundProfesor = new ArrayList<>();
            profesoriMap.forEach((id,profesor) -> {
                if ((Objects.equals(profesor.getIme(), firstName)) && (Objects.equals(profesor.getPrezime(), lastName))){
                    foundProfesor.add(profesor);
                }
            });
            if(foundProfesor.isEmpty()){
                return null;
            }
            else{
                return foundProfesor.get(0);
            }
        }catch (BazaPodatakaException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Long getPredmetIDbyName(String name){
        List<Long> foundID = new ArrayList<>();
        try{
            Map<Long,Predmet> predmeti = getPredmeti();
            predmeti.forEach((id,predmet) -> {
                if(Objects.equals(predmet.getNaziv(), name)){
                    foundID.add(id);
                }
            });
        }catch (BazaPodatakaException e){
            e.printStackTrace();
        }

        if(foundID.isEmpty()){
            return 0L;
        }
        else{
            return foundID.get(0);
        }
    }

    public static Long getStudentIDbyFullName(String fullName){
        List<Long> foundID = new ArrayList<>();
        try{
            Map<Long,Student> studenti = getStudenti();
            String[] split = fullName.split(" ");
            var firstName = split[0];
            var lastName = split[1];
            studenti.forEach((id,student) -> {
                if((Objects.equals(student.getIme(), firstName)) && (Objects.equals(student.getPrezime(), lastName))){
                    foundID.add(id);
                }
            });
        }catch (BazaPodatakaException e){
            e.printStackTrace();
        }

        if(foundID.isEmpty()){
            return 0L;
        }
        else{
            return foundID.get(0);
        }
    }


    public static Set<Student> getSetOfStudents(){
        Set<Student> studentiSet = new HashSet<>();
        try{
            Map<Long,Student> studentiMap = getStudenti();

            studentiMap.forEach((key,value) -> {
                studentiSet.add(value);
            });
        }catch(BazaPodatakaException e){
            e.printStackTrace();
        }
        return studentiSet;
    }

    //=========STUDENT==========

    public static Map<Long, Student> getStudenti() throws BazaPodatakaException{
        HashMap<Long,Student> studentiMap = new HashMap<>();

        try(Connection connection = connectToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM STUDENT");


            while(rs.next()){
                Long id = rs.getLong("id");
                String jmbag = rs.getString("jmbag");
                String ime = rs.getString("ime");
                String prezime = rs.getString("prezime");
                Date date = (Date) rs.getDate("datum_rodjenja");
                Instant instant = Instant.ofEpochMilli(date.getTime());
                LocalDate datumRodenja = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
                LocalDate datum = rs.getTimestamp("datum_rodjenja").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


                studentiMap.put(id,new Student(id,ime,prezime,jmbag,datumRodenja));
;            }

        }catch(SQLException | IOException e){
            throw new BazaPodatakaException();
        }

        return studentiMap;
    }

    public static Map<Long,Student> getFilteredStudenti(Student student) throws BazaPodatakaException{
        try(Connection connection = connectToDatabase()){
            if(student == null){
                return getStudenti();
            }
            else{
                Map<Long,Student> studentiMap = new HashMap<>();
                StringBuilder sqlUpit = new StringBuilder("SELECT * FROM STUDENT WHERE 1 = 1");

                if(student.getId() != null){
                    sqlUpit.append(" AND ID = " + student.getId());
                }
                if(student.getJmbag() != null){
                    sqlUpit.append(" AND JMBAG LIKE '%" + student.getJmbag() + "%'");
                }
                if(student.getIme() != null){
                    sqlUpit.append(" AND IME LIKE '%" + student.getIme() + "%'");
                }
                if(student.getPrezime() != null){
                    sqlUpit.append(" AND PREZIME LIKE '%" + student.getPrezime() + "%'");
                }
                if(student.getDatumRodenja() != null){
                    sqlUpit.append(" AND DATUM_RODJENJA LIKE '%" + student.getDatumRodenja() + "%'");
                }

                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sqlUpit.toString());


                while(rs.next()){
                    Long id = rs.getLong("id");
                    String jmbag = rs.getString("jmbag");
                    String ime = rs.getString("ime");
                    String prezime = rs.getString("prezime");
                    Date date = (Date) rs.getDate("datum_rodjenja");
                    Instant instant = Instant.ofEpochMilli(date.getTime());
                    LocalDate datumRodenja = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();

                    studentiMap.put(id,new Student(id,ime,prezime,jmbag,datumRodenja));
                }
                return studentiMap;
            }
        }catch(SQLException | IOException e){
            throw new BazaPodatakaException();
        }
    }

    public static void addStudent(Student student) throws BazaPodatakaException{
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO STUDENT(IME, PREZIME, JMBAG, DATUM_RODJENJA) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, student.getIme());
            preparedStatement.setString(2,student.getPrezime());
            preparedStatement.setString(3,student.getJmbag());
            preparedStatement.setDate(4,Date.valueOf(student.getDatumRodenja()));
            preparedStatement.executeUpdate();



        }catch(SQLException | IOException e){
            throw new BazaPodatakaException();
        }
    }

    //==========PROFESOR==========

    public static Map<Long, Profesor> getProfesori() throws BazaPodatakaException{
        HashMap<Long,Profesor> profesoriMap = new HashMap<>();

        try(Connection connection = connectToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM PROFESOR");

            while(rs.next()){
                Long id = rs.getLong("id");
                String ime = rs.getString("ime");
                String prezime = rs.getString("prezime");
                String sifra = rs.getString("sifra");
                String titula = rs.getString("titula");

                profesoriMap.put(id,new Profesor(id,ime,prezime,sifra,titula));
            }
        }catch(SQLException | IOException e){
            throw new BazaPodatakaException();
        }
        return profesoriMap;
    }

    public static Map<Long,Profesor> getFilteredProfesori(Profesor profesor) throws BazaPodatakaException{
        try(Connection connection = connectToDatabase()){
            if(profesor == null){
                return getProfesori();
            }
            else{
                Map<Long,Profesor> profesoriMap = new HashMap<>();
                StringBuilder sqlUpit = new StringBuilder("SELECT * FROM PROFESOR WHERE 1 = 1");

                if(profesor.getId() != null){
                    sqlUpit.append(" AND ID = " + profesor.getId());
                }
                if(profesor.getIme() != null){
                    sqlUpit.append(" AND IME LIKE '%" + profesor.getIme() + "%'");
                }
                if(profesor.getPrezime() != null){
                    sqlUpit.append(" AND PREZIME LIKE '%" + profesor.getPrezime() + "%'");
                }
                if(profesor.getSifra() != null){
                    sqlUpit.append(" AND SIFRA LIKE '%" + profesor.getSifra() + "%'");
                }
                if(profesor.getTitulaIzaImena() != null){
                    sqlUpit.append(" AND TITULA LIKE '%" + profesor.getTitulaIzaImena() + "%'");
                }

                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sqlUpit.toString());

                while(rs.next()){
                    Long id = rs.getLong("id");
                    String ime = rs.getString("ime");
                    String prezime = rs.getString("prezime");
                    String sifra = rs.getString("sifra");
                    String titula = rs.getString("titula");

                    profesoriMap.put(id,new Profesor(id,ime,prezime,sifra,titula));
                }
                return profesoriMap;
            }
        }catch(SQLException | IOException e){
            throw new BazaPodatakaException();
        }
    }

    public static void addProfesor(Profesor profesor) throws BazaPodatakaException{
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PROFESOR(IME, PREZIME, SIFRA, TITULA) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1,profesor.getIme());
            preparedStatement.setString(2,profesor.getPrezime());
            preparedStatement.setString(3,profesor.getSifra());
            preparedStatement.setString(4,profesor.getTitulaIzaImena());
            preparedStatement.executeUpdate();
        }catch(SQLException | IOException e){
            throw new BazaPodatakaException();
        }
    }

    //==========PREDMET==========

    public static Map<Long, Predmet> getPredmeti() throws BazaPodatakaException{
        HashMap<Long,Predmet> predmetiMap = new HashMap<>();

        try(Connection connection = connectToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM PREDMET");

            while(rs.next()){
                Long id = rs.getLong("id");
                String sifra = rs.getString("sifra");
                String naziv = rs.getString("naziv");
                Integer ects = rs.getInt("broj_ects_bodova");
                Long profesorID = rs.getLong("profesor_id");

                predmetiMap.put(id,new Predmet(id,sifra,naziv,ects,getProfesori().get(profesorID),getStudentsByPredmetID(id)));

            }

        }catch(SQLException |IOException e){
            throw new BazaPodatakaException();
        }
        return predmetiMap;
    }

    public static Map<Long,Predmet> getFilteredPredmeti(Predmet predmet) throws BazaPodatakaException{
        try(Connection connection = connectToDatabase()){
            if(predmet == null){
                return getPredmeti();
            }
            else{
                Map<Long,Predmet> predmetiMap = new HashMap<>();
                StringBuilder sqlUpit = new StringBuilder("SELECT * FROM PREDMET WHERE 1 = 1");

                if(predmet.getId() != null){
                    sqlUpit.append(" AND ID = " + predmet.getId());
                }
                if(predmet.getSifra() != null){
                    sqlUpit.append(" AND SIFRA LIKE '%" + predmet.getSifra() + "%'");
                }
                if(predmet.getNaziv() != null){
                    sqlUpit.append(" AND NAZIV LIKE '%" + predmet.getNaziv() + "%'");
                }
                if(predmet.getBrojEctsBodova() != null){
                    sqlUpit.append(" AND BROJ_ECTS_BODOVA LIKE '%" + predmet.getBrojEctsBodova() + "%'");
                }


                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sqlUpit.toString());

                while(rs.next()){
                    Long id = rs.getLong("id");
                    String sifra = rs.getString("sifra");
                    String naziv = rs.getString("naziv");
                    Integer ects = rs.getInt("broj_ects_bodova");
                    Long profesorID = rs.getLong("profesor_id");

                    predmetiMap.put(id,new Predmet(id,sifra,naziv,ects,getProfesori().get(profesorID),getStudentsByPredmetID(id)));
                }
                return predmetiMap;
            }
        }catch(SQLException | IOException e){
            throw new BazaPodatakaException();
        }
    }

    public static void addPredmet(Predmet predmet) throws BazaPodatakaException{
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PREDMET(SIFRA, NAZIV, BROJ_ECTS_BODOVA,PROFESOR_ID) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1,predmet.getSifra());
            preparedStatement.setString(2,predmet.getNaziv());
            preparedStatement.setInt(3,predmet.getBrojEctsBodova());
            preparedStatement.setInt(4, Math.toIntExact(predmet.getNositelj().getId()));
            preparedStatement.executeUpdate();
        }catch (SQLException | IOException e){
            throw new BazaPodatakaException();
        }
    }

    //=========ISPIT=========

    public static Map<Long, Ispit> getIspiti() throws BazaPodatakaException{
        HashMap<Long,Ispit> ispitiMap = new HashMap<>();

        try(Connection connection = connectToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM ISPIT");

            while(rs.next()){
                Long id = rs.getLong("id");
                String predmetID = rs.getString("PREDMET_ID");
                String studentID = rs.getString("STUDENT_ID");
                Integer ocjena = rs.getInt("OCJENA");
                java.util.Date datumVrijeme = rs.getTimestamp("DATUM_I_VRIJEME");

                ispitiMap.put(id,new Ispit(id,getPredmeti().get(Long.valueOf(predmetID)),getStudenti().get(Long.valueOf(studentID)), Ocjena.parseOcjena(ocjena),datumVrijeme.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));

            }

        }catch(SQLException |IOException e){
            throw new BazaPodatakaException();
        }
        return ispitiMap;
    }

    public static Map<Long,Ispit> getFilteredIspiti(Ispit ispit) throws BazaPodatakaException{
        try(Connection connection = connectToDatabase()){
            if(ispit == null){
                return getIspiti();
            }
            else{
                Map<Long,Ispit> ispitiMap = new HashMap<>();
                StringBuilder sqlUpit = new StringBuilder("SELECT * FROM ISPIT WHERE 1 = 1");

                if(ispit.getId() != null){
                    sqlUpit.append(" AND ID = " + ispit.getId());
                }
                if(ispit.getPredmet() != null){
                    sqlUpit.append(" AND PREDMET_ID LIKE '%" + ispit.getPredmet().getId() + "%'");
                }
                if(ispit.getStudent() != null){
                    sqlUpit.append(" AND STUDENT_ID LIKE '%" + ispit.getStudent().getId() + "%'");
                }
                if(ispit.getOcjena() != null){
                    sqlUpit.append(" AND OCJENA LIKE '%" + ispit.getOcjena().vrijednost + "%'");
                }
                if(ispit.getDatumIVrijeme() != null){
                    sqlUpit.append(" AND DATUM_I_VRIJEME LIKE '%" + ispit.getDatumIVrijeme() + "%'");
                }


                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sqlUpit.toString());

                while(rs.next()){
                    Long id = rs.getLong("id");
                    String predmetID = rs.getString("PREDMET_ID");
                    String studentID = rs.getString("STUDENT_ID");
                    Integer ocjena = rs.getInt("OCJENA");
                    java.util.Date datumVrijeme = rs.getTimestamp("DATUM_I_VRIJEME");

                    ispitiMap.put(id,new Ispit(id,getPredmeti().get(Long.valueOf(predmetID)),getStudenti().get(Long.valueOf(studentID)), Ocjena.parseOcjena(ocjena),datumVrijeme.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));

                }
                return ispitiMap;
            }
        }catch(SQLException | IOException e){
            throw new BazaPodatakaException();
        }
    }

    public static void addIspit(Ispit ispit) throws BazaPodatakaException{
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ISPIT(PREDMET_ID, STUDENT_ID, OCJENA, DATUM_I_VRIJEME) VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, Math.toIntExact(ispit.getPredmet().getId()));
            preparedStatement.setInt(2, Math.toIntExact(ispit.getStudent().getId()));
            preparedStatement.setInt(3,ispit.getOcjena().vrijednost);
            preparedStatement.setTimestamp(4,Timestamp.valueOf(ispit.getDatumIVrijeme()));
            preparedStatement.executeUpdate();
        }catch (SQLException | IOException e){
            e.printStackTrace();
        }
    }
}
