package com.auts.lcssv.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.litebean.OrderItemDetailBean;
import com.auts.lcssv.presenter.OrderItemDetailPresenter;
import com.auts.lcssv.presenter.viewback.OrderItemDetailView;
import com.auts.lcssv.util.LogUtils;

import butterknife.BindView;

/**
 * 订单详情界面,如果是待打款，还可以取消订单和上传凭证.
 */

public class OrderItemDetailActivity extends BaseActivity {

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

    @BindView(R.id.tv_product_qixian)
    TextView tvProductQixian; //产品期限

    @BindView(R.id.tv_area)
    TextView tvArea; //投资领域

    @BindView(R.id.tv_fanyong_present)
    TextView tvFanyongPresent; //返佣比例

    @BindView(R.id.tv_fanyong_amount)
    TextView tvFanyongAmount; //返佣金额

    @BindView(R.id.tv_contract_status)
    TextView tvContractStatus; //合同状态

    @BindView(R.id.tv_product_status)
    TextView tvProductStatus; //产品发行状态

    @BindView(R.id.tv_pay_status)
    TextView tvPayStatus; //支付状态

    @BindView(R.id.tv_dakuan_certificate)
    TextView tvDakuanCertificate; //打款凭证

    @BindView(R.id.tv_customer_name)
    TextView tvCustomerName; //客户姓名

    @BindView(R.id.tv_customer_phone)
    TextView tvCustomerPhone; //客户手机号

    @BindView(R.id.tv_tip)
    TextView tvTip; //客户手机号

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
                orderStatus = orderBean.getResult().getStatus();

                if (ORDER_STATUS_WAIT_PAY.equals(orderStatus)) {
                    btnCancelOrder.setVisibility(View.VISIBLE);
                } else {
                    btnCancelOrder.setVisibility(View.GONE);
                }

                tvOrderId.setText(orderBean.getResult().getOrderNO());
                tvProductname.setText(orderBean.getResult().getProductShortName());
                tvCustomerName.setText(orderBean.getResult().getCustomerName());
            }

            @Override
            public void onGetOrderError() {
                LogUtils.debug("onGetOrderSuccess " );
            }
        });

        presenter.getOrderDetailByID(orderid);

        LogUtils.debug("orderid: " + orderid);
    }
}
