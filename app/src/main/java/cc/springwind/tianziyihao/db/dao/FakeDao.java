package cc.springwind.tianziyihao.db.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cc.springwind.tianziyihao.entity.GoodDetailInfo;

/**
 * Created by HeFan on 2016/7/12.
 */
public class FakeDao implements Serializable {
    static String jsonStrOfHomeGroupLists = "[\n" +
            "{\n" +
            "\"name\":\"生鲜肉类\",\n" +
            "\"child\":[\n" +
            "{\n" +
            "\"name\":\"田标鸡\",\n" +
            "\"price\":\"180元/份\",\n" +
            "\"url\":\"http://ww3.sinaimg.cn/mw690/639a7bf1gw1ex6th728cij20c80cstbj.jpg\"\n" +
            "},\n" +
            "{\n" +
            "\"name\":\"田标鸡\",\n" +
            "\"price\":\"180元/份\",\n" +
            "\"url\":\"http://ww3.sinaimg.cn/mw690/639a7bf1gw1ex6th728cij20c80cstbj.jpg\"\n" +
            "},\n" +
            "{\n" +
            "\"name\":\"田标鸡\",\n" +
            "\"price\":\"180元/份\",\n" +
            "\"url\":\"http://ww3.sinaimg.cn/mw690/639a7bf1gw1ex6th728cij20c80cstbj.jpg\"\n" +
            "},\n" +
            "{\n" +
            "\"name\":\"田标鸡\",\n" +
            "\"price\":\"180元/份\",\n" +
            "\"url\":\"http://ww3.sinaimg.cn/mw690/639a7bf1gw1ex6th728cij20c80cstbj.jpg\"\n" +
            "}\n" +
            "]\n" +
            "},\n" +
            "{\n" +
            "\"name\":\"生鲜肉类\",\n" +
            "\"child\":[\n" +
            "{\n" +
            "\"name\":\"田标鸡\",\n" +
            "\"price\":\"180元/份\",\n" +
            "\"url\":\"http://ww3.sinaimg.cn/mw690/639a7bf1gw1ex6th728cij20c80cstbj.jpg\"\n" +
            "},\n" +
            "{\n" +
            "\"name\":\"田标鸡\",\n" +
            "\"price\":\"180元/份\",\n" +
            "\"url\":\"http://ww3.sinaimg.cn/mw690/639a7bf1gw1ex6th728cij20c80cstbj.jpg\"\n" +
            "},\n" +
            "{\n" +
            "\"name\":\"田标鸡\",\n" +
            "\"price\":\"180元/份\",\n" +
            "\"url\":\"http://ww3.sinaimg.cn/mw690/639a7bf1gw1ex6th728cij20c80cstbj.jpg\"\n" +
            "},\n" +
            "{\n" +
            "\"name\":\"田标鸡\",\n" +
            "\"price\":\"180元/份\",\n" +
            "\"url\":\"http://ww3.sinaimg.cn/mw690/639a7bf1gw1ex6th728cij20c80cstbj.jpg\"\n" +
            "}\n" +
            "]\n" +
            "}\n" +
            "]";

    String jsonStrOfHomeLimitPurchaseList = "[{\n" +
            "\"id\":\"sg001\",\n" +
            "\"url\": \"http://ww4.sinaimg.cn/mw690/651ebbd5gw1ew4dcw7rwjj20j60cs0vc.jpg\",\n" +
            "\"name\": \"猪前腿肉\",\n" +
            "\"price\": \"10元/斤\",\n" +
            "\"priceOrigin\": \"20元/斤\",\n" +
            "\"limitPurchase\": \"限时抢购\"\n" +
            "},\n" +
            "{\n" +
            "\"id\":\"sg002\",\n" +
            "\"url\": \"http://ww4.sinaimg.cn/mw690/651ebbd5gw1ew4dcw7rwjj20j60cs0vc.jpg\",\n" +
            "\"name\": \"香港大菠萝\",\n" +
            "\"price\": \"10元/斤\",\n" +
            "\"priceOrigin\": \"20元/斤\",\n" +
            "\"limitPurchase\": \"限时抢购\"\n" +
            "}\n" +
            ",{\n" +
            "\"id\":\"sg003\",\n" +
            "\"url\": \"http://ww4.sinaimg.cn/mw690/651ebbd5gw1ew4dcw7rwjj20j60cs0vc.jpg\",\n" +
            "\"name\": \"菲律宾大芒果\",\n" +
            "\"price\": \"10元/斤\",\n" +
            "\"priceOrigin\": \"20元/斤\",\n" +
            "\"limitPurchase\": \"限时抢购 15点前\"\n" +
            "}\n" +
            "\n" +
            "]";

