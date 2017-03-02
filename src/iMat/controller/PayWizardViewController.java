package iMat.controller;

import iMat.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.time.LocalDate;
import java.util.List;

public class PayWizardViewController {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab overview;

    @FXML
    private Tab credentials;

    @FXML
    private Tab delivery;

    @FXML
    private Tab payment;


    //Reference the main application
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public void showOverviewTab(){
        tabPane.getSelectionModel().select(overview);

        updateTabEnabledStatus();
        updateShoppingBagGrid();
    }

    @FXML
    public void initialize() {
        Customer customer = main.iMat.getCustomer();

        firstNameArea.setText(customer.getFirstName());
        lastNameArea.setText(customer.getLastName());
        phoneArea.setText(customer.getPhoneNumber());
        addressArea.setText(customer.getAddress());
        postalCodeArea.setText(customer.getPostCode());
        postAddressArea.setText(customer.getPostAddress());

        firstNameArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                customer.setFirstName(firstNameArea.getText());
                updateTabEnabledStatus();
            }
        });

        lastNameArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                customer.setLastName(lastNameArea.getText());
                updateTabEnabledStatus();
            }
        });

        phoneArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                customer.setPhoneNumber(phoneArea.getText());
                updateTabEnabledStatus();
            }
        });

        addressArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                customer.setAddress(addressArea.getText());
                updateTabEnabledStatus();
            }
        });

        postalCodeArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                customer.setPostCode(postalCodeArea.getText());
                updateTabEnabledStatus();
            }
        });

        postAddressArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                customer.setPostAddress(postAddressArea.getText());
                updateTabEnabledStatus();
            }
        });

        //TODO gör vettiga värden

        LocalDate today = LocalDate.now();

        int dayIndex = 0, monthIndex = 0, dayNumber = today.getDayOfMonth();

        String[] weekdays = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
        String[] translatedWeekdays = {"Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag", "Söndag"};

        for (int i = 0; i < weekdays.length; i++){
            if (today.getDayOfWeek().toString().equals(weekdays[i])){
                dayIndex = i;
                break;
            }
        }

        String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        String[] translatedMonths = {"Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"};

        for (int i = 0; i < months.length; i++){
            if (today.getMonth().toString().equals(months[i])){
                monthIndex = i;
                break;
            }
        }

        String[] displayDates = new String[7];

        for (int i = 0; i < 5; i++){
            dayIndex = (dayIndex + 1) % 7;
            if (dayNumber + 1 >= today.lengthOfMonth()){
                dayNumber = 0;
                monthIndex = (monthIndex + 1) % 12;
            }
            dayNumber++;
            displayDates[i] = translatedWeekdays[dayIndex] + " " + dayNumber + " " + translatedMonths[monthIndex];
            //System.out.println(translatedWeekdays[dayIndex] + " " + dayNumber + " " + translatedMonths[monthIndex]);
        }

        ObservableList<String> times  = FXCollections.observableArrayList( "08:00 - 10:00", "10:00 - 12:00", "12:00 - 14:00", "14:00 - 16:00", "16:00 - 18:00", "18:00 - 20:00");
        ObservableList<String> dates  = FXCollections.observableArrayList( displayDates[0], displayDates[1], displayDates[2], displayDates[3], displayDates[4], displayDates[5]);

        dateComboBox.setItems(dates);
        timeComboBox.setItems(times);

        cardPanel.setVisible(false);

        TextField[]textFields = {cardOwnerArea, cardNumberArea, cardMonthArea, cardYearArea, cardCVCArea};

        for (TextField t: textFields) {
            t.textProperty().addListener(new ChangeListener<String>() {
                public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                    updateTabEnabledStatus();
                }
            });
        }
    }

    @FXML
    private void updateTabEnabledStatus(ActionEvent e){
        updateTabEnabledStatus();
    }

    private void updateTabEnabledStatus() {

        if (isOverviewFinished()) {
            overviewNextButton.setDisable(false);
            credentials.setDisable(false);
        } else {
            overviewNextButton.setDisable(true);
            credentials.setDisable(true);
        }

        if (isCredentialsFinished() && !credentials.isDisabled()) {
            credentialsNextButton.setDisable(false);
            delivery.setDisable(false);
        } else {
            credentialsNextButton.setDisable(true);
            delivery.setDisable(true);
        }

        if (isDeliveryFinished() && !delivery.isDisabled()) {
            deliveryNextButton.setDisable(false);
            payment.setDisable(false);
        } else {
            deliveryNextButton.setDisable(true);
            payment.setDisable(true);
        }

        if (isPaymentFinished() && !payment.isDisabled()) {
            confirmationButton.setDisable(false);
        } else {
            confirmationButton.setDisable(true);
        }
    }

    @FXML
    private void nextButtonPressed(ActionEvent a) {

        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();


        if (currentTab.equals(overview)) {
            currentTab = credentials;
        } else if (currentTab.equals(credentials)) {
            currentTab = delivery;
        } else if (currentTab.equals(delivery)) {
            currentTab = payment;
        }

        updateTabEnabledStatus();
        tabPane.getSelectionModel().select(currentTab);

    }

    @FXML
    private void backButtonPressed(ActionEvent a) {

        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();

        if (currentTab.equals(payment)) {
            currentTab = delivery;
        } else if (currentTab.equals(delivery)) {
            currentTab = credentials;
        } else if (currentTab.equals(credentials)) {
            currentTab = overview;
        }

        tabPane.getSelectionModel().select(currentTab);

    }

    @FXML
    private void cancelButtonPressed(ActionEvent a) {

    }

    //----------------Overview----------------\\
    @FXML
    private Button overviewNextButton;

    @FXML
    private GridPane shoppingBagGrid;

    @FXML
    private AnchorPane payViewPane;

    @FXML
    private Label numberOfItemsLabelOverview;

    @FXML
    private Label totalPriceLabelOverview;

    public boolean isOverviewFinished() {
        return main.iMat.getShoppingCart().getItems().size() > 0;
    }

    public void updateShoppingBagGrid() {

        numberOfItemsLabelOverview.setText("" + main.iMat.getShoppingCart().getItems().size());
        totalPriceLabelOverview.setText("" + main.iMat.getShoppingCart().getTotal());

        //Clear grid
        shoppingBagGrid.getChildren().clear();
        shoppingBagGrid.addColumn(0);
        shoppingBagGrid.getColumnConstraints().set(0, new ColumnConstraints(10, 350, 350));
        shoppingBagGrid.addColumn(1);
        shoppingBagGrid.getColumnConstraints().set(1, new ColumnConstraints(30));
        shoppingBagGrid.addColumn(2);
        shoppingBagGrid.getColumnConstraints().set(2, new ColumnConstraints(60, 120, 120));
        shoppingBagGrid.addColumn(3);
        shoppingBagGrid.getColumnConstraints().set(3, new ColumnConstraints(30));
        shoppingBagGrid.addColumn(4);
        /*ColumnConstraints a = new ColumnConstraints(120, 120, 120);
        a.setMaxWidth(Region.USE_COMPUTED_SIZE);
        a.setPrefWidth(Region.USE_COMPUTED_SIZE);
        shoppingBagGrid.getColumnConstraints().set(4, a);*/
        shoppingBagGrid.getColumnConstraints().set(4, new ColumnConstraints(500, 500, 500));
        shoppingBagGrid.addColumn(5);
        shoppingBagGrid.getColumnConstraints().set(5, new ColumnConstraints(30));

        List<ShoppingItem> shoppingItems = main.iMat.getShoppingCart().getItems();

        for (int index = 0; index < shoppingItems.size(); index++) {

            //Initialize all components
            Button subtractButton = new Button("");
            subtractButton.setPrefWidth(30);
            subtractButton.setMaxWidth(30);
            subtractButton.setMinWidth(30);
            subtractButton.setPrefHeight(30);
            subtractButton.setMaxHeight(30);
            subtractButton.setMinHeight(30);
            subtractButton.setId(index + "subtractButton");
            subtractButton.getStyleClass().add("subtractButton");
            subtractButton.setOnAction((e) -> addOrSubtractButtonPressed(false));

            Button addButton = new Button("");
            addButton.setPrefWidth(30);
            addButton.setMaxWidth(30);
            addButton.setMinWidth(30);
            addButton.setPrefHeight(30);
            addButton.setMaxHeight(30);
            addButton.setMinHeight(30);
            addButton.setId(index + "addButton");
            addButton.getStyleClass().add("addButton");
            addButton.setOnAction((e) -> addOrSubtractButtonPressed(true));

            Button removeButton = new Button("");
            removeButton.setPrefHeight(30);
            removeButton.setMinHeight(30);
            removeButton.setMaxHeight(30);
            removeButton.setPrefWidth(30);
            removeButton.setMinWidth(30);
            removeButton.setMaxWidth(30);
            removeButton.setId(index + "removeButton");
            removeButton.getStyleClass().add("deleteButton");
            removeButton.setOnAction((e) -> removeButtonPressed());


            TextArea amountTextArea = new TextArea("st");
            amountTextArea.setPrefHeight(30);
            amountTextArea.setMinHeight(30);
            amountTextArea.setMaxHeight(30);
            amountTextArea.setId(index + "amountTextArea");
            amountTextArea.setOnInputMethodTextChanged((e) -> amountTextAreaChanged());

            shoppingBagGrid.addRow(index);
            ShoppingItem s = shoppingItems.get(index);
            shoppingBagGrid.add(new Label("  " + s.getProduct().getName()), 0, index);
            shoppingBagGrid.add(subtractButton, 1, index);
            amountTextArea.setText(s.getAmount() + " " + s.getProduct().getUnitSuffix());
            shoppingBagGrid.add(amountTextArea, 2, index);
            shoppingBagGrid.add(addButton, 3, index);
            Label priceLabel = new Label("  " + s.getTotal() + " kr");
            priceLabel.paddingProperty().set(new Insets(0, 0, 0, 200));
            shoppingBagGrid.add(priceLabel, 4, index);
            shoppingBagGrid.add(removeButton, 5, index);
        }


    }

    private void removeButtonPressed() {


        for (Node n : shoppingBagGrid.getChildren()) {
            if (n.isFocused()) {
                int removeIndex = Character.getNumericValue((n.getId()).charAt(0));
                main.iMat.getShoppingCart().removeItem(removeIndex);
            }
        }
        updateShoppingBagGrid();
        updateTabEnabledStatus();

    }

    private void addOrSubtractButtonPressed(boolean add) {

        for (Node n : shoppingBagGrid.getChildren()) {
            if (n.isFocused()) {
                int index = Character.getNumericValue((n.getId()).charAt(0));
                double newAmount = add ? main.iMat.getShoppingCart().getItems().get(index).getAmount() + 1 : main.iMat.getShoppingCart().getItems().get(index).getAmount() - 1;
                if (newAmount > 0) {
                    main.iMat.getShoppingCart().getItems().get(index).setAmount(newAmount);
                } else {
                    main.iMat.getShoppingCart().removeItem(index);
                }
            }
        }
        updateShoppingBagGrid();
        updateTabEnabledStatus();

    }

    private void amountTextAreaChanged() {
        //TODO

        /*for (Node n: shoppingBagGrid.getChildren()) {
            if (n.isFocused()){
                int index = Character.getNumericValue((n.getId()).charAt(0));
                //main.iMat.getShoppingCart().removeItem(removeIndex);
            }
        }
        updateShoppingBagGrid();*/
    }

    //----------------Credentials----------------\\
    @FXML
    private Button credentialsNextButton;

    @FXML
    private TextField firstNameArea;

    @FXML
    private TextField lastNameArea;

    @FXML
    private TextField phoneArea;

    @FXML
    private TextField addressArea;

    @FXML
    private TextField postalCodeArea;

    @FXML
    private TextField postAddressArea;

    private boolean isCredentialsFinished() {
        String[] inputs = {firstNameArea.getText(), lastNameArea.getText(), phoneArea.getText(), addressArea.getText(), postalCodeArea.getText(), postAddressArea.getText()};

        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i].equals("")) {
                return false;
            }

            switch (i) {

                case 2: // Phone number
                    try {
                        long number = Long.parseLong(inputs[i].replace(' ', '0').replace('+', '0').replace('-', '0'));
                    } catch (NumberFormatException e) {
                        return false;
                    }
                    break;

                case 4: // Postal Code
                    try {
                        int number = Integer.parseInt(inputs[i].replace(' ', '0'));
                    } catch (NumberFormatException e) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    @FXML
    private void credentialAreaChanged(ActionEvent a) {
        Customer customer = main.iMat.getCustomer();
        customer.setFirstName(firstNameArea.getText());
        customer.setLastName(lastNameArea.getText());
        customer.setPhoneNumber(phoneArea.getText());
        customer.setAddress(addressArea.getText());
        customer.setPostCode(postalCodeArea.getText());
        customer.setPostAddress(postAddressArea.getText());

        updateTabEnabledStatus();
    }

    //----------------Delivery----------------\\

    @FXML
    private ComboBox<String> dateComboBox;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private Button deliveryNextButton;

    private boolean isDeliveryFinished() {
        int d =  dateComboBox.getSelectionModel().getSelectedIndex();
        int t = timeComboBox.getSelectionModel().getSelectedIndex();
        return  d != -1 && d != 5 && t != -1; // The 5 is there because of the error
    }

    //----------------Payment----------------\\

    @FXML
    private ToggleGroup payMethodRadioButtons;

    @FXML
    private RadioButton cardRadioButton;

    @FXML
    private Button confirmationButton;

    @FXML
    private AnchorPane cardPanel;

    @FXML
    private TextField cardOwnerArea;

    @FXML
    private TextField cardNumberArea;

    @FXML
    private TextField cardMonthArea;

    @FXML
    private TextField cardYearArea;

    @FXML
    private TextField cardCVCArea;

    @FXML
    void cardRadioButtonPressed(ActionEvent event) {
        cardPanel.setVisible(true);
        cardPanel.setDisable(false);

        updateTabEnabledStatus();
    }

    @FXML
    void notCardRadioButtonPressed(ActionEvent event) {
        cardPanel.setVisible(false);
        cardPanel.setDisable(true);

        updateTabEnabledStatus();
    }


    private boolean isPaymentFinished() {
        if (cardRadioButton.isSelected()){
            String[] inputs = {cardOwnerArea.getText(), cardNumberArea.getText(), cardMonthArea.getText(), cardYearArea.getText(), cardCVCArea.getText()};

            for (int i = 0; i < inputs.length; i++){
                if (inputs[i].equals("")){
                    return false;
                }

                switch (i){
                    case 1: // Card Number
                        for (char c: inputs[i].toCharArray()) {
                            if (Character.getNumericValue(c) == -1){
                                return false;
                            }
                        }
                        break;

                    case 2: // Expiration Month
                        try {
                            int a = Integer.parseInt(inputs[i]);
                            if (a > 12 || a < 1){
                                return false;
                            }
                        } catch (NumberFormatException n){
                            return false;
                        }
                        break;

                    case 3: // Expiration Year
                        try {
                            int a = Integer.parseInt(inputs[i]);
                            if (a > 99 || a < 0){
                                return false;
                            }
                        } catch (NumberFormatException n){
                            return false;
                        }
                        break;

                    case 4: // Card CVC
                        if (inputs[i].length() != 3){
                            return false;
                        }
                        for (char c: inputs[i].toCharArray()) {
                            if (Character.getNumericValue(c) == -1){
                                return false;
                            }
                        }
                        break;
                }
            }

        }

        for (Toggle t: payMethodRadioButtons.getToggles()) {

            if (t.isSelected()){
                return true;
            }
        }

        return false;
    }

}
