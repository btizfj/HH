<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fkjava" uri="/pager-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: XiaoMi
  Date: 2019/4/19
  Time: 20:37
  To change this template use File | Settings | File Templates.
  用户和理发师的“预约管理界面”
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>消息管理界面</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-sm bg-light">
    <ul class="navbar-nav">
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/welcome">欢迎登陆</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/messagePage">消息管理</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#" style="color: #1f6f4a">预约管理</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/addRoomPage">添加客房</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/roomManagementPage">管理客房</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/orderCommentPage">客户评价</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/logout">退出登录</a>
        </li>
    </ul>
</nav>
<br>

<div class="container">
    <div class="row" style="margin-top: 10px">
        <div class="table-responsive">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>编号</th>
                    <th>预约价格</th>
                    <th>操作时间</th>
                    <th>订单状态</th>
                    <th>接受订单</th>
                    <th>取消订单</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orders}" var="order" varStatus="stat">
                    <tr>
                        <td>${stat.index+1}</td>
                        <td>${order.room.price}</td>
                        <td>${order.time}</td>
                        <td>${orderState[order.isProcess]}</td>
                        <c:choose>
                            <c:when test="${order.isProcess == 0}">
                                <td><a href="${pageContext.request.contextPath}/acceptOrderById?id=${order.id}" class="text-primary">接受</a></td>
                            </c:when>
                            <c:when test="${order.isProcess == 1}">
                                <td><a class="text-primary">已接受</a></td>
                            </c:when>
                            <c:when test="${order.isProcess == 2}">
                                <td><a class="text-primary">已取消</a></td>
                            </c:when>
                            <c:when test="${order.isProcess == 3}">
                                <td><a class="text-primary">已退房</a></td>
                            </c:when>
                        </c:choose>
                        <c:choose>
                            <c:when test="${order.isProcess == 0}">
                                <td><a href="${pageContext.request.contextPath}/cancelOrderById?id=${order.id}" class="text-primary">取消</a></td>
                            </c:when>
                            <c:when test="${order.isProcess == 1}">
                                <td><a class="text-primary">已接受</a></td>
                            </c:when>
                            <c:when test="${order.isProcess == 2}">
                                <td><a class="text-primary">已取消</a></td>
                            </c:when>
                            <c:when test="${order.isProcess == 3}">
                                <td><a class="text-primary">已退房</a></td>
                            </c:when>
                        </c:choose>
                        <td><a href="${pageContext.request.contextPath}/deleteOrderById?id=${order.id}" class="text-primary">删除</a></td>
                    </tr>
                </c:forEach>
                <!-- 分页标签 -->
                <tr valign="top">
                    <td align="center" colspan="12" rowspan="2">
                        <fkjava:pager
                                pageIndex="${requestScope.pageModel.pageIndex}"
                                pageSize="${requestScope.pageModel.pageSize}"
                                recordCount="${requestScope.pageModel.recordCount}"
                                submitUrl="${pageContext.request.contextPath}/reservationPage?pageIndex={0}"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>
