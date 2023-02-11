package hr.java.vjezbe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Stream;

public class Test {

    public static void main(String[] args) {
        System.out.println("ere");
        try(Stream<String> stream = Files.lines(new File("dat\\citaj.txt").toPath(), Charset.forName("UTF-8"))) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        } ;
    }


}
