package iMat.controller;
import iMat.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Created by konglobemeralt on 2017-02-23.
 */
public class ProductViewController extends AnchorPane implements Initializable, ShoppingCartListener {

    @FXML
    private TextField searchBarMain;

    @FXML
    private GridPane centerPaneMain;

    //Reference the main application
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    List<ShoppingItem> shoppingItems;

    public void searchForItem(String searchQuery){
        //clear grid
        centerPaneMain.getChildren().clear();

        List<Product> searchResults = main.iMat.findProducts(searchQuery);
        int i = 0;
        for(Product result : searchResults){
            centerPaneMain.add(new ItemController(new ShoppingItem(result, 2)), i, 0);
            i++;
        }

    }


    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init itemView");
        shoppingItems = main.iMat.getShoppingCart().getItems();
        main.iMat.getShoppingCart().addShoppingCartListener(this);




        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(1), 2)), 0, 0);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(2), 2)), 0, 1);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(3), 2)), 0, 2);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(4), 2)), 0, 3);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(5), 2)), 1, 0);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(6), 2)), 1, 1);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(7), 2)), 1, 2);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(8), 2)), 1, 3);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(9), 2)), 2, 0);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(3), 2)), 2, 1);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(4), 2)), 2, 2);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(5), 2)), 2, 3);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(6), 2)), 3, 0);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(7), 2)), 3, 1);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(8), 2)), 3, 2);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(9), 2)), 3, 3);
        main.iMat.getShoppingCart().getTotal();
    }
    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {

    }
}