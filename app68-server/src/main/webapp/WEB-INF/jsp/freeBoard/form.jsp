<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='UTF-8'>
    <title>TaskTop</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<h1>자유게시글</h1>
<form action='add' method='post' enctype='multipart/form-data'>
    제목 <input type='text' name='freeTitle'><br>
    내용 <textarea name='freeContent'></textarea><br>
    파일 <input type='file' name='files' multiple><br>
    <button>등록</button>
</form>

<jsp:include page="../footer.jsp"/>

</body>
</html>