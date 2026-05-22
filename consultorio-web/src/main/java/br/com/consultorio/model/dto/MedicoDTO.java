package br.com.consultorio.model.dto;
public class MedicoDTO {
    private Long id;
    private String nome;
    private String crm;
    private String especialidade;
    private String telefone;
    private Long usuarioId;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; } public void setNome(String n) { this.nome = n; }
    public String getCrm() { return crm; } public void setCrm(String c) { this.crm = c; }
    public String getEspecialidade() { return especialidade; } public void setEspecialidade(String e) { this.especialidade = e; }
    public String getTelefone() { return telefone; } public void setTelefone(String t) { this.telefone = t; }
    public Long getUsuarioId() { return usuarioId; } public void setUsuarioId(Long u) { this.usuarioId = u; }
}
