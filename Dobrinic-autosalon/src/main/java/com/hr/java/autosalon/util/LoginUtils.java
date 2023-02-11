package com.hr.java.autosalon.util;

import com.hr.java.autosalon.components.Admin;
import com.hr.java.autosalon.components.Role;
import com.hr.java.autosalon.components.User;
import com.hr.java.autosalon.exceptions.CurrentUserException;
import com.hr.java.autosalon.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Login Utilities
 */
public class LoginUtils {

    private static final String USERS_FILE = "dat\\users.txt";
    private static final String CURRENT_USER_FILE = "dat\\current-user.txt";
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Converts a password into hashed string
     * @param password user typed password
     * @return hashed password
     */
    public static String getHashedPassword(String password){
            try{
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(password.getBytes());
                byte[] digest = md.digest();
                String hash = String.format("%032x", new BigInteger(1, digest));
                System.out.println(hash);
                return hash;

            }catch(NoSuchAlgorithmException e){
                e.printStackTrace();
                logger.error(e.getMessage(),e);
                return "";
            }

    }

    /**
     * Checks if the user input is same to the one written in the file
     * @param username username
     * @param password password
     * @return boolean value if the login is valid
     */
    public static LoginDetails<Role> validLoginCheck(String username, String password){
        try(Scanner scanner = new Scanner(new File(USERS_FILE))){
            while(scanner.hasNextLine()){
                var fileUsername = scanner.nextLine();
                var filePassword = scanner.nextLine();

                if(fileUsername.equals(username) && getHashedPassword(password).equals(filePassword)){
                    if(username.equals("admin")){
                        return new LoginDetails<>(new Admin(username),true);
                    }
                    else{
                        return new LoginDetails<>(new User(username),true);
                    }
                }
            }
            return new LoginDetails<>(null,false);
        }catch(FileNotFoundException e){
            logger.error(e.getMessage(),e);
            throw new CurrentUserException(e);
        }
    }

    /**
     * Updates the current-user file with currently logged-in user
     * @param username username
     */
    public synchronized static void updateCurrentUser(String username){
        try{
            FileWriter input = new FileWriter(CURRENT_USER_FILE);
            input.write(username);
            input.close();
        } catch(IOException e){
            logger.error(e.getMessage(),e);
            throw new CurrentUserException(e);
        }
    }


    /**
     * Returns the current user from "current-user.txt"
     * @return user
     */
    public synchronized static String getCurrentUser(){
        try(Scanner scanner = new Scanner(new File(CURRENT_USER_FILE))){
            String username = null;
            while(scanner.hasNextLine()){
                username = scanner.nextLine();
            }
            return username;
        }catch(IOException e){
            logger.error(e.getMessage(),e);
            throw new CurrentUserException(e);
        }

    }
}
