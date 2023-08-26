-- 교수
DROP TABLE IF EXISTS task_professor RESTRICT;

-- 강의
DROP TABLE IF EXISTS task_lecture RESTRICT;

-- 학생
DROP TABLE IF EXISTS task_student RESTRICT;

-- 고용형태
DROP TABLE IF EXISTS task_employment_type RESTRICT;

-- 회원
DROP TABLE IF EXISTS task_member RESTRICT;

-- 과목
DROP TABLE IF EXISTS task_subject RESTRICT;

-- 강의일정
DROP TABLE IF EXISTS task_lecture_schedule RESTRICT;

-- 과제
DROP TABLE IF EXISTS task_assignment RESTRICT;

-- 참여자
DROP TABLE IF EXISTS task_participant RESTRICT;

-- 과제제출
DROP TABLE IF EXISTS task_submission RESTRICT;

-- 강의게시판
DROP TABLE IF EXISTS task_lecture_board RESTRICT;

-- 참여유형
DROP TABLE IF EXISTS task_participation_type RESTRICT;

-- 강의게시글 댓글
DROP TABLE IF EXISTS task_lec_board_reply RESTRICT;

-- 게시글첨부파일
DROP TABLE IF EXISTS task_board_attach_file RESTRICT;

-- 자유게시판
DROP TABLE IF EXISTS task_free_board RESTRICT;

-- 자유게시글 댓글
DROP TABLE IF EXISTS task_free_board_reply RESTRICT;

-- 교수
CREATE TABLE task_professor (
                                professor_no     INTEGER    NOT NULL COMMENT '교수번호', -- 교수번호
                                emp_type_no      INTEGER    NOT NULL COMMENT '고용형태번호', -- 고용형태번호
                                professor_status VARCHAR(1) NOT NULL DEFAULT 'Y' COMMENT '재직여부' -- 재직여부
)
    COMMENT '교수';

-- 교수
ALTER TABLE task_professor
    ADD CONSTRAINT PK_task_professor -- 교수 기본키
        PRIMARY KEY (
                     professor_no -- 교수번호
            );

-- 강의
CREATE TABLE task_lecture (
                              lecture_no       INTEGER     NOT NULL COMMENT '강의번호', -- 강의번호
                              professor_no     INTEGER     NOT NULL COMMENT '교수번호', -- 교수번호
                              subject_no       INTEGER     NOT NULL COMMENT '과목번호', -- 과목번호
                              lecture_year     INTEGER     NOT NULL COMMENT '강의년도', -- 강의년도
                              lecture_semester INTEGER     NOT NULL COMMENT '강의학기', -- 강의학기
                              lecture_name     VARCHAR(50) NOT NULL COMMENT '강의명', -- 강의명
                              lecture_status   VARCHAR(1)  NOT NULL DEFAULT 'R' COMMENT '강의진행상태' -- 강의진행상태
)
    COMMENT '강의';

-- 강의
ALTER TABLE task_lecture
    ADD CONSTRAINT PK_task_lecture -- 강의 기본키
        PRIMARY KEY (
                     lecture_no -- 강의번호
            );

ALTER TABLE task_lecture
    MODIFY COLUMN lecture_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '강의번호';

-- 학생
CREATE TABLE task_student (
                              student_no     INTEGER    NOT NULL COMMENT '학생번호', -- 학생번호
                              student_status VARCHAR(1) NOT NULL DEFAULT 'Y' COMMENT '재학여부' -- 재학여부
)
    COMMENT '학생';

-- 학생
ALTER TABLE task_student
    ADD CONSTRAINT PK_task_student -- 학생 기본키
        PRIMARY KEY (
                     student_no -- 학생번호
            );

-- 고용형태
CREATE TABLE task_employment_type (
                                      emp_type_no   INTEGER     NOT NULL COMMENT '고용형태번호', -- 고용형태번호
                                      emp_type_name VARCHAR(50) NOT NULL COMMENT '고용형태명' -- 고용형태명
)
    COMMENT '고용형태';

-- 고용형태
ALTER TABLE task_employment_type
    ADD CONSTRAINT PK_task_employment_type -- 고용형태 기본키
        PRIMARY KEY (
                     emp_type_no -- 고용형태번호
            );

-- 고용형태 유니크 인덱스
CREATE UNIQUE INDEX UIX_task_employment_type
    ON task_employment_type ( -- 고용형태
                             emp_type_name ASC -- 고용형태명
        );

