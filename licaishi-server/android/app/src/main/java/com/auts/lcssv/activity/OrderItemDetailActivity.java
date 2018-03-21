package com.auts.lcssv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.litebean.OrderItemDetailBean;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.listener.GetPhotoBeforeListener;
import com.auts.lcssv.manager.imageloader.GlideCircleTransform;
import com.auts.lcssv.manager.imageloader.ImageLoader;
import com.auts.lcssv.popup.GetPhotoPopup;
import com.auts.lcssv.presenter.OrderItemDetailPresenter;
import com.auts.lcssv.presenter.viewback.OrderItemDetailView;
import com.auts.lcssv.util.Base64Utils;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.util.PathUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单详情界面,如果是待打款，还可以取消订单和上传凭证.
 */

public class OrderItemDetailActivity extends BaseActivity implements GetPhotoBeforeListener {

    private static String ORDER_STATUS_WAIT_PAY = "01";

    private String orderid;

    private String orderStatus;

    @BindView(R.id.btn_cancel_order)
    Button btnCancelOrder;

    @BindView(R.id.tv_orderid)
    TextView tvOrderId; //订单ID

    @BindView(R.id.tv_amount)
    TextView tvAmount; //订单金额

    @BindView(R.id.tv_productname)
    TextView tvProductname; //产品名称


    @BindView(R.id.tv_comratio)
    TextView tvComratio; //返佣比例

    @BindView(R.id.tv_commission)
    TextView tvCommission; //返佣金额

    @BindView(R.id.tv_pro_ratio)
    TextView tvProRatio;//预期收益率

    @BindView(R.id.tv_profit)
    TextView tvProfit; //客户收益

    @BindView(R.id.tv_contract_status)
    TextView tvContractStatus; //合同状态

    @BindView(R.id.tv_voucher_status)
    TextView tvVoucherStatus; //支付凭证上传状态

    @BindView(R.id.tv_voucher_path)
    TextView tvVoucherPath; //上传打款凭证

    @BindView(R.id.img_voucher_data)
    ImageView imgVoucherData;

    @BindView(R.id.tv_customer_name)
    TextView tvCustomerName; //客户姓名

    OrderItemDetailPresenter presenter;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_item_detail);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.tv_order_detail_title);
        orderid = getIntent().getStringExtra("orderid");

        presenter = new OrderItemDetailPresenter(null, new OrderItemDetailView() {
            @Override
            public void onGetOrderSuccess(OrderItemDetailBean orderBean) {
                if (orderBean == null
                        || orderBean.getStatus() != 200
                        || orderBean.getResult() == null
                        || TextUtils.isEmpty(orderBean.getResult().getOrderNO())) {
                    return;
                }
                orderStatus = orderBean.getResult().getStatus(); //订单状态

                if (ORDER_STATUS_WAIT_PAY.equals(orderStatus)) {
                    btnCancelOrder.setVisibility(View.VISIBLE);
                } else {
                    btnCancelOrder.setVisibility(View.GONE);
                }

                tvOrderId.setText(orderBean.getResult().getOrderNO()); //订单号
                tvAmount.setText(orderBean.getResult().getAmount()); //订单金额
                tvComratio.setText(orderBean.getResult().getComRatio());//返佣比例
                tvCommission.setText(orderBean.getResult().getCommission()); //返佣金额
                tvProRatio.setText(orderBean.getResult().getProRatio());
                tvProfit.setText(orderBean.getResult().getProfit());//客户收益
                tvContractStatus.setText(orderBean.getResult().getContractStatus());
                tvVoucherStatus.setText(orderBean.getResult().getVoucher_status());//支付凭证上传状态
//                imgVoucherData.setimageorderBean.getResult().getVoucher_path()
                tvCustomerName.setText(orderBean.getResult().getCustomerName()); //客户姓名

                tvProductname.setText(orderBean.getResult().getProductShortName()); //产品名称
            }

            @Override
            public void onGetOrderError() {
                LogUtils.debug("onGetOrderSuccess " );
            }
        });

        presenter.getOrderDetailByID(orderid);

        LogUtils.debug("orderid: " + orderid);
    }

    //点击登录
    @OnClick(R.id.tv_voucher_path)
    public void tv_upload() {
        LogUtils.debug("upload");
        GetPhotoPopup photoPopup = new GetPhotoPopup(this, this);
        photoPopup.showAsDropDown(imgVoucherData);
    }

    @Override
    public void getPhotoFromCamera() {
        Intent intent = new Intent(this, GetPhotoActivity.class);
        intent.putExtra("type", AppConstans.GetPhoto.GET_PHOTO_FROM_CAMERA);
        startActivityForResult(intent, AppConstans.GetPhoto.ONE_DRAGON);
    }

    @Override
    public void getPhotoFromAlbum() {
        Intent intent = new Intent(this, GetPhotoActivity.class);
        intent.putExtra("type", AppConstans.GetPhoto.GET_PHOTO_FROM_ALBUM);
        startActivityForResult(intent, AppConstans.GetPhoto.ONE_DRAGON);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AppConstans.GetPhoto.ONE_DRAGON:
                if (resultCode == RESULT_OK && data != null) {
                    String imageString = data.getStringExtra("image_string");
                    byte[] buf = Base64Utils.decode(imageString);
                    InputStream inputStream = new ByteArrayInputStream(buf);

                    String dir = PathUtils.getCameraImageDir();
                    File file = new File(dir, System.currentTimeMillis() + ".jpg");
                    try {
                        OutputStream os = new FileOutputStream(file);
                        int bytesRead;
                        byte[] buffer = new byte[8192];
                        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                        os.close();
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String imageUrl = file.getAbsolutePath();
                    if (TextUtils.isEmpty(imageUrl)) {
                        imageUrl = "";
                    }
                    LogUtils.error("=====imageUrl6==", imageUrl + "===");
                    ImageLoader.getLoader(this)
                            .load(imageUrl)
                            .placeholder(R.drawable.head_portrait)
//                            .transform(new GlideCircleTransform(this))
                            .into(imgVoucherData);
                    presenter.uploadBase64(imageString, "1", orderid);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }

    }

}
