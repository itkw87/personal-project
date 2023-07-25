
-- ps_memeber 테이블 예제 데이터
-- authority => S : 학생, T : 선생
insert into ps_member(member_no, authority, birth, grade, name, gender, korean_score, english_score, math_score, scoreAvg, email, password, status)
   values(1, 'S', 20020901, 1, 'aaa', '남', 11, 11, 11, 11.0, 'aaa@test.com', '1111', true);
       
insert into ps_member(member_no, authority, birth, grade, name, gender, korean_score, english_score, math_score, scoreAvg, email, password, status)
   values(2, 'S', 20030825, 2, 'bbb', '남', 11, 11, 11, 11.0, 'bbb@test.com', '1111', false);
   
insert into ps_member(member_no, authority, birth, grade, name, gender, korean_score, english_score, math_score, scoreAvg, email, password, status)
   values(3, 'S', 20040925, 3, 'ccc', '남', 11, 11, 11, 11.0, 'ccc@test.com', '1111', true);
   
insert into ps_member(member_no, authority, birth, name, gender, email, password, status)
   values(4, 'T', 19400925, 'ddd', '여', 'ddd@test.com', '1111', true);
   
insert into ps_member(member_no, authority, birth, name, gender, email, password, status)
   values(5, 'T', 19900215, 'eee', '여', 'eee@test.com', '1111', false);
   
insert into ps_member(member_no, authority, birth, name, gender, email, password, status)
   values(6, 'T', 19800320, 'fff', '남', 'fff@test.com', '1111', true);
   
   
   
-- ps_board 테이블 예제 데이터
insert into ps_board(board_no, title, content, writer, password, category) values(1, '제목1', '내용1', '홍길동', '1111', 1);
insert into ps_board(board_no, title, content, writer, password, category) values(2, '제목2', '내용2', '임꺽정', '1111', 1);
insert into ps_board(board_no, title, content, writer, password, category) values(3, '제목3', '내용3', '유관순', '1111', 1);
insert into ps_board(board_no, title, content, writer, password, category) values(4, '제목4', '내용4', '이순신', '1111', 2);
insert into ps_board(board_no, title, content, writer, password, category) values(5, '제목5', '내용5', '윤봉길', '1111', 2);
insert into ps_board(board_no, title, content, writer, password, category) values(6, '제목6', '내용6', '안중근', '1111', 2);
insert into ps_board(board_no, title, content, writer, password, category) values(7, '제목7', '내용7', '김구', '1111', 2);
