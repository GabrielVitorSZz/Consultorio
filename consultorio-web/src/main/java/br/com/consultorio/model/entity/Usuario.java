package br.com.consultorio.model.entity;
import jakarta.persistence.*;

@Entity @Table(name = "usuario")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 150) private String nome;
    @Column(nullable = false, unique = true, length = 150) private String email;
    @Column(nullable = false, length = 200) private String senha;
    @Column(nullable = false) private boolean ativo = true;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "perfil_id", nullable = false) private PerfilUsuario perfil;
    public Usuario() {}
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; } public void setNome(String n) { this.nome = n; }
    public String getEmail() { return email; } public void setEmail(String e) { this.email = e; }
    public String getSenha() { return senha; } public void setSenha(String s) { this.senha = s; }
    public boolean isAtivo() { return ativo; } public void setAtivo(boolean a) { this.ativo = a; }
    public PerfilUsuario getPerfil() { return perfil; } public void setPerfil(PerfilUsuario p) { this.perfil = p; }
}
