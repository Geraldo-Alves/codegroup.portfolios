package com.codegroup.portfolios.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email; 
    private String senha;

    @OneToMany(mappedBy = "refIdUsuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PerfilUsuario> perfis = new ArrayList<>();

        public List<Perfil> getPerfis() {
        return perfis.stream()
                .map(PerfilUsuario::getRefIdPerfil)
                .toList();
    }
 
}
