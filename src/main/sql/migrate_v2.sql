insert into `presidents_v2`.`presidents` (
	`id`, `lastname`, `firstname`, `order`, `wiki_link`
)
select `ID`, `lastname`, `firstname`, `order`, `wiki_link`
from `presidents`.`p_presidents`;

CREATE TABLE `eventsV2` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	`description` TEXT NOT NULL,
	`president_id` INT(11) NOT NULL,
	`weight` TINYINT(4) NOT NULL DEFAULT 0,
	`importance` varchar(16) NOT NULL DEFAULT 'minor',
	`category` varchar(32) NULL,
	`summary` TEXT NULL,
	`start` DATE NULL,
	`end` DATE NULL,
	`wiki_link` TEXT NULL,
	primary key (`id`),
	CONSTRAINT `events_ibfk_1`
	FOREIGN KEY (`president_id`)
	REFERENCES `presidents` (`id`))
;

insert into `eventsV2` (
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
	case when category = 'unassigned' then null else category end,
	`summary`,
	`start`,
	`end`,
	`wiki_link`
from `events`;

insert into `presidents_v2`.`users` (`username`, `password`, `enabled`)
select `username`, `password`, `enabled`
from `presidents`.`users`;

insert into `presidents_v2`.`authorities` (`username`, `authority`)
select `username`, `authority`
from `presidents`.`authorities`;



