package cc.springwind.tianziyihao.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.bean.CartInfo;
import cc.springwind.tianziyihao.ui.fragment.CartFragment;

/**
 * 购物车列表项的适配器
 */
public class CartListAdapter extends BaseAdapter {
    private CartFragment cartFragment;
    private DecimalFormat decimalFormat;
    private CartInfo item;
    private boolean isSelected = false;
    private List<CartInfo> cartInfoList;

    public CartListAdapter(CartFragment cartFragment, List<CartInfo> cartInfoList) {
        this.cartFragment = cartFragment;
        this.cartInfoList = cartInfoList;
        decimalFormat = new DecimalFormat("0.0");
    }

    @Override
    public int getCount() {
        return cartInfoList.size();
    }

    @Override
    public CartInfo getItem(int position) {
        return cartInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(cartFragment.getContext(), R.layout.item_cart, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        item = getItem(position);

        ImageLoader.getInstance().displayImage(item.good_img_url, viewHolder.ivCartItem);
        viewHolder.tvCartItem.setText(item.good_name);
        viewHolder.proCount.setText(item.count + "");
        viewHolder.tvCartItemPrice.setText(item.good_price);
        viewHolder.tvCartItemCount.setText(item.count + "");
//            viewHolder.tvItemSum.setText(new BigDecimal(Float.parseFloat(item.good_price)).multiply(new BigDecimal
// (item.count)) + "");

        viewHolder.tvItemSum.setText(decimalFormat.format(Float.parseFloat("16.1") * (float) item.count) + "");

        viewHolder.proAdd.setTag(position);
        viewHolder.proReduce.setTag(position);
        viewHolder.tvDelete.setTag(position);
        viewHolder.rbCartItem.setTag(position);
        viewHolder.rbCartItem.setChecked(isSelected);

        viewHolder.tvDelete.setOnClickListener(deleteListener);
        viewHolder.proAdd.setOnClickListener(addListener);
        viewHolder.proReduce.setOnClickListener(reduceListener);
        viewHolder.rbCartItem.setOnCheckedChangeListener(checkListener);

        return convertView;
    }

    public void setDeleteListener(View.OnClickListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setAddListener(View.OnClickListener addListener) {
        this.addListener = addListener;
    }

    public void setReduceListener(View.OnClickListener reduceListener) {
        this.reduceListener = reduceListener;
    }

    public void setCheckListener(CompoundButton.OnCheckedChangeListener checkListener) {
        this.checkListener = checkListener;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    View.OnClickListener deleteListener;
    View.OnClickListener addListener;
    View.OnClickListener reduceListener;
    CompoundButton.OnCheckedChangeListener checkListener;


    class ViewHolder {
        @InjectView(R.id.rb_cart_item)
        CheckBox rbCartItem;
        @InjectView(R.id.iv_cart_item)
        ImageView ivCartItem;
        @InjectView(R.id.tv_cart_item)
        TextView tvCartItem;
        @InjectView(R.id.pro_add)
        Button proAdd;
        @InjectView(R.id.pro_count_good)
        TextView proCount;
        @InjectView(R.id.pro_reduce)
        Button proReduce;
        @InjectView(R.id.tv_cart_item_price)
        TextView tvCartItemPrice;
        @InjectView(R.id.tv_cart_item_count)
        TextView tvCartItemCount;
        @InjectView(R.id.tv_delete)
        TextView tvDelete;
        @InjectView(R.id.tv_item_sum)
        TextView tvItemSum;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
