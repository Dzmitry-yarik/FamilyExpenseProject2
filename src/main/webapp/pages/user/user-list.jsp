<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

    <c:choose>
        <c:when test="${requestScope.initialAmount == 0}">
            <form action="user" method="post">
                <input type="hidden" name="action" value="save_initial_amount">
                <input type="number" name="initialAmount" placeholder="Enter initial amount" required>
                <input type="submit" value="Save Initial Amount">
            </form>
        </c:when>
        <c:otherwise>
            <p>Initial Amount: <fmt:formatNumber value="${requestScope.initialAmount}"
                                                 minFractionDigits="2" maxFractionDigits="2"/>
            <form action="user" method="post">
                <input type="hidden" name="action" value="replenish_account">
                <input type="number" name="replenishAmount" placeholder="Enter amount" required>
                <input type="submit" value="Replenish">
            </form></p>
        </c:otherwise>
    </c:choose>

    <tr>
        <th>Name</th>
        <th>Total Amount</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:set var="grandTotalAmount" value="0"/>
    <c:forEach items="${users}" var="user">
        <c:set var="totalAmount" value="0"/>
        <c:forEach items="${user.recordSet}" var="record">
            <c:set var="totalAmount" value="${totalAmount + record.amount}"/>
        </c:forEach>
        <tr>
            <td>
                <form action="category" method="post">
                    <input type="hidden" name="userId" value="${user.user_id}">
                    <input type="hidden" name="action" value="user-categories">
                    <button type="submit">${user.name}</button>
                </form>
            </td>
            <td><fmt:formatNumber value="${totalAmount}"
                                  minFractionDigits="2" maxFractionDigits="2"/></td>
            <td>
                <form action="user" method="post" onsubmit="return checkPassword(this);">
                    <input type="hidden" name="action" value="edit">
                    <input type="hidden" name="id" value="${user.user_id}">
                    <input type="hidden" name="userPassword" value="${user.password}">
                    <input type="password" name="inputPassword" placeholder="Enter password">
                    <span class="error-message" style="display:none;color:red">You entered the wrong password. You cannot edit .</span>
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
        <c:set var="grandTotalAmount" value="${grandTotalAmount + totalAmount}"/>
    </c:forEach>
</table>

<p>Total cost amount: <fmt:formatNumber value="${grandTotalAmount}"
                                        minFractionDigits="2" maxFractionDigits="2"/></p>
<p>Remainder: <fmt:formatNumber value="${requestScope.initialAmount - grandTotalAmount}"
                                minFractionDigits="2" maxFractionDigits="2"/></p>

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