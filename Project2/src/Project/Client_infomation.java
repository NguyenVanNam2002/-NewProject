/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import Project.Data.ProjectSignUp;
import Project.Data.ProjectSignUpDAOImpl;
import Project.Data.ProjectSignUpDAP;
import Project.DbProject.DbProject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 *
 * @author Admin
 */
public class Client_infomation {
    private ProjectSignUpDAP psud = new ProjectSignUpDAOImpl();
    private ProjectSignUp psu;
    @FXML
    private AnchorPane abv;
    @FXML
    private Text user;

    @FXML
    private JFXButton menu;

    @FXML
    private JFXButton updateinformation;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField phone;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXButton backsetting;
    @FXML
    private Text errors;

    @FXML
    private Text errors1;

    @FXML
    private Text errors11;
    @FXML
    private Text succes;

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
    void btnupdateInformation(ActionEvent event) {
        try {
            if(Validate()){
                ProjectSignUp update = extractChangeInformationFromFields();
                update.setAccount(this.psu.getAccount());
                boolean suc = psud.information(update);
                if(suc){
                    succes.setText("update succesfully");
                }
            }
        } catch (Exception e) {
        }
    }
    
    public void initialize(ProjectSignUp p){
        this.psu = p;
        if(this.psu != null){
            user.setText(p.getAccount());
            infomaitonselect(p.getAccount());
        }
    }
    public void initialize(){
       
    }
    private ProjectSignUp extractPasswordFromFields() {
        ProjectSignUp sign = new ProjectSignUp(); 
        sign.setAccount(user.getText());
        return sign;
    }
    private ProjectSignUp extractChangeInformationFromFields() {
        ProjectSignUp sign = new ProjectSignUp(); 
        sign.setAccount(user.getText());
        sign.setName(name.getText());
        sign.setPhone(phone.getText());
        sign.setAddress(address.getText());
        return sign;
    }
    private boolean Validate(){
        if(name.getText().isEmpty()){
            errors.setText("Name not empty");
            return false;
        }else{
            errors.setText("");
        }
        if(name.getText().length() < 5){
            errors.setText("Name more than 5");
            return false;
        }else{
            errors.setText("");
        }
        if(name.getText().length() > 255){
            errors.setText("Name less than 255");
            return false;
        }else{
            errors.setText("");
        }
        
        
        if(phone.getText().isEmpty() ){
            errors1.setText("Your Phone not empty");
            return false; 
        }else{
           try {
                Integer.parseInt(phone.getText());
                errors1.setText("");
            } catch (NumberFormatException e) {
                errors1.setText("Price is not character !");
                return false;
            }
        }
        if(phone.getText().length() != 10 ){
            errors1.setText("Your Phone Number not equals 10");
            return false; 
        }else{
            errors1.setText("");
        }
        if(phone.getText().substring(0, 2).contentEquals("09") || phone.getText().substring(0, 2).contentEquals("03")
         ||  phone.getText().substring(0, 2).contentEquals("08") || phone.getText().substring(0, 2).contentEquals("07")
         || phone.getText().substring(0, 2).contentEquals("05") 
         ){
            errors1.setText("");
        }else{
            errors1.setText("phone numbers starting with 09 , 08 , 07 , 05 and 03");
            return false; 
            
        }
        
        if(address.getText().isEmpty()){
            errors11.setText("address not empty");
            return false; 
        }else{
            errors11.setText("");
        }
        if(address.getText().length() < 10){
            errors11.setText("Unknown address");
            return false; 
        }else{
            errors11.setText("");
        }
        if(address.getText().contains("Hà Nội") || address.getText().contains("hà nội")){
             errors11.setText("");
        }else{
            errors11.setText("You are too far from Hanoi.");
            return false; 
        }
        return true;
    }
   public void infomaitonselect(String user){
        String sql = "SELECT * FROM account_client WHERE accounts = ? ";
       
        try(Connection con = DbProject.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ){
             stmt.setString(1, user);
             ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                name.setText(rs.getString("nick_name"));
                phone.setText(rs.getString("phone"));
                address.setText(rs.getString("address"));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
        }
    }
}
