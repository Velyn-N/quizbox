package de.nmadev.quizbox;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import de.nmadev.quizbox.Participant.Role;
import lombok.Getter;

@ServerEndpoint("/quiz/{role}/{name}")
@ApplicationScoped
public class QuizWebSocket {
    
    private @Getter List<Participant> participants = new ArrayList<>();
    
    @OnOpen
    public void onOpen(Session session, @PathParam("role") String role, @PathParam("name") String name) {
        try {
            Role roleNum = Role.valueOf(role);
            participants.add(new Participant(name, roleNum, session));
        } catch (IllegalArgumentException e) {
            // Shouldn't happen
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("role") String role, @PathParam("name") String name) {
        participants.removeIf(part -> part.getName().equalsIgnoreCase(name));
    }

    @OnMessage
    public void onMessage(String message, @PathParam("role") String role, @PathParam("name") String name) {
        if (message.equalsIgnoreCase("buzzer")) {
            String finishedMsg = "[Buzzer]" + name + " buzzered!";
            participants.forEach(part -> part.getSession().getAsyncRemote().sendText(finishedMsg));
        }
    }
}
