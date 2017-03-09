package iMat.controller;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import iMat.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
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
            ShoppingItem shoppingItem = new ShoppingItem(main.iMat.getProduct(random.nextInt(main.iMat.getProducts().size()-1)), 0);

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

    @FXML
    private void startShoppingButton(ActionEvent event) {
        List<Product> products = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 12; i++){
            products.add(main.iMat.getProduct(random.nextInt(main.iMat.getProducts().size()-1)));
            for (int j = 0; j < i; j++){
                if (products.get(j).equals(products.get(products.size()-1))){
                    products.remove((products.size()-1));
                    i--;
                    break;
                }
            }
        }
        try{
            main.showProductView();
        }catch (IOException e){

        }
        main.fillProductView(products);
        main.pageHistory().addProductLink(products);
    }
}
