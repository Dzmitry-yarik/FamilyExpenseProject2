<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Expense Record List</title>
    <script>
        function showRecords(period) {
            var rows = document.querySelectorAll("#recordsTable tbody tr");
            rows.forEach(function(row) {
                var recordDate = new Date(row.getAttribute("data-date"));
                if (period === "month") {
                    var now = new Date();
                    var lastMonth = new Date(now.getFullYear(), now.getMonth() - 1, now.getDate());
                    row.style.display = (recordDate >= lastMonth) ? "table-row" : "none";
                } else {
                    row.style.display = "table-row";
                }
            });
        }
    </script>
</head>

<body>
<h1>All records</h1>
<table id="recordsTable">
    <thead>
    <tr>
        <th>Name</th>
        <th>User</th>
        <th>Category</th>
        <th>Amount</th>
        <th>Date</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${records}" var="record">
        <tr data-date="${record.date}">
            <td>${record.name}</td>
            <td>${record.user.name}</td>
            <td>${record.category.name}</td>
            <td>${record.amount}</td>
            <td>${record.date}</td>
            <td>
                <form action="record" method="post">
                    <input type="hidden" name="action" value="edit-record">
                    <input type="hidden" name="id" value="${record.record_id}">
                    <button type="submit">Edit</button>
                </form>
            </td>
            <td>
                <form action="record" method="post">
                    <input type="hidden" name="action" value="delete-record">
                    <input type="hidden" name="id" value="${record.record_id}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div>
    <button onclick="showRecords('all')">All</button>
    <button onclick="showRecords('month')">Current month</button>
</div>
<br>
<form action="record" method="post">
    <input type="hidden" name="action" value="create-record">
    <input type="submit" value="Add Record">
</form>
</body>
</html>