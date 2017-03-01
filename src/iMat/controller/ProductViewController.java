package iMat.controller;
import iMat.Main;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCartListener;
import se.chalmers.ait.dat215.project.ShoppingItem;

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



    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init itemView");
        shoppingItems = main.iMat.getShoppingCart().getItems();
        main.iMat.getShoppingCart().addShoppingCartListener(this);


       // Button tempButton1 = new Button("Button_1");
       // Button tempButton2 = new Button("Button_2");

       // centerPaneMain.add(tempButton1, 0, 0);
       // centerPaneMain.add(tempButton2, 0, 1);

        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(1), 2)), 0, 1);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(2), 2)), 0, 2);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(3), 2)), 0, 3);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(4), 2)), 1, 1);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(5), 2)), 1, 2);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(6), 2)), 1, 3);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(7), 2)), 2, 1);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(8), 2)), 2, 2);
        centerPaneMain.add(new ItemController(new ShoppingItem(main.iMat.getProduct(9), 2)), 2, 3);

        main.iMat.getShoppingCart().getTotal();
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
    }



}
