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

    public void searchForItem(ActionEvent event){
        System.out.println("Searched for item: " + searchBarMain.getText());
    }

}
