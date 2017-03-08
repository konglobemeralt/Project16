package iMat.controller;

import iMat.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kraft on 2017-03-07.
 */
public class ReceiptPanelController extends AnchorPane{

    @FXML
    private Label dateLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label numberOfItemsLabel;

    @FXML
    private Label priceLabel;

    //Reference the main application
    private Main main;

    private Order order;

    public ReceiptPanelController(Order order, Main main){
        this.order = order;
        this.main = main;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/receiptPanel.fxml"));
        loader.setController(this);

        AnchorPane receiptPanel;

        try {
            receiptPanel = loader.load();

            //TODO lägg till all text här
            List<ShoppingItem> items = order.getItems();
            double price = 0;
            for (ShoppingItem p: items) {
                price += p.getAmount()*p.getProduct().getPrice();
            }
            price = Math.round(price*100)/100.0;

            priceLabel.setText(price + " kr");
            numberOfItemsLabel.setText(items.size()+" st");

            String[] weekdays = {"Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag", "Söndag"};
            String[] months = {"Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"};

            //priceLabel


            this.getChildren().add(receiptPanel);
        }
        catch (IOException e){

        }
    }
}
