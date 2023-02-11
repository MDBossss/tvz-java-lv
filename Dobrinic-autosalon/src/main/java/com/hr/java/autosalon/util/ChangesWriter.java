package com.hr.java.autosalon.util;

import com.hr.java.autosalon.exceptions.ChangesException;

import java.io.*;
import java.util.List;

/**
 * Writes changes to a file
 */
public class ChangesWriter {

    private static final String CHANGES_FILE = "dat\\changes.dat";

    /**
     * Reads changes from a file
     * @return List of changes
     * @throws ChangesException changes exception
     */
    public static List<Change> getChanges() throws ChangesException{
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(CHANGES_FILE))){
            return (List<Change>) in.readObject();
        }catch (ClassNotFoundException | IOException e){
            throw new ChangesException(e);
        }
    }

    /**
     * Adds and writes 1 change to a file
     * @param change change to add
     * @throws ChangesException changes exception
     */
    public static void addChange(Change change)throws ChangesException{
        List<Change> changesList = getChanges();
        changesList.add(change);

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(CHANGES_FILE))){
            out.writeObject(changesList);
        }catch(IOException e){
            throw new ChangesException(e);
        }
    }

    /**
     * Adds and writes list of changes to a file
     * @param newChanges List of changes to add
     * @throws ChangesException changes exception
     */
    public static void addChanges(List<Change> newChanges) throws ChangesException{
        List<Change> changes = getChanges();
        changes.addAll(newChanges);

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(CHANGES_FILE))){
            out.writeObject(changes);
        }catch(IOException e){
            throw new ChangesException(e);
        }
    }

    /**
     * Writes changes list to a file
     * @param changesList list of changes to add
     * @throws ChangesException changes exception
     */
    public static void writeToChanges(List<Change> changesList) throws ChangesException{
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(CHANGES_FILE))){
            out.writeObject(changesList);
        }catch(IOException e){
            throw new ChangesException(e);
        }
    }

}
