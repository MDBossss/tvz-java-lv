package hr.java.vjezbe.entitet;

public class Profesor extends NastavnaOsoba {

    private String titulaIzaImena;

    public Profesor(String ime, String prezime, String sifra, String titulaIzaImena) {
        super(ime, prezime, sifra);
        this.titulaIzaImena = titulaIzaImena;
    }

    public String getTitulaIzaImena() {
        return titulaIzaImena;
    }

    public void setTitulaIzaImena(String titulaIzaImena) {
        this.titulaIzaImena = titulaIzaImena;
    }

    //    private Profesor(String ime, String prezime) {
//        super(ime,prezime);
//    }
//    public static class Builder {
//        private String ime;
//        private String prezime;
//        private String titulaIzaImena;
//
//        public Builder(String ime,String prezime){
//            this.ime = ime;
//            this.prezime = prezime;
//        }
//
//
//        public Profesor.Builder titulaIzaImena(String titulaIzaImena){
//            this.titulaIzaImena = titulaIzaImena;
//            return this;
//        }
//
//        public Profesor build(){
//            Profesor Profesor = new Profesor(ime,prezime);
//            return Profesor;
//        }


    public static class ProfesorBuilder {
        private String ime;
        private String prezime;
        private String sifra;
        private String titulaIzaImena;

        public ProfesorBuilder setIme(String ime) {
            this.ime = ime;
            return this;
        }

        public ProfesorBuilder setPrezime(String prezime) {
            this.prezime = prezime;
            return this;
        }

        public ProfesorBuilder setSifra(String sifra) {
            this.sifra = sifra;
            return this;
        }

        public ProfesorBuilder setTitulaIzaImena(String titulaIzaImena) {
            this.titulaIzaImena = titulaIzaImena;
            return this;
        }

        public Profesor createProfesor() {
            return new Profesor(ime, prezime, sifra, titulaIzaImena);
        }
    }
}
