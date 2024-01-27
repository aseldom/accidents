create table accidents_rules (
  id serial primary key,
  accidents_id int references accidents(id),
  rules_id int references rules(id)
);
