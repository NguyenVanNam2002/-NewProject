/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import Project.Data.Category;
import Project.Data.OrderDAO;
import Project.Data.OrderDAOImpl;
import Project.Data.Product;
import Project.Data.ProjectSignUp;
import Project.DbProject.DbProject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author icom
 */
public class Client_ChooseController {

    private Category cate;
    private OrderDAO od = new OrderDAOImpl();
    private Product pd;
    private ProjectSignUp psu;

    @FXML
    private AnchorPane root;

    @FXML
    private Text user;
    @FXML
    private Text erross;

    @FXML
    private Text id;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private TextField productname;

    @FXML
    private JFXButton menu;

    @FXML
    private GridPane grid;

    @FXML
    private Text drink;

    @FXML
    private Text food;
    @FXML
    private JFXComboBox<String> category;

    @FXML
    private Text thename;

    @FXML
    private ImageView imageview;

    @FXML
    private Text theprice;

    @FXML
    private Spinner<Integer> sprinner;

    @FXML
    private JFXButton order;

    private MyListener myListener;
    ObservableList<Product> list = FXCollections.observableArrayList();
    ObservableList<Product> lis = FXCollections.observableArrayList();

    @FXML
    private JFXHamburger hamberger;

    @FXML
    private JFXDrawer drawer;

    private Product extractProductFromFields() {
        Product sign = new Product();
        sign.setName(thename.getText());
        sign.setId(Integer.parseInt(id.getText()));
        return sign;
    }

    @FXML
    void btncategory(ActionEvent event) throws IOException {
        if (category.getSelectionModel().getSelectedItem().toString().equals("Tất cả")) {
            ProjectSignUp menus = extractSignUpFromFields();
            Nagatice.getInstance().goToChoose(menus);
        } else if (category.getSelectionModel().getSelectedItem().toString().equals("Đồ ăn")) {
            ProjectSignUp menus = extractSignUpFromFields();
            Category cate = extractFoodFromFields();
            Nagatice.getInstance().goToChoose(menus, cate);
        } else if (category.getSelectionModel().getSelectedItem().toString().equals("Nước uống")) {
            ProjectSignUp menus = extractSignUpFromFields();
            Category cate = extractDrinkFoodFromFields();
            Nagatice.getInstance().goToChoose(menus, cate);
        }
    }

    @FXML
    void btnSearchClick(ActionEvent event) throws IOException {
        ProjectSignUp account = extractSignUpFromFields();
        Product name = extractSearchFromFields();
        Nagatice.getInstance().goToSearch(account, name);
    }

    @FXML
    void btnfeedback(ActionEvent event) throws IOException {
        Product ea = extractProductFromFields();
//        Nagatice.getInstance().goFeedbackClient(acc, ea);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Client_feedback.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();

        Client_feedback itemController = fxmlLoader.getController();
        itemController.Setdata(ea);

        Scene secondScene = new Scene(anchorPane, 966, 685);
        // Một cửa sổ mới (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("FeedBack Form");
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.setX(root.getLayoutX() + 650);
        newWindow.setY(root.getLayoutY() + 200);
        newWindow.show();
    }

    private Category extractFoodFromFields() {
        Category ca = new Category();
        ca.setCat_name(food.getText());
        return ca;
    }

    private Category extractDrinkFoodFromFields() {
        Category ca = new Category();
        ca.setCat_name(drink.getText());
        return ca;
    }

    private Product extractSearchFromFields() {
        Product pro = new Product();
        pro.setName(productname.getText());
        return pro;
    }

    private void setChosenSnack(Product snack) {
        id.setText(Integer.toString(snack.getID()));
        thename.setText(snack.getName());
        theprice.setText(snack.getPrice().toString());
        Image image = new Image(getClass().getResourceAsStream(snack.getImg()));
        imageview.setImage(image);
        int c = snack.getPrice();
        theprice.setText(Integer.toString(snack.getPrice()) + " VNĐ");
    }

    @FXML
    void btnShopping(MouseEvent event) throws IOException {
        ProjectSignUp osu = extractSignUpFromFields();
        Nagatice.getInstance().goToShopping(osu);
    }

    @FXML
    void btnorder(ActionEvent event) throws IOException {
        addShoppingCart();
    }

    public void initialize(ProjectSignUp p, Category u) throws IOException {
        this.psu = p;
        this.cate = u;
        if (this.psu != null) {
            user.setText(p.getAccount());
            Category();
        }
        if (this.cate != null) {

            where(u.getCat_name());
        }
    }

    public void initialize(ProjectSignUp p) throws IOException {
        this.psu = p;
        if (this.psu != null) {
            user.setText(p.getAccount());
            Index();
            Category();
        }

    }

    private ProjectSignUp extractSignUpFromFields() {
        ProjectSignUp sign = new ProjectSignUp();
        sign.setAccount(user.getText());
        return sign;
    }

    public void initialize() throws IOException {
        food.setText("Food");
        drink.setText("Drink");
        food.setVisible(false);
        drink.setVisible(false);
        id.setVisible(false);
        category.getItems().add("Tất cả");
        category.getItems().add("Đồ ăn");
        category.getItems().add("Nước uống");
        user.setVisible(false);
    }

