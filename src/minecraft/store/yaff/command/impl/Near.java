package store.yaff.command.impl;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import store.yaff.command.AbstractCommand;
import store.yaff.helper.Chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Near extends AbstractCommand {
    public Near(String name, String description, String syntax, String... aliases) {
        super(name, description, syntax, aliases);
    }

    @Override
    public void onCommand(String... args) {
        try {
            ArrayList<EntityLivingBase> entityList = new ArrayList<>();
            mc.world.getLoadedEntityList().stream().filter(e -> e.getDistanceToEntity(mc.player) < 120 && e instanceof EntityOtherPlayerMP && e.isEntityAlive()).forEach(e -> entityList.add((EntityLivingBase) e));
            if (entityList.isEmpty()) {
                Chat.addChatMessage("Nobody near with you");
                return;
            }
            if (args.length > 1) {
                switch (args[1].toLowerCase()) {
                    case "health" -> entityList.sort(Comparator.comparing(EntityLivingBase::getHealth));
                    case "armor" -> entityList.sort(Comparator.comparing(EntityLivingBase::getTotalArmorValue));
                    case "distance" -> entityList.sort(Comparator.comparing(mc.player::getDistanceToEntity));
                    case "hurttime" -> entityList.sort(Comparator.comparing(EntityLivingBase::getHurtTime).reversed());
                    default -> Chat.addChatMessage("Invalid sorting type");
                }
            }
            if (args.length > 2) {
                if (Boolean.parseBoolean(args[2])) {
                    Collections.reverse(entityList);
                }
            }
            entityList.forEach(e -> Chat.addChatMessage("Detected " + e.getName() + " with distance " + Math.round(e.getDistanceToEntity(mc.player)) + " blocks. X: " + e.getPosition().getX() + " Y: " + e.getPosition().getY() + " Z: " + e.getPosition().getZ()));
        } catch (Exception e) {
            Chat.addChatMessage(getSyntax());
        }
    }

}
