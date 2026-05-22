package br.com.consultorio.resource;

import br.com.consultorio.model.bo.PacienteBO;
import br.com.consultorio.model.dto.ErroDTO;
import br.com.consultorio.model.dto.PacienteDTO;
import br.com.consultorio.security.UsuarioLogado;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteResource {

    @Inject PacienteBO pacienteBO;
    @Inject UsuarioLogado usuarioLogado;

    @GET
    @RolesAllowed({"ADMIN", "MEDICO", "RECEPCIONISTA"})
    public Response listar(@QueryParam("nome") String nome) {
        if (nome != null && !nome.isBlank())
            return Response.ok(pacienteBO.buscarPorNome(nome)).build();
        return Response.ok(pacienteBO.listarAtivos()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "MEDICO", "RECEPCIONISTA"})
    public Response buscar(@PathParam("id") Long id) {
        try {
            return Response.ok(pacienteBO.buscarPorId(id)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErroDTO(e.getMessage())).build();
        }
    }

    @POST
    @RolesAllowed({"ADMIN", "RECEPCIONISTA"})
    public Response criar(PacienteDTO dto) {
        try {
            dto.setId(null);
            return Response.status(Response.Status.CREATED)
                .entity(pacienteBO.salvar(dto, usuarioLogado.getUsuarioId(), usuarioLogado.getEmail())).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErroDTO(e.getMessage())).build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "RECEPCIONISTA"})
    public Response atualizar(@PathParam("id") Long id, PacienteDTO dto) {
        try {
            dto.setId(id);
            return Response.ok(pacienteBO.salvar(dto, usuarioLogado.getUsuarioId(), usuarioLogado.getEmail())).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErroDTO(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "RECEPCIONISTA"})
    public Response inativar(@PathParam("id") Long id) {
        try {
            pacienteBO.inativar(id, usuarioLogado.getUsuarioId(), usuarioLogado.getEmail());
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErroDTO(e.getMessage())).build();
        }
    }
}
