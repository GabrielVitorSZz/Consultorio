package br.com.consultorio.model.dto;
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Long perfilId;
    private String perfilNome;
    private boolean ativo;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; } public void setNome(String n) { this.nome = n; }
    public String getEmail() { return email; } public void setEmail(String e) { this.email = e; }
    public String getSenha() { return senha; } public void setSenha(String s) { this.senha = s; }
    public Long getPerfilId() { return perfilId; } public void setPerfilId(Long p) { this.perfilId = p; }
    public String getPerfilNome() { return perfilNome; } public void setPerfilNome(String p) { this.perfilNome = p; }
    public boolean isAtivo() { return ativo; } public void setAtivo(boolean a) { this.ativo = a; }
}
