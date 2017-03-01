package iMat.controller;
import iMat.Main;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;


/**
 * Created by konglobemeralt on 2017-02-23.
 */
public class MainViewController {
    @FXML
    private TextField searchBarMain;

    //Reference the main application
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public void homeButtonPressed(ActionEvent event){
        System.out.println("Home Button pressed");
    }

    public void helpButtonPressed(ActionEvent event){
        System.out.println("Help Button pressed");
    }
    public void profileButtonPressed(ActionEvent event){
        System.out.println("profile Button pressed");
    }
    public void kundkorgButtonPressed(ActionEvent event) {
        System.out.println("kundkorg Button pressed");
        if(main.getMainLayout().getRight() == null){
            try{
                main.showShoppingBagView();
            }
            catch (IOException e){
                //Avsluta
            }
        }
        else{
            main.getMainLayout().setRight(null);
        }

    }
    public void searchButtonPressed(ActionEvent event){
        System.out.print("search Button pressed. ");
        searchForItem(event);
    }

    public void searchForItem(ActionEvent event){
        System.out.println("Searched for: " + searchBarMain.getText());
    }

}