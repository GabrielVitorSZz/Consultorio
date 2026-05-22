package br.com.consultorio.model.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "ficha_atendimento")
public class FichaAtendimento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "consulta_id", nullable = false, unique = true) private Consulta consulta;
    @Column(length = 500) private String diagnostico;
    @Column(length = 1000) private String prescricao;
    @Column(name = "exames_solicitados", length = 500) private String examesSolicitados;
    @Column(name = "observacoes_medico", length = 1000) private String observacoesMedico;
    @Column(name = "data_registro", nullable = false) private LocalDateTime dataRegistro = LocalDateTime.now();
    public FichaAtendimento() {}
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Consulta getConsulta() { return consulta; } public void setConsulta(Consulta c) { this.consulta = c; }
    public String getDiagnostico() { return diagnostico; } public void setDiagnostico(String d) { this.diagnostico = d; }
    public String getPrescricao() { return prescricao; } public void setPrescricao(String p) { this.prescricao = p; }
    public String getExamesSolicitados() { return examesSolicitados; } public void setExamesSolicitados(String e) { this.examesSolicitados = e; }
    public String getObservacoesMedico() { return observacoesMedico; } public void setObservacoesMedico(String o) { this.observacoesMedico = o; }
    public LocalDateTime getDataRegistro() { return dataRegistro; } public void setDataRegistro(LocalDateTime d) { this.dataRegistro = d; }
}
