/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import Project.Data.Order;
import Project.Data.OrderDAO;
import Project.Data.OrderDAOImpl;
import Project.Data.Product;
import Project.DbProject.DbProject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 *
 * @author Admin
 */
public class OrderItem {
    private OrderDAO oder = new OrderDAOImpl();
    @FXML
    private ImageView image;

    @FXML
    private Text name;
    @FXML
    private Text id;
     @FXML
    private Spinner<Integer> spinner;
    @FXML
    private CheckBox check;
    @FXML
    private Text price;
    
    private OrderListener orderlis;
    private Product o;
    @FXML
    private Text user;
    @FXML
    void click(MouseEvent event) {
        orderlis.onClickListener(check,p);
    }
   
    
    
    @FXML
    void Delete(MouseEvent event) {
       orderlis.OnDelete(p);
    }
    
    @FXML
    void quantityUpdate(MouseEvent event) {
        Order update = expformfield();
        boolean result = oder.update(update);
        orderlis.Update();
    }
    
    public int getPrice(Order o ){
        this.p = o;
        int a = o.getTotalPrice();
        return a;
    }
    public String getProductID(Order o){
        this.p = o;
        String a = o.getProductID();
        return a;
    }
    public int getQuantity(Order o ){
        this.p = o;
        int a = o.getQuantity();
        return a;
    }
    private Order  p;
     public void setData(Order fruit ,Product o , OrderListener orderlis ) {
        this.p= fruit;
        this.o = o;
        this.orderlis = orderlis ;
        int b = fruit.getQuantity();
        int c = o.getPrice();
        SpinnerValueFactory<Integer> valueFactory = 
               new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, b);
        spinner.setValueFactory(valueFactory);
        price.setText(Integer.toString(fruit.getTotalPrice()));
        spinner.valueProperty().addListener((overValue , olerValue , newValue)->{
            int a = c * spinner.getValue();
            price.setText(Integer.toString(a));
        });
        id.setText(fruit.getProductID());
        name.setText(o.getName());
        user.setText(fruit.getAccount());
        Image images = new Image(getClass().getResourceAsStream(o.getImg()));
        this.image.setImage(images);
        
     }   
     
     public void initialize(){
          String[] styleClasses = new String[] { "", 
                Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL
 
        };
        spinner.getStyleClass().addAll(Arrays.asList(styleClasses));
        id.setVisible(false);
        user.setVisible(false);
        check.setSelected(true);
    }
     
    private Order expformfield(){
        Order  orders = new Order();
        orders.setProductID(id.getText());
        orders.setQuantity(spinner.getValue());
        orders.setTotalPrice(Integer.parseInt(price.getText()));
        return orders;
    }
    
     public  boolean delete(String b) {
        String sql = "DELETE FROM ShoppingCart WHERE productID = ? ";
        try (
                Connection conn = DbProject.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, Integer.parseInt(b));

            int rowDeleted = stmt.executeUpdate();

            if (rowDeleted == 1) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
}
