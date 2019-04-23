package cn.yznu.hh.service.impl;

import cn.yznu.hh.bean.Message;
import cn.yznu.hh.bean.Order;
import cn.yznu.hh.bean.Room;
import cn.yznu.hh.bean.User;
import cn.yznu.hh.dao.UserDao;
import cn.yznu.hh.service.HHService;
import cn.yznu.hh.util.tag.PageModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OPMS系统服务层接口实现类
 * @Service用于将当前类注释为一个Spring的bean，名为userService
 */
@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT)
@Service("hhService")
public class HHServiceImpl implements HHService {

    /**
     * 自动注入持久层Dao对象
     * */
    @Resource
    private UserDao userDao;


    @Override
    public User findUserByAccount(String username, String password) {
        return userDao.selectUserByAccount(username,password);
    }

    @Override
    public List<Message> findAdminMessageByPage(PageModel pageModel) {
        Map<String,Object> params = new HashMap<>();
        int recordCount = userDao.countAdminMessage();
        pageModel.setRecordCount(recordCount);
        if(recordCount > 0){
            /** 开始分页查询数据：查询第几页的数据 */
            params.put("pageModel", pageModel);
        }
        List<Message> messages = userDao.selectMessageByPage(params);
        return messages;
    }

    @Override
    public void removeMessageById(Integer id) {
        userDao.deleteMessageById(id);
    }

    @Override
    public List<Order> findOrderByPage(int id, PageModel pageModel) {
        Map<String,Object> params = new HashMap<>();
        int recordCount = userDao.countOrderByUserId(id);
        pageModel.setRecordCount(recordCount);
        if(recordCount > 0){
            /** 开始分页查询数据：查询第几页的数据 */
            params.put("id", id);
            params.put("pageModel", pageModel);
        }
        List<Order> orders = userDao.selectOrderByPage(params);
        return orders;
    }

    @Override
    public void removeOrderById(Integer id) {
        userDao.deleteOrderById(id);
    }

    @Override
    public void saveOrder(int id, Integer room_id, Date time) {
        userDao.insertOrder(id,room_id,time);
    }

    @Override
    public void saveMessage(Integer barber_id, String s, Date time) {
        userDao.insertMessage(barber_id,s,time);
    }

    @Override
    public List<Order> findAdminOrderByPage(PageModel pageModel) {
        Map<String,Object> params = new HashMap<>();
        int recordCount = userDao.countAdminOrder();
        pageModel.setRecordCount(recordCount);
        if(recordCount > 0){
            /** 开始分页查询数据：查询第几页的数据 */
            params.put("pageModel", pageModel);
        }
        List<Order> orders = userDao.selectBarberOrderByPage(params);
        return orders;
    }

    @Override
    public void acceptOrderById(Integer id) {
        userDao.acceptOrderViaId(id);
    }

    @Override
    public void cancelOrderById(Integer id) {
        userDao.cancelOrderViaId(id);
    }

    @Override
    public Order findOrderById(Integer id) {
        return userDao.selectOrderById(id);
    }

    @Override
    public void sendAcceptOrderMessageById(Integer user_id, Date date) {
        userDao.insertAcceptOrderMessageById(user_id,date);
    }

    @Override
    public void sendCancelOrderMessageById(Integer user_id, Date date) {
        userDao.insertCancelOrderMessageById(user_id,date);
    }

    @Override
    public List<User> findAllUserByPage(PageModel pageModel) {
        Map<String,Object> params = new HashMap<>();
        int recordCount = userDao.countAllUser();
        pageModel.setRecordCount(recordCount);
        if(recordCount > 0){
            /** 开始分页查询数据：查询第几页的数据 */
            params.put("pageModel", pageModel);
        }
        List<User> users = userDao.selectAllUserByPage(params);
        return users;
    }

    @Override
    public void removeUserById(Integer id) {
        userDao.deleteUserById(id);
    }

    @Override
    public List<User> findAllBarberByPage(PageModel pageModel) {
        Map<String,Object> params = new HashMap<>();
        int recordCount = userDao.countAllBarber();
        pageModel.setRecordCount(recordCount);
        if(recordCount > 0){
            /** 开始分页查询数据：查询第几页的数据 */
            params.put("pageModel", pageModel);
        }
        List<User> users = userDao.selectAllBarberByPage(params);
        return users;
    }

    @Override
    public List<Order> findAllOrderByPage(PageModel pageModel) {
        Map<String,Object> params = new HashMap<>();
        int recordCount = userDao.countAllOrder();
        pageModel.setRecordCount(recordCount);
        if(recordCount > 0){
            /** 开始分页查询数据：查询第几页的数据 */
            params.put("pageModel", pageModel);
        }
        List<Order> orders = userDao.selectAllOrderByPage(params);
        return orders;
    }

    @Override
    public void saveBarberToDB(String username, String password, String b_name, Integer usertype, String phonenumber) {
        userDao.insertBarberToDB(username,password,b_name,usertype,phonenumber);
    }

    @Override
    public List<Room> findAllRoomByPage(PageModel pageModel) {
        Map<String,Object> params = new HashMap<>();
        int recordCount = userDao.countAllRoom();
        pageModel.setRecordCount(recordCount);
        if(recordCount > 0){
            /** 开始分页查询数据：查询第几页的数据 */
            params.put("pageModel", pageModel);
        }
        List<Room> rooms = userDao.selectAllRoomByPage(params);
        return rooms;
    }

    @Override
    public void removeRoomById(Integer id) {
        userDao.deleteRoomById(id);
    }

    @Override
    public void saveRoomToDB(String room_id, String room_type, String price, String filename) {
        userDao.insertRoomToDB(room_id,room_type,price,filename);
    }

    @Override
    public void modifyRoomBookedState(Integer room_id) {
        userDao.updateRoomBookedState(room_id);
    }

    @Override
    public Integer findOrderRoomIdByUserId(Integer id) {
        return userDao.selectOrderRoomIdByUserId(id);
    }

    @Override
    public Room findRoomById(Integer room_id) {
        return userDao.selectRoomById(room_id);
    }

    @Override
    public void modifyRoomCancelBookedState(Integer room_id) {
        userDao.updateRoomCancelBookedState(room_id);
    }

    @Override
    public List<Message> findUserMessageByPage(int id, PageModel pageModel) {
        Map<String,Object> params = new HashMap<>();
        int recordCount = userDao.countUserMessage(id);
        pageModel.setRecordCount(recordCount);
        if(recordCount > 0){
            /** 开始分页查询数据：查询第几页的数据 */
            params.put("pageModel", pageModel);
            params.put("id", id);
        }
        List<Message> messages = userDao.selectUserMessageByPage(params);
        return messages;
    }

    @Override
    public void setRoomEmpty(Integer room_id) {
        userDao.updateRoomEmpty(room_id);
    }

    @Override
    public void setOrderExitById(Integer id) {
        userDao.updateOrderExitById(id);
    }

    @Override
    public void saveCommentInToOrder(String comment, Integer order_id) {
        userDao.insertCommentInToOrder(comment,order_id);
    }
}
