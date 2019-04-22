package cn.yznu.hh.controller.actioncontroller;

import cn.yznu.hh.service.HHService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    /**
     * 自动注入hhService
     */
    @Autowired
    @Qualifier("hhService")
    private HHService hhService;

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "commonPage/main";
    }


}
