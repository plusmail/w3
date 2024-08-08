
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Todo Register</title>
</head>
<body>
<h1>Todo Register</h1>
<form action="/todo/register" method="post">
    <div>
        <label>
           제목 : <input type="text" name="title" placeholder="할일 제목 입력하시오.">
        </label>
    </div>
    <div>
        <label>
            날짜: <input type="date" name="dueDate">
        </label>
    </div>

    <button type="reset">초기화</button>
    <button type="submit">등록</button>
</form>

</body>
</html>
