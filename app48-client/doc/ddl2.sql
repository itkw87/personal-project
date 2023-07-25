create table ps_board(
  board_no int not null, 
  title varchar(255) not null,
  content text null,
  writer int not null,
  password varchar(100) null,
  view_count int default 0,
  created_date datetime default now(),
  category int not null
);

alter table ps_board
  add constraint primary key (board_no),
  modify column board_no int not null auto_increment;
  
create table ps_member(
  member_no int not null,
  authority varchar(1) not null,
  birth varchar(20) not null,
  grade int null,
  name varchar(20) not null,
  gender char(1) not null,
  korean_score int null,
  english_score int null,
  math_score int null,
  scoreAvg float null,
  email varchar(50) not null,
  password varchar(100) not null,
  status boolean not null,
  created_date date default (current_date())
  );
  
  alter table ps_member
    add constraint primary key (member_no),
    modify column member_no int not null auto_increment;
    
  -- 게시판 작성자에 대해 외부키 설정
  alter table ps_board
    add constraint ps_board_fk foreign key (writer) references ps_member (member_no);
