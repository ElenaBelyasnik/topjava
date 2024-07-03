<%--https://www.baeldung.com/tomcat-utf-8--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <form>
        <input type="hidden" name="action" value="filter">
        <h3>Интервал дат</h3>
        <label for="startDate">c:</label>
        <input type="date" id="startDate" name="startDate" value="${param.startDate}">
        <label for="endDate">до:</label>
        <input type="date" id="endDate" name="startDate" value="${param.endDate}">
        <br><br>
        <h3>Временной полуинтервал</h3>
        <label for="startTime">с:</label>
        <input type="time" id="startTime" name="startTime" value="${param.startTime}">
        <label for="endTime">по:</label>
        <input type="time" id="endTime" name="endTime" value="${param.endTime}">
        <br><br>
        <button type="submit">Установить фильтр</button>
    </form>
    <a href="meals?action=create">Добавить еду</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Дата</th>
            <th>Описание</th>
            <th>Калории</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Изменить</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Удалить</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>