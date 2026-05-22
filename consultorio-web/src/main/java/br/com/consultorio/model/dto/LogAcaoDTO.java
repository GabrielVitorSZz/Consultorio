package br.com.consultorio.model.dto;
public class LogAcaoDTO {
    private Long id;
    private String acao;
    private String usuarioEmail;
    private String dataHora;
    private String detalhes;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getAcao() { return acao; } public void setAcao(String a) { this.acao = a; }
    public String getUsuarioEmail() { return usuarioEmail; } public void setUsuarioEmail(String e) { this.usuarioEmail = e; }
    public String getDataHora() { return dataHora; } public void setDataHora(String d) { this.dataHora = d; }
    public String getDetalhes() { return detalhes; } public void setDetalhes(String d) { this.detalhes = d; }
}
