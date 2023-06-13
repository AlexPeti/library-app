package org.pcw.lendinglibrary.rest;

import org.modelmapper.ModelMapper;
import org.pcw.lendinglibrary.dto.UserDTO;
import org.pcw.lendinglibrary.model.User;
import org.pcw.lendinglibrary.service.IUserService;
import org.pcw.lendinglibrary.service.exceptions.BookNotFoundException;
import org.pcw.lendinglibrary.service.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/user")
public class UserResource {

    @Inject
    IUserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    private final ModelMapper modelMapper = new ModelMapper();

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserDTO userDTO) throws UserNotFoundException {

        User user = userService.getUserByUsername(userDTO.getUsername());

        if (user != null && user.getPassword().equals(userDTO.getPassword())) {

            return Response.ok().build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(UserDTO userDTO) {
        try {
            User user = userService.register(userDTO);

            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/borrow")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response borrowBook(Map<String, String> payload) {
        try {
            String username = payload.get("username");
            String bookTitle = payload.get("bookTitle");

            logger.info("Received request - Username: {}, Book Title: {}", username, bookTitle);

            userService.borrowBook(username, bookTitle);
            return Response.status(Response.Status.OK).build();

        } catch (UserNotFoundException | BookNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/return")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response returnBook(Map<String, String> payload) {
        try {
            String username = payload.get("username");
            String bookTitle = payload.get("bookTitle");

            userService.returnBook(username, bookTitle);
            return Response.status(Response.Status.OK).build();

        } catch (UserNotFoundException | BookNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
