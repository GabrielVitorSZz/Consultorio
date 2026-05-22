package br.com.consultorio.resource;

import br.com.consultorio.model.dao.LogAcaoDAO;
import br.com.consultorio.model.dto.LogAcaoDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/auditoria")
@Produces(MediaType.APPLICATION_JSON)
public class AuditoriaResource {

    @Inject LogAcaoDAO logDAO;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @GET
    @RolesAllowed("ADMIN")
    public Response listar(@QueryParam("limite") @DefaultValue("200") int limite) {
        List<LogAcaoDTO> lista = logDAO.listarUltimos(limite).stream().map(l -> {
            LogAcaoDTO dto = new LogAcaoDTO();
            dto.setId(l.getId());
            dto.setAcao(l.getAcao());
            dto.setUsuarioEmail(l.getUsuarioEmail() != null ? l.getUsuarioEmail() : "Sistema");
            dto.setDataHora(l.getDataHora() != null ? l.getDataHora().format(FMT) : "");
            dto.setDetalhes(l.getDetalhes());
            return dto;
        }).collect(Collectors.toList());
        return Response.ok(lista).build();
    }
}
