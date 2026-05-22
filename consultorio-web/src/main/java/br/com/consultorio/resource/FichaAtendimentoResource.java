package br.com.consultorio.resource;

import br.com.consultorio.model.bo.FichaAtendimentoBO;
import br.com.consultorio.model.dto.ErroDTO;
import br.com.consultorio.model.dto.FichaHistoricoDTO;
import br.com.consultorio.security.UsuarioLogado;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/fichas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FichaAtendimentoResource {

    @Inject FichaAtendimentoBO fichaBO;
    @Inject UsuarioLogado usuarioLogado;

    @GET
    @Path("/paciente/{pacienteId}")
    @RolesAllowed({"ADMIN", "MEDICO", "RECEPCIONISTA"})
    public Response listarPorPaciente(@PathParam("pacienteId") Long pacienteId) {
        return Response.ok(fichaBO.listarPorPaciente(pacienteId)).build();
    }

    @GET
    @Path("/consulta/{consultaId}")
    @RolesAllowed({"ADMIN", "MEDICO", "RECEPCIONISTA"})
    public Response buscarPorConsulta(@PathParam("consultaId") Long consultaId) {
        try {
            return Response.ok(fichaBO.buscarPorConsulta(consultaId)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErroDTO(e.getMessage())).build();
        }
    }

    /** Apenas médicos podem registrar fichas de atendimento */
    @POST
    @RolesAllowed("MEDICO")
    public Response registrar(FichaHistoricoDTO dto) {
        try {
            dto.setId(null);
            FichaHistoricoDTO criada = fichaBO.salvar(dto, usuarioLogado.getUsuarioId(), usuarioLogado.getEmail());
            return Response.status(Response.Status.CREATED).entity(criada).build();
        } catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN).entity(new ErroDTO(e.getMessage())).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErroDTO(e.getMessage())).build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("MEDICO")
    public Response atualizar(@PathParam("id") Long id, FichaHistoricoDTO dto) {
        try {
            dto.setId(id);
            return Response.ok(fichaBO.salvar(dto, usuarioLogado.getUsuarioId(), usuarioLogado.getEmail())).build();
        } catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN).entity(new ErroDTO(e.getMessage())).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErroDTO(e.getMessage())).build();
        }
    }
}
