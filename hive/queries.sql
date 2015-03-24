
select * from movies;
select * from movies limit 10;

describe movies;
describe formatted movies;

select movieid, title from movies limit 10;
select m.movieid, m.title from movies m limit 10;

select upper(title) from movies limit 10;

select count(*) from movies;
select avg(rating) from ratings;

SELECT count(*) FROM ratings;
SELECT count(DISTINCT userid) FROM ratings;
SELECT count(DISTINCT userid),count(DISTINCT movieid) FROM ratings;

select movieid, title, split(genres,"\\|") AS genre from movies limit 10;


