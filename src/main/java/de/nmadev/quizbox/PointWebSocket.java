package de.nmadev.quizbox;

import java.util.ConcurrentModificationException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

import de.nmadev.quizbox.Participant.Role;

@ServerEndpoint("/points/{role}/{name}")
@ApplicationScoped
public class PointWebSocket {
    
    private static final Gson GSON = new Gson();
    @Inject QuizApplication quizApp;
    
    @OnOpen
    public void onOpen(Session session, @PathParam("role") String role, @PathParam("name") String name) {
        try {
            Role roleNum = Role.valueOf(role);
            Participant part = quizApp.getParticipantForNameOrNew(name, roleNum);
            part.setPointSession(session);

            broadcastPoints();
        } catch (IllegalArgumentException e) {
            // Shouldn't happen
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("role") String role, @PathParam("name") String name) {
        try {
            quizApp.removeIfPresent(name);
            broadcastPoints();
        } catch (ConcurrentModificationException e) {
            // When BuzzerWebSocket already removed the Participant
        }
    }

    public void broadcastPoints() {
        List<PointJson> data = getPointJsonList();
        String jsonData = GSON.toJson(data);

        quizApp.getParticipants().forEach(part -> part.getPointSession().getAsyncRemote().sendText(jsonData));
    }

    private List<PointJson> getPointJsonList() {
        return quizApp.getParticipants()
            .stream()
            .filter(Participant::canHavePoints)
            .map(PointJson::from)
            .toList();
    }
}
