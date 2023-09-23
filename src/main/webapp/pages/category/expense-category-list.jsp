<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Expense Category List</title>
</head>

<body>
<h1>All categories</h1>
<table>
    <tr>
        <th>Name</th>
        <th>Total Amount</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${categories}" var="category">
        <c:set var="totalAmount" value="0"/>
        <c:forEach items="${category.recordSet}" var="record">
            <c:set var="totalAmount" value="${totalAmount + record.amount}"/>
        </c:forEach>
        <tr>
            <td>${category.name}</td>
            <td>${totalAmount}</td>
            <td>
                <form action="category" method="post">
                    <input type="hidden" name="action" value="edit-category">
                    <input type="hidden" name="id" value="${category.category_id}">
                    <button type="submit">Edit</button>
                </form>
            </td>
            <td>
                <form action="category" method="post">
                    <input type="hidden" name="action" value="delete-category">
                    <input type="hidden" name="id" value="${category.category_id}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<form action="category" method="post">
    <input type="hidden" name="action" value="create-category">
    <input type="submit" value="Add Category">
</form>
</body>
</html>