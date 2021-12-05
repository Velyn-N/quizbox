package de.nmadev.quizbox;

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
    
    private @Inject QuizApplication quizApp;
    
    @OnOpen
    public void onOpen(Session session, @PathParam("role") String role, @PathParam("name") String name) {
        try {
            Role roleNum = Role.valueOf(role);
            Participant part = quizApp.getParticipantForNameOrNew(name, roleNum);
            part.setBuzzerSession(session);
            System.out.println(part);
        } catch (IllegalArgumentException e) {
            System.out.println("Booooom");
            e.printStackTrace();
            // Shouldn't happen
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("role") String role, @PathParam("name") String name) {
        quizApp.removeIfPresent(name);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("role") String role, @PathParam("name") String name) {
        if (message.equalsIgnoreCase("buzzer")) {
            String finishedMsg = "[Buzzer]" + name + " buzzered!";

System.out.println(quizApp.getParticipants());

            quizApp.getParticipants().forEach(part -> 
                part.getBuzzerSession()
                .getAsyncRemote()
                .sendText(finishedMsg)
            );
        }
    }
}