CREATE TABLE perfil_usuario (
    id BIGINT PRIMARY KEY,
    ref_id_perfil BIGINT not null,
    ref_id_usuario BIGINT not null,
    status VARCHAR(1), 
    FOREIGN KEY (ref_id_perfil) REFERENCES perfil(id),
    FOREIGN KEY (ref_id_usuario) REFERENCES usuario(id)
);