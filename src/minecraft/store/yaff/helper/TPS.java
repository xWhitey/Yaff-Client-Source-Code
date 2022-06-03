package store.yaff.helper;

import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;
import store.yaff.event.impl.Packet;

import java.util.Arrays;

public class TPS {
    protected final float[] ticks = new float[20];
    protected long prevTime;
    protected int currentTick;

    public TPS() {
        this.prevTime = -1;
        Arrays.fill(this.ticks, 0);
    }

    public float getTickRate() {
        int tickCount = 0;
        float tickRate = 0;
        for (float tick : this.ticks) {
            if (tick > 0) {
                tickRate += tick;
                tickCount++;
            }
        }
        return MathHelper.clamp((tickRate / tickCount), 0, 20f);
    }

    public String getFormattedTickRate() {
        return String.valueOf(java.lang.Math.round(getTickRate() * 100f) / 100f);
    }

    public void onPacket(Packet.Receive packetEvent) {
        if (packetEvent.getPacket() instanceof SPacketTimeUpdate) {
            if (this.prevTime != -1) {
                this.ticks[this.currentTick % this.ticks.length] = MathHelper.clamp((20f / ((float) (System.currentTimeMillis() - this.prevTime) / 1000f)), 0, 20f);
                this.currentTick++;
            }
            this.prevTime = System.currentTimeMillis();
        }
    }

}