    String jsonStrOfGoodDetail = "{\n" +
            "\"good_id\":\"zr001\",\n" +
            "\"reveal_img_urls\":[\n" +
            "{\"reveal_img_url\":\"http://ww4.sinaimg.cn/mw690/ac593e95jw1f318fk3q47j20k60kdgot.jpg\"},\n" +
            "{\"reveal_img_url\":\"http://ww3.sinaimg.cn/mw690/ac593e95jw1f318fk3yrmj20k70k4777.jpg\"},\n" +
            "{\"reveal_img_url\":\"http://ww1.sinaimg.cn/mw690/ac593e95jw1f318fl5i30j20gq0gl41f.jpg\"}\n" +
            "],\n" +
            "\"good_name\":\"猪前腿肉 400g\",\n" +
            "\"good_description\":\"猪前腿肉，又称夹心肉，位于前腿上部，半肥半瘦，肉老筋多，吸收水分能力强 前腿肉又称夹心肉，位于前腿上部，半肥半瘦，肉老筋多，吸收水分能力较强，适于做馅和肉丸子。" +
            "(亲，我们对产品的重量力求精确，生鲜肉类实际重量±5%为正常范围。）\",\n" +
            "\"price\":\"16.1\",\n" +
            "\"price_original\":\"￥18.2/份\",\n" +
            "\"notes\":\"温馨提示：当天订货，次日配送。配送范围详见下方购物须知。限购时间，每天00:00~15:00限量抢购哦。\",\n" +
            "\"good_params\":[{\"good_param\":\"产品编号：SZ0033-zqtr\"},\n" +
            "{\"good_param\":\"产品规格：400g 盒\"},\n" +
            "{\"good_param\":\"产品产地：福州▪沙县▪清流\"},\n" +
            "{\"good_param\":\"产品品牌：田字一号\"},\n" +
            "{\"good_param\":\"储存方式：冷藏保鲜\"}],\n" +
            "\"service_support\":\"限时配送\",\n" +
            "\"comments\":\"好,支持,威武,有希望了\",\n" +
            "\"detail_img_urls\":\n" +
            "[\n" +
            "{\"detail_img_url\":\"http://ww4.sinaimg.cn/mw690/ac593e95jw1f318fk3q47j20k60kdgot.jpg\"},\n" +
            "{\"detail_img_url\":\"http://ww3.sinaimg.cn/mw690/ac593e95jw1f318fk3yrmj20k70k4777.jpg\"},\n" +
            "{\"detail_img_url\":\"http://ww1.sinaimg.cn/mw690/ac593e95jw1f318fl5i30j20gq0gl41f.jpg\"}\n" +
            "]\n" +
            "}";

    private String jsonStrOfClassifyGroupLists = "[\n" +
            "{\n" +
            "\"name\":\"生鲜肉类\",\n" +
            "\"groupList\":[\n" +
            "{\n" +
            "\"id\":\"zr\",\n" +
            "\"name\":\"猪肉\",\n" +
            "\"url\":\"http://ww2.sinaimg.cn/mw690/ac593e95jw1f2s0ha8bdrj20ce07ztab.jpg\"\n" +
            "},\n" +
            "{\n" +
            "\"id\":\"jr\",\n" +
            "\"name\":\"鸡肉\",\n" +
            "\"url\":\"http://ww2.sinaimg.cn/mw690/ac593e95jw1f2s0ha8bdrj20ce07ztab.jpg\"\n" +
            "}\n" +
            "]\n" +
            "},\n" +
            "{\n" +
            "\"name\":\"禽蛋粮油\",\n" +
            "\"groupList\":[\n" +
            "{\n" +
            "\"id\":\"dp\",\n" +
            "\"name\":\"蛋品\",\n" +
            "\"url\":\"http://ww2.sinaimg.cn/mw690/ac593e95jw1f2s0ha8bdrj20ce07ztab.jpg\"\n" +
            "},\n" +
            "{\n" +
            "\"id\":\"ly\",\n" +
            "\"name\":\"粮油\",\n" +
            "\"url\":\"http://ww2.sinaimg.cn/mw690/ac593e95jw1f2s0ha8bdrj20ce07ztab.jpg\"\n" +
            "}\n" +
            "]\n" +
            "}\n" +
            "]";

