package br.com.consultorio.model.dto;
public class TokenDTO {
    private String token;
    private String nome;
    private String email;
    private String perfil;
    private Long usuarioId;
    public TokenDTO() {}
    public TokenDTO(String token, String nome, String email, String perfil, Long usuarioId) {
        this.token = token; this.nome = nome; this.email = email; this.perfil = perfil; this.usuarioId = usuarioId;
    }
    public String getToken() { return token; } public void setToken(String t) { this.token = t; }
    public String getNome() { return nome; } public void setNome(String n) { this.nome = n; }
    public String getEmail() { return email; } public void setEmail(String e) { this.email = e; }
    public String getPerfil() { return perfil; } public void setPerfil(String p) { this.perfil = p; }
    public Long getUsuarioId() { return usuarioId; } public void setUsuarioId(Long id) { this.usuarioId = id; }
}
