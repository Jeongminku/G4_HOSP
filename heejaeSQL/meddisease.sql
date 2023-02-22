------------------------------------------------------------
CREATE DATABASE hosp DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

use hosp;

drop table archive_disease;
drop table membermed;
drop table med;
drop table disease;

select * from member;
select * from med;
select * from disease;

insert into med values(1, '저희는 건강한 눈과 가치 있는 생활을 많은 사람들이 누릴 수 있도록 항상 노력해 왔습니다.
3명의 교수진과 7명의 안과 간호사 및 3명의 전문기사가 각 환자에 따라 각 분야에 맞는
최신의 장비를 이용하여 진단 및 치료를 할 수 있도록 최선을 다하고 있습니다.', '안과');
insert into med values(2, '건강한 치아관리를 위한 최고의 장비들 보유', '치과');
insert into med values(3, '정형외과는 사지 및 척추의 뼈와 관절, 근육 및 인대를 포함한
인체의 모든 근골격계 질환 및 외상에 대하여 인공지능을 접목한 정확한 진단, 임상적 데이터를 바탕으로 한
가장 효과적인 비수술적 치료 및 수술적 치료를 시행하고 있습니다.', '정형외과');
insert into med values(4, '피부과는 건강하고 아름다운 피부를 위해 통합적이고 전문적인 진료를 제공하고 있습니다.', '피부과');
insert into med values(5, '산부인과는 임신과 출산을 담당하는 산과와 여성 생식기질환을 다루는 부인과로 구성됩니다.
모든 여성의 건강을 위해 전문화된 편안한 진료를 제공합니다.', '산부인과');

insert into disease values(1, '백내장', 1);
insert into disease values(2, '사랑니', 2);
insert into disease values(3, '허리디스크', 3);
insert into disease values(4, '피부암', 4);
insert into disease values(5, '난임', 5);

select * from hospitalize a join member b on a.member_id = b.member_id where a.hospstatus = 'Y';








-- seq_mysql 테이블 생성
create table seq (
id int not null,
seq_name varchar(50) not null
);

-- 생성된 펑션 삭제 
drop function if exists get_seq;

-- auto_increment 적용
delimiter $$
create function get_seq (p_seq_name varchar(45))
returns int reads sql data
begin
declare result_id int;
update seq set id = last_insert_id(id+1)
where seq_name = p_seq_name;
set result_id = (select last_insert_id());
return result_id;
end $$
delimiter;

-- 시퀀스 생성
insert into seq values (1, 'med_id');

-- 시퀀스 삽입
get_seq('boardSeq')