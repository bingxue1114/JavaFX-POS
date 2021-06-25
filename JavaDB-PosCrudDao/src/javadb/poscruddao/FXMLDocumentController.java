package javadb.poscruddao;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import models.OrderDetail;
import models.OrderDetailDAO;
import models.Product;
import models.ProductDAO;

public class FXMLDocumentController implements Initializable {

    private List<Product> products = new ArrayList();
    private List<OrderDetail> order_detail_list = new ArrayList();

    @FXML
    private TextField queryID;
    @FXML
    private TextField queryName;
    @FXML
    private TableView<Product> table_pos;
    @FXML
    private TableColumn<Product, String> col_product_id;
    @FXML
    private TableColumn<Product, String> col_category;
    @FXML
    private TableColumn<Product, String> col_name;
    @FXML
    private TableColumn<Product, Integer> col_price;
    @FXML
    private TableColumn<Product, String> col_photo;
    @FXML
    private TableColumn<Product, String> col_description;
    @FXML
    private Pagination pagination;

    //表格的每一頁顯示幾個rows
    private final int RowsPerPage = 8;

    //方便操作資料庫的物件
    private ProductDAO prdao = new ProductDAO();
    private OrderDetailDAO ordao = new OrderDetailDAO();

    @FXML
    private TextField queryID1;
    @FXML
    private TableColumn<OrderDetail, String> col_order_num;
    @FXML
    private TableColumn<OrderDetail, String> col_order_product_name;
    @FXML
    private TableColumn<OrderDetail, Integer> col_order_quantity;
    @FXML
    private Pagination pagination1;
    @FXML
    private TableView<OrderDetail> table_order;
//    private TableColumn<OrderDetail, Integer> col_order_id;
    @FXML
    private TableColumn<OrderDetail, String> col_order_finished;
    
//以上OK

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable(); //表格初始化
    }

    //表格初始化很複雜請仔細分解動作
    private void initTable() {

//----------------------------------------產品編輯 開始----------------------------------------------------------------
        //表格最後一欄是空白，不要顯示!
        table_pos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //table_student.setPrefHeight(200);
        col_product_id.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_photo.setCellValueFactory(new PropertyValueFactory<>("photo"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));

        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                showTablePage_pos(newValue.intValue(), RowsPerPage);
            }
        });

        // 表格切換到一下筆，對應的驅動方法，此處暫時沒用到，寫法與前面類似
        table_pos.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
            }
        });

        //讓表格內容可以修改
        table_pos.setEditable(true);
        //表格欄位設定成可以編輯必須分別塞入一個TextFieldTableCell類別元件
        col_category.setCellFactory(TextFieldTableCell.forTableColumn());
        col_name.setCellFactory(TextFieldTableCell.forTableColumn());
        col_price.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col_photo.setCellFactory(TextFieldTableCell.forTableColumn());
        col_description.setCellFactory(TextFieldTableCell.forTableColumn());

        //產品類別欄位若有修改驅動這個方法
        col_category.setOnEditCommit(new EventHandler<CellEditEvent<Product, String>>() {
            @Override
            public void handle(CellEditEvent<Product, String> event) {
                Product prud = table_pos.getSelectionModel().getSelectedItem();
                prud.setCategory(event.getNewValue());
            }
        });

        //產品名稱欄位若有修改驅動這個方法
        col_name.setOnEditCommit(new EventHandler<CellEditEvent<Product, String>>() {
            @Override
            public void handle(CellEditEvent<Product, String> event) {
                Product prud = table_pos.getSelectionModel().getSelectedItem();
                System.out.println(event.getNewValue());
                prud.setName(event.getNewValue());
            }
        });

        //產品價格欄位若有修改驅動這個方法
        col_price.setOnEditCommit(new EventHandler<CellEditEvent<Product, Integer>>() {
            @Override
            public void handle(CellEditEvent<Product, Integer> event) {
                Product prud = table_pos.getSelectionModel().getSelectedItem();
                prud.setPrice(event.getNewValue());
            }
        });

        //產品圖片欄位若有修改驅動這個方法
        col_photo.setOnEditCommit(new EventHandler<CellEditEvent<Product, String>>() {
            @Override
            public void handle(CellEditEvent<Product, String> event) {
                Product prud = table_pos.getSelectionModel().getSelectedItem();
                prud.setPhoto(event.getNewValue());
            }
        });

        //產品敘述欄位若有修改驅動這個方法
        col_description.setOnEditCommit(new EventHandler<CellEditEvent<Product, String>>() {
            @Override
            public void handle(CellEditEvent<Product, String> event) {
                Product prud = table_pos.getSelectionModel().getSelectedItem();
                prud.setDescription(event.getNewValue());
            }
        });
