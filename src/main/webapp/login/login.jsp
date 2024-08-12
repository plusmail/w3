
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>로그인 페이지</title>
</head>
<body>
<h1>로그인 페이지</h1>
<c:if test="${param.result == 'error'}">
    <h1>로그인 에러 </h1>
</c:if>

<form action="/login" method="post">
    <label for="id">
        아이디 :<input type="text" name="id"><br>
    </label>
    <label for="password">
        비밀번호 : <input type="password" name="password">
    </label>
    <label>
        자동 로그인 : <input type="checkbox" name="auto">
    </label>
    <div>
        <button type="reset">리셋</button>
        <button type="submit">로그인</button>
    </div>
</form>
</body>
</html>
