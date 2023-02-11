package hr.java.vjezbe.entitet;

/**
 * Profesor na tercijarnoj obrazovnoj ustanovi
 */
public class Profesor extends NastavnaOsoba {

    private String titulaIzaImena;

    public Profesor(Long id,String ime, String prezime, String sifra, String titulaIzaImena) {
        super(id,ime, prezime, sifra);
        this.titulaIzaImena = titulaIzaImena;
    }

    public String getTitulaIzaImena() {
        return titulaIzaImena;
    }

    public void setTitulaIzaImena(String titulaIzaImena) {
        this.titulaIzaImena = titulaIzaImena;
    }


    /**
     * Klasa za generiranje objekata tipa profesor
     */
    public static class ProfesorBuilder {
        private String ime;
        private String prezime;
        private String sifra;
        private String titulaIzaImena;

        private Long id;

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

        public ProfesorBuilder setId(Long id){
            this.id = id;
            return this;
        }

        /**
         * Generira objekt tipa profesor
         * @return Generiran objekt tipa profesor
         */
        public Profesor createProfesor() {
            return new Profesor(id, ime, prezime, sifra, titulaIzaImena);
        }
    }
}
