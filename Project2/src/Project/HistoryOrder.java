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
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 *
 * @author Admin
 */
public class HistoryOrder {
    private OrderDAO or = new OrderDAOImpl();
    private Order o;
    private ProjectSignUp psu;
    @FXML
    private Text user;

    @FXML
    private TableView<Order> TableviewOrder;

    @FXML
    private TableColumn<Order, Integer> orderID;

    @FXML
    private TableColumn<Order, Integer> Total_Price;

    @FXML
    private TableColumn<Order, String> datetime;

    @FXML
    private GridPane grid;
    
    ObservableList<OrderDetail> listOrder = FXCollections.observableArrayList();
    ObservableList<Product> listProduct = FXCollections.observableArrayList();
    @FXML
    void back(MouseEvent event) throws IOException {
        ProjectSignUp p = extractPasswordFromFields();
        Nagatice.getInstance().goToChoose(p);
    }
    private ProjectSignUp extractPasswordFromFields() {
        ProjectSignUp sign = new ProjectSignUp(); 
        sign.setAccount(user.getText());
        return sign;
    }
   
    @FXML
    void OnmouseClick(MouseEvent event) throws IOException {
        ProjectSignUp projectSingup =extractPasswordFromFields();
        Order ifclick = TableviewOrder.getSelectionModel().getSelectedItem();
        if(ifclick != null){
            Nagatice.getInstance().goToHistory(projectSingup, ifclick);
        }

    }
    
    private void SelectItem(int u){
        String sql = "SELECT od.*,o.*,p.* FROM order_detail as od join products as p on od.ProductID = p.ProductID"
               + " join Orders as o on od.OrderID = o.OrderID WHERE od.OrderID = ? ";
        try ( Connection con = DbProject.getConnection();
                PreparedStatement  stmt = con.prepareStatement(sql);               
            ){
            
            stmt.setInt(1, u);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                OrderDetail ors = new OrderDetail();
                ors.setQuantity(rs.getInt("od.Quantity"));
                ors.setProductID(Integer.toString(rs.getInt("od.ProductID")));
                listOrder.add(ors);
                Product pro = new Product();
                pro.setPrice(rs.getInt("p.Price"));
                pro.setImg(rs.getString("p.ImgLink"));
                pro.setName(rs.getString("p.Name"));
                listProduct.add(pro);
            }
            
        } catch (Exception e) {
        }
        
        int column = 0;
        int row = 1;
        int b = 0;

        try {
            for (int i = 0; i < listOrder.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("HistoryOrderDetail.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                HistoryOrderDetail itemController = fxmlLoader.getController();
                itemController.setData(listOrder.get(i), listProduct.get(i));
               
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
        }catch(Exception e){
        
        }
    }
    public void initialize() {
     
    }

    void initialize(ProjectSignUp p) {
        this.psu = p;
        if(this.psu != null){
            user.setText(p.getAccount());
            TableviewOrder.setItems(or.Sellect(p.getAccount()));
       
            orderID.setCellValueFactory((orders)->{
                 return orders.getValue().getIDProperty();
            });

            Total_Price.setCellValueFactory((orders)->{
                 return orders.getValue().getTotalPriceProperty();
             });
            datetime.setCellValueFactory((orders)->{
                 return orders.getValue().getDateProperty();
             });
             }
    }
    
    public void initialize(ProjectSignUp p, Order o) {
        this.psu = p;
        this.o = o;
        if(this.psu != null){
            user.setText(p.getAccount());
            TableviewOrder.setItems(or.Sellect(p.getAccount()));
       
            orderID.setCellValueFactory((orders)->{
                 return orders.getValue().getIDProperty();
            });

            Total_Price.setCellValueFactory((orders)->{
                 return orders.getValue().getTotalPriceProperty();
             });
            datetime.setCellValueFactory((orders)->{
                 return orders.getValue().getDateProperty();
             });
        }
        if(this.o != null){
            SelectItem(o.getID());
        }
    }
}
