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
            error1.setText("Tên  trống");
            return false;
        }else{
            error1.setText("");
        }
        if(name.getText().length() < 5){
            error1.setText("Tên phải nhiều hơn 5 kí tự");
            return false;
        }else{
            error1.setText("");
        }
        if(name.getText().length() > 100){
            error1.setText("Tên không thể quá 100 kí tự");
            return false;
        }else{
            error1.setText("");
        }
        
        if(account.getText().isEmpty() ){
            error2.setText("Tài khoản  trống");
            return false; 
        }else{
            error2.setText("");
        }
        if(account.getText().length() < 10 ){
            error2.setText("Tài khoản phải dài hơn 10 kí tự");
            return false; 
        }else{
            error2.setText("");
        }
        if(account.getText().length() > 40 ){
            error2.setText("Tài khoản ngắn hơn 40 kí tự");
            return false; 
        }else{
            error2.setText("");
        }
        if(!account.getText().contains("@gmail.com") ){
            error2.setText("Tài khoản thiếu @gmail.com ở sau cùng");
            return false; 
        }else{
            error2.setText("");
        }
        if(account.getText().contains(" ") ){
            error2.setText("Tài khoản không được có dáu cách");
            return false; 
        }else{
            error2.setText("");
        }
        
        if(password.getText().isEmpty() ){
            error3.setText("Mật khẩu  trống");
            return false; 
        }else{
            error3.setText("");
        }
        if(password.getText().length() < 8 ){
            error3.setText("Mật khẩu phải từ  8 kí tự đổ lên");
            return false; 
        }else{
            error3.setText("");
        }
        if(password.getText().length() > 16 ){
            error3.setText("Mật khẩu ngắn hơn 16 kí tự");
            return false; 
        }else{
            error3.setText("");
        }
        
        if(phone.getText().isEmpty() ){
            error4.setText("Số điện thoại trống");
            return false; 
        }else{
            try {
                Integer.parseInt(phone.getText());
                error4.setText("");
            } catch (NumberFormatException e) {
                error4.setText("Vui lòng nhập số điện thoại , không phải kí tự !");
                return false;
            }
        }
        if(phone.getText().length() != 10 ){
            error4.setText("Số điện thoại của bạn không hợp lệ , phải có 10 số");
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
            error4.setText("Số điệnt thoại bắt đầu bằng 09 , 08 , 07 , 05 và 03");
            return false; 
            
        }
        
        if(address.getText().isEmpty()){
            error5.setText("Địa chỉ trống");
            return false; 
        }else{
            error5.setText("");
        }
        if(address.getText().length() < 10){
            error5.setText("Địa chỉ của bạn chưa rõ ràng");
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
