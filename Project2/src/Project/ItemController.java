package Project;

import Project.Data.Product;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


public class ItemController {
    @FXML
    private ImageView img;

    @FXML
    private Text name;
    private Product p;
    @FXML
    private Text id;

    
    @FXML
    void inclick(MouseEvent event) {
        myListener.onClickListener(p);
    }
    private MyListener myListener;
     
    public void setData(Product fruit , MyListener myListener ) {
        this.p= fruit;
        this.myListener = myListener;
        id.setText(Integer.toString(fruit.getID()));
        name.setText(fruit.getName());
        Image image = new Image(getClass().getResourceAsStream(fruit.getImg()));
        img.setImage(image);
    }
    @FXML
    void add(ActionEvent event) {
        myListener.addShoppingcart(p);
    }
     public void initialize() {
        id.setVisible(false);
    }
}
