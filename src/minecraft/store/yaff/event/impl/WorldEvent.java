package store.yaff.event.impl;

import net.minecraft.client.multiplayer.WorldClient;
import store.yaff.event.AbstractEvent;

public class WorldEvent extends AbstractEvent {
    protected final WorldClient worldClient;

    public WorldEvent(WorldClient worldClient) {
        this.worldClient = worldClient;
    }

    public WorldClient getWorldClient() {
        return worldClient;
    }

}
