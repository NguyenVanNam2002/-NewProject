/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import Project.Data.Order;
import Project.Data.OrderDAO;
import Project.Data.OrderDAOImpl;
import Project.Data.OrderDetail;
import Project.Data.Product;
import Project.Data.ProjectSignUp;
import Project.DbProject.DbProject;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Admin
 */
public class Shopping_cart {

    private OrderDAO or = new OrderDAOImpl();
    private ProjectSignUp psu;
    @FXML
    private Text user;
    @FXML
    private JFXButton orders;
    @FXML
    private JFXButton Home;

    @FXML
    private GridPane grid;

    @FXML
    private Text totalprice;
    @FXML
    private JFXButton update;
    @FXML
    private Text quantity;
    @FXML
    private Text ord;
    @FXML
    private Text productID;
    @FXML
    private Text sote;
    private OrderListener orderlis;
    @FXML
    private Text orderid;
      @FXML
    private Pane show;

    @FXML
    private ImageView succes1;

    @FXML
    private Text textsussec;

    @FXML
    private ImageView error;

    @FXML
    private Text texterrors;


    ObservableList<Order> ordr = FXCollections.observableArrayList();
    ObservableList<Product> pro = FXCollections.observableArrayList();
    ObservableList<String> listProductID = FXCollections.observableArrayList();
    ObservableList<Integer> listQuantity = FXCollections.observableArrayList();
    ObservableList<String> listOrderId = FXCollections.observableArrayList();

