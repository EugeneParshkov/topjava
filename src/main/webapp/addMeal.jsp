<%--
  Created by IntelliJ IDEA.
  User: eugene
  Date: 11.02.2023
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>Add new meal</title>
    <meta charset="utf-8">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>

<form method="POST" action='meals' name="frmAddMeal" accept-charset="UTF-8">



    <input type="datetime-local" id="start" name="trip-start"
           value="2018-07-22 10-00"
           min="2018-01-01 00-00" max="2018-12-31 23-59">


    <br/>
    <br>
    Description : <input
        type="text" name="description"
        value="<c:out value="${mealTo.getDescription()}" />" /> <br />
        <br>
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${user.lastName}" />" /> <br />
        <br>
    <input type="submit" value="Save" />
    <input type="submit" value="Cancel">
</form>
</body>
</html>