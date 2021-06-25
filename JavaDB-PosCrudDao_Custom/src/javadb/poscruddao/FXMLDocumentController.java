package javadb.poscruddao;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import models.OrderDetail;
import models.OrderDetailDAO;
import models.Product;
import models.ProductDAO;

public class FXMLDocumentController implements Initializable {

    private List<Product> products = new ArrayList();
    private List<OrderDetail> order_detail_list = new ArrayList();

    @FXML
    private TableView<OrderDetail> table_pos;
    @FXML
    private TableColumn<OrderDetail, String> col_finished;
    @FXML
    private TableColumn<OrderDetail, String> col_product_name;
    @FXML
    private TableColumn<OrderDetail, String> col_quantity;

    @FXML
    private Pagination pagination;

    //表格的每一頁顯示幾個rows
    private final int RowsPerPage = 11;

    //方便操作資料庫的物件
    private ProductDAO prdao = new ProductDAO();
    private OrderDetailDAO ordao = new OrderDetailDAO();
    @FXML
    private TextArea display;
    @FXML
    private ToggleGroup radio;
    @FXML
    private TextField text_order_num;
    @FXML
    private TextField text_customer_name;
    @FXML
    private TextField text_customer_phone;
    @FXML
    private Button btn_order_num;
    @FXML
    private Button btn_customer_name;
    @FXML
    private Button btn_customer_phone;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable(); //表格初始化
    }

    //表格初始化很複雜請仔細分解動作
    private void initTable() {

//----------------------------------------產品編輯 開始----------------------------------------------------------------
        //表格最後一欄是空白，不要顯示!
        table_pos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        col_product_name.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        col_finished.setCellValueFactory(new PropertyValueFactory<>("finished"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
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
        table_pos.setEditable(false);

    }

    //表格內容載入
    private void loadTable_pos() {
        int totalPage = (int) (Math.ceil(order_detail_list.size() * 1.0 / RowsPerPage));
        pagination.setPageCount(totalPage);
        int currentpg = pagination.getCurrentPageIndex();
        showTablePage_pos(currentpg, RowsPerPage);

    }

    //顯示某一個頁面的表格內容
    private void showTablePage_pos(int pg, int row_per_pg) {
        table_pos.getItems().clear(); //先清除表格內容
        int from = pg * row_per_pg;  //計算在此頁面顯示第幾筆到第幾筆
        int to = Math.min(from + row_per_pg, order_detail_list.size());
        for (int i = from; i < to; i++) {
            table_pos.getItems().add(order_detail_list.get(i));
        }

    }

    @FXML
    private void findID(ActionEvent event) {
        order_detail_list = ordao.findById(text_order_num.getText());
        loadTable_pos();
        String IDmsg = String.format("訂單編號：%s，查詢結果：\n", text_order_num.getText());
        display.setText(IDmsg);
    }
    

    @FXML
    private void findName(ActionEvent event) {
        order_detail_list = ordao.findByName(text_customer_name.getText());
        loadTable_pos();
        String Namemsg = String.format("訂購者姓名：%s，查詢結果：\n", text_customer_name.getText());
        display.setText(Namemsg);
    }

    @FXML
    private void findPhone(ActionEvent event) {
        order_detail_list = ordao.findByPhone(text_customer_phone.getText());
        loadTable_pos();
        String Phonemsg = String.format("訂購者電話：%s，查詢結果：\n", text_customer_phone.getText());
        display.setText(Phonemsg);
    }

    @FXML
    private void radio_order_num(ActionEvent event) {
        text_order_num.setVisible(true);
        
        btn_order_num.setVisible(true);
        text_customer_name.setVisible(false);
        btn_customer_name.setVisible(false);
        text_customer_phone.setVisible(false);
        btn_customer_phone.setVisible(false);
    }

    @FXML
    private void radio_customer_name(ActionEvent event) {
        text_order_num.setVisible(false);
        btn_order_num.setVisible(false);
        text_customer_name.setVisible(true);
        btn_customer_name.setVisible(true);
        text_customer_phone.setVisible(false);
        btn_customer_phone.setVisible(false);
    }

    @FXML
    private void radio_customer_phone(ActionEvent event) {
        text_order_num.setVisible(false);
        btn_order_num.setVisible(false);
        text_customer_name.setVisible(false);
        btn_customer_name.setVisible(false);
        text_customer_phone.setVisible(true);
        btn_customer_phone.setVisible(true);
    }

}
