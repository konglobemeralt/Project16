package iMat.controller.BackButtonHandler;

import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.util.List;

/**
 * Created by Kraft on 2017-03-07.
 */
public class SavedPage {


    private Link link;

    private List<Product> productList;

    public SavedPage(Link link) {
        this.link = link;
    }

    public SavedPage(Link link, List<Product> productList) {
        this.link = link;
        this.productList = productList;
    }

    public Link getLink() {
        return link;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
