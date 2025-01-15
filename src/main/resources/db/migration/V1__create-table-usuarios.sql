
create table usuarios(

    id bigint not null auto_increment,
    user_name varchar(100) unique not null,
    clave varchar(300) not null,

    primary key(id)

);
