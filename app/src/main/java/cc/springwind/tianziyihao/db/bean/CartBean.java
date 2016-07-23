package cc.springwind.tianziyihao.db.bean;

/**
 * Created by HeFan on 2016/7/18.
 */
public class CartBean {
    /*create table cart " +
    "(_id integer primary key autoincrement , " +
    "good_id varchar(20)," +
    "good_name varchar(20), " +
    "good_img_url text," +
    "good_price text," +
    "count integer); */
    public String good_id;
    public String good_name;
    public String good_img_url;
    public String good_price;
    public int count;
}
