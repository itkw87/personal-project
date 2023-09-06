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

<h1>회원</h1>
<form action='add' method='post' enctype='multipart/form-data'>
  <table border='1'>
    <tr>
      <th>구분</th>
      <td>
        <select name='memberCode'>
          <option value='S'>학생</option>
          <option value='P'>교수</option>
        </select>
      </td>
    </tr>
    <tr>
      <th>아이디</th> <td style='width:200px;'><input type='text' name='memberId' placeholder='아이디'></td>
    </tr>
    <tr>
      <th>암호</th> <td><input type='password' name='memberPwd' placeholder='암호'></td>
    </tr>
    <tr>
      <th>이름</th> <td><input type='text' name='memberName' placeholder='이름'></td>
    </tr>
    <tr>
      <th>이메일</th> <td><input type='email' name='memberEmail' placeholder='이메일'></td>
    </tr>
    <tr>
      <th>성별</th>
      <td>
        <select name='memberGender'>
          <option value='M'>남자</option>
          <option value='W'>여자</option>
        </select>
      </td>
    </tr>
    <tr>
      <th>휴대폰</th>
      <td><input type='text' name='memberTel' placeholder='휴대전화'></td>
    </tr>
    <tr>
      <th>우편번호</th>
      <td><input type='text' name='memberZipCode' placeholder='우편번호'></td>
    </tr>
    <tr>
      <th>주소</th>
      <td><input type='text' name='memberAddr' placeholder='기본주소'></td>
    </tr>
    <tr>
      <th>상세주소</th>
      <td><input type='text' name='memberDetailAddr' placeholder='상세주소'></td>
    </tr>
    <tr>
      <th>사진</th> <td><input type='file' name='memberPhoto'></td>
    </tr>
  </table>
  <button>등록</button>
</form>

<jsp:include page="../footer.jsp"/>

</body>
</html>
