<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%--<%@ page contentType="text/html;charset=UTF-8" %>--%>
<%--https://www.baeldung.com/tomcat-utf-8--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://ru.javawebinar.topjava.util/customFunctions" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <style>
        table {
            border-collapse: collapse; /* Collapse table border style */
        }

        th, td {
            border: 1px solid black; /* Borders for cells */
            padding: 8px; /* Indentation inside cells */
        }

        .true_excess {
            background-color: #ffcccc; /* background color for lines where meal.excess == true */
        }

        .false_excess {
            background-color: #ccffcc; /* Background color for lines where meal.excess == false */
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="meals?action=create">Create <img src="img/add.png"></a>
<hr/>
<hr/>
<table>
    <thead>
    <tr>
        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr class="${meal.excess ? 'true_excess' : 'false_excess'}">
            <td>${fn:dateTimeToString(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
<%--            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>--%>
            <td><a href="meals?id=${meal.id}&action=update">Update <img src="img/pencil.png"></a>
            </td>
            <td><a href="meals?id=${meal.id}&action=delete">Delete <img src="img/delete.png"></a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>