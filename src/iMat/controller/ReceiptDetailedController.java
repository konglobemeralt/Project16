package iMat.controller;

import iMat.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kraft on 2017-03-08.
 */
public class ReceiptDetailedController {

    @FXML
    private ListView<String> itemsList;

    @FXML
    private Label dateLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label numberOfItems;

    //Reference the main application
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void initialize(){
       // itemsList.set
    }

    public void setOrder(Order order){
        List<ShoppingItem> items = order.getItems();
        double price = 0;
        for (ShoppingItem p: items) {
            price += p.getAmount()*p.getProduct().getPrice();
        }
        price = Math.round(price*100)/100.0;

        priceLabel.setText("Totalpris: "+price + " kr");
        numberOfItems.setText("Antal varor: "+items.size()+" st");

        String[] months = {"Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"};

        Date date = order.getDate();
        String minutes = ""+date.getMinutes();
        if (date.getMinutes() < 10){
            minutes = "0"+date.getMinutes();
        }
        dateLabel.setText("Beställt den " + date.getDate() + " " + months[date.getMonth()] + " " + (1900 + date.getYear()) + "  " + date.getHours() + ":" + minutes);
        List<String> listItems = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            ShoppingItem s =  items.get(i);
            listItems.add("  " + Math.round(s.getAmount()*100)/100 + " " + s.getProduct().getUnitSuffix() + "   " + s.getProduct().getName() + "  för  " + Math.round(s.getProduct().getPrice()*s.getAmount()*100)/100 + " kr" );
        }

        ObservableList<String> itemsObservable = FXCollections.observableArrayList(listItems);

        itemsList.setItems(itemsObservable);
    }

    @FXML
    void closeButtonPressed(ActionEvent event) {
        main.pageHistory().goBack();
    }
}