    public void where(String b) {
        String sql = "SELECT ProductID,ImgLink,Name,Price FROM products as p join category as c on p.CategoryID = c.CategoryID where c.NameC = ?";
        try (Connection conn = DbProject.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, b);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product u = new Product();
                u.setId(rs.getInt("ProductID"));
                u.setName(rs.getString("Name"));
                u.setPrice(rs.getInt("Price"));
                u.setImg(rs.getString("ImgLink"));
                list.add(u);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        if (list.size() > 0) {
            setChosenSnack(list.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product snack) {
                    setChosenSnack(snack);
                }

                @Override
                public void addShoppingcart(Product snack) {
                    setChosenSnack(snack);
                    try {
                        addShoppingCart();
                    } catch (IOException ex) {
                        Logger.getLogger(Client_ChooseController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
        }

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < list.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(list.get(i), myListener);
                if (column == 4) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));

            }
        } catch (IOException e) {

        }
    }

    public void Index() {
        String sql = "SELECT ProductID,ImgLink,Name,Price FROM products as p join category as c on p.CategoryID = c.CategoryID ";
        try (Connection conn = DbProject.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product u = new Product();
                u.setId(rs.getInt("ProductID"));
                u.setName(rs.getString("Name"));
                u.setPrice(rs.getInt("Price"));
                u.setImg(rs.getString("ImgLink"));
                list.add(u);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        if (list.size() > 0) {
            setChosenSnack(list.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product snack) {
                    setChosenSnack(snack);
                }

                @Override
                public void addShoppingcart(Product snack) {
                    setChosenSnack(snack);
                    try {
                        addShoppingCart();
                    } catch (IOException ex) {
                        Logger.getLogger(Client_ChooseController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
        }

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < list.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(list.get(i), myListener);
                if (column == 4) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));

            }
        } catch (IOException e) {

        }
    }

    public void Search(String b) {
        String sql = "SELECT * FROM products WHERE `Name` LIKE CONCAT ('%' , ? ,'%') ";
        try (Connection conn = DbProject.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, b);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product u = new Product();
                u.setId(rs.getInt("ProductID"));
                u.setName(rs.getString("Name"));
                u.setPrice(rs.getInt("Price"));
                u.setImg(rs.getString("ImgLink"));
                u.setProperties(rs.getString("Properties"));
                lis.add(u);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        if (lis.size() > 0) {
            setChosenSnack(lis.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product snack) {
                    setChosenSnack(snack);
                }

                @Override
                public void addShoppingcart(Product snack) {
                    setChosenSnack(snack);
                    try {
                        addShoppingCart();
                    } catch (IOException ex) {
                        Logger.getLogger(Client_ChooseController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
        }

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < lis.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(lis.get(i), myListener);
                if (column == 5) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));

            }
        } catch (IOException e) {

        }
    }

    public void initialize(ProjectSignUp p, Product d) throws IOException {
        this.psu = p;
        this.pd = d;
        if (this.psu != null) {
            user.setText(p.getAccount());
            Category();
        }
        if (this.pd != null) {
            infomaitonselect(d.getName());
            Search(d.getName());
            if (id.getText().isEmpty()) {
                erross.setText("* Cửa hàng không có sản phẩm có kí tự trên , vui lòng tìm sản phẩm khác");
            }
        }
    }

    private Product extractFeedbackFromFields() {
        Product sign = new Product();
        sign.setName(thename.getText());
        return sign;
    }

    public void infomaitonselect(String user) {
        String sql = "SELECT  p.* , c.* FROM products as p join category as c on p.CategoryID = c.CategoryID WHERE p.Name = ? ";

        try (Connection con = DbProject.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql);) {
            stmt.setString(1, user);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    id.setText(Integer.toString(rs.getInt("p.ProductID")));
                    thename.setText(rs.getString("p.Name"));
                    theprice.setText(rs.getString("p.Price"));
                    Image myimage = new Image(getClass().getResourceAsStream(rs.getString("p.ImgLink")));
                    imageview.setImage(myimage);

                }
            }
            stmt.close();
            con.close();
        } catch (Exception e) {
        }
    }

    public void Category() throws IOException {
        VBox v = FXMLLoader.load(getClass().getResource("CategoryClient.fxml"));
        drawer.setSidePane(v);
        for (Node node : v.getChildren()) {
            if (node.getAccessibleText() != null) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    ProjectSignUp u = extractSignUpFromFields();
                    switch (node.getAccessibleText()) {
                        case "info": {
                            try {
                                Nagatice.getInstance().goToInformationClient(u);
                            } catch (IOException ex) {
                                Logger.getLogger(Client_ChooseController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                        case "his":
                    {
                        try {
                            Nagatice.getInstance().goToHistory(u);
                        } catch (IOException ex) {
                            Logger.getLogger(Client_ChooseController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                            break;
                        case "out":
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Bạn chắc chắn muốn đăng xuất ?");
                            alert.setTitle("Lưu ý ");
                            Optional<ButtonType> confirmationResponse
                                    = alert.showAndWait();
                            if (confirmationResponse.get() == ButtonType.OK) {
                                try {
                                    Nagatice.getInstance().goToIndex();
                                } catch (IOException ex) {
                                    Logger.getLogger(Client_ChooseController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;
                    }

                });
            }
        }
        HamburgerBackArrowBasicTransition tration = new HamburgerBackArrowBasicTransition(hamberger);
        tration.setRate(-1);
        hamberger.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            tration.setRate(tration.getRate() * -1);
            tration.play();
            if (tration.getRate() == 1) {
                drawer.open();
            } else {
                drawer.close();
            }
        });
    }
    
    public void addShoppingCart() throws IOException{
        Product ea = extractProductFromFields();
        ProjectSignUp account =extractSignUpFromFields();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AddShoppingcart.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();

        AddShoppingCart itemController = fxmlLoader.getController();
        itemController.setData(account,ea);

        Scene secondScene = new Scene(anchorPane, 966, 685);
        Stage newWindow = new Stage();
        newWindow.setTitle("Add Shopping Cart Form");
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        
        newWindow.setX(root.getLayoutX() + 650);
        newWindow.setY(root.getLayoutY() + 200);
        newWindow.show();
    }
}
