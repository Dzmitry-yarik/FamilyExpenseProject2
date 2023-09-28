<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Categories</title>
</head>

<body>
<h1>${user.name}'s Categories</h1>
<table>
    <tr>
        <th>Category</th>
        <th>Record</th>
        <th>Amount</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${categories}" var="category">
        <tr>
            <td>${category.name}</td>
            <c:forEach items="${recordsSet}" var="record">
                <c:if test="${record.category.category_id == category.category_id}">
                    <td>${record.name}</td>
                    <td>${record.amount}</td>
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
                </c:if>
            </c:forEach>
        </tr>
    </c:forEach>
</table>
<br>
<input type="button" value="Back" onclick="window.history.back();">
</body>
</html>