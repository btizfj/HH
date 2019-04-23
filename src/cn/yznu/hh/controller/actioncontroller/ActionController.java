package cn.yznu.hh.controller.actioncontroller;

import cn.yznu.hh.bean.Message;
import cn.yznu.hh.bean.Order;
import cn.yznu.hh.bean.Room;
import cn.yznu.hh.bean.User;
import cn.yznu.hh.service.HHService;
import cn.yznu.hh.util.tag.PageModel;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static cn.yznu.hh.util.constants.Constant.*;

@Controller
public class ActionController {

    /**
     * 自动注入brsService
     */
    @Autowired
    @Qualifier("hhService")
    private HHService hhService;

    /**
     * 进入主界面
     * @return
     */
    @RequestMapping(value = "/main")
    public String main(){
        return "commonPage/main";
    }

    /**
     * 进入登录界面
     */
    @RequestMapping(value = "/login")
    public String login(){
        return "commonPage/login";
    }

    /**
     * 进入注册界面
     */
    @RequestMapping(value = "/register")
    public String register(){
        return "commonPage/register";
    }

    /**
     * 注册账户
     */
    @RequestMapping(value = "/registerAccount")
    public String registerAccount(String username, String password, String email){
//        mscPlayerService.saveAccount(username,password,email,0);
//        Constant.curPage = "";
        return "commonPage/success";
    }

    @RequestMapping(value = "/sesetPassword")
    public String sesetPassword(Integer id){
        return "commonPage/sesetPassword";
    }

    /**
     * 账号密码登录
     */
    @RequestMapping(value = "/loginByAccount",method= RequestMethod.POST)
    public String loginByAccount(String username, String password, HttpSession session, Model model){
        String url = null;
        User user = hhService.findUserByAccount(username,password);
        if (user != null){
            session.setAttribute("user",user);
            if (user.getUsertype()==0){//用户
                url="userPage/welcome";
            }else if (user.getUsertype()==1){//理发师
                url="stagePage/welcome";
            }else {//管理员
                url="adminPage/welcome";
            }
        }else {
            url = "commonPage/accountError";
        }
        model.addAttribute("user",user);
        return url;
    }

    /**
     * 消息管理（用户和理发师）
     * @return
     */
    @RequestMapping(value = "/messagePage")
    public String messagePage(Integer pageIndex, HttpSession session, Model model){
        // 创建分页对象
        PageModel pageModel = new PageModel();
        // 如果参数pageIndex不为null，设置pageIndex，即显示第几页
        if(pageIndex != null){
            pageModel.setPageIndex(pageIndex);
        }
        List<Message> messages = null;
        String url = null;
        User user = (User) session.getAttribute("user");
        switch (user.getUsertype()){
            case 0:
                messages = hhService.findUserMessageByPage(user.getId(), pageModel);
                url = "userPage/messagePage";
                break;
            case 1:
                messages = hhService.findAdminMessageByPage(pageModel);
                url = "stagePage/messagePage";
                break;
        }
        model.addAttribute("pageModel", pageModel);
        model.addAttribute("messages",messages);
        model.addAttribute("user",user);
        return url;
    }

    /**
     * 用户管理（管理员）
     * @return
     */
    @RequestMapping(value = "/adminUserManagement")
    public String adminUserManagement(Integer pageIndex, Model model){
        // 创建分页对象
        PageModel pageModel = new PageModel();
        // 如果参数pageIndex不为null，设置pageIndex，即显示第几页
        if(pageIndex != null){
            pageModel.setPageIndex(pageIndex);
        }
        List<User> users = hhService.findAllUserByPage(pageModel);
        model.addAttribute("pageModel", pageModel);
        model.addAttribute("users",users);
        model.addAttribute("userType",userType);
        return "adminPage/userManagement";
    }