ALTER TABLE task_employment_type
    MODIFY COLUMN emp_type_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '고용형태번호';

-- 회원
CREATE TABLE task_member (
                             member_no          INTEGER      NOT NULL COMMENT '회원번호', -- 회원번호
                             member_code        VARCHAR(1)   NOT NULL COMMENT '회원유형', -- 회원유형
                             member_id          VARCHAR(50)  NOT NULL COMMENT '회원ID', -- 회원ID
                             member_pwd         VARCHAR(100) NOT NULL COMMENT '회원PW', -- 회원PW
                             member_name        VARCHAR(50)  NOT NULL COMMENT '이름', -- 이름
                             member_email       VARCHAR(40)  NOT NULL COMMENT '이메일', -- 이메일
                             member_gender      VARCHAR(1)   NOT NULL COMMENT '성별', -- 성별
                             member_tel         VARCHAR(30)  NOT NULL COMMENT '전화', -- 전화
                             member_zipcode     VARCHAR(10)  NOT NULL COMMENT '우편번호', -- 우편번호
                             member_addr        VARCHAR(255) NOT NULL COMMENT '기본주소', -- 기본주소
                             member_detail_addr VARCHAR(255) NOT NULL COMMENT '상세주소', -- 상세주소
                             member_date        DATE         NOT NULL DEFAULT (CURRENT_DATE()) COMMENT '등록일', -- 등록일
                             member_status      VARCHAR(1)   NOT NULL DEFAULT 'Y' COMMENT '상태' -- 상태
)
    COMMENT '회원';

-- 회원
ALTER TABLE task_member
    ADD CONSTRAINT PK_task_member -- 회원 기본키
        PRIMARY KEY (
                     member_no -- 회원번호
            );

-- 회원 유니크 인덱스
CREATE UNIQUE INDEX UIX_task_member
    ON task_member ( -- 회원
                    member_id ASC -- 회원ID
        );

ALTER TABLE task_member
    MODIFY COLUMN member_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '회원번호';

-- 과목
CREATE TABLE task_subject (
                              subject_no     INTEGER     NOT NULL COMMENT '과목번호', -- 과목번호
                              subject_name   VARCHAR(50) NOT NULL COMMENT '과목명', -- 과목명
                              subject_type   VARCHAR(1)  NOT NULL COMMENT '과목유형', -- 과목유형
                              subject_credit INTEGER     NOT NULL COMMENT '과목학점', -- 과목학점
                              subject_hour   INTEGER     NOT NULL COMMENT '과목강의시간' -- 과목강의시간
)
    COMMENT '과목';

-- 과목
ALTER TABLE task_subject
    ADD CONSTRAINT PK_task_subject -- 과목 기본키
        PRIMARY KEY (
                     subject_no -- 과목번호
            );

-- 과목 유니크 인덱스
CREATE UNIQUE INDEX UIX_task_subject
    ON task_subject ( -- 과목
                     subject_name ASC -- 과목명
        );

ALTER TABLE task_subject
    MODIFY COLUMN subject_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '과목번호';

-- 강의일정
CREATE TABLE task_lecture_schedule (
                                       sched_no       INTEGER     NOT NULL COMMENT '강의일정번호', -- 강의일정번호
                                       lecture_no     INTEGER     NOT NULL COMMENT '강의번호', -- 강의번호
                                       lecture_day    VARCHAR(10) NOT NULL COMMENT '강의요일', -- 강의요일
                                       lecture_period INTEGER     NOT NULL COMMENT '강의교시', -- 강의교시
                                       lecture_hour   INTEGER     NOT NULL COMMENT '강의진행시간' -- 강의진행시간
)
    COMMENT '강의일정';

-- 강의일정
ALTER TABLE task_lecture_schedule
    ADD CONSTRAINT PK_task_lecture_schedule -- 강의일정 기본키
        PRIMARY KEY (
                     sched_no -- 강의일정번호
            );

ALTER TABLE task_lecture_schedule
    MODIFY COLUMN sched_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '강의일정번호';

