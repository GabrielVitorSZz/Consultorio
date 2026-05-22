package br.com.consultorio.model.dto;
public class PacienteDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String dataNascimento;
    private String sexo;
    private String observacoes;
    private boolean ativo;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; } public void setNome(String n) { this.nome = n; }
    public String getCpf() { return cpf; } public void setCpf(String c) { this.cpf = c; }
    public String getTelefone() { return telefone; } public void setTelefone(String t) { this.telefone = t; }
    public String getEmail() { return email; } public void setEmail(String e) { this.email = e; }
    public String getDataNascimento() { return dataNascimento; } public void setDataNascimento(String d) { this.dataNascimento = d; }
    public String getSexo() { return sexo; } public void setSexo(String s) { this.sexo = s; }
    public String getObservacoes() { return observacoes; } public void setObservacoes(String o) { this.observacoes = o; }
    public boolean isAtivo() { return ativo; } public void setAtivo(boolean a) { this.ativo = a; }
}
