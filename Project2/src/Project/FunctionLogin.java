/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;


import Project.Data.ProjectSignUp;
import Project.Data.ProjectSignUpDAOImpl;
import Project.Data.ProjectSignUpDAP;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Admin
 */
public class FunctionLogin {
    @FXML
    private AnchorPane a;
    private ProjectSignUpDAP psd = new ProjectSignUpDAOImpl();
    
    @FXML
    private JFXTextField account;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton btnlogin;

    @FXML
    private JFXButton btnsingup;
    
    @FXML
    private Text errors;

    @FXML
    private Text error1;
    @FXML
    private ComboBox<String> Sign;
    @FXML
    private ImageView loading;
    @FXML
    void btnLogin(ActionEvent event) throws IOException {
        try{
            if(Validate()){
                ProjectSignUp login = extractSignUpFromFields();
                boolean requierd = psd.Login(login);
                boolean Requierd = psd.LoginAdmin(login);
                if(requierd){
                    if(Sign.getSelectionModel().getSelectedItem().toString() == "Admin"){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Bạn không đủ quyền hạn !");
                        alert.show();
                        
                    }else if(Sign.getSelectionModel().getSelectedItem().toString() == "Client"){
                        loading.setVisible(true);
                        PauseTransition pt = new PauseTransition();
                        pt.setDuration(Duration.seconds(2));
                        pt.setOnFinished(e -> {
                             try {
                                 Nagatice.getInstance().goToWellcomeScene(login);
                             } catch (IOException ex) {
                                 Logger.getLogger(FunctionLogin.class.getName()).log(Level.SEVERE, null, ex);
                             } catch (InterruptedException ex) {
                                Logger.getLogger(FunctionLogin.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

                        pt.play();  
                    }
                }else if(Requierd){
                    if(Sign.getSelectionModel().getSelectedItem().toString() == "Admin"){
                        Nagatice.getInstance().goToIndexProduct();
                    }else if(
                        Sign.getSelectionModel().getSelectedItem().toString() == "Client"){
                        Nagatice.getInstance().goToWellcomeScene(login);
                    }  
                } else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Mật khẩu hoặc tài khoản không chính xác !");
                    alert.show();
                }
            }
        }catch(Exception  e){
            System.err.println(e.getMessage());
        }
    }
  
    @FXML
    void btnSingup(ActionEvent event) throws IOException {
       Nagatice.getInstance().goSingup();
    
    }
    private boolean Validate(){
        if(account.getText().isEmpty() ){
            errors.setText("Account not empty");
            return false; 
        }else{
            errors.setText("");
        }
        if(account.getText().length() < 10 ){
            errors.setText("Account more than 10");
            return false; 
        }else{
            errors.setText("");
        }
        if(account.getText().length() > 40 ){
            errors.setText("Account less than 40");
            return false; 
        }else{
            errors.setText("");
        }
        if(!account.getText().contains("@gmail.com") ){
            errors.setText("Account have a @gmail.com");
            return false; 
        }else{
            errors.setText("");
        }
        if(account.getText().contains(" ") ){
            errors.setText("Account not have space");
            return false; 
        }else{
            errors.setText("");
        }
        
        if(password.getText().isEmpty() ){
            error1.setText("Password not empty");
            return false; 
        }else{
            error1.setText("");
        }
        if(password.getText().length() < 8 ){
            error1.setText("Password more than 8");
            return false; 
        }else{
            error1.setText("");
        }
        if(password.getText().length() > 16 ){
            error1.setText("Password less than 16");
            return false; 
        }else{
            error1.setText("");
        }
        if(password.getText().length() > 16 ){
            error1.setText("Password less than 16");
            return false; 
        }else{
            error1.setText("");
        }
    
    
        return true;
    }
    
    private ProjectSignUp extractSignUpFromFields() {
        ProjectSignUp sign = new ProjectSignUp(); 
        sign.setAccount(account.getText());
        String b =password.getText();
        sign.setPassword(md5(b));
       
        return sign;
    }
    public void initialize() {
        System.out.println("#Login initialized!");
        Sign.getItems().add("Client");
        Sign.getItems().add("Admin");
        
        account.setText("namoizoioi@gmail.com");
        password.setText("0335795257");
        loading.setVisible(false);
        
    }
    private String md5(String a){
        try {
            MessageDigest digs =  MessageDigest.getInstance("MD5");
            byte[] messageDigest = digs.digest(a.getBytes());
            BigInteger number = new BigInteger(1,messageDigest);
            String hashtext = number.toString(16);
            while(hashtext.length() <32 ){
                hashtext = "0"+ hashtext;
            }
            return hashtext;
        } catch (Exception e) {
        }
        return a;
    }
}
