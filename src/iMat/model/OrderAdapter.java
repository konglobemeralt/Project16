package iMat.model;

import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

/**
 * Created by Kraft on 2017-03-08.
 */
public class OrderAdapter extends Order {
    private String deliveryDate;
    private String deliveryTime;

    public OrderAdapter(String deliveryDate, String deliveryTime) {
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public double totalPrice(){
        double sum = 0;
        for (ShoppingItem s: getItems()) {
            sum += s.getProduct().getPrice()*s.getAmount();
        }
        return sum;
    }
}
