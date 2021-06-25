package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAO {

    private Connection conn;

    public String getMaxOrderNum() {
        conn = DBConnection.getConnection();
        String maxVal = null;

        String query = "SELECT Max(order_num) as `max_id` FROM `sale_order`";

        try {
            PreparedStatement state = conn.prepareStatement(query);
            ResultSet rset = state.executeQuery();
            while (rset.next()) {
                maxVal = rset.getString("max_id");
            }
        } catch (SQLException ex) {
            System.out.println("資料庫getMaxOrderNum操作異常:" + ex.toString());
        }
        return maxVal;
    }

    public boolean insertCart(Order cart) {
        conn = DBConnection.getConnection();
        String query = "insert into `sale_order`(order_num,total_price,"
                + "customer_name,customer_phone, customer_address, table_num) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        boolean success = false;
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, cart.getOrder_num());
            state.setInt(2, cart.getTotal_price());
            state.setString(3, cart.getCustomer_name());
            state.setString(4, cart.getCustomer_phtone());
            state.setString(5, cart.getCustomer_address());
            state.setString(6, cart.getLabel_table_num());
            state.execute();
            success = true;
        } catch (SQLException ex) {
            System.out.println("insert異常:" + ex.toString());
        }
        return success;
    }

    //新增訂單明細 應該寫在OrderDetailDAO.java比較好
    public boolean insertOrderDetailItem(OrderDetail item) {
        conn = DBConnection.getConnection();

        String query = "INSERT INTO `order_detail` (`order_num`, `product_id`, product_name, `quantity`, finished,customer_name,customer_phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        boolean success = false;
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, item.getOrder_num());
            state.setString(2, item.getProduct_id());
            state.setString(3, item.getProduct_name());//optional
            state.setInt(4, item.getQuantity());
            state.setString(5, item.getFinished());
state.setString(6, item.getCustomer_name());
state.setString(7, item.getCustomer_phtone());
            
            state.execute();
            success = true;
        } catch (SQLException ex) {
            System.out.println("insert異常:" + ex.toString());
        }
        return success;
    }

    public static void main(String[] args) {

        OrderDAO orddao = new OrderDAO();
        System.out.println(orddao.getMaxOrderNum());

        String ordNum = "ord-501";

        Order cart = new Order();
        cart.setOrder_num(ordNum);
        orddao.insertCart(cart);

        OrderDetail item = new OrderDetail();
        item.setOrder_num(ordNum);
        item.setQuantity(2);
        item.setProduct_id("p-j-103");

        orddao.insertOrderDetailItem(item);
    }

}
