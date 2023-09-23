<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Expense Record Form</title>

</head>

<body>
<h1>Record Form</h1>
<form action="record" method="post">
    <input type="hidden" name="action" value="${empty record ? 'save-record' : 'update-record'}">
    <input type="hidden" name="id" value="${empty record ? '' : record.record_id}">
    User: <select name="userId">
    <c:forEach items="${users}" var="user">
        <option value="${user.user_id}" ${user.user_id == record.user.user_id ? 'selected' : ''}>${user.name}</option>
    </c:forEach>
</select><br>
    Category: <select name="categoryId">
    <c:forEach items="${categories}" var="category">
        <option value="${category.category_id}" ${category.category_id == record.category.category_id ? 'selected' : ''}>${category.name}</option>
    </c:forEach>
</select><br>
    Name: <input type="text" name="name" value="${empty record ? '' : record.name}"><br>
    Amount: <input type="text" name="amount" value="${empty record ? '' : record.amount}"><br>
    Date: <input type="date" name="date" pattern="\d{4}-\d{2}-\d{2}" value="${empty record ? '' : record.date}" required><br>
    <input type="submit" value="${empty record ? 'Create' : 'Update'}">
    <input type="button" value="Back" onclick="window.history.back();"></form>
</body>
</html>