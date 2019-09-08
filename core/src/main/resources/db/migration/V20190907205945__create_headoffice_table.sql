create table head_office
(
	id serial not null constraint headoff_pk primary key,
	balance decimal not null,
	one_cent_count int not null,
	ten_cent_count decimal not null,
	quarter_count int not null,
	one_dollar_count int not null,
	five_dollar_count int not null,
	twenty_dollar_count int not null
);
