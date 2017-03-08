package iMat.controller;
import iMat.Main;
import iMat.controller.BackButtonHandler.Link;
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
    private Button favouritesButtonCategories;

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

        if (main.getMainLayout().getCenter() == null || !main.getMainLayout().getCenter().getId().equals("scrollPane")) { //Scrollpane is the root element of productview
            try {main.showProductView();} catch (IOException e){ e.printStackTrace(); }
        }
        unselectLastSelected();
        ((Button)event.getSource()).getStyleClass().add("pressed");
        lastPressedCat = ((Button)event.getSource());
        main.fillProductView(results);
    }

    public void unselectLastSelected(){
        if(lastPressedCat != null){
            lastPressedCat.getStyleClass().removeAll("pressed");
        }

    }

    public void favouritesButtonPressed(){
        System.out.println("Favourites button pressed.");
        main.pageHistory().addLink(Link.FAVOURITES);

        if (main.getMainLayout().getCenter() == null || !main.getMainLayout().getCenter().getId().equals("scrollPane")) { //Scrollpane is the root element of productview
            try {main.showProductView();} catch (IOException e){ e.printStackTrace(); }
        }

        unselectLastSelected();
        favouritesButtonCategories.getStyleClass().add("pressed");
        lastPressedCat = favouritesButtonCategories;

        List<Product> results = Main.iMat.favorites();
        main.fillProductView(results);
    }
    public void minaListorButtonPressed(){
        System.out.println("minaListor button pressed.");
    }
    public void gamlaKvittonButtonPressed(){
        main.pageHistory().addLink(Link.RECEIPTS);
        main.showReceiptView();
    }



}
