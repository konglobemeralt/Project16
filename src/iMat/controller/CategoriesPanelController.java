package iMat.controller;
import iMat.Main;
import iMat.model.ProductSearch;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by konglobemeralt on 2017-02-23.
 */
public class CategoriesPanelController {

    private Button lastPressedCat;

    @FXML
    Button favouritesButtonCategories;

    @FXML
    private Button minaListorButton;

    @FXML
    private Button gamlaKvittonButton;

    @FXML
    private Button fruitButton;

    @FXML
    private Button vegetablesButton;

    @FXML
    private Button meatAndFishButton;

    @FXML
    private Button pentryButton;

    @FXML
    private Button breadButton;

    @FXML
    private Button dairyButton;

    @FXML
    private Button drinksButton;

    @FXML
    private Button sweetsButton;

    //Reference the main application
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void CategoryButtonPressed(ActionEvent event) {
        List<Product> results = ProductSearch.getCategory(((Button) event.getSource()).getText());
        main.pageHistory().addProductLink(results);
        main.fillProductView(results);

        //if (main.getMainLayout().getCenter() == null || !main.getMainLayout().getCenter().getId().equals("scrollPane")) { //Scrollpane is the root element of productview
            try {main.showProductView();} catch (IOException e){ e.printStackTrace(); }
        //}


       if(lastPressedCat != null){
           lastPressedCat.getStyleClass().removeAll("selectedCat");
       }

        ((Button)event.getSource()).getStyleClass().add("selectedCat");
        lastPressedCat = ((Button)event.getSource());
    }

    public void favouritesButtonPressed(){
        System.out.println("Favourites button pressed.");
        List<Product> results = Main.iMat.favorites();
        main.fillProductView(results);
    }
    public void minaListorButtonPressed(){
        System.out.println("minaListor button pressed.");
    }
    public void gamlaKvittonButtonPressed(){
        System.out.println("Favourites button pressed.");
    }



}
