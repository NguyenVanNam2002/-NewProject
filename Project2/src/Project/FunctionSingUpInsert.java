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
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Admin
 */
public class FunctionSingUpInsert {
    private ProjectSignUpDAP psd = new ProjectSignUpDAOImpl();
    
    private ProjectSignUp signs = null;
    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField account;

    @FXML
    private JFXTextField phone;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton Back;

    @FXML
    private JFXButton SignUp;
    
    @FXML
    private Text ttt;
    
    @FXML
    private Text error1;

    @FXML
    private Text error2;

    @FXML
    private Text error3;

    @FXML
    private Text error4;

    @FXML
    private Text error5;
    
    @FXML
    private Pane show;

    @FXML
    private ImageView succes;

    @FXML
    private Text texterrors;
    @FXML
    void btnBack(ActionEvent event) throws IOException {
        Nagatice.getInstance().goToIndex();
    }

    @FXML
    void btnSignUp(ActionEvent event) {
        try{
            if(Validate()){
                if(signs == null){
                    ProjectSignUp insert = extractSignUpFromFields();
                    insert = psd.insert(insert);
                    
                    show.setVisible(true);
                    succes.setVisible(true);
                    texterrors.setVisible(true);
                    PauseTransition pt = new PauseTransition();
                    pt.setDuration(Duration.seconds(2));
                    pt.setOnFinished(e -> {
                           show.setVisible(false);
                           succes.setVisible(false);
                           texterrors.setVisible(false);
                    });

                    pt.play();  
                }
            }
        }catch(Exception  e){
            System.err.println(e.getMessage());
            
          
        }
    }
    public void initialize() {
        System.out.println("#Insert Customer initialized!");
        show.setVisible(false);
        succes.setVisible(false);
        texterrors.setVisible(false);
   
    }
      
    private ProjectSignUp extractSignUpFromFields() {
        ProjectSignUp sign = new ProjectSignUp();
        
        sign.setAccount(account.getText());
        sign.setPassword(md5(password.getText()));
        sign.setName(name.getText());
        sign.setPhone(phone.getText());
        sign.setAddress(address.getText());
        
        return sign;
    }
    
    private boolean Validate(){
        if(name.getText().isEmpty()){
            error1.setText("T??n  tr???ng");
            return false;
        }else{
            error1.setText("");
        }
        if(name.getText().length() < 5){
            error1.setText("T??n ph???i nhi???u h??n 5 k?? t???");
            return false;
        }else{
            error1.setText("");
        }
        if(name.getText().length() > 100){
            error1.setText("T??n kh??ng th??? qu?? 100 k?? t???");
            return false;
        }else{
            error1.setText("");
        }
        
        if(account.getText().isEmpty() ){
            error2.setText("T??i kho???n  tr???ng");
            return false; 
        }else{
            error2.setText("");
        }
        if(account.getText().length() < 10 ){
            error2.setText("T??i kho???n ph???i d??i h??n 10 k?? t???");
            return false; 
        }else{
            error2.setText("");
        }
        if(account.getText().length() > 40 ){
            error2.setText("T??i kho???n ng???n h??n 40 k?? t???");
            return false; 
        }else{
            error2.setText("");
        }
        if(!account.getText().contains("@gmail.com") ){
            error2.setText("T??i kho???n thi???u @gmail.com ??? sau c??ng");
            return false; 
        }else{
            error2.setText("");
        }
        if(account.getText().contains(" ") ){
            error2.setText("T??i kho???n kh??ng ???????c c?? d??u c??ch");
            return false; 
        }else{
            error2.setText("");
        }
        
        if(password.getText().isEmpty() ){
            error3.setText("M???t kh???u  tr???ng");
            return false; 
        }else{
            error3.setText("");
        }
        if(password.getText().length() < 8 ){
            error3.setText("M???t kh???u ph???i t???  8 k?? t??? ????? l??n");
            return false; 
        }else{
            error3.setText("");
        }
        if(password.getText().length() > 16 ){
            error3.setText("M???t kh???u ng???n h??n 16 k?? t???");
            return false; 
        }else{
            error3.setText("");
        }
        
        if(phone.getText().isEmpty() ){
            error4.setText("S??? ??i???n tho???i tr???ng");
            return false; 
        }else{
            try {
                Integer.parseInt(phone.getText());
                error4.setText("");
            } catch (NumberFormatException e) {
                error4.setText("Vui l??ng nh???p s??? ??i???n tho???i , kh??ng ph???i k?? t??? !");
                return false;
            }
        }
        if(phone.getText().length() != 10 ){
            error4.setText("S??? ??i???n tho???i c???a b???n kh??ng h???p l??? , ph???i c?? 10 s???");
            return false; 
        }else{
            error4.setText("");
        }
        if(phone.getText().substring(0, 2).contentEquals("09") || phone.getText().substring(0, 2).contentEquals("03")
         ||  phone.getText().substring(0, 2).contentEquals("08") || phone.getText().substring(0, 2).contentEquals("07")
         || phone.getText().substring(0, 2).contentEquals("05") 
         ){
            error4.setText("");
        }else{
            error4.setText("S??? ??i???nt tho???i b???t ?????u b???ng 09 , 08 , 07 , 05 v?? 03");
            return false; 
            
        }
        
        if(address.getText().isEmpty()){
            error5.setText("?????a ch??? tr???ng");
            return false; 
        }else{
            error5.setText("");
        }
        if(address.getText().length() < 10){
            error5.setText("?????a ch??? c???a b???n ch??a r?? r??ng");
            return false; 
        }else{
            error5.setText("");
        }
        
        return true;
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
