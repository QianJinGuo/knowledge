<%@ page  language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>表单提交</title>
</head>
<body>
<form action="FirstServlet" method="post">
    皇马<input type="checkbox" name="footballTeam" value="HuangMa"/>
    尤文<input type="checkbox" name="footballTeam" value="YouWen"/>
    曼联<input type="checkbox" name="footballTeam" value="ManLian"/>
    <input type="submit" value="提交">
</form>
</body>
</html>