-- 과제
CREATE TABLE task_assignment (
                                 assign_no       INTEGER      NOT NULL COMMENT '과제번호', -- 과제번호
                                 lecture_no      INTEGER      NOT NULL COMMENT '강의번호', -- 강의번호
                                 assign_title    VARCHAR(255) NOT NULL COMMENT '제목', -- 제목
                                 assign_content  MEDIUMTEXT   NOT NULL COMMENT '내용', -- 내용
                                 assign_deadline DATETIME     NOT NULL DEFAULT NOW() COMMENT '제출마감일' -- 제출마감일
)
    COMMENT '과제';

-- 과제
ALTER TABLE task_assignment
    ADD CONSTRAINT PK_task_assignment -- 과제 기본키
        PRIMARY KEY (
                     assign_no -- 과제번호
            );

ALTER TABLE task_assignment
    MODIFY COLUMN assign_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '과제번호';

-- 참여자
CREATE TABLE task_participant (
                                  lecture_no    INTEGER    NOT NULL COMMENT '강의번호', -- 강의번호
                                  member_no     INTEGER    NOT NULL COMMENT '회원번호', -- 회원번호
                                  parti_type_no INTEGER    NOT NULL COMMENT '참여유형번호', -- 참여유형번호
                                  parti_status  VARCHAR(1) NOT NULL DEFAULT 'Y' COMMENT '참여상태' -- 참여상태
)
    COMMENT '참여자';

-- 참여자
ALTER TABLE task_participant
    ADD CONSTRAINT PK_task_participant -- 참여자 기본키
        PRIMARY KEY (
                     lecture_no, -- 강의번호
                     member_no   -- 회원번호
            );

-- 과제제출
CREATE TABLE task_submission (
                                 submit_no       INTEGER      NOT NULL COMMENT '과제제출번호', -- 과제제출번호
                                 assign_no       INTEGER      NOT NULL COMMENT '과제번호', -- 과제번호
                                 lecture_no      INTEGER      NOT NULL COMMENT '강의번호', -- 강의번호
                                 submitter       INTEGER      NOT NULL COMMENT '제출자', -- 제출자
                                 file_path       VARCHAR(255) NOT NULL COMMENT '파일경로', -- 파일경로
                                 origin_filename VARCHAR(50)  NOT NULL COMMENT '제출파일명', -- 제출파일명
                                 save_filename   VARCHAR(50)  NOT NULL COMMENT '저장파일명', -- 저장파일명
                                 submit_date     DATETIME     NOT NULL DEFAULT NOW() COMMENT '제출일' -- 제출일
)
    COMMENT '과제제출';

-- 과제제출
ALTER TABLE task_submission
    ADD CONSTRAINT PK_task_submission -- 과제제출 기본키
        PRIMARY KEY (
                     submit_no -- 과제제출번호
            );

ALTER TABLE task_submission
    MODIFY COLUMN submit_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '과제제출번호';

-- 강의게시판
CREATE TABLE task_lecture_board (
                                    lec_board_no   INTEGER      NOT NULL COMMENT '강의게시글 번호', -- 강의게시글 번호
                                    lecture_no     INTEGER      NOT NULL COMMENT '강의번호', -- 강의번호
                                    lec_title      VARCHAR(255) NOT NULL COMMENT '강의게시글 제목', -- 강의게시글 제목
                                    lec_content    MEDIUMTEXT   NOT NULL COMMENT '강의게시글 내용', -- 강의게시글 내용
                                    lec_writer     INTEGER      NOT NULL COMMENT '강의게시글 작성자', -- 강의게시글 작성자
                                    lec_view_count INTEGER      NOT NULL DEFAULT 0 COMMENT '강의게시글 조회수', -- 강의게시글 조회수
                                    lec_reg_date   DATETIME     NOT NULL DEFAULT NOW() COMMENT '강의게시글 등록일', -- 강의게시글 등록일
                                    lec_mdf_date   DATETIME     NOT NULL DEFAULT NOW() COMMENT '강의게시글 수정일', -- 강의게시글 수정일
                                    lec_status     VARCHAR(1)   NOT NULL DEFAULT 'Y' COMMENT '강의게시글 상태' -- 강의게시글 상태
)
    COMMENT '강의게시판';

-- 강의게시판
ALTER TABLE task_lecture_board
    ADD CONSTRAINT PK_task_lecture_board -- 강의게시판 기본키
        PRIMARY KEY (
                     lec_board_no -- 강의게시글 번호
            );

ALTER TABLE task_lecture_board
    MODIFY COLUMN lec_board_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '강의게시글 번호';

