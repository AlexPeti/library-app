package com.pcw.lendinglibrary.rest;

import com.pcw.lendinglibrary.dto.BookDTO;
import com.pcw.lendinglibrary.model.Book;
import com.pcw.lendinglibrary.service.IBookService;
import com.pcw.lendinglibrary.service.exceptions.BookNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/book")
public class BookResource {

    @Inject
    IBookService bookService;

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(BookDTO bookDTO) {
        try {
            Book book = bookService.insertBook(bookDTO);

            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/get/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByTitle(@PathParam("title") String title) {
        try {
            Book book = bookService.getBookByTitle(title);
            return Response.ok(title).build();
        } catch (BookNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