    /**
     * 提交房间信息
     * @param msg
     * @return
     */
    @RequestMapping(value = "/submitRoomByParams",method= RequestMethod.POST)
    public String submitRoomByParams(String msg){
        System.out.println(msg);
        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonObject jsonObject = parser.parse(msg).getAsJsonObject();
        String room_id = jsonObject.get("room_id").toString();
        String room_type = jsonObject.get("room_type").toString();
        String price = jsonObject.get("price").toString();
        String filename = jsonObject.get("filename").toString();
        room_id = room_id.replace("\"","");
        room_type = room_type.replace("\"","");
        price = price.replace("\"","");
        filename = filename.replace("\"","");

        hhService.saveRoomToDB(room_id,room_type,price,filename);
        return "commonPage/success";
    }

    /**
     * 理发师管理（管理员）
     * @return
     */
    @RequestMapping(value = "/adminBarberManagement")
    public String adminBarberManagement(Integer pageIndex, Model model){
        // 创建分页对象
        PageModel pageModel = new PageModel();
        // 如果参数pageIndex不为null，设置pageIndex，即显示第几页
        if(pageIndex != null){
            pageModel.setPageIndex(pageIndex);
        }
        List<User> users = hhService.findAllBarberByPage(pageModel);
        model.addAttribute("pageModel", pageModel);
        model.addAttribute("users",users);
        model.addAttribute("userType",userType);
        return "adminPage/barberManagement";
    }

    /**
     * 订单管理（管理员）
     * @return
     */
    @RequestMapping(value = "/adminOrderManagement")
    public String adminOrderManagement(Integer pageIndex, Model model){
        // 创建分页对象
        PageModel pageModel = new PageModel();
        // 如果参数pageIndex不为null，设置pageIndex，即显示第几页
        if(pageIndex != null){
            pageModel.setPageIndex(pageIndex);
        }
        List<Order> orders = hhService.findAllOrderByPage(pageModel);
        model.addAttribute("pageModel", pageModel);
        model.addAttribute("orders",orders);
        model.addAttribute("reservationTime",reservationTime);
        model.addAttribute("orderState",orderState);
        return "adminPage/orderManagement";
    }

    /**
     * 客房管理界面（前台）
     * @param pageIndex
     * @param model
     * @return
     */
    @RequestMapping(value = "/roomManagementPage")
    public String roomManagementPage(Integer pageIndex, Model model){
        // 创建分页对象
        PageModel pageModel = new PageModel();
        // 如果参数pageIndex不为null，设置pageIndex，即显示第几页
        if(pageIndex != null){
            pageModel.setPageIndex(pageIndex);
        }
        List<Room> rooms = hhService.findAllRoomByPage(pageModel);
        model.addAttribute("pageModel", pageModel);
        model.addAttribute("rooms",rooms);
        model.addAttribute("orderState",orderState);
        model.addAttribute("roomType",roomType);
        return "stagePage/roomManagementPage";
    }

    /**
     * 添加客房界面
     * @return
     */
    @RequestMapping(value = "/addRoomPage")
    public String addRoomPage(){
        return "stagePage/addRoomPage";
    }

    @RequestMapping(value = "/welcome")
    public String welcome(HttpSession session){
        String url = null;
        User user = (User) session.getAttribute("user");
        if (user.getUsertype()==0){//用户
            url="userPage/welcome";
        }else if (user.getUsertype()==1){//理发师
            url="stagePage/welcome";
        }else {//管理员
            url="adminPage/welcome";
        }
        return url;
    }

    /**
     * 添加理发师界面
     */
    @RequestMapping(value = "/adminAddBarber")
    public String adminAddBarber(HttpSession session){
        return "adminPage/addBarber";
    }

    /**
     * 添加理发师
     */
    @RequestMapping(value = "/addBarberToDB",method= RequestMethod.POST)
    public String addBarberToDB(String username,String password,String b_name,Integer usertype,String phonenumber){
        hhService.saveBarberToDB(username,password,b_name,usertype,phonenumber);
        return "commonPage/success";
    }

