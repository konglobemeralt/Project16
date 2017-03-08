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
public class ProductViewController extends AnchorPane {
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

    @FXML
    private void initialize(){
        savedProducts = new ArrayList<>();
    }

    private List<Product> savedProducts;

    private boolean isFavourited(ShoppingItem Item){

        for(Product favourite : Main.iMat.favorites()){
            if(favourite.equals(Item.getProduct()) ){
                return false;
            }

        }
        return true;
    }

    public void refresh(){
        fillCenterPaneProduct(savedProducts);
    }

    public void fillCenterPaneProduct(List<Product> products){
        if (savedProducts.equals(products)){
            savedProducts.clear();
            savedProducts = products;
        }

        List<ShoppingItem> shoppingItemList = new ArrayList<>();

        for (Product p: products) {
            shoppingItemList.add(new ShoppingItem(p, 0));
        }

        fillCenterPaneShoppingItem(shoppingItemList);
    }

    private void fillCenterPaneShoppingItem(List<ShoppingItem> products){


        centerPaneMain.getChildren().clear();

        int maxColIndex = main.getMainLayout().getRight() == null ? 3 : 1;


        int len = products.size();
        System.out.println(len);
        int rowIndex = 0;
        int colIndex = 0;
        int arrayIndex = 0;
        while(arrayIndex < len)
        {

            ItemController controller = new ItemController(products.get(arrayIndex), isFavourited(products.get(arrayIndex)));
            controller.setMain(main);
            centerPaneMain.add(controller,colIndex,rowIndex);
            arrayIndex++;
            colIndex++;
            if(colIndex > maxColIndex)
            {
                colIndex = 0;
                rowIndex++;
            }
        }
        scrollPane.setVvalue(0.0);
        scrollPane.autosize();
    }


}