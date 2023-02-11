package hr.java.vjezbe;

import hr.java.vjezbe.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class IzbornikController {


//PRETRAZIVANJE PODATKA
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
        MainApplication.getMainStage().setTitle("Pretraživanje ispita");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();
    }

    //UNOS PODATAKA

    public void showStudentInputScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("unosStudenata.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),600,600);
        MainApplication.getMainStage().setTitle("Unos studenta");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();
    }

    public void showProfesorInputScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("unosProfesora.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),600,600);
        MainApplication.getMainStage().setTitle("Unos profesora");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();
    }

    public void showPredmetInputScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("unosPredmeta.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),600,600);
        MainApplication.getMainStage().setTitle("Unos predmeta");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();
    }

    public void showIspitInputScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("unosIspita.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),600,600);
        MainApplication.getMainStage().setTitle("Unos ispita");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();
    }
}
