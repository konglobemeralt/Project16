package iMat.controller;

import iMat.Main;
import iMat.controller.BackButtonHandler.Link;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import se.chalmers.ait.dat215.project.CreditCard;
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
    private Tab deliveryPayment;

    @FXML
    private Tab confirmation;


    //Reference the main application
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public void showOverviewTab() {
        tabPane.getSelectionModel().select(overview);

        updateTabEnabledStatus();
        updateShoppingBagGrid();
    }

    @FXML
    void goBack(ActionEvent event) {
        main.pageHistory().goBack();
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

        CreditCard card = main.iMat.getCreditCard();
        cardOwnerArea.setText(card.getHoldersName());
        if (card.getCardNumber().length() == 16){
            cardNumberArea1.setText(card.getCardNumber().substring(0, 3));
            cardNumberArea2.setText(card.getCardNumber().substring(4, 7));
            cardNumberArea3.setText(card.getCardNumber().substring(8, 11));
            cardNumberArea4.setText(card.getCardNumber().substring(12, 15));
        }
        cardMonthArea.setText(""+card.getValidMonth());
        cardYearArea.setText(""+card.getValidYear());
        cardCVCArea.setText(""+card.getVerificationCode());

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

        LocalDate today = LocalDate.now();

        int dayIndex = 0, monthIndex = 0, dayNumber = today.getDayOfMonth();

        String[] weekdays = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
        String[] translatedWeekdays = {"Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag", "Söndag"};

        for (int i = 0; i < weekdays.length; i++) {
            if (today.getDayOfWeek().toString().equals(weekdays[i])) {
                dayIndex = i;
                break;
            }
        }

        String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        String[] translatedMonths = {"Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"};

        for (int i = 0; i < months.length; i++) {
            if (today.getMonth().toString().equals(months[i])) {
                monthIndex = i;
                break;
            }
        }

        String[] displayDates = new String[7];

        for (int i = 0; i < 5; i++) {
            dayIndex = (dayIndex + 1) % 7;
            if (dayNumber + 1 >= today.lengthOfMonth()) {
                dayNumber = 0;
                monthIndex = (monthIndex + 1) % 12;
            }
            dayNumber++;
            if (i > 1) {
                displayDates[i] = translatedWeekdays[dayIndex] + " " + dayNumber + " " + translatedMonths[monthIndex];
            } else if (i == 0) {
                displayDates[i] = "Imorgon" + " " + dayNumber + " " + translatedMonths[monthIndex];
            } else if (i == 1) {
                displayDates[i] = "Övermorgon" + " " + dayNumber + " " + translatedMonths[monthIndex];
            }
        }

        ObservableList<String> times = FXCollections.observableArrayList("08:00 - 10:00", "10:00 - 12:00", "12:00 - 14:00", "14:00 - 16:00", "16:00 - 18:00", "18:00 - 20:00");
        ObservableList<String> dates = FXCollections.observableArrayList(displayDates[0], displayDates[1], displayDates[2], displayDates[3], displayDates[4], displayDates[5]);

        dateComboBox.setItems(dates);
        timeComboBox.setItems(times);

        cardPanel.setVisible(false);

        TextField[] textFields = {cardOwnerArea, cardNumberArea1,cardNumberArea2,cardNumberArea3,cardNumberArea4, cardMonthArea, cardYearArea, cardCVCArea};

        for (TextField t : textFields) {
            t.textProperty().addListener(new ChangeListener<String>() {
                public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                    updateTabEnabledStatus();
                }
            });
        }
    }

    @FXML
    private void updateTabEnabledStatus(ActionEvent e) {
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
            deliveryPayment.setDisable(false);
        } else {
            credentialsNextButton.setDisable(true);
            deliveryPayment.setDisable(true);
        }

        if (isDeliveryPaymentFinished() && !deliveryPayment.isDisabled()) {
            deliveryNextButton.setDisable(false);
            confirmation.setDisable(false);
            updateConfirmationLabels();
        } else {
            deliveryNextButton.setDisable(true);
            confirmation.setDisable(true);
        }
    }

    @FXML
    private void nextButtonPressed(ActionEvent a) {

        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();


        if (currentTab.equals(overview)) {
            currentTab = credentials;
        } else if (currentTab.equals(credentials)) {
            currentTab = deliveryPayment;
        } else if (currentTab.equals(deliveryPayment)) {
            currentTab = confirmation;
        }

        updateTabEnabledStatus();
        tabPane.getSelectionModel().select(currentTab);

    }

    @FXML
    private void backButtonPressed(ActionEvent a) {

        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();

        if (currentTab.equals(confirmation)) {
            currentTab = deliveryPayment;
        } else if (currentTab.equals(deliveryPayment)) {
            currentTab = credentials;
        } else if (currentTab.equals(credentials)) {
            currentTab = overview;
        }

        tabPane.getSelectionModel().select(currentTab);

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
        totalPriceLabelOverview.setText("" + Math.round(main.iMat.getShoppingCart().getTotal() * 100) / 100.0);

        //Clear grid
        shoppingBagGrid.getChildren().clear();
        shoppingBagGrid.addColumn(0);
        shoppingBagGrid.getColumnConstraints().set(0, new ColumnConstraints(10, 400, 400));
        shoppingBagGrid.addColumn(1);
        shoppingBagGrid.getColumnConstraints().set(1, new ColumnConstraints(30));
        shoppingBagGrid.addColumn(2);
        //shoppingBagGrid.getColumnConstraints().set(2, new ColumnConstraints(60, 160, 160));
        shoppingBagGrid.getColumnConstraints().set(2, new ColumnConstraints(120));
        shoppingBagGrid.addColumn(3);
        shoppingBagGrid.getColumnConstraints().set(3, new ColumnConstraints(30));
        shoppingBagGrid.addColumn(4);
        shoppingBagGrid.getColumnConstraints().set(4, new ColumnConstraints(500, 500, 500));
        shoppingBagGrid.addColumn(5);
        shoppingBagGrid.getColumnConstraints().set(5, new ColumnConstraints(30));

        List<ShoppingItem> shoppingItems = main.iMat.getShoppingCart().getItems();

        for (int index = 0; index < shoppingItems.size(); index++) {

            ShoppingItem shoppingItem = shoppingItems.get(index);
            shoppingBagGrid.addRow(index);

            //Initialize all components
            Label productLabel = new Label("  " + shoppingItem.getProduct().getName());

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

            Label priceLabel = new Label("  " + Math.round(shoppingItem.getTotal() * 100) / 100.0 + " kr");
            priceLabel.paddingProperty().set(new Insets(0, 0, 0, 200));

            TextArea amountTextArea = new TextArea("st");
            amountTextArea.setPrefHeight(30);
            amountTextArea.setMinHeight(30);
            amountTextArea.setMaxHeight(30);
            amountTextArea.setId(index + "amountTextArea");
            amountTextArea.setOnMouseClicked((e) -> amountTextAreaClicked(amountTextArea));
            amountTextArea.focusedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                    if (!newPropertyValue) {
                        amountTextAreaLostFocus(amountTextArea, priceLabel);
                    }
                }
            });

            if (shoppingItem.getAmount() % 1 == 0) {
                amountTextArea.setText((int) (shoppingItem.getAmount()) + " " + shoppingItem.getProduct().getUnitSuffix());
            } else {
                amountTextArea.setText(shoppingItem.getAmount() + " " + shoppingItem.getProduct().getUnitSuffix());
            }

            if (index % 2 == 1) { // Add a pane to every other product so that we may colour it
                AnchorPane productPane = new AnchorPane(productLabel);
                productPane.autosize();
                productPane.setPrefHeight(32);
                productPane.getStyleClass().add("paneStyle");
                shoppingBagGrid.add(productPane, 0, index);

                shoppingBagGrid.add(subtractButton, 1, index);

                shoppingBagGrid.add(amountTextArea, 2, index);

                shoppingBagGrid.add(addButton, 3, index);

                AnchorPane pricePane = new AnchorPane(priceLabel);
                pricePane.autosize();
                pricePane.setPrefHeight(32);
                pricePane.getStyleClass().add("paneStyle");
                shoppingBagGrid.add(pricePane, 4, index);

                shoppingBagGrid.add(removeButton, 5, index);
            } else {


                shoppingBagGrid.add(productLabel, 0, index);
                shoppingBagGrid.add(subtractButton, 1, index);
                shoppingBagGrid.add(amountTextArea, 2, index);
                shoppingBagGrid.add(addButton, 3, index);

                shoppingBagGrid.add(priceLabel, 4, index);
                shoppingBagGrid.add(removeButton, 5, index);
            }


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
        totalPriceLabelOverview.setText("" + Math.round(main.iMat.getShoppingCart().getTotal() * 100) / 100.0);

    }

    private void amountTextAreaLostFocus(TextArea amount, Label price) {
        int index = Character.getNumericValue((amount.getId().charAt(0)));
        ShoppingItem item = main.iMat.getShoppingCart().getItems().get(index);

        try {
            double newAmount = Double.parseDouble(amount.getText());
            if (!(item.getProduct().getUnitSuffix().equals("kg") || item.getProduct().getUnitSuffix().equals("l"))) {
                newAmount = Math.round(newAmount);
            }
            item.setAmount(newAmount);
        } catch (NumberFormatException n) {
        }

        if (item.getAmount() > 0) {
            if (item.getAmount() % 1 == 0) {
                amount.setText(" " + (int) item.getAmount() + " " + item.getProduct().getUnitSuffix());
            } else {
                amount.setText(" " + item.getAmount() + " " + item.getProduct().getUnitSuffix());
            }
            price.setText("  " + Math.round(item.getTotal() * 100) / 100.0 + " kr");
        } else {
            main.iMat.getShoppingCart().removeItem(index);
            updateShoppingBagGrid();
        }
        totalPriceLabelOverview.setText("" + Math.round(main.iMat.getShoppingCart().getTotal() * 100) / 100.0);

    }

    private void amountTextAreaClicked(TextArea amount) {
        amount.setText(amount.getText().split(" ")[0]);
        amount.selectAll();
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

    @FXML
    private ImageView firstNameFeedbackImage;

    @FXML
    private ImageView lastNameFeedbackImage;

    @FXML
    private ImageView phoneFeedbackImage;

    @FXML
    private ImageView postalCodeFeedbackImage;

    @FXML
    private ImageView postAddressFeedbackImage;

    @FXML
    private ImageView addressFeedbackImage;

    @FXML
    private Label firstNameFeedbackLabel;

    @FXML
    private Label lastNameFeedbackLabel;

    @FXML
    private Label phoneFeedbackLabel;

    @FXML
    private Label addressFeedbackLabel;

    @FXML
    private Label postalCodeFeedbackLabel;

    @FXML
    private Label postAddressFeedbackLabel;

    private boolean isCredentialsFinished() {
        String[] inputs = {firstNameArea.getText(), lastNameArea.getText(), phoneArea.getText(), addressArea.getText(), postalCodeArea.getText(), postAddressArea.getText()};
        ImageView[] feedbackImages = {firstNameFeedbackImage, lastNameFeedbackImage, phoneFeedbackImage, addressFeedbackImage, postalCodeFeedbackImage, postAddressFeedbackImage};
        Label[] feedbackLabels = {firstNameFeedbackLabel, lastNameFeedbackLabel, phoneFeedbackLabel, addressFeedbackLabel, postalCodeFeedbackLabel, postAddressFeedbackLabel};

        boolean returnBool = true;

        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i].equals("")) {
                feedbackImages[i].getStyleClass().add(""); //TODO gör till tom
                feedbackLabels[i].setText("");
                returnBool = false;
                //return false;
            } else {

                switch (i) {

                    default:
                        feedbackImages[i].getStyleClass().add(""); //TODO gör till ok
                        feedbackLabels[i].setText("");
                        break;

                    case 2: // Phone number
                        try {
                            long number = Long.parseLong(inputs[i].replace(' ', '0').replace('+', '0').replace('-', '0'));
                            feedbackImages[i].getStyleClass().add(""); //TODO gör till ok
                            feedbackLabels[i].setText("");
                        } catch (NumberFormatException e) {
                            feedbackImages[i].getStyleClass().add(""); //TODO gör till inte ok
                            feedbackLabels[i].setText("Får endast innehålla siffror");
                            returnBool = false;
                            //return false;
                        }
                        break;

                    case 4: // Postal Code
                        try {
                            String[] numbers = inputs[i].split(" ");
                            String number = "";
                            for (String s : numbers) {
                                number += s;
                            }
                            if (number.length() != 5) {
                                throw new NumberFormatException();
                            }
                            int testIfNumber = Integer.parseInt(number);
                            feedbackImages[i].getStyleClass().add(""); //TODO gör till ok
                            feedbackLabels[i].setText("");
                        } catch (NumberFormatException e) {
                            feedbackImages[i].getStyleClass().add(""); //TODO gör till inte ok
                            feedbackLabels[i].setText("Måste innehålla 5 siffror");
                            returnBool = false;
                            //return false;
                        }
                        break;
                }
            }
        }
        return returnBool;
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

    //----------------Delivery & Payment----------------\\

    @FXML
    private ComboBox<String> dateComboBox;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private Button deliveryNextButton;

    @FXML
    private ToggleGroup payMethodRadioButtons;

    @FXML
    private RadioButton cardRadioButton;

    @FXML
    private AnchorPane cardPanel;

    @FXML
    private TextField cardOwnerArea;

    @FXML
    private TextField cardNumberArea1;

    @FXML
    private TextField cardNumberArea2;

    @FXML
    private TextField cardNumberArea3;

    @FXML
    private TextField cardNumberArea4;

    @FXML
    private TextField cardMonthArea;

    @FXML
    private TextField cardYearArea;

    @FXML
    private TextField cardCVCArea;

    @FXML
    private ImageView cardOwnerFeedbackImage;

    @FXML
    private ImageView cardNumberFeedbackImage;

    @FXML
    private ImageView cardDateFeedbackImage;

    @FXML
    private ImageView cardCVCFeedbackImage;

    @FXML
    void cardRadioButtonPressed(ActionEvent event) {
        cardPanel.setVisible(true);
        cardPanel.setDisable(false);

        if (cardOwnerArea.getText().equals("")) {
            cardOwnerArea.setText(main.iMat.getCustomer().getFirstName() + " " + main.iMat.getCustomer().getLastName());
        }

        updateTabEnabledStatus();
    }

    @FXML
    void notCardRadioButtonPressed(ActionEvent event) {
        cardPanel.setVisible(false);
        cardPanel.setDisable(true);

        updateTabEnabledStatus();
    }

    private boolean isDeliveryPaymentFinished() {
        int d = dateComboBox.getSelectionModel().getSelectedIndex();
        int t = timeComboBox.getSelectionModel().getSelectedIndex();
        if (!(d != -1 && d != 5 && t != -1)) {  // The 5 is there because of the error
            return false;
        }


        if (cardRadioButton.isSelected()) {
            String[] inputs = {cardOwnerArea.getText(), cardNumberArea1.getText(), cardNumberArea2.getText(), cardNumberArea3.getText(), cardNumberArea4.getText(), cardMonthArea.getText(), cardYearArea.getText(), cardCVCArea.getText()};

            boolean returnBool = true;

            for (int i = 0; i < inputs.length; i++) {
                if (inputs[i].equals("")) {
                    //return false;
                    returnBool = false;
                    switch (i) {
                        case 0:
                            cardOwnerFeedbackImage.getStyleClass().removeAll();
                            break;
                        case 4:
                            cardNumberFeedbackImage.getStyleClass().removeAll();
                            break;
                        case 6:
                            if (!inputs[i - 1].equals("")) {
                                cardDateFeedbackImage.getStyleClass().removeAll();
                            }
                            break;
                        case 7:
                            break;
                    }
                } else {

                    switch (i) {
                        case 4: // Card Number
                            char[][] cardNumbers = {inputs[1].toCharArray(), inputs[2].toCharArray(), inputs[3].toCharArray(), inputs[4].toCharArray()};

                            boolean allOk = true;
                            for (char[] cA : cardNumbers) {

                                if (cA.length != 4) {
                                    allOk = false;
                                    returnBool = false;
                                } else {

                                    for (char c : cA) {
                                        if (Character.getNumericValue(c) == -1) {
                                            returnBool = false;
                                            allOk = false;
                                            break;
                                            //return false;
                                        }
                                    }
                                }

                            }
                            if (allOk) {
                                cardNumberFeedbackImage.getStyleClass().removeAll();
                                cardNumberFeedbackImage.getStyleClass().add(""); //TODO ok
                            }
                            else {
                                cardNumberFeedbackImage.getStyleClass().removeAll();
                                cardNumberFeedbackImage.getStyleClass().add(""); //TODO inte ok
                            }


                            break;

                        case 6: // Expiration Year and Month
                            try {
                                int a = Integer.parseInt(inputs[i - 1]);
                                if (a > 12 || a < 1) {
                                    //return false;
                                    throw new NumberFormatException();
                                }
                                int b = Integer.parseInt(inputs[i]);
                                if (b > 99 || b < 0) {
                                    //return false;
                                    throw new NumberFormatException();
                                }
                                cardDateFeedbackImage.getStyleClass().removeAll();
                                cardDateFeedbackImage.getStyleClass().add(""); //TODO ok
                            } catch (NumberFormatException n) {
                                returnBool = false;
                                cardDateFeedbackImage.getStyleClass().removeAll();
                                cardDateFeedbackImage.getStyleClass().add(""); //TODO inte ok
                                //return false;
                            }
                            break;

                        case 7: // Card CVC
                            boolean isOk = true;
                            if (inputs[i].length() != 3) {
                                returnBool = false;
                                isOk = false;

                            } else {
                                for (char c : inputs[i].toCharArray()) {
                                    if (Character.getNumericValue(c) == -1) {
                                        //return false;
                                        returnBool = false;
                                        isOk = false;
                                    }
                                }
                            }
                            if (isOk){
                                cardCVCFeedbackImage.getStyleClass().removeAll();
                                cardCVCFeedbackImage.getStyleClass().add(""); // TODO ok
                            }
                            else {
                                cardCVCFeedbackImage.getStyleClass().removeAll();
                                cardCVCFeedbackImage.getStyleClass().add(""); // TODO inte ok
                            }
                            break;
                    }
                }
            }

            if (!returnBool) {
                return false;
            }

        }

        for (Toggle to : payMethodRadioButtons.getToggles()) {

            if (to.isSelected()) {
                return true;
            }
        }

        return false;
    }

    //----------------Confirmation----------------\\

    @FXML
    private Button confirmationButton;

    @FXML
    private Label deliveryConfirmationLabel;

    @FXML
    private Label nameConfirmationLabel;

    @FXML
    private Label addressConfirmationLabel;

    @FXML
    private Label postalCodeConfirmationLabel;

    @FXML
    private Label postAdressConfirmationLabel;

    @FXML
    private Label paymentConfirmationLabel;

    private void updateConfirmationLabels() {
        Customer c = main.iMat.getCustomer();

        deliveryConfirmationLabel.setText(timeComboBox.getSelectionModel().getSelectedItem() + " " + dateComboBox.getSelectionModel().getSelectedItem());
        nameConfirmationLabel.setText(c.getFirstName() + " " + c.getLastName());
        addressConfirmationLabel.setText(c.getAddress());
        postalCodeConfirmationLabel.setText(c.getPostCode());
        postAdressConfirmationLabel.setText(c.getPostAddress());

        String paymentWords = "";
        for (int i = 0; i < payMethodRadioButtons.getToggles().size(); i++) {

            if (payMethodRadioButtons.getToggles().get(i).isSelected()) {
                switch (i) {
                    case 0:
                        paymentWords = "vid leverans";
                        break;
                    case 1:
                        paymentWords = "med faktura";
                        break;
                    case 2:
                        paymentWords = "via faktura";
                        break;
                }
            }
        }

        paymentConfirmationLabel.setText(paymentWords);


    }

    @FXML
    private void confirmPurchase(ActionEvent event){
        main.iMat.placeOrder(true);
        main.showConfirmationView();
        main.updateConfirmationViewText(timeComboBox.getSelectionModel().getSelectedItem(), dateComboBox.getSelectionModel().getSelectedItem());

        main.pageHistory().addLink(Link.CONFIRMEDVIEW);
    }

}
