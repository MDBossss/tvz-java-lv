package hr.java.vjezbe.iznimke;

/**
 * Iznimka koja se baci kada nije moguce odrediti prosjek studenta
 */
public class NemoguceOdreditiProsjekStudentaException extends Exception {


    public NemoguceOdreditiProsjekStudentaException() {
    }

    public NemoguceOdreditiProsjekStudentaException(String message) {
        super(message);
    }

    public NemoguceOdreditiProsjekStudentaException(String message, Throwable cause) {
        super(message, cause);
    }

    public NemoguceOdreditiProsjekStudentaException(Throwable cause) {
        super(cause);
    }

    public NemoguceOdreditiProsjekStudentaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
