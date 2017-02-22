package sample;

import java.awt.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;

public class iMatController {

    @FXML
    private TextField foodSearchField;

    public void helpButtonClicked(ActionEvent event) {
        System.out.println("Help");
    }

    public void profileButtonClicked(ActionEvent event) {
        System.out.println("profile");
    }

    public void kundkorgButtonClicked(ActionEvent event) {
        System.out.println("kundkorg");
    }

    public void minaListorButtonClicked(ActionEvent event) {
        System.out.println("minaListor");
    }

    public void gamlaKvittonButtonClicked(ActionEvent event) {
        System.out.println("gamlaListor");
    }


    public void foodSearch(ActionEvent event) {
        System.out.println("foodSearch");
        System.out.println("Searched for " + foodSearchField.getText());
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("betalning.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Betalning");
            // stage.setScene(new Scene(iMatMain));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


