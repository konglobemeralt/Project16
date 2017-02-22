package sample;

import java.awt.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class iMatController {

    @FXML
    private TextField foodSearchField;

    public void helpButtonClicked(ActionEvent event){
       System.out.println("Help");
    }
    public void profileButtonClicked(ActionEvent event){
        System.out.println("profile");
    }

    public void kundkorgButtonClicked(ActionEvent event){
        System.out.println("kundkorg");
    }

    public void minaListorButtonClicked(ActionEvent event){
        System.out.println("minaListor");
    }

    public void gamlaKvittonButtonClicked(ActionEvent event){
        System.out.println("gamlaListor");
    }

    public void foodSearch(ActionEvent event){
        System.out.println("foodSearch");
        System.out.println("Searched for " + foodSearchField.getText());
    }

}
