create table slots
(
	id serial not null constraint slots_pk primary key,
	quantity int not null,
	price decimal not null,
	snack_id int not null,
	snack_machine_id int not null,
	position int not null
);
