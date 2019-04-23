<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fkjava" uri="/pager-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: XiaoMi
  Date: 2019/4/19
  Time: 20:37
  To change this template use File | Settings | File Templates.
  用户和理发师的“消息管理界面”
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加客房界面</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <script language="JavaScript">
        function doUpload() {
            var formData = new FormData($( "#uploadForm" )[0]);
            // var fn;//返回的文件名
            $.ajax({
                url: '${pageContext.request.contextPath }/uploadFile' ,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (filename) {
                    // alert("文件上传成功！");
                    // alert(filename)
                    // alert(filename);
                    // fn = filename;
                    uploadData(filename);//上传其他数据
                },
                error:function (jqXHR, textStatus, errorThrown) {
                    alert("文件上传失败！");
                    alert("responseText:"+jqXHR.responseText+"\n"+
                        "status:"+jqXHR.status+"\n"+
                        "readyState:"+jqXHR.readyState+"\n"+
                        "statusText:"+jqXHR.statusText+"\n"+
                        "textStatus:"+textStatus+"\n"+
                        "errorThrown:"+errorThrown)
                }
            });

        }
        function uploadData(filename) {
            var jsonObj = {};
            var room_id = $("#room_id").val();
            var room_type = $("#room_type").val();
            var price = $("#price").val();
            jsonObj.room_id = room_id;
            jsonObj.room_type = room_type;
            jsonObj.price = price;
            jsonObj.filename = filename;
            var jsonResult = JSON.stringify(jsonObj);
            document.getElementById("msg").value = jsonResult;
        }
    </script>
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
            <a class="nav-link" href="${pageContext.request.contextPath}/reservationPage">预约管理</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#" style="color: #1f6f4a">添加客房</a>
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
    <form id="uploadForm" action="${pageContext.request.contextPath}/submitRoomByParams" method="post">
        <div class="card" style="margin-bottom: 30px">
            <div class="card-body">
                <div class="row" align="center">
                    <div class="col">
                        <h3>添加客房</h3>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <div class="form-group">
                            <label for="room_id">房间号:</label>
                            <input type="text" class="form-control" id="room_id" name="room_id">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <label for="room_type">房间类型:</label>
                        <select class="form-control" id="room_type" name="room_type">
                            <option value="-1">选择类型</option>
                            <option value="0">大床房</option>
                            <option value="1">标准件</option>
                            <option value="2">单人间</option>
                            <option value="3">豪华套房</option>
                            <option value="4">总统套房</option>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <div class="form-group">
                            <label for="price">价格:</label>
                            <input type="text" class="form-control" id="price" name="price">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <div class="form-group">
                            <label for="file">房间图片:</label>
                            <input type="file" id="file" name="file">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col" align="center">
                        <input type="hidden" id="msg" name="msg">
                        <input type="submit" class="btn btn-info" value="提交" onclick="doUpload()">
                    </div>
                </div>
            </div>
        </div>
    </form>

</div>

</body>
</html>
