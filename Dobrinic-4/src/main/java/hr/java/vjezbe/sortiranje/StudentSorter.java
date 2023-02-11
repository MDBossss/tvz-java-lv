package hr.java.vjezbe.sortiranje;

import hr.java.vjezbe.entitet.Student;

import java.util.Comparator;

public class StudentSorter implements Comparator<Student> {

    public int compare(Student s1,Student s2){
        int comp = s1.getPrezime().compareTo(s2.getPrezime());
        return comp != 0 ? comp : s1.getIme().compareTo(s2.getIme());
    }
}
