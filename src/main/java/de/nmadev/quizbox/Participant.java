package de.nmadev.quizbox;

import javax.websocket.Session;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Participant {
    
    private String name;
    private Role role;
    private Session session;

    public enum Role {
        player, gamemaster, watcher
    }
}
