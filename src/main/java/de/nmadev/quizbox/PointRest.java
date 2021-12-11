package de.nmadev.quizbox;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/rest/points")
@ApplicationScoped
public class PointRest {
    
    @Inject QuizApplication quizApp;
    @Inject PointWebSocket pointSocket;

    @POST @Path("/{name}/{points}")
    public void setPoints(@PathParam("name") String name, @PathParam("points") int value) {
        Optional<Participant> opt = quizApp.getParticipantForName(name);
        if (opt.isPresent()) {
            if (opt.get().canHavePoints()) opt.get().setPoints(value);
            pointSocket.broadcastPoints();
        }
    }
}
