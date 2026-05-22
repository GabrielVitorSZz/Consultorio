package br.com.consultorio.model.dto;
/** DTO para ficha/histórico de atendimento médico */
public class FichaHistoricoDTO {
    private Long id;
    private Long consultaId;
    private String pacienteNome;
    private String medicoNome;
    private String dataConsulta;
    private String diagnostico;
    private String prescricao;
    private String examesSolicitados;
    private String observacoesMedico;
    private String dataRegistro;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Long getConsultaId() { return consultaId; } public void setConsultaId(Long c) { this.consultaId = c; }
    public String getPacienteNome() { return pacienteNome; } public void setPacienteNome(String p) { this.pacienteNome = p; }
    public String getMedicoNome() { return medicoNome; } public void setMedicoNome(String m) { this.medicoNome = m; }
    public String getDataConsulta() { return dataConsulta; } public void setDataConsulta(String d) { this.dataConsulta = d; }
    public String getDiagnostico() { return diagnostico; } public void setDiagnostico(String d) { this.diagnostico = d; }
    public String getPrescricao() { return prescricao; } public void setPrescricao(String p) { this.prescricao = p; }
    public String getExamesSolicitados() { return examesSolicitados; } public void setExamesSolicitados(String e) { this.examesSolicitados = e; }
    public String getObservacoesMedico() { return observacoesMedico; } public void setObservacoesMedico(String o) { this.observacoesMedico = o; }
    public String getDataRegistro() { return dataRegistro; } public void setDataRegistro(String d) { this.dataRegistro = d; }
}
