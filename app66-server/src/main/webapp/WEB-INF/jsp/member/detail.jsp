<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='UTF-8'>
    <title>회원</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<h1>회원</h1>
<c:if test="${empty member}">
    <p>해당 번호의 회원이 없습니다!</p>
</c:if>
<c:if test="${not empty member}">
    <form action='update' method='post' enctype='multipart/form-data'>
        <table border='1'>
            <tr>
                <th style='width:120px;'>사진</th>
                <td style='width:300px;'>
                    <c:if test="${empty member.memberPhoto}">
                        <img style='height:80px' src='/images/avatar.png'>
                    </c:if>
                    <c:if test="${not empty member.memberPhoto}">
                        <a href='https://kr.object.ncloudstorage.com/bitcamp-nc7-bucket-03/personal/member/${member.memberPhoto}'>
                            <img src='http://tzoswbbmvlov19010725.cdn.ntruss.com/personal/member/${member.memberPhoto}?type=f&w=60&h=80&faceopt=true&ttype=jpg'>
                        </a>
                    </c:if>
                    <input type='file' name='memberPhoto'></td>
            </tr>
            <tr>
                <th style='width:120px;'>번호</th>
                <td style='width:300px;'><input type='text' name='memberNo' value='${member.memberNo}' readonly></td>
            </tr>
            <tr>
                <th>구분</th>
                <td><select name='memberCode'>
                    <option value='S' ${member.getMemberCode() == 'S' ? "selected" : ""}>학생</option>
                    <option value='P' ${member.getMemberCode() == 'P' ? "selected" : ""}>교수</option></select>
                </td>
            </tr>
            <tr>
                <th>이름</th>
                <td><input type='text' name='memberName' value='${member.memberName}'></td>
            </tr>
            <tr>
                <th>이메일</th>
                <td><input type='email' name='memberEmail' value='${member.memberEmail}'></td>
            </tr>
            <c:if test="${loginUser.memberNo == param.memberNo}">
                <tr>
                    <th>암호</th>
                    <td><input type='password' name='memberPwd'></td>
                </tr>
            </c:if>
            <tr>
                <th>성별</th>
                <td><select name='memberGender'>
                    <option value='M' ${member.memberGender == 'M' ? "selected" : ""}>남자</option>
                    <option value='W' ${member.memberGender == 'W' ? "selected" : ""}>여자</option></select>
                </td>
            </tr>
            <tr>
                <th>휴대폰</th>
                <td><input type='text' name='memberTel' value='${member.memberTel}'></td>
            </tr>
            <tr>
                <th>우편번호</th>
                <td><input type='text' name='memberZipcode' value='${member.memberZipcode}'></td>
            </tr>
            <tr>
                <th>주소</th>
                <td><input type='text' name='memberAddr' value='${member.memberAddr}'></td>
            </tr>
            <tr>
                <th>상세주소</th>
                <td><input type='text' name='memberDetailAddr' value='${member.memberDetailAddr}'></td>
            </tr>
            <tr>
                <th>가입일</th>
                <td><input type='text' name='memberDate' value='${member.memberDate}' readonly></td>
            </tr>
        </table>
        <div>
            <button type='reset'>초기화</button>
                <button>변경</button>
                <a href='delete?memberNo=${member.memberNo}'>삭제</a>
            <a href='list'>목록</a>
        </div>
    </form>
</c:if>
<jsp:include page="../footer.jsp"/>

</body>
</html>
