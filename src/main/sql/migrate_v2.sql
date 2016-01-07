insert into `presidents_v2`.`presidents` (
	`id`, `lastname`, `firstname`, `order`, `wiki_link`
)
select `ID`, `lastname`, `firstname`, `order`, `wiki_link`
from `presidents`.`p_presidents`;

insert into `presidents_v2`.`events` (
	`id`,
	`description`,
	`president_id`,
	`weight`,
	`importance`,
	`category`,
	`summary`,
	`start`,
	`end`,
	`wiki_link`
)
select
	`ID`,
	`description`,
	`president_id`,
	`weight`,
	`importance`,
	`category`,
	`summary`,
	`start`,
	`end`,
	`wiki_link`
from `presidents`.`p_events`;

insert into `presidents_v2`.`users` (`username`, `password`, `enabled`)
select `username`, `password`, `enabled`
from `presidents`.`users`;

insert into `presidents_v2`.`authorities` (`username`, `authority`)
select `username`, `authority`
from `presidents`.`authorities`;



