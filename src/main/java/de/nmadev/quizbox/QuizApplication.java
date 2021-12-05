package de.nmadev.quizbox;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import de.nmadev.quizbox.Participant.Role;
import lombok.Getter;

import java.util.*;

@Path("/rest/quiz")
@ApplicationScoped
public class QuizApplication {
    
    private @Getter List<Participant> participants = new ArrayList<>();

    @GET @Path("/usernameexists/{username}")
    public boolean nameExists(@PathParam("username") String username) {
        return getParticipantForName(username).isPresent();
    }

    public Participant getParticipantForNameOrNew(String name, Role role) {
        Optional<Participant> opt = getParticipantForName(name);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            Participant part = new Participant(name, role);
            participants.add(part);
            return part;
        }
    }

    public void removeIfPresent(String name) {
        participants.removeIf(part -> part.getName().equalsIgnoreCase(name));
    }

    public Optional<Participant> getParticipantForName(String name) {
        return participants.stream()
            .filter(part -> part.getName().equalsIgnoreCase(name))
            .findAny();
    }
}