    private String jsonStrOfGoodSimpleInfo = "[\n" +
            "{\n" +
            "\"id\":\"zr001\",\n" +
            "\"name\":\"猪前腿肉 400g\",\n" +
            "\"price\":\"18.2\",\n" +
            "\"saleCount\":\"821\",\n" +
            "\"url\":\"http://ww4.sinaimg.cn/mw690/005IRmh8jw1f28d6sjhfvj30fk08rdgl.jpg\"\n" +
            "},\n" +
            "{\n" +
            "\"id\":\"zr001\",\n" +
            "\"name\":\"猪前腿肉 400g\",\n" +
            "\"price\":\"18.2\",\n" +
            "\"saleCount\":\"821\",\n" +
            "\"url\":\"http://ww4.sinaimg.cn/mw690/005IRmh8jw1f28d6sjhfvj30fk08rdgl.jpg\"\n" +
            "},\n" +
            "{\n" +
            "\"id\":\"zr001\",\n" +
            "\"name\":\"猪前腿肉 400g\",\n" +
            "\"price\":\"18.2\",\n" +
            "\"saleCount\":\"821\",\n" +
            "\"url\":\"http://ww4.sinaimg.cn/mw690/005IRmh8jw1f28d6sjhfvj30fk08rdgl.jpg\"\n" +
            "}\n" +
            ",\n" +
            "{\n" +
            "\"id\":\"zr001\",\n" +
            "\"name\":\"猪前腿肉 400g\",\n" +
            "\"price\":\"18.2\",\n" +
            "\"saleCount\":\"821\",\n" +
            "\"url\":\"http://ww4.sinaimg.cn/mw690/005IRmh8jw1f28d6sjhfvj30fk08rdgl.jpg\"\n" +
            "}\n" +
            ",\n" +
            "{\n" +
            "\"id\":\"zr001\",\n" +
            "\"name\":\"猪前腿肉 400g\",\n" +
            "\"price\":\"18.2\",\n" +
            "\"saleCount\":\"821\",\n" +
            "\"url\":\"http://ww4.sinaimg.cn/mw690/005IRmh8jw1f28d6sjhfvj30fk08rdgl.jpg\"\n" +
            "}\n" +
            "]";

    public List<HomeGoodGroup> getHomeGoodLists() {
        final List<HomeGoodGroup> listOfHomeGoodGroup = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Temp>>() {
        }.getType();
        List<Temp> listOfTemp = gson.fromJson(jsonStrOfHomeGroupLists, type);

        for (Temp temp :
                listOfTemp) {
            type = new TypeToken<List<HomeGoodChild>>() {
            }.getType();
            List<HomeGoodChild> listOfHomeGoodChild = gson.fromJson(gson.toJson(temp.child), type);
            HomeGoodGroup homeGoodGroup = new HomeGoodGroup();
            homeGoodGroup.name = temp.name;
            homeGoodGroup.homeGoodChildList = listOfHomeGoodChild;

            listOfHomeGoodGroup.add(homeGoodGroup);
        }
        return listOfHomeGoodGroup;
    }

    private List<HomeGoodChild> getChild(String child) {
        Type type = new TypeToken<List<HomeGoodChild>>() {
        }.getType();
        Gson gson = new Gson();
        List<HomeGoodChild> listOfHomeGoodChild = gson.fromJson(child, type);
        return listOfHomeGoodChild;
    }

    public List<HomeLimitPurchaseGood> getHomeLimitPurchaseList() {
        Type type = new TypeToken<List<HomeLimitPurchaseGood>>() {
        }.getType();
        Gson gson = new Gson();
        List<HomeLimitPurchaseGood> listOfHomeLimitPurchaseGood = gson.fromJson(jsonStrOfHomeLimitPurchaseList, type);
        return listOfHomeLimitPurchaseGood;
    }

