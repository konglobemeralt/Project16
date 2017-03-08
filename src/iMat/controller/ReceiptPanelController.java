package iMat.controller;

import iMat.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kraft on 2017-03-07.
 */
public class ReceiptPanelController extends AnchorPane{

    @FXML
    private Label dateLabel;

    @FXML
    private Label numberOfItemsLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label showMoreLabel;

    @FXML
    private ListView<String> itemsList;

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

            String[] months = {"Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"};

            Date date = order.getDate();
            String minutes = ""+date.getMinutes();
            if (date.getMinutes() < 10){
                minutes = "0"+date.getMinutes();
            }
            dateLabel.setText("Beställt den " + date.getDate() + " " + months[date.getMonth()] + " " + (1900 + date.getYear()) + "  " + date.getHours() + ":" + minutes);

            List<String> listItems = new ArrayList<>();

            for (int i = 0; i < items.size() && i < 3; i++) {
                ShoppingItem s =  items.get(i);
                listItems.add("  " + Math.round(s.getAmount()*100)/100 + " " + s.getProduct().getUnitSuffix() + "   " + s.getProduct().getName() + "  för  " + Math.round(s.getProduct().getPrice()*s.getAmount()*100)/100 + " kr" );
            }

            if (items.size() > 3){
                showMoreLabel.setVisible(true);
                listItems.add("  ...");
            }
            else {
                showMoreLabel.setVisible(false);
            }

            ObservableList<String> itemsObservable = FXCollections.observableArrayList(listItems);

            itemsList.setItems(itemsObservable);

            this.getChildren().add(receiptPanel);
        }
        catch (IOException e){

        }
    }

    @FXML
    void panelPressed(MouseEvent event) {
        main.showDetailedRecieptView(order);
        main.pageHistory().addOrderLink(order);
    }
}
