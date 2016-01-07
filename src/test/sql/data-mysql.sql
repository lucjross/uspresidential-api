
--
-- random presidents data
--


--drop procedure if exists insert_presidents
--;

create procedure insert_presidents()
begin
    declare v_max int default 10;
    declare v_i int default 1;

    start transaction;
    while v_i <= v_max do
        insert into `presidents` (`id`, `lastname`, `firstname`, `order`, `wiki_link`)
        values (
            v_i,
            concat('prez', v_i),
            concat('mr', v_i),
            v_i,
            concat('http://wherever/', v_i));
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
--drop procedure if exists insert_events
--;

create procedure insert_events()
begin
    declare v_max int default 100;
    declare v_i int default 0;

    start transaction;
    while v_i < v_max do
        insert into `events` (
            `id`,
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
            v_i,
            concat('desc', v_i),
            mod(v_i, 10) + 1,
            mod(v_i + 5, 10),
            elt(1 + mod(v_i, 2), 'minor','major'),
            elt(1 + mod(v_i, 5), 'foreign','domestic','appointment','misc','unassigned'),
            concat('summary', v_i),
            concat(1950 + v_i, '-01-01'),
            concat(1950 + v_i, '-02-02'),
            concat('http://wherever/', v_i));
        set v_i = v_i + 1;
    end while;
    commit;
end
;

call insert_events()
;

create procedure insert_users()
begin
    declare v_max int default 9;
    declare v_i int default 0;

    start transaction;
    while v_i < v_max do
        insert into `users` (
            `username`,
            `password`,
            `enabled`)
        values (
            concat('user', v_i),
            concat('pass', v_i),
            1);
        set v_i = v_i + 1;
    end while;
    insert into `users` (`username`, `password`, `enabled`)
        values (concat('user', v_max), concat('pass', v_max), 0);
    commit;
end
;

call insert_users()
;

insert into `users` (`username`, `password`, `enabled`) values (
    'lucas', '$2a$10$5MNaiZOpy/LGV8Z4qciDM.oL7V3pN2MngKGT7kwwYcza/SPMH7nQS', 1) -- 'fuck'
;

insert into `authorities` (`username`, `authority`) values (
    'lucas', 'ROLE_ADMIN')
;

create procedure insert_votes()
begin
    declare v_max int default 10;
    declare v_i int default 0;

    start transaction;
    while v_i < v_max - 5 do
        insert into `votes` (`user_username`, `event_id`, `vote`, `weight`)
            values (concat('user', v_i), v_i + 1, 'Yes', mod(v_i + 5, 10));
        set v_i = v_i + 1;
    end while;

    while v_i < v_max do
        insert into `votes` (`user_username`, `event_id`, `vote`, `weight`)
            values (concat('user', v_i), v_i + 1, 'No', mod(v_i + 5, 10));
        set v_i = v_i + 1;
    end while;

    commit;
end
;

call insert_votes()
;
