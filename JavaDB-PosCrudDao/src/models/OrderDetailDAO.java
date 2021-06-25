package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {

    private Connection conn;

    public List<OrderDetail> getAllOrdersDetail() {
        conn = DBConnection.getConnection();
        String query = "select * from order_detail";
        List<OrderDetail> order_detail_list = new ArrayList();

        try {
            PreparedStatement ps
                    = conn.prepareStatement(query);
            ResultSet rset = ps.executeQuery();

            while (rset.next()) {
                OrderDetail orderdetail = new OrderDetail();
                orderdetail.setId(rset.getInt("id"));
                orderdetail.setOrder_num(rset.getString("order_num"));
                orderdetail.setProduct_name(rset.getString("product_name"));
                orderdetail.setQuantity(rset.getInt("quantity"));
                orderdetail.setFinished(rset.getString("finished"));
                order_detail_list.add(orderdetail);

            }
        } catch (SQLException ex) {
            System.out.println("getAllproducts異常:" + ex.toString());
        }

        return order_detail_list;

    }

    public boolean delete(int id) {
        conn = DBConnection.getConnection();
        String query = "delete from order_detail where id =?";
        boolean sucess = false;
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            sucess = statement.executeUpdate() > 0;
            if (sucess) {
                System.out.println("Record deleted successfully.");
            } else {
                System.out.println("Record not found.");
            }
        } catch (SQLException ex) {
            System.out.println("delete異常:\n" + ex.toString());
        }
        return sucess;
    }

//       
    public void update(OrderDetail orderdetail) {
        conn = DBConnection.getConnection();
        String query = "update order_detail set finished=? where id = ?";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, orderdetail.getFinished());
            state.setInt(2, orderdetail.getId());

            state.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("update異常:" + ex.toString());
        }
    }

    //選擇某個order_num
    public List<OrderDetail> findById(String order_num) {
        conn = DBConnection.getConnection();
        boolean success = false;
        List<OrderDetail> order_detail_list = new ArrayList();
        String query = "select * from order_detail where order_num like ?";

        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, "%" + order_num + "%");
            ResultSet rset = state.executeQuery();

            while (rset.next()) {
                success = true;
                OrderDetail orderdetail = new OrderDetail();
                orderdetail.setId(rset.getInt("id"));
                orderdetail.setOrder_num(rset.getString("order_num"));
                orderdetail.setProduct_name(rset.getString("product_name"));
                orderdetail.setQuantity(rset.getInt("quantity"));
                orderdetail.setFinished(rset.getString("finished"));
                order_detail_list.add(orderdetail);
            }
        } catch (SQLException ex) {
            System.out.println("資料庫selectByID操作異常:" + ex.toString());
        }

        if (success) {
            return order_detail_list;
        } else {
            return null;
        }

    }
    
    
    //選擇未完成
    public List<OrderDetail> findByNotFinished(String finished) {
        conn = DBConnection.getConnection();
        boolean success = false;
        List<OrderDetail> order_detail_list = new ArrayList();
        String query = "select * from order_detail where finished = ?";

        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, finished);
            ResultSet rset = state.executeQuery();

            while (rset.next()) {
                success = true;
                OrderDetail orderdetail = new OrderDetail();
                orderdetail.setId(rset.getInt("id"));
                orderdetail.setOrder_num(rset.getString("order_num"));
                orderdetail.setProduct_name(rset.getString("product_name"));
                orderdetail.setQuantity(rset.getInt("quantity"));
                orderdetail.setFinished(rset.getString("finished"));
                order_detail_list.add(orderdetail);
            }
        } catch (SQLException ex) {
            System.out.println("資料庫selectByID操作異常:" + ex.toString());
        }

        if (success) {
            return order_detail_list;
        } else {
            return null;
        }

    }
    
    //選擇完成
    public List<OrderDetail> findByFinished(String finished) {
        conn = DBConnection.getConnection();
        boolean success = false;
        List<OrderDetail> order_detail_list = new ArrayList();
        String query = "select * from order_detail where finished = ?";

        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, finished);
            ResultSet rset = state.executeQuery();

            while (rset.next()) {
                success = true;
                OrderDetail orderdetail = new OrderDetail();
                orderdetail.setId(rset.getInt("id"));
                orderdetail.setOrder_num(rset.getString("order_num"));
                orderdetail.setProduct_name(rset.getString("product_name"));
                orderdetail.setQuantity(rset.getInt("quantity"));
                orderdetail.setFinished(rset.getString("finished"));
                order_detail_list.add(orderdetail);
            }
        } catch (SQLException ex) {
            System.out.println("資料庫selectByID操作異常:" + ex.toString());
        }

        if (success) {
            return order_detail_list;
        } else {
            return null;
        }

    }

}
