package iMat.controller;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


/**
 * Created by konglobemeralt on 2017-02-23.
 */
public class mainController {
    @FXML
    TextField searchBarMain;

    public void homeButtonPressed(ActionEvent event){
        System.out.println("Home Button pressed");
    }

    public void helpButtonPressed(ActionEvent event){
        System.out.println("Help Button pressed");
    }
    public void profileButtonPressed(ActionEvent event){
        System.out.println("profile Button pressed");
    }
    public void kundkorgButtonPressed(ActionEvent event){
        System.out.println("kundkorg Button pressed");
    }
    public void searchButtonPressed(ActionEvent event){
        System.out.print("search Button pressed. ");
        searchForItem(event);
    }

    public void searchForItem(ActionEvent event){
        System.out.println("Searched for: " + searchBarMain.getText());
    }

}
