/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import Project.Data.OrderDetail;
import Project.Data.Product;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 *
 * @author Admin
 */
public class HistoryOrderDetail {
    
    private Product products;
    private OrderDetail orders;
    @FXML
    private ImageView image;

    @FXML
    private Text name;

    @FXML
    private Text price;

    @FXML
    private Text quantity;
     public void setData(OrderDetail orders ,Product product ) {
        this.orders= orders;
        this.products = product;
        quantity.setText(Integer.toString(orders.getQuantity()));
        price.setText(Integer.toString(orders.getQuantity() * product.getPrice())+" VNĐ");
        name.setText(products.getName());
        Image images = new Image(getClass().getResourceAsStream(product.getImg()));
        image.setImage(images);
     } 
    
}
