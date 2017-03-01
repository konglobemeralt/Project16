package iMat.controller;
import iMat.Main;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
/**
 * Created by konglobemeralt on 2017-02-23.
 */
public class CategoriesPanelController {

    @FXML
    Button favouritesButtonCategories;

    //Reference the main application
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public void favouritesButtonPressed(){
        System.out.println("Favourites button pressed.");
    }
    public void minaListorButtonPressed(){
        System.out.println("minaListor button pressed.");
    }
    public void gamlaKvittonButtonPressed(){
        System.out.println("Favourites button pressed.");
    }



}
