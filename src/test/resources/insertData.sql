
--
-- random presidents data
--
drop procedure if exists insert_presidents
;

create procedure insert_presidents()
begin
    declare v_max int default 10;
    declare v_i int default 0;

    start transaction;
    while v_i < v_max do
        insert into `p_presidents` (`lastname`, `firstname`, `order`, `wiki_link`)
        values (
            concat('prez', rand()),
            concat('mr', rand()),
            v_i + 1,
            concat('http://what', rand()));

        set v_i = v_i + 1;
    end while;
    commit;
end
;

call insert_presidents()
;

--
-- random events data
--
drop procedure if exists insert_events
;

create procedure insert_events()
begin
    declare v_max int default 100;
    declare v_i int default 0;

    start transaction;
    while v_i < v_max do
        insert into `p_events` (
            `description`,
            `president_id`,
            `weight`,
            `importance`,
            `category`,
            `summary`,
            `start`,
            `end`,
            `wiki_link`)
        values (
            concat('desc', rand()),
            (select `ID` from `p_presidents` order by rand() limit 1),
            floor(0 + (rand() * 10)),
            elt(1 + floor(rand() * 2), 'minor','major'),
            elt(1 + floor(rand() * 5), 'foreign','domestic','appointment','misc','unassigned'),
            concat('summary', rand()),
            concat(floor(1970 + (rand() * 30)), '-01-01'),
            concat(floor(1970 + (rand() * 30)), '-02-02'),
            concat('http://wherever', rand()));

        set v_i = v_i + 1;
    end while;
    commit;
end
;

call insert_events()
;