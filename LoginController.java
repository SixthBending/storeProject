import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.server.RemoteObjectInvocationHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController implements ActionListener {
    private LoginScreen loginScreen;
    private RemoteDataAdapter rda;


    public LoginController(LoginScreen loginScreen, RemoteDataAdapter dataAdapter) {
        this.loginScreen = loginScreen;
        this.rda = dataAdapter;
        this.loginScreen.getBtnLogin().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginScreen.getBtnLogin()) {
            String username = loginScreen.getTxtUserName().getText().trim();
            String password = loginScreen.getTxtPassword().getText().trim();

            System.out.println("Login with username = " + username + " and password = " + password);
            User user = RemoteDataAdapter.loadUser(username, password);

            if (user == null) {
                JOptionPane.showMessageDialog(null, "This user does not exist!");
            }
            else {
                MainApp.getInstance().setClient(user);
                this.loginScreen.setVisible(false);
                StoreManager.getInstance().getProductView().setVisible(true);
            }
        }
    }


}
