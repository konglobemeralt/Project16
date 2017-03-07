package iMat.controller;
import iMat.Main;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingCartListener;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.net.URL;
import java.util.ArrayList;
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

    @FXML
    private ScrollPane scrollPane;

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
        /*int i = 14;
        while(--i > 1)
        {
            shoppingItems.add((new ShoppingItem(main.iMat.getProduct(i), 1)));
        }*/
        //fillCenterPane(shoppingItems);
        //testgrej


        main.iMat.getShoppingCart().getTotal();
    }
    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {

    }

    public void fillCenterPane(List<Product> products){

        List<ShoppingItem> shoppingItemList = new ArrayList<>();

        for (Product p: products) {
            shoppingItemList.add(new ShoppingItem(p, 0));
        }

        centerPaneMain.getChildren().clear();


        int len = shoppingItemList.size();
        System.out.println(len);
        int rowIndex = 0;
        int colIndex = 0;
        int arrayIndex = 0;
        while(arrayIndex < len)
        {
            ItemController controller = new ItemController(shoppingItemList.get(arrayIndex));
            controller.setMain(main);
            centerPaneMain.add(controller,colIndex,rowIndex);
            arrayIndex++;
            colIndex++;
            if(colIndex > 3)
            {
                colIndex = 0;
                rowIndex++;
            }
        }
        scrollPane.setVvalue(0.0);
        scrollPane.autosize();
    }
}