-- 참여유형
CREATE TABLE task_participation_type (
                                         parti_type_no   INTEGER     NOT NULL COMMENT '참여유형번호', -- 참여유형번호
                                         parti_type_name VARCHAR(50) NOT NULL COMMENT '참여유형명' -- 참여유형명
)
    COMMENT '참여유형';

-- 참여유형
ALTER TABLE task_participation_type
    ADD CONSTRAINT PK_task_participation_type -- 참여유형 기본키
        PRIMARY KEY (
                     parti_type_no -- 참여유형번호
            );

-- 참여유형 유니크 인덱스
CREATE UNIQUE INDEX UIX_task_participation_type
    ON task_participation_type ( -- 참여유형
                                parti_type_name ASC -- 참여유형명
        );

ALTER TABLE task_participation_type
    MODIFY COLUMN parti_type_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '참여유형번호';

-- 강의게시글 댓글
CREATE TABLE task_lec_board_reply (
                                      lec_rep_no      INTEGER      NOT NULL COMMENT '강의게시글 댓글 번호', -- 강의게시글 댓글 번호
                                      lec_board_no    INTEGER      NOT NULL COMMENT '강의게시글 번호', -- 강의게시글 번호
                                      lec_rep_writer  INTEGER      NOT NULL COMMENT '강의게시글 댓글 작성자', -- 강의게시글 댓글 작성자
                                      lec_rep_content VARCHAR(255) NOT NULL COMMENT '강의게시글 댓글 내용', -- 강의게시글 댓글 내용
                                      lec_rep_date    DATETIME     NOT NULL DEFAULT NOW() COMMENT '강의게시글 댓글 등록일', -- 강의게시글 댓글 등록일
                                      lec_rep_status  VARCHAR(1)   NOT NULL DEFAULT 'Y' COMMENT '강의게시글 댓글 상태' -- 강의게시글 댓글 상태
)
    COMMENT '강의게시글 댓글';

-- 강의게시글 댓글
ALTER TABLE task_lec_board_reply
    ADD CONSTRAINT PK_task_lec_board_reply -- 강의게시글 댓글 기본키
        PRIMARY KEY (
                     lec_rep_no -- 강의게시글 댓글 번호
            );

ALTER TABLE task_lec_board_reply
    MODIFY COLUMN lec_rep_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '강의게시글 댓글 번호';

-- 게시글첨부파일
CREATE TABLE task_board_attach_file (
                                        file_no         INTEGER      NOT NULL COMMENT '첨부파일번호', -- 첨부파일번호
                                        free_board_no   INTEGER      NOT NULL COMMENT '자유게시글 번호', -- 자유게시글 번호
                                        file_path       VARCHAR(255) NOT NULL COMMENT '파일경로', -- 파일경로
                                        origin_filename VARCHAR(50)  NOT NULL COMMENT '첨부파일명', -- 첨부파일명
                                        save_filename   VARCHAR(50)  NOT NULL COMMENT '저장파일명' -- 저장파일명
)
    COMMENT '게시글첨부파일';

-- 게시글첨부파일
ALTER TABLE task_board_attach_file
    ADD CONSTRAINT PK_task_board_attach_file -- 게시글첨부파일 기본키
        PRIMARY KEY (
                     file_no -- 첨부파일번호
            );

ALTER TABLE task_board_attach_file
    MODIFY COLUMN file_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '첨부파일번호';

-- 자유게시판
CREATE TABLE task_free_board (
                                 free_board_no   INTEGER      NOT NULL COMMENT '자유게시글 번호', -- 자유게시글 번호
                                 free_title      VARCHAR(255) NOT NULL COMMENT '자유게시글 제목', -- 자유게시글 제목
                                 free_content    MEDIUMTEXT   NOT NULL COMMENT '자유게시글 내용', -- 자유게시글 내용
                                 free_writer     INTEGER      NOT NULL COMMENT '자유게시글 작성자', -- 자유게시글 작성자
                                 free_view_count INTEGER      NOT NULL DEFAULT 0 COMMENT '자유게시글 조회수', -- 자유게시글 조회수
                                 free_reg_date   DATETIME     NOT NULL DEFAULT NOW() COMMENT '자유게시글 등록일', -- 자유게시글 등록일
                                 free_mdf_date   DATETIME     NOT NULL DEFAULT NOW() COMMENT '자유게시글 수정일', -- 자유게시글 수정일
                                 free_status     VARCHAR(1)   NOT NULL DEFAULT 'Y' COMMENT '자유게시글 상태' -- 자유게시글 상태
)
    COMMENT '자유게시판';

