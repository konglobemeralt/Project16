package iMat.controller;
import iMat.Main;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCartListener;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Created by konglobemeralt on 2017-02-23.
 */
public class itemViewController extends AnchorPane implements Initializable, ShoppingCartListener {
    @FXML
    TextField searchBarMain;

    @FXML
    GridPane centerPaneMain;

    IMatDataHandler iMatHandler;
    List<ShoppingItem> shoppingItems;



    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init itemView");
        iMatHandler = IMatDataHandler.getInstance();
        shoppingItems = iMatHandler.getShoppingCart().getItems();
        iMatHandler.getShoppingCart().addShoppingCartListener(this);

        centerPaneMain.add(new itemController(new ShoppingItem(iMatHandler.getProduct(25), 2)), 2, 0);
        centerPaneMain.add(new itemController(new ShoppingItem(iMatHandler.getProduct(34), 2)), 2, 1);

        iMatHandler.getShoppingCart().getTotal();
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
    }



}
