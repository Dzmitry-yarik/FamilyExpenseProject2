<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>User Form</title>
</head>

<body>
<h1>User Form</h1>
<form action="user" method="post">
    <input type="hidden" name="action" value="${empty user ? 'save' : 'update'}">
    <input type="hidden" name="id" value="${empty user ? '' : user.user_id}">
    <label for="name">Name:</label>
    <input id="name" type="text" name="name" value="${empty user ? '' : user.name}">
    <br>
    <label for="password">Password:</label>
    <input id="password" type="password" name="password" value="${empty user ? '' : user.password}">
    <br>
    <input type="submit" value="${empty user ? 'Create' : 'Update'}">
    <input type="button" value="Back" onclick="window.history.back();">
</form>
</body>
</html>