-- 자유게시판
ALTER TABLE task_free_board
    ADD CONSTRAINT PK_task_free_board -- 자유게시판 기본키
        PRIMARY KEY (
                     free_board_no -- 자유게시글 번호
            );

ALTER TABLE task_free_board
    MODIFY COLUMN free_board_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '자유게시글 번호';

-- 자유게시글 댓글
CREATE TABLE task_free_board_reply (
                                       free_rep_no      INTEGER      NOT NULL COMMENT '자유게시글 댓글 번호', -- 자유게시글 댓글 번호
                                       free_board_no    INTEGER      NOT NULL COMMENT '자유게시글 번호', -- 자유게시글 번호
                                       free_rep_writer  INTEGER      NOT NULL COMMENT '자유게시글 댓글 작성자', -- 자유게시글 댓글 작성자
                                       free_rep_content VARCHAR(255) NOT NULL COMMENT '자유게시글 댓글 내용', -- 자유게시글 댓글 내용
                                       free_rep_date    DATETIME     NOT NULL DEFAULT NOW() COMMENT '자유게시글 댓글 등록일', -- 자유게시글 댓글 등록일
                                       free_rep_status  VARCHAR(1)   NOT NULL COMMENT '자유게시글 댓글 상태' -- 자유게시글 댓글 상태
)
    COMMENT '자유게시글 댓글';

-- 자유게시글 댓글
ALTER TABLE task_free_board_reply
    ADD CONSTRAINT PK_task_free_board_reply -- 자유게시글 댓글 기본키
        PRIMARY KEY (
                     free_rep_no -- 자유게시글 댓글 번호
            );

ALTER TABLE task_free_board_reply
    MODIFY COLUMN free_rep_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '자유게시글 댓글 번호';

-- 교수
ALTER TABLE task_professor
    ADD CONSTRAINT FK_task_member_TO_task_professor -- 회원 -> 교수
        FOREIGN KEY (
                     professor_no -- 교수번호
            )
            REFERENCES task_member ( -- 회원
                                    member_no -- 회원번호
                );

-- 교수
ALTER TABLE task_professor
    ADD CONSTRAINT FK_task_employment_type_TO_task_professor -- 고용형태 -> 교수
        FOREIGN KEY (
                     emp_type_no -- 고용형태번호
            )
            REFERENCES task_employment_type ( -- 고용형태
                                             emp_type_no -- 고용형태번호
                );

-- 강의
ALTER TABLE task_lecture
    ADD CONSTRAINT FK_task_subject_TO_task_lecture -- 과목 -> 강의
        FOREIGN KEY (
                     subject_no -- 과목번호
            )
            REFERENCES task_subject ( -- 과목
                                     subject_no -- 과목번호
                );

-- 강의
ALTER TABLE task_lecture
    ADD CONSTRAINT FK_task_professor_TO_task_lecture -- 교수 -> 강의
        FOREIGN KEY (
                     professor_no -- 교수번호
            )
            REFERENCES task_professor ( -- 교수
                                       professor_no -- 교수번호
                );

-- 학생
ALTER TABLE task_student
    ADD CONSTRAINT FK_task_member_TO_task_student -- 회원 -> 학생
        FOREIGN KEY (
                     student_no -- 학생번호
            )
            REFERENCES task_member ( -- 회원
                                    member_no -- 회원번호
                );

-- 강의일정
ALTER TABLE task_lecture_schedule
    ADD CONSTRAINT FK_task_lecture_TO_task_lecture_schedule -- 강의 -> 강의일정
        FOREIGN KEY (
                     lecture_no -- 강의번호
            )
            REFERENCES task_lecture ( -- 강의
                                     lecture_no -- 강의번호
                );

-- 과제
ALTER TABLE task_assignment
    ADD CONSTRAINT FK_task_lecture_TO_task_assignment -- 강의 -> 과제
        FOREIGN KEY (
                     lecture_no -- 강의번호
            )
            REFERENCES task_lecture ( -- 강의
                                     lecture_no -- 강의번호
                );

-- 참여자
ALTER TABLE task_participant
    ADD CONSTRAINT FK_task_lecture_TO_task_participant -- 강의 -> 참여자
        FOREIGN KEY (
                     lecture_no -- 강의번호
            )
            REFERENCES task_lecture ( -- 강의
                                     lecture_no -- 강의번호
                );

