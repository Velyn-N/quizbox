package de.nmadev.quizbox;

import javax.websocket.Session;

import lombok.*;

/**
 * A Participant represents any User currently logged into the Application using a WebSocket {@link Session}. <br>
 * <br>
 * All {@link Role#player}s can have points, see {@link #canHavePoints()}.
 */
@Getter @Setter
@RequiredArgsConstructor
public class Participant {
    
    private final String name;
    private final Role role;
    private Session buzzerSession;
    private Session pointSession;
    private int points;

    /**
     * Only {@link Role#player}s can have points.
     * @return if this is a Player.
     */
    public boolean canHavePoints() {
        return role.equals(Role.player);
    }

    /**
     * Possible Roles a {@link Participant} can have: Player, GameMaster, Viewer
     */
    public enum Role {
        player, gamemaster, viewer
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Participant) {
            return ((Participant) obj).getName().equals(name);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Participant'" + name + "'";
    }
}
