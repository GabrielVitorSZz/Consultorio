package br.com.consultorio.model.entity;
import jakarta.persistence.*;

@Entity @Table(name = "medico")
public class Medico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true, length = 30) private String crm;
    @Column(nullable = false, length = 100) private String especialidade;
    @Column(length = 20) private String telefone;
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "usuario_id", nullable = false, unique = true) private Usuario usuario;
    public Medico() {}
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getCrm() { return crm; } public void setCrm(String c) { this.crm = c; }
    public String getEspecialidade() { return especialidade; } public void setEspecialidade(String e) { this.especialidade = e; }
    public String getTelefone() { return telefone; } public void setTelefone(String t) { this.telefone = t; }
    public Usuario getUsuario() { return usuario; } public void setUsuario(Usuario u) { this.usuario = u; }
    public String getNome() { return usuario != null ? usuario.getNome() : ""; }
}
