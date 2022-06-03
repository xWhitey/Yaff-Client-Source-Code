package store.yaff.command.impl;

import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.math.NumberUtils;
import store.yaff.command.AbstractCommand;
import store.yaff.helper.Chat;

public class Clip extends AbstractCommand {
    public Clip(String name, String description, String syntax, String... aliases) {
        super(name, description, syntax, aliases);
    }

    @Override
    public void onCommand(String... args) {
        try {
            if (args.length >= 2) {
                if (args[0].equalsIgnoreCase("vclip")) {
                    try {
                        if (NumberUtils.isNumber(args[1])) {
                            for (int i = 0; i < 15; i++) {
                                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                            }
                            for (int i = 0; i < 15; i++) {
                                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + Double.parseDouble(args[1]), mc.player.posZ, false));
                            }
                        }
                        if (args[1].equals("up")) {
                            BlockPos feet = new BlockPos(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);
                            int surfaceLevel = this.mc.world.getSeaLevel();
                            int worldHeight = this.mc.world.getActualHeight();
                            if (feet.getY() > surfaceLevel && this.mc.world.getBlockState(feet.up()).getBlock() instanceof BlockAir) {
                                return;
                            }
                            int startingYPos = Math.max(feet.getY(), surfaceLevel);
                            for (int currentIteratedY = startingYPos; currentIteratedY < worldHeight; ++currentIteratedY) {
                                BlockPos newPos = new BlockPos(feet.getX(), currentIteratedY, feet.getZ());
                                if (!(this.mc.world.getBlockState(newPos).getBlock() instanceof BlockAir) && newPos.getY() > feet.getY()) {
                                    for (int i = 0; i < 10; i++) {
                                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                                    }
                                    for (int i = 0; i < 10; i++) {
                                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, newPos.getY() + 3, mc.player.posZ, false));
                                    }
                                }
                            }
                        }
                        if (args[1].equals("down")) {
                            for (int i = 0; i < 20; i++) {
                                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                            }
                            for (int i = 0; i < 20; i++) {
                                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, -2, mc.player.posZ, false));
                            }
                        }
                    } catch (Exception var11) {
                    }
                }
                if (args[0].equalsIgnoreCase("hclip")) {
                    double x = this.mc.player.posX;
                    double y = this.mc.player.posY;
                    double z = this.mc.player.posZ;
                    float yaw = this.mc.player.rotationYaw * 0.017453292F;
                    try {
                        for (int i = 0; i < 20; i++) {
                            mc.player.connection.sendPacket(new CPacketPlayer.Position(x - Math.sin(yaw) * Double.parseDouble(args[1]), y, z + Math.cos(yaw) * Double.parseDouble(args[1]), false));
                        }
                        for (int i = 0; i < 20; i++) {
                            mc.player.connection.sendPacket(new CPacketPlayer.Position(x - Math.sin(yaw) * Double.parseDouble(args[1]), y, z + Math.cos(yaw) * Double.parseDouble(args[1]), false));
                        }
                        //this.mc.player.setPositionAndUpdate(x - Math.sin((double)yaw) * Double.parseDouble(args[1]), y, z + Math.cos((double)yaw) * Double.parseDouble(args[1]));
                    } catch (Exception var10) {
                    }
                }
            } else {
                Chat.addChatMessage(this.getSyntax());
            }
        } catch (Exception e) {
            Chat.addChatMessage(getSyntax());
        }
    }

}
