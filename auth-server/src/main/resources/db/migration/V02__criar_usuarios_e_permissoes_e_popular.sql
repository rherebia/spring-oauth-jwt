create table usuario (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(256),
    senha VARCHAR(256)
);

create table permissao (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(256)
);

create table usuario_permissao (
    usuario_id BIGINT,
    permissao_id BIGINT,
    
    foreign key (usuario_id) references usuario (id),
    foreign key (permissao_id) references permissao (id),
    primary key (usuario_id, permissao_id)
);

insert into permissao
	(id, nome)
values
	(1, 'USER'),
	(2, 'ADMIN');
	
insert into usuario 
	(id, nome, senha)
values
	(1, 'john', '123'),
	(2, 'tom', '111'),
	(3, 'user1', 'pass'),
	(4, 'admin', 'admin'),
	(5, 'bse', '123');
	
insert into usuario_permissao
	(usuario_id, permissao_id)
values
	(1, 1),
	(2, 2),
	(3, 1),
	(4, 2),
	(5, 1);