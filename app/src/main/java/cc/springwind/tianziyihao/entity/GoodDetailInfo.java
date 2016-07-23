package cc.springwind.tianziyihao.entity;

import java.util.List;

/**
 * Created by HeFan on 2016/7/14.
 */
public class GoodDetailInfo {
    public String good_id;

    public List< String> reveal_img_urls ;

    public String good_name;

    public String good_description;

    public String price;

    public String price_original;

    public String notes;

    public List<String> good_params ;

    public String service_support;

    public String comments;

    public List<String> detail_img_urls ;

    @Override
    public String toString() {
        return super.toString();
    }
}
