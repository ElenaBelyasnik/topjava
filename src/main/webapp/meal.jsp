<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%--<%@ page contentType="text/html;charset=UTF-8" %>--%>
<%--https://www.baeldung.com/tomcat-utf-8--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://ru.javawebinar.topjava.util/customFunctions" %>
<html lang="ru">
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>${param.action == 'create' ? "Create" : "Update" }</h2>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<form action="meals" method="post">
    <input type="hidden" name="id" value="${meal.id}">
    <label for="dateTime">Дата и время:</label>
    <input type="datetime-local" id="dateTime" name="dateTime" value="${meal.dateTime}" required><br><br>

    <label for="description">Описание:</label>
    <input type="text" id="description" name="description" value="${meal.description}" required><br><br>

    <label for="calories">Калории:</label>
    <input type="number" id="calories" name="calories" value="${meal.calories}" required><br><br>

    <button type="submit">Сохранить</button>
    <button type="button" onclick="cancelChanges()">Отменить</button>
    <script>
        function cancelChanges() {
            if (confirm('Вы уверены, что хотите отменить изменения?')) {

                window.history.back();
            }
        }
    </script>
</form>
</body>
</html>