    /**
     * 用户打开预约界面
     * @return
     */
    @RequestMapping(value = "/reservationPage")
    public String reservationPage(Integer pageIndex, HttpSession session, Model model){
        // 创建分页对象
        PageModel pageModel = new PageModel();
        // 如果参数pageIndex不为null，设置pageIndex，即显示第几页
        if(pageIndex != null){
            pageModel.setPageIndex(pageIndex);
        }
        List<Order> orders = null;
        User user = (User) session.getAttribute("user");
        String url = null;
        switch (user.getUsertype()){
            case 0:
                url = "userPage/reservationPage";
                orders = hhService.findOrderByPage(user.getId(),pageModel);
                break;
            case 1:
                url = "stagePage/reservationPage";
                orders = hhService.findAdminOrderByPage(pageModel);
                break;
        }
        model.addAttribute("pageModel", pageModel);
        model.addAttribute("orders",orders);
        model.addAttribute("orderState",orderState);
        model.addAttribute("roomType",roomType);
        model.addAttribute("reservationTime",reservationTime);
        return url;
    }

    @RequestMapping(value = "/commentOrder")
    public String commentOrder(Integer pageIndex, HttpSession session, Model model){
        // 创建分页对象
        PageModel pageModel = new PageModel();
        // 如果参数pageIndex不为null，设置pageIndex，即显示第几页
        if(pageIndex != null){
            pageModel.setPageIndex(pageIndex);
        }
        User user = (User) session.getAttribute("user");

        List<Order> orders =  orders = hhService.findOrderByPage(user.getId(),pageModel);
        String url = null;
        model.addAttribute("pageModel", pageModel);
        model.addAttribute("orders",orders);
        model.addAttribute("orderState",orderState);
        model.addAttribute("roomType",roomType);
        model.addAttribute("reservationTime",reservationTime);
        return "userPage/commentOrder";
    }

    @RequestMapping(value = "/orderCommentPage")
    public String orderCommentPage(Integer pageIndex, HttpSession session, Model model){
        // 创建分页对象
        PageModel pageModel = new PageModel();
        // 如果参数pageIndex不为null，设置pageIndex，即显示第几页
        if(pageIndex != null){
            pageModel.setPageIndex(pageIndex);
        }
        List<Order> orders = hhService.findAllOrderByPage(pageModel);
        model.addAttribute("pageModel", pageModel);
        model.addAttribute("orders",orders);
        model.addAttribute("orderState",orderState);
        model.addAttribute("roomType",roomType);
        model.addAttribute("reservationTime",reservationTime);
        return "stagePage/orderCommentPage";
    }

    @RequestMapping(value = "/deleteMessageById")
    public String deleteMessageById(Integer id, HttpSession session){
        hhService.removeMessageById(id);
//        User user = (User) session.getAttribute("user");
//        String url = null;
//        switch (user.getUsertype()){
//            case 0:
//                url = "userPage/messagePage";
//                break;
//            case 1:
//                url = "stagePage/messagePage";
//                break;
//        }
        return "commonPage/success";
    }

    @RequestMapping(value = "/deleteOrderById")
    public String deleteOrderById(Integer id,HttpSession session){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        User user = (User) session.getAttribute("user");
        //id为订单号，订单里面有房间号（唯一），通过房间号查找修改房间状态为空
        Order order = hhService.findOrderById(id);
        hhService.modifyRoomCancelBookedState(order.getRoom().getRoom_id());
        hhService.removeOrderById(id);
        hhService.saveMessage(-1,"用户"+user.getUsername()+"已取消订单！",java.sql.Date.valueOf(sdf.format(d)));//-1表示管理员消息
        return "commonPage/success";
    }

    /**
     * 用户退房
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "/exitOrderById")
    public String exitOrderById(Integer id,HttpSession session){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        User user = (User) session.getAttribute("user");
        Integer room_id = hhService.findOrderRoomIdByUserId(id);
        Room room = hhService.findRoomById(room_id);
        hhService.setRoomEmpty(room.getId());
        System.out.println("用户订房的房间号为："+room.getRoom_id());
//        hhService.removeOrderById(id);
        hhService.setOrderExitById(id);
        hhService.saveMessage(-1,room.getRoom_id()+"的用户已退房，请打扫卫生！",java.sql.Date.valueOf(sdf.format(d)));//-1表示管理员消息
        return "commonPage/success";
    }

    /**
     * 接受订单
     * @param id
     * @return
     */
    @RequestMapping(value = "/acceptOrderById")
    public String acceptOrderById(Integer id){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        hhService.acceptOrderById(id);
        Order order = hhService.findOrderById(id);
        hhService.sendAcceptOrderMessageById(order.getUser_id(),java.sql.Date.valueOf(sdf.format(d)));
        return "commonPage/success";
    }