//----------------------------------------產品編輯 結束----------------------------------------------------------------

//----------------------------------------訂單編輯 開始----------------------------------------------------------------
        //表格最後一欄是空白，不要顯示!
        table_order.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        col_order_num.setCellValueFactory(new PropertyValueFactory<>("order_num"));
        col_order_product_name.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        col_order_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_order_finished.setCellValueFactory(new PropertyValueFactory<>("finished"));

        pagination1.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                showTablePage_order(newValue.intValue(), RowsPerPage);
            }
        });

        // 表格切換到一下筆，對應的驅動方法，此處暫時沒用到，寫法與前面類似
        table_order.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
            }
        });

        //讓表格內容可以修改
        table_order.setEditable(true);
        
        //產品類別欄位若有修改驅動這個方法
        col_order_finished.setOnEditCommit(new EventHandler<CellEditEvent<OrderDetail, String>>() {
            @Override
            public void handle(CellEditEvent<OrderDetail, String> event) {
                OrderDetail orud = table_order.getSelectionModel().getSelectedItem();
                orud.setFinished(event.getNewValue());
            }
        });
//----------------------------------------訂單編輯 結束----------------------------------------------------------------
    }
    
//----------------------------------------產品載入 開始----------------------------------------------------------------
    //表格內容載入
    private void loadTable_pos() {
        int totalPage = (int) (Math.ceil(products.size() * 1.0 / RowsPerPage));
        pagination.setPageCount(totalPage);
        //pagination.setCurrentPageIndex(0);
        int currentpg = pagination.getCurrentPageIndex();
        showTablePage_pos(currentpg, RowsPerPage);

    }
    
    //顯示某一個頁面的表格內容
    private void showTablePage_pos(int pg, int row_per_pg) {
        table_pos.getItems().clear(); //先清除表格內容
        int from = pg * row_per_pg;  //計算在此頁面顯示第幾筆到第幾筆
        int to = Math.min(from + row_per_pg, products.size());
        for (int i = from; i < to; i++) {
            table_pos.getItems().add(products.get(i));
        }

    }
//----------------------------------------產品載入 結束----------------------------------------------------------------
    
//----------------------------------------訂單載入 開始----------------------------------------------------------------
    //表格內容載入
    private void loadTable_orderdetail() {
        int totalPage = (int) (Math.ceil(order_detail_list.size() * 1.0 / RowsPerPage));
        pagination1.setPageCount(totalPage);
        //pagination.setCurrentPageIndex(0);
        int currentpg = pagination1.getCurrentPageIndex();
        showTablePage_order(currentpg, RowsPerPage);
    }
    
    //顯示某一個頁面的表格內容
    private void showTablePage_order(int pg, int row_per_pg) {
        table_order.getItems().clear(); //先清除表格內容
        int from = pg * row_per_pg;  //計算在此頁面顯示第幾筆到第幾筆
        int to = Math.min(from + row_per_pg, order_detail_list.size());
        for (int i = from; i < to; i++) {
            table_order.getItems().add(order_detail_list.get(i));
        }
    }
//----------------------------------------訂單載入 結束----------------------------------------------------------------
    
