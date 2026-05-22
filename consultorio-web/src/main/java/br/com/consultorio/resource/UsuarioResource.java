package br.com.consultorio.resource;

import br.com.consultorio.model.bo.UsuarioBO;
import br.com.consultorio.model.dao.PerfilUsuarioDAO;
import br.com.consultorio.model.dto.ErroDTO;
import br.com.consultorio.model.dto.PerfilDTO;
import br.com.consultorio.model.dto.UsuarioDTO;
import br.com.consultorio.security.UsuarioLogado;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject UsuarioBO usuarioBO;
    @Inject PerfilUsuarioDAO perfilDAO;
    @Inject UsuarioLogado usuarioLogado;

    @GET
    @RolesAllowed("ADMIN")
    public Response listar() {
        return Response.ok(usuarioBO.listarTodos()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            return Response.ok(usuarioBO.buscarPorId(id)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErroDTO(e.getMessage())).build();
        }
    }

    @POST
    @RolesAllowed("ADMIN")
    public Response criar(UsuarioDTO dto) {
        try {
            dto.setId(null);
            UsuarioDTO criado = usuarioBO.salvar(dto, usuarioLogado.getEmail());
            return Response.status(Response.Status.CREATED).entity(criado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErroDTO(e.getMessage())).build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response atualizar(@PathParam("id") Long id, UsuarioDTO dto) {
        try {
            dto.setId(id);
            return Response.ok(usuarioBO.salvar(dto, usuarioLogado.getEmail())).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErroDTO(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response inativar(@PathParam("id") Long id) {
        try {
            usuarioBO.inativar(id, usuarioLogado.getEmail());
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErroDTO(e.getMessage())).build();
        }
    }

    @GET
    @Path("/perfis")
    @RolesAllowed({"ADMIN", "MEDICO", "RECEPCIONISTA"})
    public Response listarPerfis() {
        List<PerfilDTO> perfis = perfilDAO.listarTodos().stream().map(p -> {
            PerfilDTO dto = new PerfilDTO();
            dto.setId(p.getId()); dto.setNome(p.getNome()); dto.setDescricao(p.getDescricao());
            return dto;
        }).collect(Collectors.toList());
        return Response.ok(perfis).build();
    }
}