    public GoodDetailInfo queryGoodDetailWithId(String id) {
        GoodDetailInfo goodDetailInfo = new GoodDetailInfo();
        try {
            JSONObject jsonObject = new JSONObject(jsonStrOfGoodDetail);

            goodDetailInfo.good_id = jsonObject.optString("good_id");
            goodDetailInfo.good_name = jsonObject.optString("good_name");
            goodDetailInfo.good_description = jsonObject.optString("good_description");
            goodDetailInfo.price = jsonObject.optString("price");
            goodDetailInfo.price_original = jsonObject.optString("price_original");
            goodDetailInfo.notes = jsonObject.optString("notes");
            goodDetailInfo.service_support = jsonObject.optString("service_support");
            goodDetailInfo.comments = jsonObject.optString("comments");

            JSONArray reveal_img_urls = jsonObject.optJSONArray("reveal_img_urls");
            if (reveal_img_urls != null && reveal_img_urls.length() > 0) {
                int length = reveal_img_urls.length();
                goodDetailInfo.reveal_img_urls = new ArrayList<>(length);
                JSONObject tmpObject;
                for (int ix = 0; ix < length; ix++) {
                    tmpObject = reveal_img_urls.optJSONObject(ix);
                    if (tmpObject != null) {
                        goodDetailInfo.reveal_img_urls.add(tmpObject.optString("reveal_img_url"));
                    }
                }
            }

            JSONArray good_params = jsonObject.optJSONArray("good_params");
            if (good_params != null && good_params.length() > 0) {
                int length = good_params.length();
                goodDetailInfo.good_params = new ArrayList<>(length);
                JSONObject tmpObject;
                for (int ix = 0; ix < length; ix++) {
                    tmpObject = good_params.optJSONObject(ix);
                    if (tmpObject != null) {
                        goodDetailInfo.good_params.add(tmpObject.optString("good_param"));
                    }
                }
            }

            JSONArray detail_img_urls = jsonObject.optJSONArray("detail_img_urls");
            if (detail_img_urls != null && detail_img_urls.length() > 0) {
                int length = detail_img_urls.length();
                goodDetailInfo.detail_img_urls = new ArrayList<>(length);
                JSONObject tmpObject;
                for (int ix = 0; ix < length; ix++) {
                    tmpObject = detail_img_urls.optJSONObject(ix);
                    if (tmpObject != null) {
                        goodDetailInfo.detail_img_urls.add(tmpObject.optString("detail_img_url"));
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return goodDetailInfo;
    }

    public List<ClassifyGroup> getClassifyGroupList() {
        List<ClassifyGroup> classifyGroupList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonStrOfClassifyGroupLists);
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    ClassifyGroup group = new ClassifyGroup();
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    group.name = jsonObject.optString("name");
                    JSONArray groupList = jsonObject.optJSONArray("groupList");
                    List<SecondLevelGroup> secondLevelGroupList = new ArrayList<>();
                    for (int j = 0; j < groupList.length(); j++) {
                        SecondLevelGroup secondLevelGroup = new SecondLevelGroup();
                        secondLevelGroup.id = groupList.optJSONObject(j).optString("id");
                        secondLevelGroup.name = groupList.optJSONObject(j).optString("name");
                        secondLevelGroup.url = groupList.optJSONObject(j).optString("url");
                        secondLevelGroupList.add(secondLevelGroup);
                    }
                    group.groupList = secondLevelGroupList;
                    classifyGroupList.add(group);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return classifyGroupList;
    }

    public List<GoodSimpleInfo> getClassifyGoodListBySortType(String sortType) {
        Type type = new TypeToken<List<GoodSimpleInfo>>() {
        }.getType();
        Gson gson = new Gson();
        List<GoodSimpleInfo> goodSimpleInfoList = gson.fromJson(jsonStrOfGoodSimpleInfo, type);
        return goodSimpleInfoList;
    }

    public List<HashMap<String, String>> getScrollImageUrls() {
        List<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("small", "http://ww1.sinaimg.cn/mw690/8282c7cfjw1f555o9qomij20c808c3z9.jpg");
        map1.put("big", "http://ww1.sinaimg.cn/mw690/94dfe97bgw1f563jgqp3fj20hs1kh453.jpg");
        list.add(map1);
        HashMap<String, String> map2 = new HashMap<>();
        map2.put("small", "http://ww4.sinaimg.cn/mw690/8282c7cfjw1f555o9o8poj20c808cdh3.jpg");
        map2.put("big", "http://ww4.sinaimg.cn/mw690/94dfe97bgw1f563jh08j8j20hs1g6q9v.jpg");
        list.add(map2);
        HashMap<String, String> map3 = new HashMap<>();
        map3.put("small", "http://ww2.sinaimg.cn/mw690/938718b5jw1epx05dfpqwj20bs06uwey.jpg");
        map3.put("big", "http://ww3.sinaimg.cn/mw690/94dfe97bgw1f563jhhw79j20hs1fv43a.jpg");
        list.add(map3);
        HashMap<String, String> map4 = new HashMap<>();
        map4.put("small", "http://ww4.sinaimg.cn/mw690/8282c7cfjw1f555o9xsydj20c808c3z7.jpg");
        map4.put("big", "http://ww1.sinaimg.cn/mw690/94dfe97bgw1f563jhy60mj20hs1jt79q.jpg");
        list.add(map4);

        return list;
    }

    public class HomeGoodGroup {
        public String name;
        public List<HomeGoodChild> homeGoodChildList;
    }

    public class HomeGoodChild {
        public String name;
        public String price;
        public String url;
    }

    public class HomeLimitPurchaseGood {
        public String id;
        public String url;
        public String price;
        public String limitPurchase;
        public String name;
        public String priceOrigin;
    }

    public class Temp {
        public String name;
        public Object child;
    }

    public class ClassifyGroup implements Serializable {
        public String name;
        public List<SecondLevelGroup> groupList;
    }

    public class SecondLevelGroup implements Serializable {
        public String id;
        public String name;
        public String url;
    }

    public class GoodSimpleInfo {
        public String id;
        public String name;
        public String price;
        public String url;
        public String saleCount;
    }

}
