package br.com.consultorio.model.dto;
/** DTO para agendamento e visualização de consultas */
public class AgendamentoConsultaDTO {
    private Long id;
    private Long pacienteId;
    private String pacienteNome;
    private Long medicoId;
    private String medicoNome;
    private String medicoEspecialidade;
    private String dataHora;
    private String status;
    private String motivo;
    private String observacoes;
    private boolean temFicha;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Long getPacienteId() { return pacienteId; } public void setPacienteId(Long p) { this.pacienteId = p; }
    public String getPacienteNome() { return pacienteNome; } public void setPacienteNome(String p) { this.pacienteNome = p; }
    public Long getMedicoId() { return medicoId; } public void setMedicoId(Long m) { this.medicoId = m; }
    public String getMedicoNome() { return medicoNome; } public void setMedicoNome(String m) { this.medicoNome = m; }
    public String getMedicoEspecialidade() { return medicoEspecialidade; } public void setMedicoEspecialidade(String m) { this.medicoEspecialidade = m; }
    public String getDataHora() { return dataHora; } public void setDataHora(String d) { this.dataHora = d; }
    public String getStatus() { return status; } public void setStatus(String s) { this.status = s; }
    public String getMotivo() { return motivo; } public void setMotivo(String m) { this.motivo = m; }
    public String getObservacoes() { return observacoes; } public void setObservacoes(String o) { this.observacoes = o; }
    public boolean isTemFicha() { return temFicha; } public void setTemFicha(boolean t) { this.temFicha = t; }
}
