insert into assassins_user (id, password, username, role) values (1, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Napoleon', 'CUSTOMER');
insert into customer (id, balance) values (1, 2500);

insert into assassins_user (id, password, username, role) values (2, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Altair', 'EXECUTOR');
insert into executor (id, balance, busy, rating, num_of_completed_requests) values (2, 1500, false, 3.0, 1);
insert into assassins_user (id, password, username, role) values (3, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Aragon', 'EXECUTOR');
insert into executor (id, balance, busy, rating, num_of_completed_requests) values (3, 1000, false, 4.0, 1);
insert into assassins_user (id, password, username, role) values (4, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Gerald', 'EXECUTOR');
insert into executor (id, balance, busy, rating, num_of_completed_requests) values (4, 1000, false, 3.7, 1);
insert into assassins_user (id, password, username, role) values (5, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Legolas', 'EXECUTOR');
insert into executor (id, balance, busy, rating, num_of_completed_requests) values (5, 1000, false, 3.9, 1);
insert into assassins_user (id, password, username, role) values (6, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Gimli', 'EXECUTOR');
insert into executor (id, balance, busy, rating, num_of_completed_requests) values (6, 1000, false, 4.8, 1);

insert into assassins_user (id, password, username, role) values (7, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Master1', 'MASTER_ASSASSIN');
insert into master (id) values (7);
insert into assassins_user (id, password, username, role) values (8, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Master2', 'MASTER_ASSASSIN');
insert into master (id) values (8);

insert into assassins_user (id, password, username, role) values (9, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Gunsmith1', 'GUNSMITH');
insert into gunsmith (id) values (9);
insert into assassins_user (id, password, username, role) values (10, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Gunsmith2', 'GUNSMITH');
insert into gunsmith (id) values (10);

insert into assassins_user (id, password, username, role) values (11, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Cabman1', 'CABMAN');
insert into cabman (id) values (11);
insert into assassins_user (id, password, username, role) values (12, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Cabman2', 'CABMAN');
insert into cabman (id) values (12);
