package cn.yznu.hh.dao;

import cn.yznu.hh.bean.Message;
import cn.yznu.hh.bean.Order;
import cn.yznu.hh.bean.Room;
import cn.yznu.hh.bean.User;
import cn.yznu.hh.dao.provider.UserDynamicSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import static cn.yznu.hh.util.constants.Constant.*;

public interface UserDao {

    @SelectProvider(type= UserDynamicSqlProvider.class,method="selectUserByAccount")
    User selectUserByAccount(String username, String password);

    @Select("select count(*) from " + MESSAGETABLE + " where user_id=-1" )
    int countAdminMessage();

    @Select("select count(*) from " + MESSAGETABLE + " where user_id=#{user_id}" )
    int countUserMessage(Integer user_id);

    @SelectProvider(type= UserDynamicSqlProvider.class,method="selectMessageByPage")
    List<Message> selectMessageByPage(Map<String, Object> params);

    @SelectProvider(type= UserDynamicSqlProvider.class,method="selectUserMessageByPage")
    List<Message> selectUserMessageByPage(Map<String, Object> params);

    @Delete("delete from " + MESSAGETABLE + " where id=#{id}")
    void deleteMessageById(Integer id);

    @Select("select count(*) from " + ORDERTABLE + " where user_id=#{id}" )
    int countOrderByUserId(int id);

    @Select("select * from " + ROOMTABLE + " where id=#{id}" )
    Room selectRoomById(int id);

    @SelectProvider(type= UserDynamicSqlProvider.class,method="selectOrderByPage")
    @Results({
            @Result(column = "room_id",property = "room",
                    one = @One(select = "cn.yznu.hh.dao.UserDao.selectRoomById",fetchType = FetchType.LAZY)),
    })
    List<Order> selectOrderByPage(Map<String, Object> params);

    @Delete("delete from " + ORDERTABLE + " where id=#{id}")
    void deleteOrderById(Integer id);

    @SelectProvider(type= UserDynamicSqlProvider.class,method="insertOrder")
    void insertOrder(int id, Integer room_id, Date time);

    @SelectProvider(type= UserDynamicSqlProvider.class,method="insertMessage")
    void insertMessage(Integer barber_id, String s, Date time);

    @Select("select count(*) from " + ORDERTABLE)
    int countAdminOrder();

    @SelectProvider(type= UserDynamicSqlProvider.class,method="selectBarberOrderByPage")
    @Results({
            @Result(column = "room_id",property = "room",
                    one = @One(select = "cn.yznu.hh.dao.UserDao.selectRoomById",fetchType = FetchType.LAZY)),
    })
    List<Order> selectBarberOrderByPage(Map<String, Object> params);

    @Update("update " + ORDERTABLE + " set isProcess=1 where id=#{id}")
    void acceptOrderViaId(Integer id);

    @Update("update " + ORDERTABLE + " set isProcess=2 where id=#{id}")
    void cancelOrderViaId(Integer id);

    @Select("select * from " + ORDERTABLE + " where id=#{id}" )
    @Results({
            @Result(column = "room_id",property = "room",
                    one = @One(select = "cn.yznu.hh.dao.UserDao.selectRoomById",fetchType = FetchType.LAZY)),
    })
    Order selectOrderById(Integer id);

    @SelectProvider(type= UserDynamicSqlProvider.class,method="insertAcceptOrderMessageById")
    void insertAcceptOrderMessageById(Integer user_id, Date date);

    @SelectProvider(type= UserDynamicSqlProvider.class,method="insertCancelOrderMessageById")
    void insertCancelOrderMessageById(Integer user_id, Date date);

    @Select("select count(*) from " + USERTABLE +" where usertype=0")
    int countAllUser();

    @SelectProvider(type= UserDynamicSqlProvider.class,method="selectAllUserByPage")
    List<User> selectAllUserByPage(Map<String, Object> params);

    @Delete("delete from " + USERTABLE + " where id=#{id}")
    void deleteUserById(Integer id);

    @Select("select count(*) from " + USERTABLE +" where usertype=1")
    int countAllBarber();

    @SelectProvider(type= UserDynamicSqlProvider.class,method="selectAllBarberByPage")
    List<User> selectAllBarberByPage(Map<String, Object> params);

    @Select("select count(*) from " + ORDERTABLE)
    int countAllOrder();

    @SelectProvider(type= UserDynamicSqlProvider.class,method="selectAllOrderByPage")
    @Results({
            @Result(column = "room_id",property = "room",
                    one = @One(select = "cn.yznu.hh.dao.UserDao.selectRoomById",fetchType = FetchType.LAZY)),
    })
    List<Order> selectAllOrderByPage(Map<String, Object> params);

    @SelectProvider(type= UserDynamicSqlProvider.class,method="insertBarberToDB")
    void insertBarberToDB(String username, String password, String b_name, Integer usertype, String phonenumber);

    @Select("select count(*) from " + ROOMTABLE)
    int countAllRoom();

    @SelectProvider(type= UserDynamicSqlProvider.class,method="selectAllRoomByPage")
    List<Room> selectAllRoomByPage(Map<String, Object> params);

    @Delete("delete from " + ROOMTABLE + " where id=#{id}")
    void deleteRoomById(Integer id);

    @SelectProvider(type= UserDynamicSqlProvider.class,method="insertRoomToDB")
    void insertRoomToDB(String room_id, String room_type, String price, String filename);

//    @Select("update " + ROOMTABLE + " set isBook=1 where id=#{room_id}")
    @SelectProvider(type= UserDynamicSqlProvider.class,method="updateRoomBookedState")
    void updateRoomBookedState(Integer room_id);

    @SelectProvider(type= UserDynamicSqlProvider.class,method="selectOrderRoomIdByUserId")
    Integer selectOrderRoomIdByUserId(Integer id);

    @SelectProvider(type= UserDynamicSqlProvider.class,method="updateRoomCancelBookedState")
    void updateRoomCancelBookedState(Integer room_id);

//    @Update("update " + ROOMTABLE + " set isBook=0 where id=#{room_id}")
    @SelectProvider(type= UserDynamicSqlProvider.class,method="updateRoomEmpty")
    void updateRoomEmpty(Integer room_id);

    @SelectProvider(type= UserDynamicSqlProvider.class,method="updateOrderExitById")
    void updateOrderExitById(Integer id);

    @SelectProvider(type= UserDynamicSqlProvider.class,method="insertCommentInToOrder")
    void insertCommentInToOrder(String comment, Integer order_id);
}
