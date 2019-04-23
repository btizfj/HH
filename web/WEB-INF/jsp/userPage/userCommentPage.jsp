<%--
  Created by IntelliJ IDEA.
  User: XiaoMi
  Date: 2019/4/23
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>评价订单</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <h1 align="center" style="margin: 50px">填写评价</h1>
    <div class="card">
        <div class="card-body" align="center">
            <form action="${pageContext.request.contextPath}/commentOrderById">
                <div class="form-group">
                    <label for="comment">填写评价:</label>
                    <input type="text" class="form-control" id="comment" name="comment" placeholder="这里填写">
                </div>
                <input type="hidden" name="order_id" id="order_id" value="${order_id}">
                <button type="submit" class="btn btn-primary">提交</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>