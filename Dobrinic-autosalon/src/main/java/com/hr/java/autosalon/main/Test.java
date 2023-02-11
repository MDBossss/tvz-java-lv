package com.hr.java.autosalon.main;

import com.hr.java.autosalon.components.*;
import com.hr.java.autosalon.enums.Condition;
import com.hr.java.autosalon.enums.GearboxType;
import com.hr.java.autosalon.exceptions.ChangesException;
import com.hr.java.autosalon.util.Change;
import com.hr.java.autosalon.util.ChangesWriter;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Test {

    private static final String CHANGES_FILE = "dat\\changes.dat";

    public static void main(String[] args){
        VehicleHistory vehicleHistory = new VehicleHistory(1L,Condition.NEW,3, LocalDate.now());
        Engine engine = new DieselEngine(1L,230000,10.0, 53.5);
        Equipment equipment = new Equipment.EquipmentBuilder().addLeather(true).build();
        Gearbox gearbox = new Gearbox(1L,GearboxType.AUTOMATIC,6);

        Car carold = new Car(2L,"Mercedes","W124","Yellow",vehicleHistory,engine,4,equipment,gearbox);

        Car carnew = new Car(3L,"Mercedes","W126","Yellow",vehicleHistory,engine,4,equipment,gearbox);

        Car carnew1 = new Car(3L,"TOYOTA","W126","Yellow",vehicleHistory,engine,4,equipment,gearbox);
        Car carnew2 = new Car(3L,"TOYOTA","W129","Yellow",vehicleHistory,engine,4,equipment,gearbox);

        Change<Car,Car> change = new Change<>(carold,carnew,"mike", LocalDateTime.now());
        Change<Car,Car> change1 = new Change<>(carnew2,carnew1,"mike", LocalDateTime.now());


        List<Change> changesFromFile = new ArrayList<>();

        try{
            changesFromFile = ChangesWriter.getChanges();
            changesFromFile.removeIf(ch -> ch.getUsername().contains("mike"));

            ChangesWriter.writeToChanges(changesFromFile);

            changesFromFile.forEach(chang -> System.out.println(chang.getUsername()));
        }catch(ChangesException e){
            e.printStackTrace();
        }

    }




}
