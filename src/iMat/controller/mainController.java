package iMat.controller;
import iMat.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


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
    public void kundkorgButtonPressed(ActionEvent event) {
        System.out.println("kundkorg Button pressed");
        if(Main.mainLayout.getRight() == null){
            try{
                loadKundKorgPanel();
            }
            catch (IOException e){
                //Avsluta
            }
        }
        else{
            Main.mainLayout.setRight(null);
        }

    }
    public void searchButtonPressed(ActionEvent event){
        System.out.print("search Button pressed. ");
        searchForItem(event);
    }

    private void loadKundKorgPanel() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/kundKorgPanel.fxml"));
        AnchorPane kundKorgPanel = loader.load();
        Main.mainLayout.setRight(kundKorgPanel);
    }

    public void searchForItem(ActionEvent event){
        System.out.println("Searched for: " + searchBarMain.getText());
    }

}
