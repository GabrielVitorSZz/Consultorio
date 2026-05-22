package br.com.consultorio.model.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "log_acao")
public class LogAcao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 200) private String acao;
    @Column(name = "usuario_id") private Long usuarioId;
    @Column(name = "usuario_email", length = 150) private String usuarioEmail;
    @Column(name = "data_hora", nullable = false) private LocalDateTime dataHora = LocalDateTime.now();
    @Column(length = 500) private String detalhes;
    public LogAcao() {}
    public LogAcao(String acao, Long usuarioId, String usuarioEmail, String detalhes) {
        this.acao = acao; this.usuarioId = usuarioId; this.usuarioEmail = usuarioEmail;
        this.detalhes = detalhes; this.dataHora = LocalDateTime.now();
    }
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getAcao() { return acao; } public void setAcao(String a) { this.acao = a; }
    public Long getUsuarioId() { return usuarioId; } public void setUsuarioId(Long u) { this.usuarioId = u; }
    public String getUsuarioEmail() { return usuarioEmail; } public void setUsuarioEmail(String e) { this.usuarioEmail = e; }
    public LocalDateTime getDataHora() { return dataHora; } public void setDataHora(LocalDateTime d) { this.dataHora = d; }
    public String getDetalhes() { return detalhes; } public void setDetalhes(String d) { this.detalhes = d; }
}
