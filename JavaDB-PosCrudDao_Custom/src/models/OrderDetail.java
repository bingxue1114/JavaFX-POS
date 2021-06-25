package models;

public class OrderDetail {

    private int id;
    private String order_num;
    private String product_id;
    private int quantity;
    private int product_price;
    private String product_name;
    private String finished;
    private String customer_name;
    private String customer_address;
    private String customer_phtone;

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_phtone() {
        return customer_phtone;
    }

    public void setCustomer_phtone(String customer_phtone) {
        this.customer_phtone = customer_phtone;
    }

    public OrderDetail() {
    }

    public OrderDetail(int id, String order_num, String product_name, int quantity, String finished) {
        this.id = id;
        this.order_num = order_num;
        this.product_name = product_name;
        this.quantity = quantity;
        this.finished = finished;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }



}
