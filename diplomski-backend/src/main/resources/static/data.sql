insert into language (name) values ('Srpski');
insert into language(name) values ('Engleski');
insert into language(name) values ('Italijanski');
insert into language(name) values ('Spanski');

insert into course(name,local_id,foreign_id) values('Engleski(SRB)', 1, 2);

insert into chapter(level,name) values(1,'Osnove I');
insert into chapter(level,name) values(2,'Osnove II');
insert into chapter(level,name) values(3,'Osnove III');

insert into course_chapters values(1,1);
insert into course_chapters values(1,2);
insert into course_chapters values(1,3);

insert into lesson(lesson_type) values (0);
insert into lesson(lesson_type) values (1);
insert into lesson(lesson_type) values (2);
insert into lesson(lesson_type) values (3);
insert into lesson(lesson_type) values (2);
insert into lesson(lesson_type) values (1);
insert into lesson(lesson_type) values (0);

insert into chapter_lessons values(1,1);
insert into chapter_lessons values(1,2);
insert into chapter_lessons values(1,3);
insert into chapter_lessons values(2,4);
insert into chapter_lessons values(2,5);
insert into chapter_lessons values(3,6);
insert into chapter_lessons values(3,7);

insert into task(question, answer) values('I', 'Ja');
insert into task(question, answer) values('We', 'Mi');
insert into task(question, answer) values('Apple', 'Jabuka');
insert into task(question, answer) values('Water', 'Voda');
insert into task(question, answer) values('I drink', 'Ja pijem');
insert into task(question, answer) values('I eat', 'Ja jedem');

insert into lesson_tasks values(1,1);
insert into lesson_tasks values(1,2);
insert into lesson_tasks values(1,3);
insert into lesson_tasks values(1,4);
insert into lesson_tasks values(2,5);
insert into lesson_tasks values(2,6);

insert into user(email,role,name,password) values('petrovic@gmail.com', 0, 'Petar Petrovic', '1234');