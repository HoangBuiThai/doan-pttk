package Controller;

import DbConnection.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    @FXML
    private TextField usernameTextfield;

    @FXML
    private TextField passwordTextfield;

    @FXML
    private Label errorTextfield;

    private ConnectionClass DbConnection;
    private PreparedStatement preparedStatement;


    public void performLoginAction(ActionEvent event) throws  IOException{
        DbConnection = new ConnectionClass();
        try{
            Connection conn = DbConnection.getConnectionAdmin();
            String sql_query = "Select * from nhanvien where username='" + usernameTextfield.getText() + "' and password='" + passwordTextfield.getText() + "'";
            ResultSet rs = conn.createStatement().executeQuery(sql_query);
            if(rs.next()){
                String job = String.valueOf(rs.getString(3));
                String name = rs.getString(4);
                String manv = rs.getString(1);
                String maquan=rs.getString(2);
                System.out.println(job);
                if(job.equals("TT")){
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../ui/employeeScene.fxml"));
                    Parent employeeScene = loader.load();
                    Scene scene = new Scene(employeeScene);
                    employeeController eController = loader.getController();
                    eController.setDetail(name,manv);
                    stage.setTitle("Quản lý khách hàng");
                    stage.setScene(scene);
                }else if(job.equals("QL")){
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../ui/adminScene.fxml"));
                    Parent adminScene = loader.load();
                    Scene scene = new Scene(adminScene);
                    stage.setTitle("Admin");
                    stage.setScene(scene);
                }else if(job.equals("QK")){
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../ui/managerScene.fxml"));
                    Parent managerScene = loader.load();
                    Scene scene = new Scene(managerScene);
                    stage.setTitle("Quản lý Kho");
                    stage.setScene(scene);
                }else if(job.equals("GH")&&!maquan.isEmpty()){
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../ui/shipperScene.fxml"));
                    Parent shipperScene = loader.load();
                    Scene scene = new Scene(shipperScene);
                    shipperController sController = loader.getController();
                    sController.setDetail(manv,name,maquan);
                    stage.setTitle("Shipper");
                    stage.setScene(scene);
                }
            }else {
                errorTextfield.setText("Sai");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