//----------------------------------------產品 新增 修改 刪除 尋找 開始----------------------------------------------------------------

    //產品敘述欄位若有修改驅動這個方法，可以在SceneBuider中定義這個事件
    private void onDescriptionEditCommit(CellEditEvent<Product, String> event) {
        Product prud = table_pos.getSelectionModel().getSelectedItem();
        prud.setDescription(event.getNewValue());
    }

    @FXML
    private void update(ActionEvent event) {
        Product prud = table_pos.getSelectionModel().getSelectedItem();
        String product_id = prud.getProduct_id();
        String category = prud.getCategory();
        String name = prud.getName();
        int price = prud.getPrice();
        String photo = prud.getPhoto();
        String description = prud.getDescription();
        prdao.update(new Product(product_id, category, name, price, photo, description));
        products = prdao.getAllProducts();
        loadTable_pos();
    }

    @FXML
    private void delete(ActionEvent event) {
        Product prud = table_pos.getSelectionModel().getSelectedItem();
        String product_id = prud.getProduct_id();
        boolean sucess = prdao.delete(product_id);
        products = prdao.getAllProducts();
        loadTable_pos();
    }

    @FXML
    private void insert(ActionEvent event) {

        Product prud = table_pos.getSelectionModel().getSelectedItem();
        String id = prud.getProduct_id();
        String category = prud.getCategory();
        String name = prud.getName();
        int price = prud.getPrice();
        String photo = prud.getPhoto();
        String description = prud.getDescription();
        prdao.insert(new Product(id, category, name, price, photo, description));
        products = prdao.getAllProducts();
        loadTable_pos();

    }

    @FXML
    private void blankRecord(ActionEvent event) {
        String order_num = prdao.getMaxOrderNum();

        if (order_num == null) {
            order_num = "p-j-100";
        }

        System.out.println(order_num);
        System.out.println(order_num.split("-")[2]);

        //將現有訂單編號加上1
        int serial_num = Integer.parseInt(order_num.split("-")[2]) + 1;
        System.out.println(serial_num);

        //每家公司都有其訂單或產品的編號系統，這裡用ord-xxx表之
        String new_order_num = "p-j-" + serial_num;
        table_pos.getItems().add(new Product(new_order_num, "請輸入產品類別", "請輸入產品名稱", 0, "banana.png", "請輸入產品敘述"));
    }

    @FXML
    private void findID(ActionEvent event) {
        products.clear();
        products.add(prdao.findById(queryID.getText()));
        loadTable_pos();
    }

    @FXML
    private void findName(ActionEvent event) {
        products = prdao.findByNameContaining(queryName.getText());
        loadTable_pos();
    }

    @FXML
    private void findAll(ActionEvent event) {
        prdao.getAllProducts();
        products = prdao.getAllProducts();
        loadTable_pos();
    }
    
//----------------------------------------產品 新增 修改 刪除 尋找 結束----------------------------------------------------------------
    
//----------------------------------------訂單 新增 修改 刪除 尋找 開始----------------------------------------------------------------

    @FXML
    private void selectAllCourses(ActionEvent event) {
        ordao.getAllOrdersDetail();
        order_detail_list = ordao.getAllOrdersDetail();
        loadTable_orderdetail();
    }  //OK

    @FXML
    private void update_order(ActionEvent event) { 
        OrderDetail prud = table_order.getSelectionModel().getSelectedItem();
        int id = prud.getId();
        String finished = prud.getFinished();
        OrderDetail crt = new OrderDetail();
        crt.setId(id);
        crt.setFinished("已出餐");
        //寫入一筆訂單道資料庫
        ordao.update(crt);
        order_detail_list = ordao.getAllOrdersDetail();
        loadTable_orderdetail();
    } 

    private void delete_order(ActionEvent event) {
        OrderDetail prud = table_order.getSelectionModel().getSelectedItem();
        int id = prud.getId();
        boolean sucess = ordao.delete(id);
        order_detail_list = ordao.getAllOrdersDetail();
        loadTable_orderdetail();
    }

    
    @FXML
    private void findOrder_num(ActionEvent event) {
        order_detail_list = ordao.findById(queryID1.getText());
        loadTable_orderdetail();
    }
    
    @FXML
    private void findNotfinished(ActionEvent event) {
        order_detail_list = ordao.findByNotFinished("未出餐");
        loadTable_orderdetail();
    }
    
    @FXML
    private void findFinished(ActionEvent event) {
        order_detail_list = ordao.findByNotFinished("已出餐");
        loadTable_orderdetail();
    }

//----------------------------------------訂單 新增 修改 刪除 尋找 結束----------------------------------------------------------------

}
