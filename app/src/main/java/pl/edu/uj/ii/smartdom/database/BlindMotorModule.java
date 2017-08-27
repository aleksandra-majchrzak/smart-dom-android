package pl.edu.uj.ii.smartdom.database;

/**
 * Created by Mohru on 27.08.2017.
 */

public class BlindMotorModule extends Module {
    private boolean shouldStart = false;

    public BlindMotorModule(String serverId, String name, String roomServerId) {
        super(serverId, name, roomServerId);
    }

    public BlindMotorModule(String serverId, String name, String roomServerId, boolean shouldStart) {
        super(serverId, name, roomServerId);

        this.shouldStart = shouldStart;
    }

    public boolean shouldStart() {
        return shouldStart;
    }

    public void setShouldStart(boolean shouldStart) {
        this.shouldStart = shouldStart;
    }
}
