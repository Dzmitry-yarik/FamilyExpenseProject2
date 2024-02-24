<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Expense Category Form</title>
</head>

<body>
<h1>Category Form</h1>
<form action="${empty category ? '/save-category' : '/update-category'}" method="post">
    <input type="hidden" name="action" value="${empty category ? 'save-category' : 'update-category'}">
    <input type="hidden" name="id" value="${empty category ? '' : category.category_id}">
    <label for="name">Name:</label>
    <input id="name" type="text" name="name" value="${empty category ? '' : category.name}">

    <br>
    <input type="submit" value="${empty category ? 'Create' : 'Update'}">
    <input type="button" value="Back" onclick="window.history.back();">
</form>
</body>
</html>