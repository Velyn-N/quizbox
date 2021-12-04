package de.nmadev.quizbox;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/rest/quiz")
@ApplicationScoped
public class QuizRest {
    
    private @Inject QuizWebSocket webSocket;

    @GET @Path("/usernameexists/{username}")
    public boolean nameExists(@PathParam("username") String username) {
        return webSocket.getParticipants()
            .stream()
            .filter(part -> part.getName().equalsIgnoreCase(username))
            .findAny()
            .isPresent();
    }
}
