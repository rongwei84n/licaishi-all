package com.auts.lcs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auts.lcs.consts.Const;
import com.auts.lcs.model.response.BannerResponseModel;
import com.auts.lcs.model.response.BannerResponseModel.Banner;
import com.auts.lcs.model.response.BannerResponseModel.Result;
import com.auts.lcs.util.MyResponseutils;

/**
 * 首页Banner图的控制.
 * @author huangrongwei
 *
 */
@RestController
@CrossOrigin
public class BannerController extends SBaseController {

    @RequestMapping(value = "/v1/get_banners", method = RequestMethod.GET, produces = { "application/json" })
    public BannerResponseModel getBanner(HttpServletRequest request) {
        BannerResponseModel rsp = new BannerResponseModel();
        rsp.setStatus(Const.STATUS_OK);
        rsp.setMessage(MyResponseutils.parseMsg(Const.STATUS_OK));

        List<Banner> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Banner banner = new Banner();
            banner.setName("pic-" + i);
            banner.setUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521182162941&di=e61191aa974e4ea5e711bcf0b14a4a3a&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3973334759%2C722530092%26fm%3D214%26gp%3D0.jpg");
            list.add(banner);
        }
        Result result = new Result();
        result.setList(list);
        rsp.setResult(result);

        return rsp;
    }
}
