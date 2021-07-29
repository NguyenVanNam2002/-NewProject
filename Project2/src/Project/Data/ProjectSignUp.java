/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.Data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * @author Admin
 */
public class ProjectSignUp {
    private final StringProperty ten_tai_khoan;
    private final StringProperty mat_khau;
    private final StringProperty nick_name;
    private final StringProperty phone;
    private final StringProperty address;
    
    
    public ProjectSignUp(){
         ten_tai_khoan = new SimpleStringProperty();
         mat_khau = new SimpleStringProperty();
         nick_name = new SimpleStringProperty();
         phone = new SimpleStringProperty();
         address = new SimpleStringProperty(); 
    }
    
    public String getAccount(){
        return ten_tai_khoan.get(); 
    }
    
    public String getPassword(){
        return mat_khau.get();
    }
    public String getName(){
        return nick_name.get();
    }
    public String getPhone(){
        return phone.get();
    }
    public String getAddress(){
        return address.get();
    }
    
    public void setAccount(String account){
        this.ten_tai_khoan.set(account);
    }
    
    public void setPassword(String pass){
        this.mat_khau.set(pass);
    }
    public void setName(String name){
        this.nick_name.set(name);
    }
    
    public void setPhone(String phone){
        this.phone.set(phone);
    }
    public void setAddress(String address){
        this.address.set(address);
    }
    
    public StringProperty getAccountProperty(){
        return this.ten_tai_khoan;
    }
    
    public StringProperty getPasswordProperty(){
        return this.mat_khau;
    }
    
    public StringProperty getNameProperty(){
        return this.nick_name;
    }
    
    public StringProperty getPhoneProperty(){
        return this.phone;
    }
    
    public StringProperty getAddressProperty(){
        return this.address;
    }
    
}
