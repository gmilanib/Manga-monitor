CREATE TABLE volume (id integer,
                     url varchar(255),
                     numero integer not null,
                     titulo varchar(255),
                     primary key (id));

CREATE TABLE "tentativa_consulta" (id integer primary key,
                                   data varchar(255),
                                   disponibilidade varchar(255) check (disponibilidade in ('DISPONIVEL', 'INDISPONIVEL', 'DESCONHECIDA')), valor numeric(38, 2),
                                   volume_id bigint not null,
                                   titulo varchar(255),
                                   numero integer);
