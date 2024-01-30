insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$WvnqWAu.Cj01p3TUUNpsoumptsjhcmkx.ol2GejWaeIYMLzgkkg26',
(select id from authorities where authority = 'ROLE_ADMIN'));