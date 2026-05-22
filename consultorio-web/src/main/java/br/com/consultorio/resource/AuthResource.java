package br.com.consultorio.resource;

import br.com.consultorio.model.dto.ErroDTO;
import br.com.consultorio.model.dto.LoginDTO;
import br.com.consultorio.model.dto.TokenDTO;
import br.com.consultorio.security.AuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject AuthService authService;

    @POST
    @Path("/login")
    public Response login(LoginDTO dto) {
        try {
            TokenDTO token = authService.autenticar(dto);
            return Response.ok(token).build();
        } catch (SecurityException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErroDTO(e.getMessage())).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErroDTO(e.getMessage())).build();
        }
    }
}
