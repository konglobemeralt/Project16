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

        for (int index = orders.size() - 1; index >= 0; index--){

            ReceiptPanelController controller = new ReceiptPanelController(orders.get(index), main);
            ordersGrid.add(controller,0, index);

            if (index % 2 == 1){
                controller.getStyleClass().add("paneStyleDark");
            }

            scrollPaneReceipt.setVvalue(0.0);
            scrollPaneReceipt.autosize();
        }
    }
}
