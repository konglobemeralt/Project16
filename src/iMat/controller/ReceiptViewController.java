package iMat.controller;

import iMat.Main;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.Order;

import java.util.List;

/**
 * Created by Kraft on 2017-03-07.
 */
public class ReceiptViewController {

    @FXML
    protected GridPane ordersGrid;

    @FXML
    private ScrollPane scrollPaneReceipt;


    //Reference the main application
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public void updateOrders(){

        List<Order> orders = main.iMat.getOrders();

        orders = sortOrders(orders);

        for (int index = 0; index < orders.size(); index++){

            ReceiptPanelController controller = new ReceiptPanelController(orders.get(index), main);
            ordersGrid.add(controller,0, index);

            if (index % 2 == 1){
                controller.getStyleClass().add("paneStyle");
            }

            scrollPaneReceipt.setVvalue(0.0);
            scrollPaneReceipt.autosize();
        }
    }

    private List<Order> sortOrders(List<Order> input){
        boolean didSwap = true;
        while (didSwap){
            didSwap = false;
            for (int i = 1; i < input.size(); i++){
                if (input.get(i).getDate().compareTo(input.get(i-1).getDate()) > 0){
                    swap(input, i, i-1);
                    didSwap = true;
                }
            }
        }
        return input;
    }

    private List<Order> swap(List<Order> input, int i, int j){
        Order k =  input.get(i);
        input.set(i, input.get(j));
        input.set(j, k);
        return input;
    }
}
