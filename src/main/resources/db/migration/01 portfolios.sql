CREATE TABLE "pessoa" (
  "id" serial PRIMARY KEY,
  "nome" varchar(100),
  "cpf" varchar(14),
  "celular" varchar(11),
  "created_at" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "usuario" (
  "id" serial PRIMARY KEY,
  "email" varchar(100),
  "senha" varchar(255),
  "ref_id_pessoa" long NOT NULL,
  "created_at" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "perfil" (
  "id" serial PRIMARY KEY,
  "nome" varchar(100),
  "descricao" varchar(100),
  "created_at" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "perfil_usuario" (
  "id" serial PRIMARY KEY,
  "ref_id_perfil" long NOT NULL,
  "ref_id_usuario" long NOT NULL,
  "created_at" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "status_projeto" (
  "id" serial PRIMARY KEY,
  "nome" varchar(100),
  "id_status_pai" long,
  "permite_exclusao" bool,
  "created_at" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "projeto" (
  "id" serial PRIMARY KEY,
  "nome" varchar(100),
  "data_inicio" timestamp,
  "data_previsao_termino" timestamp,
  "data_termino" timestamp,
  "orcamento_total" decimal,
  "descricao" varchar(1000),
  "created_at" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "andamento" (
  "id" serial PRIMARY KEY,
  "ref_id_projeto" long NOT NULL,
  "ref_id_status" long NOT NULL,
  "ref_id_membro" long NOT NULL,
  "atual" bool DEFAULT (true),
  "data_inicio" timestamp DEFAULT (current_timestamp),
  "data_fim" timestamp
);

CREATE TABLE "membro" (
  "id" serial PRIMARY KEY,
  "ref_id_pessoa" long NOT NULL,
  "ref_id_projeto" long NOT NULL,
  "ref_id_atribuicao" long NOT NULL,
  "gerente_responsavel" bool,
  "status" varchar(1),
  "created_at" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "atribuicao" (
  "id" serial PRIMARY KEY,
  "nome" varchar(100),
  "descricao" varchar(200),
  "created_at" timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "usuario" ADD FOREIGN KEY ("ref_id_pessoa") REFERENCES "pessoa" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "perfil_usuario" ADD FOREIGN KEY ("ref_id_perfil") REFERENCES "perfil" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "perfil_usuario" ADD FOREIGN KEY ("ref_id_usuario") REFERENCES "usuario" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "status_projeto" ADD FOREIGN KEY ("id") REFERENCES "status_projeto" ("id_status_pai") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "status_projeto" ADD FOREIGN KEY ("nome") REFERENCES "andamento" ("ref_id_status") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "projeto" ADD FOREIGN KEY ("data_termino") REFERENCES "andamento" ("ref_id_projeto") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "pessoa" ADD FOREIGN KEY ("id") REFERENCES "membro" ("ref_id_pessoa") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "projeto" ADD FOREIGN KEY ("id") REFERENCES "membro" ("ref_id_projeto") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "atribuicao" ADD FOREIGN KEY ("id") REFERENCES "membro" ("ref_id_atribuicao") DEFERRABLE INITIALLY IMMEDIATE;