package br.com.consultorio.model.entity;
import jakarta.persistence.*;

@Entity @Table(name = "perfil_usuario")
public class PerfilUsuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true, length = 50) private String nome;
    @Column(length = 200) private String descricao;
    public PerfilUsuario() {}
    public PerfilUsuario(Long id) { this.id = id; }
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; } public void setNome(String n) { this.nome = n; }
    public String getDescricao() { return descricao; } public void setDescricao(String d) { this.descricao = d; }
}
