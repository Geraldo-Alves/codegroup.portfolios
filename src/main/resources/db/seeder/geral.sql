/*Perfis*/
insert into perfil (nome, descricao) values ('root', 'Perfil com acesso total ao sistema');
insert into perfil (nome, descricao) values ('admin', 'Perfil com acesso total ao sistema, exceto para gerenciamento de usuários e perfis');
insert into perfil (nome, descricao) values ('gerente', 'Perfil com acesso para gerenciamento de projetos e membros');
insert into perfil (nome, descricao) values ('funcionario', 'Perfil com acesso para visualização de projetos e membros');

/*Usuários*/
insert into pessoa (nome, cpf, celular) values ('Root', '000.000.000-00', '61199999999');
/* Senha: 123456 */
insert into usuario (email, senha, refIdPessoa) values ('root@root.com', '$2a$10$AnQLTIwoBpx7OgRDLvJNNeXpWV1e.PxvmaNBmhn7D3OB.zKAPMJ06', 1);
insert into perfil_usuario (ref_id_perfil, ref_id_usuario) values (1, 1);

/*Status de Projeto*/
insert into status_projeto (nome, id_status_pai, permite_exclusao) values ('Em análise', null, true);
insert into status_projeto (nome, id_status_pai, permite_exclusao) values ('Análise realizada', 1, true);
insert into status_projeto (nome, id_status_pai, permite_exclusao) values ('Análise aprovada', 2, true);
insert into status_projeto (nome, id_status_pai, permite_exclusao) values ('Iniciado', 3, false);
insert into status_projeto (nome, id_status_pai, permite_exclusao) values ('Planejado', 4, true);
insert into status_projeto (nome, id_status_pai, permite_exclusao) values ('Em andamento', 5, false);
insert into status_projeto (nome, id_status_pai, permite_exclusao) values ('Encerrado', 6, false);
insert into status_projeto (nome, id_status_pai, permite_exclusao) values ('Cancelado', null, true);

/*Atribuições*/
insert into atribuicao (nome, descricao) values ('Gerente de Projeto', 'Responsável por gerenciar o projeto e a equipe');
insert into atribuicao (nome, descricao) values ('Funcionário', 'Responsável por executar as tarefas do projeto');