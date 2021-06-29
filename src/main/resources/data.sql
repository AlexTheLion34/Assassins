insert into users (balance, password, username) values (100000, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'alex');
insert into users (balance, password, username) values (100000, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'vasiliy');

insert into roles (id, name) values (1, 'ROLE_CUSTOMER');
insert into roles (id, name) values (2, 'ROLE_EXECUTOR');

insert into users_roles (users_id, roles_id) values (1, 1);
insert into users_roles (users_id, roles_id) values (2, 2);