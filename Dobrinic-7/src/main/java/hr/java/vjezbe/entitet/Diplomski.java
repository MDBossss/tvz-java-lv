package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.PostojiViseNajmladihStudenataException;

/**
 * Interface za smjerove na diplomskoj razini.
 */
public interface Diplomski extends Visokoskolska {
    /**
     * Pronalazi najboljeg studenta za rektorovu nagradu.
     * @return Najbolji student izabran za rektorovu nagradu
     * @throws PostojiViseNajmladihStudenataException
     */
    public Student odrediStudentaZaRektorovuNagradu() throws PostojiViseNajmladihStudenataException;
}
