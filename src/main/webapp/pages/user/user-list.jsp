<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User List</title>
    </style>
    <script>
        function checkPassword(form) {
            var inputPassword = form.querySelector("[name='inputPassword']").value;
            var userPassword = form.querySelector("[name='userPassword']").value;

            if (inputPassword === userPassword) {
                return true;
            } else {
                var errorMessage = form.querySelector(".error-message");
                errorMessage.style.display = "block";
                return false;
            }
        }

        function confirmDelete() {
            return confirm("Вы уверены?");
        }
    </script>
</head>

<body>
<h1>All users</h1>
<table>
    <tr>
        <th>Name</th>
        <th>Total Amount</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <c:set var="totalAmount" value="0"/>
        <c:forEach items="${user.recordSet}" var="record">
            <c:set var="totalAmount" value="${totalAmount + record.amount}"/>
        </c:forEach>
        <tr>
            <td>
                <form action="category" method="post">
                    <input type="hidden" name="userId" value="${user.user_id}">
                    <input type="hidden" name="action" value="user_categories">
                    <button type="submit">${user.name}</button>
                </form>
            </td>
            <td>${totalAmount}</td>
            <td>
                <form action="user" method="post" onsubmit="return checkPassword(this);">
                    <input type="hidden" name="action" value="edit">
                    <input type="hidden" name="id" value="${user.user_id}">
                    <input type="hidden" name="userPassword" value="${user.password}">
                    <input type="password" name="inputPassword" placeholder="Enter password">
                    <span class="error-message" style="display:none;color:red">Неправильный пароль. Вы не можете выполнить редактирование.</span>
                    <button type="submit">Edit</button>
                </form>
            </td>
            <td>
                <form action="user" method="post" onsubmit="return confirmDelete();">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${user.user_id}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<form action="user" method="post">
    <input type="hidden" name="action" value="create">
    <input type="submit" value="Add User">
</form>
<form id="categoryForm" action="user" method="post">
    <input type="hidden" name="action" value="list-category">
    <input type="submit" value="Categories">
</form>
<form id="userForm" action="user" method="post">
    <input type="hidden" name="action" value="list-record">
    <input type="submit" value="Records">
</form>
</body>
</html>