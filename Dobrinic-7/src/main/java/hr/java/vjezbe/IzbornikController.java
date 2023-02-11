package hr.java.vjezbe;

import hr.java.vjezbe.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class IzbornikController {

    public void showStudentSearchScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("pretrazivanjeStudenata.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        MainApplication.getMainStage().setTitle("Pretraživanje studenata");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();
    }

    public void showProfesorSearchScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("pretrazivanjeProfesora.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),600,600);
        MainApplication.getMainStage().setTitle("Pretraživanje profesora");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();
    }

    public void showPredmetSearchScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("pretrazivanjePredmeta.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),600,600);
        MainApplication.getMainStage().setTitle("Pretraživanje predmeta");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();
    }

    public void showIspitSearchScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("pretrazivanjeIspita.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),600,600);
        MainApplication.getMainStage().setTitle("Pretraživanje profesora");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();
    }
}
