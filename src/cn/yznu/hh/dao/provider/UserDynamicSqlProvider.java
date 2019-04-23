package cn.yznu.hh.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import java.sql.Date;
import java.util.Map;

import static cn.yznu.hh.util.constants.Constant.*;

public class UserDynamicSqlProvider {

    public String insertCommentInToOrder(String comment, Integer order_id){
        String sql =  new SQL(){
            {
                UPDATE(ORDERTABLE);
                SET(" comment='"+comment+"' ");
                WHERE(" id= "+order_id);
            }
        }.toString();
        return sql;
    }

    public String updateOrderExitById(Integer id){
        String sql =  new SQL(){
            {
                UPDATE(ORDERTABLE);
                SET(" isProcess=3 ");
                WHERE(" id=#{id} ");
            }
        }.toString();
        return sql;
    }

    public String updateRoomEmpty(Integer room_id){
        String sql =  new SQL(){
            {
                UPDATE(ROOMTABLE);
                SET(" isBook=0 ");
                WHERE(" room_id=#{room_id} ");
            }
        }.toString();
        return sql;
    }

    public String updateRoomCancelBookedState(Integer room_id){
        String sql =  new SQL(){
            {
                UPDATE(ROOMTABLE);
                SET(" isBook=0 ");
                WHERE(" room_id=#{room_id} ");
            }
        }.toString();
        return sql;
    }

    public String selectOrderRoomIdByUserId(Integer id){
        String sql =  new SQL(){
            {
                SELECT("room_id");
                FROM(ORDERTABLE);
                WHERE(" id=#{id} ");
            }
        }.toString();
        return sql;
    }

    public String updateRoomBookedState(Integer room_id){
        String sql =  new SQL(){
            {
                UPDATE(ROOMTABLE);
                SET(" isBook=1 ");
                WHERE(" id=#{room_id} ");
            }
        }.toString();
        return sql;
    }

    public String insertRoomToDB(String room_id, String room_type, String price, String filename){
        String sql =  new SQL(){
            {
                INSERT_INTO(ROOMTABLE);
                VALUES("room_id",""+room_id+"");
                VALUES("room_type",""+room_type+"");
                VALUES("price",""+price+"");
                VALUES("pic","'"+filename+"'");
                VALUES("isBook",""+0+"");
            }
        }.toString();
        return sql;
    }

    public String insertBarberToDB(String username, String password, String b_name, Integer usertype, String phonenumber){
        String sql =  new SQL(){
            {
                INSERT_INTO(USERTABLE);
                VALUES("username","'"+username+"'");
                VALUES("password","'"+password+"'");
                VALUES("b_name","'"+b_name+"'");
                VALUES("usertype",""+usertype+"");
                VALUES("phonenumber",""+phonenumber+"");
            }
        }.toString();
        return sql;
    }

    public String insertAcceptOrderMessageById(Integer user_id, Date date){
        String sql =  new SQL(){
            {
                INSERT_INTO(MESSAGETABLE);
                VALUES("user_id",""+user_id+"");
                VALUES("time","'"+date+"'");
                VALUES("content","'"+"您预约的房间已经被前台工作人员接受，请及时去前台登记！"+"'");
            }
        }.toString();
        return sql;
    }

    public String insertCancelOrderMessageById(Integer user_id, Date date){
        String sql =  new SQL(){
            {
                INSERT_INTO(MESSAGETABLE);
                VALUES("user_id",""+user_id+"");
                VALUES("time","'"+date+"'");
                VALUES("content","'"+"您预约的房间已经被前台工作人员拒绝，请重新预约！"+"'");
            }
        }.toString();
        return sql;
    }

    public String insertOrder(int id, Integer room_id, Date time){
        System.out.println(id);
        System.out.println(room_id);
        System.out.println(time);
        String sql =  new SQL(){
            {
                INSERT_INTO(ORDERTABLE);
                VALUES("user_id",""+id+"");
                VALUES("room_id",""+room_id+"");
                VALUES("time","'"+time+"'");
                VALUES("isProcess",""+0+"");
            }
        }.toString();
        return sql;
    }

    public String insertMessage(Integer barber_id, String s, Date time){
        String sql =  new SQL(){
            {
                INSERT_INTO(MESSAGETABLE);
                VALUES("user_id",""+barber_id+"");
                VALUES("isread",""+0+"");
                VALUES("content","'"+s+"'");
                VALUES("time","'"+time+"'");
            }
        }.toString();
        return sql;
    }

    public String selectOrderByPage(Map<String, Object> params){
        String sql =  new SQL(){
            {
                SELECT("*");
                FROM(ORDERTABLE);
                WHERE(" user_id=#{id}");
            }
        }.toString();
        if(params.get("pageModel") != null){
            sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize} ";
        }
        return sql;
    }

    public String selectBarberOrderByPage(Map<String, Object> params){
        String sql =  new SQL(){
            {
                SELECT("*");
                FROM(ORDERTABLE);
            }
        }.toString();
        if(params.get("pageModel") != null){
            sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize} ";
        }
        return sql;
    }

    public String selectAllUserByPage(Map<String, Object> params){
        String sql =  new SQL(){
            {
                SELECT("*");
                FROM(USERTABLE);
                WHERE(" usertype=0 ");
            }
        }.toString();
        if(params.get("pageModel") != null){
            sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize} ";
        }
        return sql;
    }

    public String selectAllBarberByPage(Map<String, Object> params){
        String sql =  new SQL(){
            {
                SELECT("*");
                FROM(USERTABLE);
                WHERE(" usertype=1 ");
            }
        }.toString();
        if(params.get("pageModel") != null){
            sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize} ";
        }
        return sql;
    }

    public String selectAllRoomByPage(Map<String, Object> params){
        String sql =  new SQL(){
            {
                SELECT("*");
                FROM(ROOMTABLE);
            }
        }.toString();
        if(params.get("pageModel") != null){
            sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize} ";
        }
        return sql;
    }

    public String selectAllOrderByPage(Map<String, Object> params){
        String sql =  new SQL(){
            {
                SELECT("*");
                FROM(ORDERTABLE);
            }
        }.toString();
        if(params.get("pageModel") != null){
            sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize} ";
        }
        return sql;
    }

    public String selectUserMessageByPage(Map<String, Object> params){
        String sql =  new SQL(){
            {
                SELECT("*");
                FROM(MESSAGETABLE);
                WHERE(" user_id=#{id}");
            }
        }.toString();
        if(params.get("pageModel") != null){
            sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize} ";
        }
        return sql;
    }

    public String selectMessageByPage(Map<String, Object> params){
        String sql =  new SQL(){
            {
                SELECT("*");
                FROM(MESSAGETABLE);
                WHERE(" user_id=-1");
            }
        }.toString();
        if(params.get("pageModel") != null){
            sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize} ";
        }
        return sql;
    }

    public String selectUserByAccount(String username, String password){
        String sql =  new SQL(){
            {
                SELECT("*");
                FROM(USERTABLE);
                WHERE(" username= '"+username+"'");
                WHERE(" password= '"+password+"'");
            }
        }.toString();
        return sql;
    }


}
