- 댓글 게시판에 필요한 사항
게시판 분류:String kind;
글입력 번호: int idx; -sequence
글제목 : String title;
글 내용 : String content;
조회수 : int readcount;
그룹번호 : int groupId;
댓글깊이(tab) : int depth;
그룹내의 글번호 : int groupSeq; ->글입력번호가 시퀸스일 경우 글입력번호를 참조하여 사용가능하므로 필요없는 사항일 수 있음.
글쓴사람의 아이디: String writeId;
글쓴사람의 닉네임 : String writeName;
글쓴 날짜: Date writeDay;
첨부파일 : String filename;
글삭제여부:int isdel;

- 위의 사항을 오라클로 데이터베이스를 구성하시오.
drop table board;
create table board(
idx number primary key,
title varchar2(100) not null,
content clob,
readcount number(8) default(0) not null,
groupid number not null,
depth number not null,
groupseq number not null,
writeid varchar2(30) not null,
writename varchar2(30) not null,
writeday date not null,
filename varchar2(260),
isdel number(1) check(isdel between 0 and 1),
kind varchar2(20) not null
);

drop sequence board_idx_seq;
create sequence board_idx_seq increment by 1 start with 1;

insert into board(idx,title,content, readcount, 
groupid, depth, groupseq,
writeid,writename, writeday,
isdel,kind) 
values(board_idx_seq.nextval,'게시판 1번째 글','게시판 내용1', 0,
board_idx_seq.currval,0, 1,
'admin','김자바',sysdate,
0,'일반게시판');

insert into board(idx,title,content, readcount, 
groupid, depth, groupseq,
writeid,writename, writeday,
isdel,kind) 
values(board_idx_seq.nextval,'게시판 2번째 글','게시판 내용2', 0,
board_idx_seq.currval,0, 1,
'admin','김자바',sysdate,
0,'일반게시판');

insert into board(idx,title,content, readcount, 
groupid, depth, groupseq,
writeid,writename, writeday,
isdel,kind) 
values(board_idx_seq.nextval,'게시판 3번째 글','게시판 내용3', 0,
board_idx_seq.currval,0, 1,
'admin','김자바',sysdate,
0,'일반게시판');

insert into board(idx,title,content, readcount, 
groupid, depth, groupseq,
writeid,writename, writeday,
isdel,kind) 
values(board_idx_seq.nextval,'게시판 4번째 글','게시판 내용4', 0,
board_idx_seq.currval,0, 1,
'admin','김자바',sysdate,
0,'일반게시판');

select * from board;

--오라클에서 groupid는 parentid로 사용하기 때문에 댓글이 아닌 경우 0으로 값 변경
update board set groupid=0;

-- 깊이가 1인 댓글 달기
insert into board(idx,title,content, readcount, 
groupid, depth, groupseq,
writeid,writename, writeday,
isdel,kind) 
values(board_idx_seq.nextval,'게시판 2번째 글의 댓글 2-1','게시판 내용2-1', 0,
2,1,1,
'admin','김자바',sysdate,
0,'일반게시판');

insert into board(idx,title,content, readcount, 
groupid, depth, groupseq,
writeid,writename, writeday,
isdel,kind) 
values(board_idx_seq.nextval,'게시판 2번째 글의 댓글 2-2','게시판 내용2-2', 0,
2,1,1,
'admin','김자바',sysdate,
0,'일반게시판');

insert into board(idx,title,content, readcount, 
groupid, depth, groupseq,
writeid,writename, writeday,
isdel,kind) 
values(board_idx_seq.nextval,'게시판 2번째 글의 댓글 2-3','게시판 내용2-3', 0,
2,1,1,
'admin','김자바',sysdate,
0,'일반게시판');

--댓글의 댓글
insert into board(idx,title,content, readcount, 
groupid, depth, groupseq,
writeid,writename, writeday,
isdel,kind) 
values(board_idx_seq.nextval,'게시판 2번째 글의 댓글의 댓글 2-2-1','게시판 내용2-2-1', 0,
6,2,1,
'admin','김자바',sysdate,
0,'일반게시판');

--출력
select * from board start with groupid=0 
connect by prior idx=groupid 
order siblings by idx desc;

--여러개의 글을 반복하여 올리기
begin
    for I in 5..30
    loop
    insert into board(idx,title,content, readcount, 
    groupid, depth, groupseq,
    writeid,writename, writeday,
    isdel,kind) 
    values(board_idx_seq.nextval,'게시판 '|| I || '번째 글','게시판 내용'|| I, 0,
    0 ,0, 1, --처음만드는 글은 pid=0
    'admin','김자바',sysdate,
    0,'일반게시판');
    end loop;
end;

--글의 수정
update board
set title=title||'수정', content=content||'수정',writeday=sysdate   
where idx=4;

select * from board where idx=4;

--페이지글출력
select rownum,b.* from
(select rownum rn,a.* from
(select * from Board order by idx)a 
where rownum <=30 --페이지번호의 글번호(3page*10) 
order by rownum desc) b 
where rownum between 1 and 10 --한페이지에 들어가는 글의 수 
order by b.rn asc

--페이지를 입력받아 첫번째 물음표를 해결

select rownum,b.* from
(select rownum rn,a.* from
(select * from board start with groupid=0 
connect by prior idx=groupid 
order siblings by idx desc) a 
where rownum <=10  --page*한페이지당 글의 수 
order by rownum desc) b 
where rownum between 1 and 10 --한페이지 글의 수
order by b.rn asc;

--지워지지 않은 글이고, 게시판 종류가 일반게시판
select rownum,b.* from
(select rownum rn,a.* from
(select * from board where isdel=0 and kind='일반게시판' start with groupid=0 
connect by prior idx=groupid 
order siblings by idx desc) a 
where rownum <=10  --page*한페이지당 글의 수 
order by rownum desc) b 
where rownum between 1 and 10 --한페이지 글의 수
order by b.rn asc;
