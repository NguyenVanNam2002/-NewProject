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
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Admin
 */
public class Client_passoword {
    private ProjectSignUpDAP psud = new ProjectSignUpDAOImpl();
    private ProjectSignUp psu;
    @FXML
    private Text user;

    @FXML
    private JFXButton menu;
    
    @FXML
    private JFXButton changepassword;

    @FXML
    private JFXPasswordField newpassword;

    @FXML
    private Text erros;
    
     @FXML
    private JFXPasswordField oldPassword;

    @FXML
    private Text erros1;

    @FXML
    private JFXPasswordField newpassword1;

    @FXML
    private Text erros11;

    @FXML
    private JFXButton backsetting;
    @FXML
    private Pane show;

    @FXML
    private ImageView succes;

    @FXML
    private Text texterrors;
    
    
    @FXML
    void back(MouseEvent event) throws IOException {
        ProjectSignUp p = extractPasswordFromFields();
        Nagatice.getInstance().goToChoose(p);
    }
    @FXML
    void ChangePassword(MouseEvent event) throws IOException {
        ProjectSignUp p = extractPasswordFromFields();
        Nagatice.getInstance().goToChangePassword(p);
    }

    @FXML
    void Infomation(MouseEvent event) throws IOException {
        ProjectSignUp p = extractPasswordFromFields();
        Nagatice.getInstance().goToInformationClient(p);
    }
    @FXML
    void btnChangePassword(ActionEvent event) {
        try {
            if(Validate()){
                ProjectSignUp password = extractPasswordFromFields();
                boolean passwords = psud.Login(password);
                if(passwords){
                    if(newpassword1.getText().contentEquals(newpassword.getText())){
                        ProjectSignUp change = extractChangePasswordFromFields();
                        change.setAccount(this.psu.getAccount());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("B???n c?? ch???c ?????i m???t kh???u ?");
                        alert.setTitle("L??u ?? ");
                        Optional<ButtonType> confirmationResponse
                                = alert.showAndWait();
                        if (confirmationResponse.get() == ButtonType.OK) {
                            boolean reslut = psud.update(change);
                            if(reslut){
                                show.setVisible(true);
                                succes.setVisible(true);
                                texterrors.setVisible(true);
                                PauseTransition pt = new PauseTransition();
                                pt.setDuration(Duration.seconds(2));
                                pt.setOnFinished(e -> {
                                       show.setVisible(false);
                                       succes.setVisible(false);
                                       texterrors.setVisible(false);
                                    try {
                                        Nagatice.getInstance().goToIndex();
                                    } catch (IOException ex) {
                                        Logger.getLogger(Client_passoword.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                });

                                pt.play();  
                            }else{
                                
                            }
                        }
                    }else{
                        erros11.setText("Nh???p l???i m???t kh???u m???i ch??a ch??nh x??c !");
                    }
                }else{
                    erros1.setText("M???t kh???u c?? kh??ng ch??nh x??c !");
                }
            }
            
        } catch (Exception e) {
        }
    }

    public void initialize(ProjectSignUp p){
        this.psu = p;
        if(this.psu != null){
            user.setText(p.getAccount());
        }
    }
     public void initialize() {
        
        show.setVisible(false);
        succes.setVisible(false);
        texterrors.setVisible(false);
   
    }
    private ProjectSignUp extractPasswordFromFields() {
        ProjectSignUp sign = new ProjectSignUp(); 
        sign.setAccount(user.getText());
        sign.setPassword(md5(oldPassword.getText()));
        return sign;
    }
    private ProjectSignUp extractChangePasswordFromFields() {
        ProjectSignUp sign = new ProjectSignUp(); 
        sign.setAccount(user.getText());
        sign.setPassword(md5(newpassword.getText()));
        return sign;
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
    private boolean Validate(){
      
        if(oldPassword.getText().isEmpty() ){
            erros1.setText("M???t kh???u c?? tr???ng");
            return false; 
        }else{
            erros1.setText("");
        }
        if(oldPassword.getText().length() < 8 ){
            erros1.setText("M???t kh???u ph???i d??i h??n 8 k?? t???");
            return false; 
        }else{
            erros1.setText("");
        }
        if(oldPassword.getText().length() > 16 ){
            erros1.setText("M???t kh???u ph???i ng???n h??n 16 k?? t???");
            return false; 
        }else{
           erros1.setText("");
        }
        
        
        if(newpassword.getText().isEmpty() ){
            erros.setText("M???t kh???u m???i tr???ng");
            return false; 
        }else{
            erros.setText("");
        }
        if(newpassword.getText().length() < 8 ){
            erros.setText("M???t kh???u m???i ph???i d??i h??n  8 k?? t???");
            return false; 
        }else{
            erros.setText("");
        }
        if(newpassword.getText().length() > 16 ){
            erros.setText("M???t kh???u m???i ph???i ng???n h??n 16 k?? t???");
            return false; 
        }else{
           erros.setText("");
        }
        
        return true;
    }
}
