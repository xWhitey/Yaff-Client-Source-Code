package store.yaff.feature.impl.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.FeatureDisableEvent;
import store.yaff.event.impl.FeatureStateEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.event.impl.WorldEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Chat;
import store.yaff.setting.impl.BooleanSetting;

import java.util.ArrayList;

public class Murder extends AbstractFeature {
    public static final BooleanSetting renderNameTags = new BooleanSetting("renderNameTags", "None", true, () -> true);

    protected final ArrayList<Entity> murderList = new ArrayList<>();
    protected final ArrayList<Entity> detectiveList = new ArrayList<>();

    public Murder(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(renderNameTags);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            for (Entity entity : mc.world.getLoadedEntityList()) {
                if (entity instanceof EntityPlayer && entity != mc.player) {
                    if ((((EntityPlayer) entity).getHeldItemMainhand().getItem() instanceof ItemSword) && !murderList.contains(entity)) {
                        Chat.addChatMessage(entity.getName() + " is Murder");
                        murderList.add(entity);
                    }
                    if ((((EntityPlayer) entity).getHeldItemMainhand().getItem() instanceof ItemBow) && !detectiveList.contains(entity)) {
                        Chat.addChatMessage(entity.getName() + " is Detective");
                        detectiveList.add(entity);
                    }
                }
            }
        }
        if (event instanceof WorldEvent || (event instanceof FeatureStateEvent stateEvent && stateEvent.getFeature().equals(this)) || (event instanceof FeatureDisableEvent disableEvent && disableEvent.getFeature().equals(this))) {
            murderList.clear();
            detectiveList.clear();
        }
    }

}
