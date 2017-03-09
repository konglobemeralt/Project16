package iMat.controller;

import iMat.Main;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.security.PublicKey;
import java.util.Random;

/**
 * Created by Kraft on 2017-03-08.
 */
public class HomeController {

    @FXML
    private GridPane featuredItemsGrid;

    //Reference the main application
    private Main main;

    public void setMain(Main main){
        this.main = main;
    }

    public void update(){
        Random random = new Random();
        for (int columnIndex = 0; columnIndex < 4; columnIndex++){
            ShoppingItem shoppingItem = new ShoppingItem(main.iMat.getProduct(random.nextInt(main.iMat.getProducts().size())), 0);

            ItemController controller = new ItemController(shoppingItem, isFavourited(shoppingItem));
            controller.setMain(main);

            featuredItemsGrid.add(controller, columnIndex, 0);
        }
    }

    private boolean isFavourited(ShoppingItem Item){

        for(Product favourite : Main.iMat.favorites()){
            if(favourite.equals(Item.getProduct()) ){
                return false;
            }

        }
        return true;
    }
}
