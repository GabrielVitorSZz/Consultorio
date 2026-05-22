package br.com.consultorio.resource;

import br.com.consultorio.model.bo.MedicoBO;
import br.com.consultorio.model.dto.ErroDTO;
import br.com.consultorio.model.dto.MedicoDTO;
import br.com.consultorio.security.UsuarioLogado;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/medicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicoResource {

    @Inject MedicoBO medicoBO;
    @Inject UsuarioLogado usuarioLogado;

    @GET
    @RolesAllowed({"ADMIN", "MEDICO", "RECEPCIONISTA"})
    public Response listar() {
        return Response.ok(medicoBO.listarTodos()).build();
    }

    @POST
    @RolesAllowed("ADMIN")
    public Response criar(MedicoDTO dto) {
        try {
            dto.setId(null);
            return Response.status(Response.Status.CREATED)
                .entity(medicoBO.salvar(dto, usuarioLogado.getEmail())).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErroDTO(e.getMessage())).build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response atualizar(@PathParam("id") Long id, MedicoDTO dto) {
        try {
            dto.setId(id);
            return Response.ok(medicoBO.salvar(dto, usuarioLogado.getEmail())).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErroDTO(e.getMessage())).build();
        }
    }
}
