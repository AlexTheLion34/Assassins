insert into assassins_user (id, password, username, role) values (1, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Napoleon', 'CUSTOMER');
insert into customer (id, balance) values (1, 2500);

insert into assassins_user (id, password, username, role) values (2, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Altair', 'EXECUTOR');
insert into executor (id, balance, busy, rating) values (2, 2500, false, 4.0);
insert into assassins_user (id, password, username, role) values (6, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Aragon', 'EXECUTOR');
insert into executor (id, balance, busy, rating) values (6, 2500, false, 4.0);

insert into assassins_user (id, password, username, role) values (3, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Master1', 'MASTER_ASSASSIN');
insert into master (id) values (3);

insert into assassins_user (id, password, username, role) values (4, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Gunsmith1', 'GUNSMITH');
insert into gunsmith (id) values (4);
insert into assassins_user (id, password, username, role) values (7, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Gunsmith2', 'GUNSMITH');
insert into gunsmith (id) values (7);

insert into assassins_user (id, password, username, role) values (5, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Cabman1', 'CABMAN');
insert into cabman (id) values (5);
insert into assassins_user (id, password, username, role) values (8, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Cabman2', 'CABMAN');
insert into cabman (id) values (8);
-- insert into users (id, password, username, busy, role) values (3, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Baltasar', false, 'EXECUTOR');
-- insert into users (id, password, username, busy, role) values (4, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Aragon', false, 'EXECUTOR');
-- insert into users (id, password, username, busy, role) values (5, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Gerald', false, 'EXECUTOR');
-- insert into users (id, password, username, busy, role) values (6, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Jhon', false, 'EXECUTOR');
--
-- insert into user_info (balance, user_id) values (2300, 1);
--
-- insert into user_info (balance, rating, user_id) values (1000, 3.5, 2);
-- insert into user_info (balance, rating, user_id) values (2000, 3.8, 3);
-- insert into user_info (balance, rating, user_id) values (1500, 4.5, 4);
-- insert into user_info (balance, rating, user_id) values (1600, 5.0, 5);
-- insert into user_info (balance, rating, user_id) values (2300, 3.2, 6);
--
--
-- insert into users (id, password, username, busy, role) values (7, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Master 1', false, 'MASTER_ASSASSIN');
-- insert into users (id, password, username, busy, role) values (8, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Master 2', false, 'MASTER_ASSASSIN');
--
-- insert into users (id, password, username, busy, role) values (9, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Gun 1', false, 'GUNSMITH');
-- insert into users (id, password, username, busy, role) values (10, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Gun 2', false, 'GUNSMITH');
--
-- insert into users (id, password, username, busy, role) values (11, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Cab 1', false, 'CABMAN');
-- insert into users (id, password, username, busy, role) values (12, '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'Cab 2', false, 'CABMAN');