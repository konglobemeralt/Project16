package iMat.controller;
import iMat.Main;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.CartEvent;
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


        //testgrej
        int i = 148;
        while(--i > 1)
        {
            shoppingItems.add((new ShoppingItem(main.iMat.getProduct(i), 0)));
        }
        fillCenterPane(shoppingItems);
        //testgrej


        main.iMat.getShoppingCart().getTotal();
    }
    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {

    }

    private void fillCenterPane(List<ShoppingItem> shoppingItemlist)
    {
        int len = shoppingItemlist.size();
        System.out.println(len);
        int rowIndex = 0;
        int colIndex = 0;
        int arrayIndex = 0;
        while(arrayIndex < len)
        {
            centerPaneMain.add(new ItemController(shoppingItemlist.get(arrayIndex)),colIndex,rowIndex);
            arrayIndex++;
            colIndex++;
            if(colIndex > 3)
            {
                colIndex = 0;
                rowIndex++;
            }
        }
    }
}