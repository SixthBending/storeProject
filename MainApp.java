import jdk.jfr.internal.tool.Main;
import sun.rmi.runtime.Log;

import javax.swing.*;

public class MainApp {

    private User client = null;
    private RemoteDataAdapter rda;
    private LoginScreen loginScreen;
    private LoginController loginController;
    private ProductView productView;

    private static MainApp instance;

    public void setClient(User client) {
        this.client = client;
    }
    public void setRda(RemoteDataAdapter remoteDataAdapter) {
        this.rda = remoteDataAdapter;
    }

    public LoginScreen getLoginScreen() {
        return loginScreen;
    }

    public LoginController getLoginController(){
        return this.loginController;
    }
    public ProductView getProductView() {
        return this.productView;
    }


    public static void main(String[] args) {
        /* Test Data Access
        DataAccess dao = StoreManager.getInstance().getDataAccess();

        dao.connect();

        ProductModel prod = dao.loadProduct(1); // Apple;
        if (prod != null)
            System.out.println("Product with ID = " + prod.productID + " name = " + prod.name + " price = " + prod.price + " quantity = " + prod.quantity);

        prod.productID = 100;
        prod.name = "Samsung TV";
        prod.price = 399.99;
        prod.quantity = 1000;

        dao.saveProduct(prod);

        prod = dao.loadProduct(100); // Samsung!!!
        if (prod != null)
            System.out.println("Product with ID = " + prod.productID + " name = " + prod.name + " price = " + prod.price + " quantity = " + prod.quantity);

        */
        MainApp.getInstance().getLoginScreen().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainApp.getInstance().getLoginScreen().setVisible(true); // Show the LoginScreen!

        DataAccess dao = StoreManager.getInstance().getDataAccess();
        dao.connect();
        ProductModel prod = dao.loadProduct(1); //Apple
        if(prod != null)
            System.out.println("Product with ID = " + prod.productID + " name = " + prod.name + " price = " + prod.price + " quantity = " + prod.quantity);


    }

    public static MainApp getInstance() {
        return instance;
    }

    public User getCurrentUser() {
        return client;
    }
}