    @FXML
    void btnorder(ActionEvent event) throws SQLException, IOException {

        if (validation()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Bạn muốn Order sản phẩm ?");
            alert.setTitle("Lưu ý");
            Optional<ButtonType> confirmationResponse
                    = alert.showAndWait();
            if (confirmationResponse.get() == ButtonType.OK) {
                Order orderss = extactFromfiled();
                orderss = or.insertOrder(orderss);
                selectShoping(user.getText(), sote.getText());
                for (int d = 0; d < listOrderId.size(); d++) {
                    if (selectOrderDetail(listOrderId.get(d))) {
                    } else {
                        for (int c = 0; c < listProductID.size(); c++) {
                            insert(listOrderId.get(d), listProductID.get(c), listQuantity.get(c));
                            delete(listProductID.get(c));
                        }
                    }

                }
                 show.setVisible(true);
                succes1.setVisible(true);
                textsussec.setVisible(true);
                PauseTransition pt = new PauseTransition();
                pt.setDuration(Duration.seconds(2));
                pt.setOnFinished(e -> {
                       show.setVisible(false);
                       succes1.setVisible(false);
                       textsussec.setVisible(false);
                       ProjectSignUp account = extractSignUpFromFields();
                    try {
                        Nagatice.getInstance().goToShopping(account);
                    } catch (IOException ex) {
                        Logger.getLogger(Shopping_cart.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                pt.play(); 
               
            }
        }
    }

    @FXML
    void btnHome(ActionEvent event) throws IOException {
        ProjectSignUp p = extractSignUpFromFields();
        Nagatice.getInstance().goToChoose(p);
    }

    public void initialize(ProjectSignUp p) {
        this.psu = p;
        if (this.psu != null) {
            user.setText(p.getAccount());
            selectShopingcart(p.getAccount());
        }
    }

    public void initialize() {
        productID.setVisible(false);
        totalprice.setVisible(false);
        quantity.setVisible(false);
        orderid.setVisible(false);
        show.setVisible(false);
        succes1.setVisible(false);
        textsussec.setVisible(false);
        error.setVisible(false);
        texterrors.setVisible(false);
    }

    private void setChosenSnack(Order snack) {
        productID.setText(snack.getProductID());
        totalprice.setText(snack.getTotalPrice().toString());
        quantity.setText(snack.getQuantity().toString());
    }

    private void setChosenSnack() {
        productID.setText("");
        totalprice.setText("");
        quantity.setText("");
    }

    private ProjectSignUp extractSignUpFromFields() {
        ProjectSignUp sign = new ProjectSignUp();
        sign.setAccount(user.getText());
        return sign;
    }

    private OrderDetail extract() {
        OrderDetail sign = new OrderDetail();
        sign.setID(Integer.parseInt(orderid.getText()));
        return sign;
    }

    // shopping cart
    public void selectShopingcart(String a) {
        String sql = "SELECT o.*,p.* FROM  ShoppingCart as o join products as p on o.productID = p.productID WHERE accounts  = ? ";
        try (Connection con = DbProject.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql);) {
            stmt.setString(1, a);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setImg(rs.getString("p.ImgLink"));
                p.setName(rs.getString("p.Name"));
                p.setPrice(rs.getInt("p.Price"));
                Order o = new Order();
                o.setQuantity(rs.getInt("o.quantity"));
                o.setTotalPrice(rs.getInt("o.Total_price"));
                o.setProductID(Integer.toString(rs.getInt("o.productID")));
                o.setAccount(rs.getString("o.accounts"));
                ordr.add(o);
                pro.add(p);
            }
        } catch (Exception e) {
        }

        if (ordr.size() > 0) {
            orderlis = new OrderListener() {
                @Override
                public void onClickListener(CheckBox y, Order order) {
                    if (y.isSelected() == false) {
                        setChosenSnack(order);
                        int c = Integer.parseInt(sote.getText()) - Integer.parseInt(totalprice.getText());
                        sote.setText(Integer.toString(c));
                        for (int ai = 0; ai < listProductID.size(); ai++) {
                            if (productID.getText().equals(listProductID.get(ai))) {
                                listProductID.remove(listProductID.get(ai));
                                listQuantity.remove(listQuantity.get(ai));
                            }

                        }
                    } else {
                        int c = Integer.parseInt(sote.getText()) + Integer.parseInt(totalprice.getText());
                        sote.setText(Integer.toString(c));
                        listProductID.add(productID.getText());
                        listQuantity.add(Integer.parseInt(quantity.getText()));
                    }

                }

                @Override
                public void OnDelete(Order order) {
                    setChosenSnack(order);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Bạn muốn xóa sản phẩm khỏi giỏ hàng?");
                    alert.setTitle("Lưu ý");
                    Optional<ButtonType> confirmationResponse
                            = alert.showAndWait();
                    if (confirmationResponse.get() == ButtonType.OK) {
                        if (productID.getText() != null) {
                            delete(productID.getText());
                            ProjectSignUp u = extractSignUpFromFields();
                            try {
                                Nagatice.getInstance().goToShopping(u);
                            } catch (IOException ex) {
                                Logger.getLogger(Shopping_cart.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {

                        }
                    }
                }

                @Override
                public void Update() {
                    ProjectSignUp u = extractSignUpFromFields();
                    try {
                        Nagatice.getInstance().goToShopping(u);
                    } catch (IOException ex) {
                        Logger.getLogger(Shopping_cart.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
        }
        int column = 0;
        int row = 1;
        int b = 0;

        try {
            for (int i = 0; i < ordr.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("OrderItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                OrderItem itemController = fxmlLoader.getController();
                itemController.setData(ordr.get(i), pro.get(i), orderlis);
                b += itemController.getPrice(ordr.get(i));
                listProductID.add(itemController.getProductID(ordr.get(i)));
                listQuantity.add(itemController.getQuantity(ordr.get(i)));
                if (column == 1) {
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
            sote.setText(Integer.toString(b));
        } catch (IOException e) {
        }
    }

    public boolean delete(String b) {
        String sql = "DELETE FROM ShoppingCart WHERE productID = ?";
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

    // Order
    private Order extactFromfiled() {
        Order orda = new Order();
        orda.setAccount(user.getText());
        orda.setTotalPrice(Integer.parseInt(sote.getText()));
        LocalDateTime now = LocalDateTime.now();
        String b = now.toString();
        orda.setDate(b);
        return orda;
    }

    public void selectShoping(String a, String c) {
        String sql = "SELECT OrderID FROM  Orders WHERE Client_ID  = ? and total_price = ? ";
        try (Connection con = DbProject.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql);) {
            stmt.setString(1, a);
            stmt.setInt(2, Integer.parseInt(c));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listOrderId.add(Integer.toString(rs.getInt("OrderID")));
            }
        } catch (Exception e) {
        }
    }

    // Order Detail
    public boolean selectOrderDetail(String a) {
        String sql = "SELECT * FROM  order_detail WHERE OrderID = ? ";
        try (Connection con = DbProject.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql);) {

            stmt.setInt(1, Integer.parseInt(a));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public void insert(String c, String b, int d) throws SQLException {
        String sql = "INSERT INTO order_detail (OrderID, ProductID, Quantity) "
                + "VALUES ( ?, ?, ?)"
                + "LIMIT 1";
        ResultSet key = null;
        try (
                Connection connect = DbProject.getConnection();
                PreparedStatement stmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setInt(1, Integer.parseInt(c));
            stmt.setInt(2, Integer.parseInt(b));
            stmt.setInt(3, d);

            int rowInserted = stmt.executeUpdate();
            if (rowInserted == 1) {

            } else {
                System.out.println("No Product inserted");

            }
        } catch (Exception e) {
            System.err.println(e);

        }
    }

    // validation
    private boolean validation() {
        if (Integer.parseInt(sote.getText()) <= 0) {
            show.setVisible(true);
            error.setVisible(true);
            texterrors.setVisible(true);
            PauseTransition pt = new PauseTransition();
            pt.setDuration(Duration.seconds(2));
            pt.setOnFinished(e -> {
                   show.setVisible(false);
                   error.setVisible(false);
                   texterrors.setVisible(false);
            });

            pt.play();
            return false;
        }
        return true;
    }
}
