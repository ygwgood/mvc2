drop table member;
create table member(
idx number primary key,
id varchar2(20) not null unique,
password varchar2(20) not null
);

drop sequence member_idx_seq;
create sequence member_idx_seq increment by 1 start with 1;

insert into member values(member_idx_seq.nextval,'admin','1111');