package Hospital_DBMS;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import helpers.Info;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {

    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private ImageView imageView;
    @FXML
    private Label error;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        error.setVisible(false);
        Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
        clip.setArcHeight(20);
        clip.setArcHeight(20);
        imageView.setClip(clip);
    }

    @FXML
    private void loginAction(ActionEvent event) throws IOException {
        Info.username = txtUsername.getText();
        Info.password = txtPassword.getText();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.exit(1);
        }

        try {
            Info.connection = DriverManager.getConnection("jdbc:oracle:thin:@50anhoi.ddns.net:1521:orcl", Info.username, Info.password);
        } catch (SQLException e) {
            error.setVisible(true);
        }

        if (Info.connection != null) {
            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
                JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
                decorator.setCustomMaximize(false);
                decorator.setBorder(Border.EMPTY);
                decorator.setTitle("Hospital DBMS");

                Scene scene = new Scene(decorator);
                stage.setHeight(750);
                stage.setWidth(1300);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                //Hide login window
                btnLogin.getScene().getWindow().hide();
            } catch (IOException e) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

}
