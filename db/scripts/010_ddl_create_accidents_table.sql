create table accidents (
  id serial primary key,
  name text,
  text text,
  address text,
  type_id int not null references types(id)
);