-- 참여자
ALTER TABLE task_participant
    ADD CONSTRAINT FK_task_member_TO_task_participant -- 회원 -> 참여자
        FOREIGN KEY (
                     member_no -- 회원번호
            )
            REFERENCES task_member ( -- 회원
                                    member_no -- 회원번호
                );

-- 참여자
ALTER TABLE task_participant
    ADD CONSTRAINT FK_task_participation_type_TO_task_participant -- 참여유형 -> 참여자
        FOREIGN KEY (
                     parti_type_no -- 참여유형번호
            )
            REFERENCES task_participation_type ( -- 참여유형
                                                parti_type_no -- 참여유형번호
                );

-- 과제제출
ALTER TABLE task_submission
    ADD CONSTRAINT FK_task_participant_TO_task_submission -- 참여자 -> 과제제출
        FOREIGN KEY (
                     lecture_no, -- 강의번호
                     submitter   -- 제출자
            )
            REFERENCES task_participant ( -- 참여자
                                         lecture_no, -- 강의번호
                                         member_no   -- 회원번호
                );

-- 과제제출
ALTER TABLE task_submission
    ADD CONSTRAINT FK_task_assignment_TO_task_submission -- 과제 -> 과제제출
        FOREIGN KEY (
                     assign_no -- 과제번호
            )
            REFERENCES task_assignment ( -- 과제
                                        assign_no -- 과제번호
                );

-- 강의게시판
ALTER TABLE task_lecture_board
    ADD CONSTRAINT FK_task_participant_TO_task_lecture_board -- 참여자 -> 강의게시판
        FOREIGN KEY (
                     lecture_no, -- 강의번호
                     lec_writer  -- 강의게시글 작성자
            )
            REFERENCES task_participant ( -- 참여자
                                         lecture_no, -- 강의번호
                                         member_no   -- 회원번호
                );

-- 강의게시글 댓글
ALTER TABLE task_lec_board_reply
    ADD CONSTRAINT FK_task_member_TO_task_lec_board_reply -- 회원 -> 강의게시글 댓글
        FOREIGN KEY (
                     lec_rep_writer -- 강의게시글 댓글 작성자
            )
            REFERENCES task_member ( -- 회원
                                    member_no -- 회원번호
                );

-- 강의게시글 댓글
ALTER TABLE task_lec_board_reply
    ADD CONSTRAINT FK_task_lecture_board_TO_task_lec_board_reply -- 강의게시판 -> 강의게시글 댓글
        FOREIGN KEY (
                     lec_board_no -- 강의게시글 번호
            )
            REFERENCES task_lecture_board ( -- 강의게시판
                                           lec_board_no -- 강의게시글 번호
                );

-- 게시글첨부파일
ALTER TABLE task_board_attach_file
    ADD CONSTRAINT FK_task_free_board_TO_task_board_attach_file -- 자유게시판 -> 게시글첨부파일
        FOREIGN KEY (
                     free_board_no -- 자유게시글 번호
            )
            REFERENCES task_free_board ( -- 자유게시판
                                        free_board_no -- 자유게시글 번호
                );

-- 자유게시판
ALTER TABLE task_free_board
    ADD CONSTRAINT FK_task_member_TO_task_free_board -- 회원 -> 자유게시판
        FOREIGN KEY (
                     free_writer -- 자유게시글 작성자
            )
            REFERENCES task_member ( -- 회원
                                    member_no -- 회원번호
                );

-- 자유게시글 댓글
ALTER TABLE task_free_board_reply
    ADD CONSTRAINT FK_task_free_board_TO_task_free_board_reply -- 자유게시판 -> 자유게시글 댓글
        FOREIGN KEY (
                     free_board_no -- 자유게시글 번호
            )
            REFERENCES task_free_board ( -- 자유게시판
                                        free_board_no -- 자유게시글 번호
                );

-- 자유게시글 댓글
ALTER TABLE task_free_board_reply
    ADD CONSTRAINT FK_task_member_TO_task_free_board_reply -- 회원 -> 자유게시글 댓글
        FOREIGN KEY (
                     free_rep_writer -- 자유게시글 댓글 작성자
            )
            REFERENCES task_member ( -- 회원
                                    member_no -- 회원번호
                );