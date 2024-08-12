<jsp:useBean id="dto" scope="request" type="kroryi.w3.todo.dto.TodoDTO"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Todo Read</title>
</head>
<script>
    // 쿠키 설정 함수
    function setCookie(name, value, days) {
        const date = new Date();
        date.setTime(date.getTime() + (days * 60 * 1000));
        const expires = "expires=" + date.toUTCString();
        document.cookie = name + "=" + value + ";" + expires + ";path=/";
    }

    // 쿠키 가져오기 함수
    function getCookie(name) {
        const nameEQ = name + "=";
        //name1=value1;name2:value2
        const ca = document.cookie.split(';');
        for(let i = 0; i < ca.length; i++) {
            let c = ca[i].trim();
            if (c.indexOf(nameEQ) == 0) {
                return c.substring(nameEQ.length, c.length);
            }
        }
        return null;
    }

    // 팝업창 표시 함수
    function showPopup() {
        const popup = document.getElementById("popup");
        popup.style.display = "block";
    }

    // 팝업창 닫기 함수
    function closePopup() {
        const popup = document.getElementById("popup");
        popup.style.display = "none";
        // 쿠키 설정 (하루 동안 팝업창이 다시 나타나지 않음)
        setCookie("popupClosed", "true", 1);
    }

    // 페이지 로드 시 팝업창을 표시할지 결정
    window.onload = function() {

        console.log(getCookie("viewTodos"))

        const popupClosed = getCookie("popupClosed");
        if (!popupClosed) {
            showPopup();
        }
    }
</script>
<style>
    /* 간단한 팝업 스타일 */
    #popup {
        display: none;
        position: fixed;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        padding: 20px;
        background-color: white;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        z-index: 1000;
    }

    #popup button {
        margin-top: 10px;
    }
</style>
<body>
<h1>Todo Read</h1>

${dto.tno}<br>
${dto.title}<br>
${dto.dueDate}<br>
${dto.finished}<br>

<a href="/todo/modify?tno=${dto.tno}">수정/삭제</a>
<a href="/todo/list">목록보기</a>


<div id="popup">
    <p>This is a popup message!</p>
    <button onclick="closePopup()">Close</button>
</div>
</body>
</html>
