/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.Data;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Admin
 */
public class Order {
    private ObjectProperty<Integer> id ;
    private StringProperty account;
    private StringProperty productID;
    private ObjectProperty<Integer> quantity;
    private ObjectProperty<Integer> total_price;
    private StringProperty date;
    
    public Order(){
        id = new SimpleObjectProperty<>(null);
        account = new SimpleStringProperty();
        productID = new SimpleStringProperty();
        quantity = new SimpleObjectProperty<>();
        total_price = new SimpleObjectProperty<>();
        date = new SimpleStringProperty();
    }
    
    public Integer getID(){
        return id.get();
    }
    
    public String getAccount(){
        return  account.get();
    }
    
    public String getProductID(){
        return  productID.get();
    }
    
    public Integer getQuantity(){
        return quantity.get();
    }
    
    public Integer getTotalPrice(){
        return total_price.get();
    }
    
    public  String getDate(){
        return date.get();
    }
    
    public void setID(int id){
        this.id.set(id);
    }
    public  void setAccount(String account){
        this.account.set(account);
    }
    public void setProductID(String productID){
        this.productID.set(productID);
    }
    public void setQuantity(int quantity){
        this.quantity.set(quantity);
    }
    public void setTotalPrice(int totalPrice){
        this.total_price.set(totalPrice);
    }
    public void setDate(String date){
        this.date.set(date);
    }
    
    public ObjectProperty<Integer> getIDProperty(){
       return this.id;
    }
    
    public StringProperty getProductIDProperty(){
       return this.productID;
    }
    
    public ObjectProperty<Integer> getQuantityProperty(){
       return this.quantity;
    }
    
    public ObjectProperty<Integer> getTotalPriceProperty(){
       return this.total_price;
    }
    
    public StringProperty getAccountProperty(){
       return this.account;
    }
    public StringProperty getDateProperty(){
       return this.date;
    }
 
}
