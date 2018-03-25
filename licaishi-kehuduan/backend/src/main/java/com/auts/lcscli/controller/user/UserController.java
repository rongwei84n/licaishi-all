package com.auts.lcscli.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auts.lcscli.consts.Const;
import com.auts.lcscli.controller.SBaseController;
import com.auts.lcscli.model.common.PhiHomeBaseResponse;
import com.auts.lcscli.model.enums.OrderStatus;
import com.auts.lcscli.model.response.AllInfoCountResponse;
import com.auts.lcscli.service.OrderService;

/**
 * 用户相关功能
 *
 * @author li.bing
 */
@RestController
@CrossOrigin
public class UserController extends SBaseController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    public static final String DEFAULT_CHARSET = "utf-8";
    public static String HTTP_HEAD_AUTHORIZATION = "Authorization";
    public static String HTTP_HEAD_CONTENT_TYPE = "Content-Type";

    @Autowired
    OrderService orderService;

    /**
     * 查询产品列表
     * type 01：集合信托  02集合资管 03债权基金 04股权基金 05阳光私募
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1/user/allInfoCount", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryAllInfoCount(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        AllInfoCountResponse allInfoCount = new AllInfoCountResponse();
        LOGGER.info("queryAllInfoCount type [{}]");
        String token = request.getHeader(Const.AUTHORIZATION);
        LOGGER.info("queryOrders toekn [{}]", token);
        String uid = getUidByToken(token);

        //计算佣金 todo ....
        int totalCount = orderService.queryOrderCountByStatus(null, uid);
        int wpTotalCount = orderService.queryOrderCountByStatus(OrderStatus.WP.getValue(), uid);
        int wcTotalCount = orderService.queryOrderCountByStatus(OrderStatus.WC.getValue(), uid);
        int ocTotalCount = orderService.queryOrderCountByStatus(OrderStatus.OC.getValue(), uid);
        int ofTotalCount = orderService.queryOrderCountByStatus(OrderStatus.OF.getValue(), uid);
        allInfoCount.getStatusMap().put(OrderStatus.WP.getValue(), wpTotalCount);
        allInfoCount.getStatusMap().put(OrderStatus.WC.getValue(), wcTotalCount);
        allInfoCount.getStatusMap().put(OrderStatus.OC.getValue(), ocTotalCount);
        allInfoCount.getStatusMap().put(OrderStatus.OF.getValue(), ofTotalCount);
        allInfoCount.getStatusMap().put("ALL", totalCount);
        rspObj.setResult(allInfoCount);
        return successResponse(rspObj);
    }
}
