create table snack_machines
(
	id serial not null constraint sm_pk primary key,
	one_cent_count int not null,
	ten_cent_count decimal not null,
	quarter_count int not null,
	one_dollar_count int not null,
	five_dollar_count int not null,
	twenty_dollar_count int not null
);
