package de.nmadev.quizbox;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PointJson implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int points;

    /**
     * Constructs a {@link PointJson} from a given {@link Participant}. <br>
     * Returns <code>null</code> if {@link Participant#canHavePoints()} returns false.
     * @param participant the {@link Participant}
     * @return A nullable {@link PointJson}
     */
    public static PointJson from(Participant participant) {
        return (participant.canHavePoints()) ? new PointJson(participant.getName(), participant.getPoints()) : null;
    }
}
