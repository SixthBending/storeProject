import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemoteDataAdapter implements DataAccess {
    Gson gson = new Gson();
    Socket s = null;
    DataInputStream dis = null;
    DataOutputStream dos = null;

    @Override
    public void connect() {
        try {
            s = new Socket("localhost", 5056);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveProduct(ProductModel product) {
        RequestModel inp = new RequestModel();
        inp.code = RequestModel.SAVE_PRODUCT_REQUEST;
        inp.body = gson.toJson(product);

        String jSonSave = gson.toJson(inp);
        try {
            dos.writeUTF(jSonSave);
            String s1 = dis.readUTF();
            ResponseModel resp = gson.fromJson(s1, ResponseModel.class);
            if (resp.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
            } else         // this is a JSON string for a product information
                if (resp.code == ResponseModel.DATA_NOT_FOUND) {
                    System.out.println("The Server could not find a product with that ID!");
                } else {
                    ProductModel model = gson.fromJson(resp.body, ProductModel.class);
                    System.out.println("The Server has saved the ProductModel object");
                    System.out.println("ProductID = " + model.productID);
                    System.out.println("Product name = " + model.name);
                }
        }  catch (IOException i) {
            System.out.println("Brother");
            i.printStackTrace();
        }
    }

    @Override
    public ProductModel loadProduct(int productID) {
        RequestModel req = new RequestModel();
        req.code = RequestModel.LOAD_PRODUCT_REQUEST;
        req.body = String.valueOf(productID);

        String json = gson.toJson(req);
        try {
            dos.writeUTF(json);

            String received = dis.readUTF();

            System.out.println("Server response:" + received);

            ResponseModel res = gson.fromJson(received, ResponseModel.class);

            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
                return null;
            }
            else         // this is a JSON string for a product information
                if (res.code == ResponseModel.DATA_NOT_FOUND) {
                    System.out.println("The Server could not find a product with that ID!");
                    return null;
                }
                else {
                    ProductModel model = gson.fromJson(res.body, ProductModel.class);
                    System.out.println("Receiving a ProductModel object");
                    System.out.println("ProductID = " + model.productID);
                    System.out.println("Product name = " + model.name);
                    return model; // found this product and return!!!
                }



        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public User loadUser(String username, String password) {
        RequestModel requestModel = new RequestModel();
        requestModel.code = RequestModel.USER_REQUEST;
        requestModel.body = username + " " + password;

        String jSonUser = gson.toJson(requestModel);
        try {
            dos.writeUTF(jSonUser);
            String rec = dis.readUTF();
            System.out.println("Server Response: " + rec);
            ResponseModel responseModel = gson.fromJson(rec, ResponseModel.class);
            if(responseModel.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
                return null;
            }
            else         // this is a JSON string for user info
                if (responseModel.code == ResponseModel.DATA_NOT_FOUND) {
                    System.out.println("The Server could not find a user with that ID!");
                    return null;
                }
                else {
                    User user = gson.fromJson(responseModel.body, User.class);
                    System.out.println(user.getUserName() + " successfully logged in!");
                    return user; // user logged in and return!!!
                }



        } catch (IOException i) {
            System.out.println("Failed");
            i.printStackTrace();
        }
        return null;
    }
}
