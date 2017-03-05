package iMat.model;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

/**
 * Created by Kraft on 2017-03-04.
 */
public class ProductSearch {

    public static List<Product> search(String search){
        IMatDataHandler iMat = IMatDataHandler.getInstance();
        List<Product> sortiment = iMat.getProducts();
        List<Product> result = new ArrayList<>();

        search = search.toLowerCase();
        /*String first = search.substring(0,1);
        first.toUpperCase();
        search = first + search.substring(1);*/

        for (Product p: sortiment) {
            String name = p.getName().toLowerCase();
            if (name.length() > name.replaceAll(search, "").length()){
                result.add(p);
            }
        }

        return result;
    }

    public static List<Product> getCategory(String category) {
        IMatDataHandler iMat = IMatDataHandler.getInstance();
        List<Product> results = new ArrayList<>();
        List<Product> sortiment = iMat.getProducts();

        switch (category) {
            case "Frukt":
                results = addLists(addLists(iMat.getProducts(ProductCategory.BERRY),iMat.getProducts(ProductCategory.CITRUS_FRUIT), iMat.getProducts(ProductCategory.MELONS), iMat.getProducts(ProductCategory.EXOTIC_FRUIT)), iMat.getProducts(ProductCategory.FRUIT) );
                break;

            case "Grönt":
                List<Product> potatoes = new ArrayList<>();
                for (int i = 115; i < 119; i++){
                    potatoes.add(sortiment.get(i));
                }
                results = addLists(iMat.getProducts(ProductCategory.VEGETABLE_FRUIT), iMat.getProducts(ProductCategory.CABBAGE),potatoes, addLists(iMat.getProducts(ProductCategory.HERB), iMat.getProducts(ProductCategory.POD)));
                break;

            case "Kött och fisk":
                results = addLists(iMat.getProducts(ProductCategory.MEAT), iMat.getProducts(ProductCategory.FISH));
                break;

            case "Skafferi":
                List<Product> rice = new ArrayList<>();
                for (int i = 112; i < 115; i++){
                    rice.add(sortiment.get(i));
                }
                rice.add(sortiment.get(119));
                results = addLists(iMat.getProducts(ProductCategory.FLOUR_SUGAR_SALT),iMat.getProducts(ProductCategory.PASTA),rice,addLists(iMat.getProducts(ProductCategory.NUTS_AND_SEEDS),iMat.getProducts(ProductCategory.HOT_DRINKS)));
                break;

            case "Bageri":
                results = iMat.getProducts(ProductCategory.BREAD);
                break;

            case "Mejeri":
                results = iMat.getProducts(ProductCategory.DAIRIES);
                break;

            case "Drycker":
                results = addLists(iMat.getProducts(ProductCategory.COLD_DRINKS), iMat.getProducts(ProductCategory.HOT_DRINKS));
                break;

            case "Sötsaker":
                results = iMat.getProducts(ProductCategory.SWEET);
                break;

        }

        return results;
    }

    private static List<Product> addLists(List<Product> a, List<Product> b) {
        for (Product p : b) {
            a.add(p);
        }
        return a;
    }

    private static List<Product> addLists(List<Product> a, List<Product> b, List<Product> c) {
        return addLists(addLists(a, b), c);
    }

    private static List<Product> addLists(List<Product> a, List<Product> b, List<Product> c, List<Product> d) {

        return addLists(addLists(a,b),addLists(c,d));
    }


}
