package cn.yznu.hh.util.constants;

public class Constant {

    //当前页面
    public static String curPage = "";

    public static final String USERTABLE = "hh_user";//用户表
    public static final String MESSAGETABLE = "hh_message";//歌曲表
    public static final String ORDERTABLE = "hh_order";//订单表
    public static final String ROOMTABLE = "hh_room";//房间表

    //订单处理状态
    public static final String orderState[] = {"等待处理", "已接受", "已取消","已退房"};
    public static final String reservationTime[] = {"8:00-10:00", "10:00-12:00", "14:00-16:00", "16:00-18:00"};
    //房间类型
    public static final String roomType[] = {"大床房", "标准件", "单人间", "豪华套房","总统套房"};

    public static final String userType[] = {"用户", "前台", "管理员"};

    //每一页显示的最大条目数量
    public static int PAGE_DEFAULT_SIZE = 5;

}
