package br.com.consultorio.resource;

import br.com.consultorio.model.bo.ConsultaBO;
import br.com.consultorio.model.dto.AgendamentoConsultaDTO;
import br.com.consultorio.model.dto.ErroDTO;
import br.com.consultorio.security.UsuarioLogado;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/consultas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultaResource {

    @Inject ConsultaBO consultaBO;
    @Inject UsuarioLogado usuarioLogado;

    @GET
    @RolesAllowed({"ADMIN", "MEDICO", "RECEPCIONISTA"})
    public Response listar(@QueryParam("pacienteId") Long pacienteId,
                           @QueryParam("medicoId") Long medicoId) {
        if (pacienteId != null) return Response.ok(consultaBO.listarPorPaciente(pacienteId)).build();
        if (medicoId != null) return Response.ok(consultaBO.listarPorMedico(medicoId)).build();
        return Response.ok(consultaBO.listarTodas()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "MEDICO", "RECEPCIONISTA"})
    public Response buscar(@PathParam("id") Long id) {
        try {
            return Response.ok(consultaBO.buscarPorId(id)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErroDTO(e.getMessage())).build();
        }
    }

    @POST
    @RolesAllowed({"ADMIN", "RECEPCIONISTA", "MEDICO"})
    public Response agendar(AgendamentoConsultaDTO dto) {
        try {
            dto.setId(null);
            AgendamentoConsultaDTO criada = consultaBO.agendar(dto, usuarioLogado.getUsuarioId(), usuarioLogado.getEmail());
            return Response.status(Response.Status.CREATED).entity(criada).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErroDTO(e.getMessage())).build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "RECEPCIONISTA", "MEDICO"})
    public Response reagendar(@PathParam("id") Long id, AgendamentoConsultaDTO dto) {
        try {
            dto.setId(id);
            return Response.ok(consultaBO.agendar(dto, usuarioLogado.getUsuarioId(), usuarioLogado.getEmail())).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErroDTO(e.getMessage())).build();
        }
    }

    @PUT
    @Path("/{id}/status")
    @RolesAllowed({"ADMIN", "RECEPCIONISTA", "MEDICO"})
    public Response atualizarStatus(@PathParam("id") Long id, @QueryParam("status") String status) {
        try {
            return Response.ok(consultaBO.atualizarStatus(id, status, usuarioLogado.getUsuarioId(), usuarioLogado.getEmail())).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErroDTO(e.getMessage())).build();
        }
    }
}
