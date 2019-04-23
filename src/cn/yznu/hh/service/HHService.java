package cn.yznu.hh.service;

import cn.yznu.hh.bean.Message;
import cn.yznu.hh.bean.Order;
import cn.yznu.hh.bean.Room;
import cn.yznu.hh.bean.User;
import cn.yznu.hh.util.tag.PageModel;

import java.sql.Date;
import java.util.List;

public interface HHService {


    User findUserByAccount(String username, String password);

    List<Message> findAdminMessageByPage(PageModel pageModel);

    void removeMessageById(Integer id);

    List<Order> findOrderByPage(int id, PageModel pageModel);

//    List<Order> findAllOrderByPage(PageModel pageModel);

    void removeOrderById(Integer id);

    void saveOrder(int id, Integer room_id, Date time);

    void saveMessage(Integer barber_id, String s, Date time);

    List<Order> findAdminOrderByPage(PageModel pageModel);

    void acceptOrderById(Integer id);

    void cancelOrderById(Integer id);

    Order findOrderById(Integer id);

    void sendAcceptOrderMessageById(Integer user_id, Date date);

    void sendCancelOrderMessageById(Integer user_id, Date date);

    List<User> findAllUserByPage(PageModel pageModel);

    void removeUserById(Integer id);

    List<User> findAllBarberByPage(PageModel pageModel);

    List<Order> findAllOrderByPage(PageModel pageModel);

    void saveBarberToDB(String username, String password, String b_name, Integer usertype, String phonenumber);

    List<Room> findAllRoomByPage(PageModel pageModel);

    void removeRoomById(Integer id);

    void saveRoomToDB(String room_id, String room_type, String price, String filename);

    void modifyRoomBookedState(Integer room_id);

    Integer findOrderRoomIdByUserId(Integer id);

    Room findRoomById(Integer room_id);

    void modifyRoomCancelBookedState(Integer room_id);

    List<Message> findUserMessageByPage(int id, PageModel pageModel);

    void setRoomEmpty(Integer room_id);

    void setOrderExitById(Integer id);

    void saveCommentInToOrder(String comment, Integer order_id);
}
