-- task_employment_type 테이블에 데이터 삽입:
INSERT INTO task_employment_type (emp_type_name)
VALUES ('정규교수'), ('비정규교수'), ('강사');



-- task_member 테이블에 데이터 삽입:
INSERT INTO task_member (member_code, member_id, member_pwd, member_name, member_email, member_gender, member_tel, member_zipcode, member_addr, member_detail_addr, member_status)
VALUES ('P', 'p1', sha1('1111'), '김교수', 'p1@test.com', 'M', '010-1234-5678', '12345', '서울시 강남구', '123번지', 'Y'),
       ('S', 's1', sha1('1111'), '김학생', 's1@test.com', 'W', '010-9876-5432', '54321', '서울시 종로구', '987번지', 'Y');



-- task_student 테이블에 데이터 삽입:
INSERT INTO task_student (student_no, student_status)
VALUES (2, 'Y');



-- task_professor 테이블에 데이터 삽입:
INSERT INTO task_professor (professor_no, emp_type_no, professor_status)
VALUES (1, 1, 'Y');



-- task_subject 테이블에 데이터 삽입:
INSERT INTO task_subject (subject_name, subject_type, subject_credit, subject_hour)
VALUES ('수학', 'M', 3, 48), ('영어', 'M', 2, 32);



-- task_lecture 테이블에 데이터 삽입:
INSERT INTO task_lecture (lecture_no, professor_no, subject_no, lecture_year, lecture_semester, lecture_name, lecture_status)
VALUES (1, 1, 1, 2023, 1, '수학 강의', 'R'),
       (2, 1, 2, 2023, 1, '영어 강의', 'R');



-- task_lecture_schedule 테이블에 데이터 삽입:
INSERT INTO task_lecture_schedule (sched_no, lecture_no, lecture_day, lecture_period, lecture_hour)
VALUES (1, 1, '월', 1, 2), (2, 1, '수', 2, 2), (3, 2, '화', 1, 2), (4, 2, '목', 2, 2);



-- task_assignment 테이블에 데이터 삽입:
INSERT INTO task_assignment (assign_no, lecture_no, assign_title, assign_content, assign_deadline)
VALUES (1, 1, '수학 과제1', '수학 과제 내용입니다.', NOW()),
       (2, 2, '영어 과제1', '영어 과제 내용입니다.', NOW());



-- task_participation_type 테이블에 데이터 삽입:
INSERT INTO task_participation_type (parti_type_name)
VALUES ('수강생'), ('강사');


-- task_participant 테이블에 데이터 삽입:
INSERT INTO task_participant (lecture_no, member_no, parti_type_no, parti_status)
VALUES (1, 2, 1, 'Y'),
       (1, 1, 2, 'Y'),
       (2, 2, 1, 'Y'),
       (2, 1, 2, 'Y');



-- task_submission 테이블에 데이터 삽입:
INSERT INTO task_submission (submit_no, assign_no, lecture_no, submitter, file_path, origin_filename, save_filename, submit_date)
VALUES (1, 1, 1, 2, '/path/to/file1.pdf', 'file1.pdf', '12345_file1.pdf', NOW()),
       (2, 1, 1, 1, '/path/to/file2.pdf', 'file2.pdf', '54321_file2.pdf', NOW());



-- task_lec_board 테이블에 데이터 삽입:
INSERT INTO task_lec_board (lec_board_no, lecture_no, lec_title, lec_content, lec_writer, lec_view_count, lec_reg_date, lec_mdf_date, lec_status)
VALUES (1, 1, '수학 강의 공지', '수학 강의에 관한 공지사항입니다.', 2, 10, NOW(), NOW(), 'Y'),
       (2, 2, '영어 강의 공지', '영어 강의에 관한 공지사항입니다.', 1, 5, NOW(), NOW(), 'Y');



-- task_lec_board_reply 테이블에 데이터 삽입:
INSERT INTO task_lec_board_reply (lec_rep_no, lec_board_no, lec_rep_writer, lec_rep_content, lec_rep_date, lec_rep_status)
VALUES (1, 1, 2, '수학 강의 공지에 대한 댓글입니다.', NOW(), 'Y'),
       (2, 2, 1, '영어 강의 공지에 대한 댓글입니다.', NOW(), 'Y');



-- task_free_board 테이블에 데이터 삽입:
INSERT INTO task_free_board (free_board_no, free_title, free_content, free_writer, free_view_count, free_reg_date, free_mdf_date, free_status)
VALUES (1, '자유게시판 글1', '자유게시판 첫 번째 글 내용입니다.', 2, 7, NOW(), NOW(), 'Y'),
       (2, '자유게시판 글2', '자유게시판 두 번째 글 내용입니다.', 1, 12, NOW(), NOW(), 'Y');



-- task_free_board_reply 테이블에 데이터 삽입:
INSERT INTO task_free_board_reply (free_rep_no, free_board_no, free_rep_writer, free_rep_content, free_rep_date, free_rep_status)
VALUES (1, 1, 2, '자유게시판 글1에 대한 댓글입니다.', NOW(), 'Y'),
       (2, 2, 1, '자유게시판 글2에 대한 댓글입니다.', NOW(), 'Y');



-- task_board_attach_file 테이블에 데이터 삽입:
INSERT INTO task_board_attach_file (file_no, free_board_no, file_path, origin_filename, save_filename)
VALUES (1, 1, '/path/to/file1.pdf', '첨부파일1.pdf', 'saved_file1.pdf'),
       (2, 1, '/path/to/file2.docx', '첨부파일2.docx', 'saved_file2.docx');