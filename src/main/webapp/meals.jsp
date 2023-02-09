<%--
  Created by IntelliJ IDEA.
  User: eugene
  Date: 08.02.2023
  Time: 23:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <title>Meals</title>

</head>
<body>
<h3><a href="index.html">Home</a></h3>
<style>
    .red {
        color: red; /* Цвет символа */
    }
    .green {
        color: green;
    }
</style>
<table width=700 cellspacing="0" border="1" bordercolor="black" cellpadding="7">

    <tr align="center">
        <td align="center">Date</td>
        <td align="center">Description</td>
        <td>Calories</td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <jsp:useBean id="mealTo" scope="request" type="java.util.List"/>
        <c:forEach var="meal" items="${mealTo}">

    <tr style=" color :${meal.isExcess() ? 'red' : 'green'}">
        <td>${meal.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
        <td>${meal.getDescription()}</td>
        <td>${meal.getCalories()}</td>
        <td><p><a href="">Update</a></p></td>
        <td><p><a href="">Delete</a></p></td>
    </tr>
    </c:forEach>
    </tr>

</table>

</body>
</html>
