<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='UTF-8'>
    <title>TaskTop</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<h1>로그인</h1>

<form action='login' method='post'>
    <table border='1'>
        <tr>
            <th>아이디</th> <td><input type='text' name='memberId' value='${cookie.memberId.value}'></td>
        </tr>
        <tr>
            <th>암호</th> <td><input type='password' name='memberPwd'></td>
        </tr>
    </table>
    <button>로그인</button>
    <input type='checkbox' name='saveId' ${cookie.memberId != null ? "checked" : ""}> 아이디 저장
</form>

<jsp:include page="../footer.jsp"/>

</body>
</html>












