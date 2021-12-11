package de.nmadev.quizbox;

import java.util.ConcurrentModificationException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import de.nmadev.quizbox.Participant.Role;

@ServerEndpoint("/buzzer/{role}/{name}")
@ApplicationScoped
public class BuzzerWebSocket {
    
    @Inject QuizApplication quizApp;
    
    @OnOpen
    public void onOpen(Session session, @PathParam("role") String role, @PathParam("name") String name) {
        try {
            Role roleNum = Role.valueOf(role);
            Participant part = quizApp.getParticipantForNameOrNew(name, roleNum);
            part.setBuzzerSession(session);
        } catch (IllegalArgumentException e) {
            // Shouldn't happen
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("role") String role, @PathParam("name") String name) {
        try {
            quizApp.removeIfPresent(name);
        } catch (ConcurrentModificationException e) {
            // When PointWebSocket already removed the Participant
        }
    }

    @OnMessage
    public void onMessage(String message, @PathParam("role") String role, @PathParam("name") String name) {
        if (message.equalsIgnoreCase("buzzer")) {
            String finishedMsg = name + " buzzered!";

            quizApp.getParticipants().forEach(part -> 
                part.getBuzzerSession()
                .getAsyncRemote()
                .sendText(finishedMsg)
            );
        }
    }
}
