package br.com.consultorio.model.dto;
public class ErroDTO {
    private String mensagem;
    public ErroDTO() {}
    public ErroDTO(String mensagem) { this.mensagem = mensagem; }
    public String getMensagem() { return mensagem; } public void setMensagem(String m) { this.mensagem = m; }
}
