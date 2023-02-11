package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

public class Sveuciliste<T extends ObrazovnaUstanova>{
    List<T> obrazovneUstanove;

    public Sveuciliste(){
        this.obrazovneUstanove = new ArrayList<>();
    }

    public void dodajObrazovnuUstanovu(T obrazovnaUstanova){
        obrazovneUstanove.add(obrazovnaUstanova);
    }

    public T dohvariObrazovnuUstanovu(int index){
        return obrazovneUstanove.get(index);
    }

    public List<T> getObrazovneUstanove(){
        return new ArrayList<>(obrazovneUstanove);
    }
}
