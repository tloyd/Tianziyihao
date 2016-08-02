package cc.springwind.tianziyihao.db.bean;

import java.io.Serializable;

public class Order implements Serializable {
    public int order_id;
    public String username;
    public float sum_price;
    public String receive_district_code;
    public String receive_name;
    public String receive_tel;
    public String receive_address;
    public int order_flag;
}
