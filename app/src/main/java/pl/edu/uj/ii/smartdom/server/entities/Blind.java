package pl.edu.uj.ii.smartdom.server.entities;

/**
 * Created by Mohru on 26.08.2017.
 */

public class Blind extends Module {

    private boolean shouldStart = false;

    public Blind(String serverId, boolean shouldStart) {
        super(serverId);
        this.shouldStart = shouldStart;
    }
}
