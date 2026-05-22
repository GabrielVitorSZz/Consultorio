package br.com.consultorio.model.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity @Table(name = "paciente")
public class Paciente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 150) private String nome;
    @Column(nullable = false, unique = true, length = 20) private String cpf;
    @Column(length = 20) private String telefone;
    @Column(length = 150) private String email;
    @Column(name = "data_nascimento") private LocalDate dataNascimento;
    @Column(length = 1) private String sexo;
    @Column(length = 500) private String observacoes;
    @Column(nullable = false) private boolean ativo = true;
    public Paciente() {}
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; } public void setNome(String n) { this.nome = n; }
    public String getCpf() { return cpf; } public void setCpf(String c) { this.cpf = c; }
    public String getTelefone() { return telefone; } public void setTelefone(String t) { this.telefone = t; }
    public String getEmail() { return email; } public void setEmail(String e) { this.email = e; }
    public LocalDate getDataNascimento() { return dataNascimento; } public void setDataNascimento(LocalDate d) { this.dataNascimento = d; }
    public String getSexo() { return sexo; } public void setSexo(String s) { this.sexo = s; }
    public String getObservacoes() { return observacoes; } public void setObservacoes(String o) { this.observacoes = o; }
    public boolean isAtivo() { return ativo; } public void setAtivo(boolean a) { this.ativo = a; }
}
