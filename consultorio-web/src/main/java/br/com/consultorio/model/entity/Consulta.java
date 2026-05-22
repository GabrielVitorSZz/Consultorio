package br.com.consultorio.model.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "consulta")
public class Consulta {
    public enum Status { AGENDADA, REALIZADA, CANCELADA, FALTA }
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "paciente_id", nullable = false) private Paciente paciente;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "medico_id", nullable = false) private Medico medico;
    @Column(name = "data_hora", nullable = false) private LocalDateTime dataHora;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Status status = Status.AGENDADA;
    @Column(length = 300) private String motivo;
    @Column(length = 1000) private String observacoes;
    public Consulta() {}
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Paciente getPaciente() { return paciente; } public void setPaciente(Paciente p) { this.paciente = p; }
    public Medico getMedico() { return medico; } public void setMedico(Medico m) { this.medico = m; }
    public LocalDateTime getDataHora() { return dataHora; } public void setDataHora(LocalDateTime d) { this.dataHora = d; }
    public Status getStatus() { return status; } public void setStatus(Status s) { this.status = s; }
    public String getMotivo() { return motivo; } public void setMotivo(String m) { this.motivo = m; }
    public String getObservacoes() { return observacoes; } public void setObservacoes(String o) { this.observacoes = o; }
}
