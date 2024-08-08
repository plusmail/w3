
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Todo List</title>
</head>
<style>
    .todolist ul li {
        display: flex;
        list-style: none;
    }
    .todolist ul li {
        float: left;
        padding: 5px;
        align-items: center;
    }
</style>
<body>
<h1>Todo List</h1>
<div class="todolist">
    <ul>
        <c:forEach var="dto" items="${dtoList}">
            <li>${dto.tno}</li>
            <li><a href="/todo/read?tno=${dto.tno}">${dto.title}</a></li>
            <li>${dto.dueDate}</li>
            <li>${dto.finished ? "완료" : "미완료"}</li>
        </c:forEach>
    </ul>
</div>

</body>
</html>
