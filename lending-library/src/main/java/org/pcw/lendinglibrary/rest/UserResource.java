package org.pcw.lendinglibrary.rest;

import org.pcw.lendinglibrary.dto.UserDTO;
import org.pcw.lendinglibrary.model.Book;
import org.pcw.lendinglibrary.model.User;
import org.pcw.lendinglibrary.service.IBookService;
import org.pcw.lendinglibrary.service.IUserService;
import org.pcw.lendinglibrary.service.exceptions.BookNotFoundException;
import org.pcw.lendinglibrary.service.exceptions.UserNotFoundException;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserResource {

    @Inject
    IUserService userService;

    @Inject
    IBookService bookService;

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

    /**
     * A REST endpoint that enables us to borrow a book as a User.
     * The URL for using this endpoint is "http://localhost:8080/api/user/borrow/{userId}/{bookTitle}"
     * @param
     * @param bookTitle The title of the book.
     * @return Status 200 OK if the request is successful, NOT_FOUND if the User's id or the book's title cannot be found.
     */
//    @POST
//    @Path("/borrow/{userId}/{bookTitle}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response borrowBook(@PathParam("userId") Long userId, @PathParam("bookTitle") String bookTitle) {
//        try {
//            Book book = bookService.getBookByTitle(bookTitle);
//            Long bookId = book.getId();
//
//            userService.borrowBook(userId, bookId);
//            return Response.status(Response.Status.OK).build();
//
//        } catch (UserNotFoundException | BookNotFoundException e) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }

    @POST
    @Path("/borrow/{username}/{bookTitle}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response borrowBook(@PathParam("username") String username, @PathParam("bookTitle") String bookTitle) {
        try {
            User user = userService.getUserByUsername(username);
//            Long userId = user.getId();
//
           Book book = bookService.getBookByTitle(bookTitle);
//            Long bookId = book.getId();

            userService.borrowBook(username, bookTitle);
            return Response.status(Response.Status.OK).build();

        } catch (UserNotFoundException | BookNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * A REST endpoint that enables a User to return a previously borrowed book.
     * The URL for using this endpoint is "http://localhost:8080/api/user/return/{userId}/{bookTitle}"
     *
     * @param
     * @param bookTitle The title of the book we want to return.
     * @return Status 200 OK if the request is successful, NOT_FOUND if the user's id or the book's title cannot be found.
     */
//    @POST
//    @Path("/return/{userId}/{bookTitle}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response returnBook(@PathParam("userId") Long userId, @PathParam("bookTitle") String bookTitle) {
//        try {
//            Book book = bookService.getBookByTitle(bookTitle);
//            Long bookId = book.getId();
//
//            userService.returnBook(userId, bookId);
//            return Response.status(Response.Status.OK).build();
//
//        } catch (UserNotFoundException | BookNotFoundException e) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }

    @POST
    @Path("/return/{username}/{bookTitle}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response returnBook(@PathParam("username") String username, @PathParam("bookTitle") String bookTitle) {
        try {
            User user = userService.getUserByUsername(username);
            Long userId = user.getId();

            Book book = bookService.getBookByTitle(bookTitle);
            Long bookId = book.getId();

            userService.returnBook(userId, bookId);
            return Response.status(Response.Status.OK).build();

        } catch (UserNotFoundException | BookNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