    /**
     * 打开评论界面
     * @return
     */
//    @RequestMapping(value = "/commentOrderById")
    @RequestMapping(value = "/userCommentPage")
    public String userCommentPage(Integer order_id,Model model){
        model.addAttribute("order_id",order_id);
        return "userPage/userCommentPage";
    }

    /**
     * 提交评论
     * @param order_id
     * @param model
     * @return
     */
    @RequestMapping(value = "/commentOrderById")
    public String commentOrderById(Integer order_id,String comment,Model model){
//        model.addAttribute("order_id",order_id);
        hhService.saveCommentInToOrder(comment,order_id);
        return "commonPage/success";
    }

    /**
     * 取消订单
     * @param id
     * @return
     */
    @RequestMapping(value = "/cancelOrderById")
    public String cancelOrderById(Integer id){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        hhService.cancelOrderById(id);
        Order order = hhService.findOrderById(id);
        Integer room_id = order.getRoom().getId();
        hhService.setRoomEmpty(room_id);
        hhService.sendCancelOrderMessageById(order.getUser_id(),java.sql.Date.valueOf(sdf.format(d)));
        return "commonPage/success";
    }

    @RequestMapping(value = "/deleteRoomById")
    public String deleteRoomById(Integer id){
        hhService.removeRoomById(id);
        return "commonPage/success";
    }


    @RequestMapping(value = "/deleteUserById")
    public String deleteUserById(Integer id){
        hhService.removeUserById(id);
        return "commonPage/success";
    }

    /**
     * 打开我要预约界面
     * @return
     */
    @RequestMapping(value = "/userReservation")
    public String userReservation(Model model,Integer pageIndex){
//        List<User> rooms = hhService.findAllRoom();
//        model.addAttribute("rooms",rooms);s
        // 创建分页对象
        PageModel pageModel = new PageModel();
        // 如果参数pageIndex不为null，设置pageIndex，即显示第几页
        if(pageIndex != null){
            pageModel.setPageIndex(pageIndex);
        }
        List<Room> rooms = hhService.findAllRoomByPage(pageModel);
        model.addAttribute("pageModel", pageModel);
        model.addAttribute("rooms",rooms);
        model.addAttribute("orderState",orderState);
        model.addAttribute("roomType",roomType);
        return "userPage/userReservation";
    }

    /**
     * 提交预约
     * @param model
     * @return
     */
    @RequestMapping(value = "/submitOrderByParams",method= RequestMethod.POST)
    public String submitOrderByParams(Integer barber_id, Integer r_time, Integer r_price, HttpSession session, Model model){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        User user = (User) session.getAttribute("user");
        hhService.saveOrder(user.getId(),barber_id, java.sql.Date.valueOf(sdf.format(d)));
        hhService.saveMessage(barber_id,"有用户提交预约，请查看！",java.sql.Date.valueOf(sdf.format(d)));
        return "commonPage/success";
    }

    /**
     * 用户预定房间
     * @return
     */
    @RequestMapping(value = "/userBookRoomById")
    public String userBookRoomById(Integer id, HttpSession session, Model model){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        User user = (User) session.getAttribute("user");
        hhService.modifyRoomBookedState(id);//修改房间状态为“客满”
        hhService.saveOrder(user.getId(),id,java.sql.Date.valueOf(sdf.format(d)));
        hhService.saveMessage(-1,"有用户提交预约，请查看！",java.sql.Date.valueOf(sdf.format(d)));//-1表示管理员消息
        return "commonPage/success";
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/resetPassword")
    public String resetPassword(String username,String oldpassword,String newpassword){
        return "";
//        System.out.println("111");
//        User user = mscPlayerService.findUserByAccount(username,oldpassword);
//        System.out.println(user);
//        String url = "";
//        if (user == null){
//            url = "failed";
//        }else {
//            mscPlayerService.resetPasswordById(user.getId(),username,newpassword);
//            url = "success";
//        }
//        Constant.curPage = "";
//        return url;
    }


}
