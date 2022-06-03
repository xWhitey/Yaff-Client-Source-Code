package store.yaff.feature.impl.combat;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.ListSetting;

import java.util.ArrayList;

public class AntiBot extends AbstractFeature {
    public static final BooleanSetting removeEntity = new BooleanSetting("Remove Entity", "None", true, () -> true);
    public static final ListSetting filterMode = new ListSetting("Mode", "None", "Matrix", () -> true, "Matrix", "Reflex");

    protected static final ArrayList<Entity> botList = new ArrayList<>();

    public AntiBot(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(removeEntity, filterMode);
    }

    public static boolean isBot(Entity entity) {
        return botList.contains(entity);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            for (Entity entity : mc.world.getLoadedEntityList().stream().filter(e -> e.getDistanceToEntity(mc.player) <= 8 && !botList.contains(e)).toList()) {
                switch (filterMode.getListValue().toLowerCase()) {
                    case "matrix" -> {
                        if (!(entity.ticksExisted >= 12) && entity instanceof EntityOtherPlayerMP && !(((EntityOtherPlayerMP) entity).getHealth() >= 20f) && !entity.isDead && ((EntityOtherPlayerMP) entity).hurtTime > 0 && ((EntityOtherPlayerMP) entity).getTotalArmorValue() == 0 && mc.player.connection.getPlayerInfo(entity.getUniqueID()).getResponseTime() != 0 && mc.player.connection.getPlayerInfo(entity.getUniqueID()).getResponseTime() <= 40f && ((EntityOtherPlayerMP) entity).getLastDamageSource() == null) {
                            if (removeEntity.getBooleanValue()) {
                                mc.world.removeEntity(entity);
                            } else {
                                botList.add(entity);
                            }
                        }
                    }
                    case "reflex" -> {
                        if (entity.isInvisible()) {
                            entity.isDead = true;
                        }
                        if (entity.getName().length() != 8f || mc.player.getDistanceToEntity(entity) > 5f || Math.round(entity.posY) != Math.round(mc.player.posY + 2f)) {
                            continue;
                        }
                        entity.isDead = true;
                    }
                }
            }
        }
    }

}