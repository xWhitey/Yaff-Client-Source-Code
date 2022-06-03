package store.yaff.helper;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;

import java.util.*;

public class Inventory {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public static void swapSlots(int firstSlot, int secondSlot) {
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, firstSlot, 0, ClickType.SWAP, mc.player);
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, secondSlot, 0, ClickType.SWAP, mc.player);
        //mc.playerController.windowClick(mc.player.inventoryContainer.windowId, firstSlot, 0, ClickType.SWAP, mc.player);
        mc.playerController.updateController();
    }

    public static void openInventory() {
        Objects.requireNonNull(mc.getConnection()).getNetworkManager().sendPacketWithoutEvent(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.OPEN_INVENTORY));
    }

    public static void closeInventory() {
        Objects.requireNonNull(mc.getConnection()).getNetworkManager().sendPacketWithoutEvent(new CPacketCloseWindow(mc.player.inventoryContainer.windowId));
    }

    public static boolean isInventoryEmpty(int slots) {
        for (byte b = 0; b < slots; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (itemStack.getItem() != Item.getItemById(0)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInventoryFull(int slots) {
        for (byte b = 0; b < slots; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (itemStack.getItem() == Item.getItemById(0)) {
                return false;
            }
        }
        return true;
    }

    public static int getEmptySlot(int slots) {
        for (byte b = 0; b < slots; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (itemStack.getItem() == Item.getItemById(0)) {
                return b;
            }
        }
        return 0;
    }

    public static boolean isChestEmpty(ContainerChest chestContainer) {
        for (byte b = 0; b < chestContainer.inventorySlots.size(); b++) {
            ItemStack containerStack = chestContainer.getLowerChestInventory().getStackInSlot(b);
            if (containerStack.getItem() != Item.getItemById(0)) {
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Byte> getItemStacksInChest(ContainerChest chestContainer, boolean shuffleArray) {
        ArrayList<Byte> stackSlots = new ArrayList<>();
        for (byte b = 0; b < chestContainer.inventorySlots.size(); b++) {
            ItemStack containerStack = chestContainer.getLowerChestInventory().getStackInSlot(b);
            if (!Inventory.isEmpty(containerStack)) {
                stackSlots.add(b);
            }
        }
        if (shuffleArray) {
            Collections.shuffle(stackSlots);
        }
        return stackSlots;
    }

    public static boolean isEmpty(ItemStack itemStack) {
        return itemStack.getItem() == Item.getItemById(0);
    }

    public static boolean isSword(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemSword;
    }

    public static boolean isTool(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemTool;
    }

    public static boolean isPotion(ItemStack itemStack, boolean isSplash) {
        return isSplash ? itemStack.getItem() instanceof ItemPotion && (itemStack.getItem() == Items.SPLASH_POTION) : itemStack.getItem() instanceof ItemPotion;
    }

    public static boolean isArmor(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemArmor;
    }

    public static boolean isSwordExist(boolean toolSave) {
        for (byte b = 0; b < 9; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (itemStack.getItem() instanceof ItemSword && !((itemStack.getMaxDamage() - itemStack.getItemDamage()) < 5 && toolSave)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isToolExist(boolean toolSave) {
        for (byte b = 0; b < 9; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (itemStack.getItem() instanceof ItemTool && !((itemStack.getMaxDamage() - itemStack.getItemDamage()) < 5 && toolSave)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isArmorExist(boolean armorSave) {
        for (byte b = 0; b < 9; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (itemStack.getItem() instanceof ItemArmor && !((itemStack.getMaxDamage() - itemStack.getItemDamage()) < 5 && armorSave)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPotionExist(boolean isSplash) {
        for (byte b = 0; b < 9; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (isSplash ? itemStack.getItem() instanceof ItemPotion && (itemStack.getItem() == Items.SPLASH_POTION) : itemStack.getItem() instanceof ItemPotion) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBlockExist() {
        for (byte b = 0; b < 9; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (itemStack.getItem() instanceof ItemBlock itemBlock) {
                if (World.isFullBlock(itemBlock.getBlock())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isPearlExist() {
        for (byte b = 0; b < 9; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (itemStack.getItem() instanceof ItemEnderPearl) {
                return true;
            }
        }
        return false;
    }

    public static int getSword(boolean toolSave) {
        for (byte b = 0; b < 9; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (itemStack.getItem() instanceof ItemSword && !((itemStack.getMaxDamage() - itemStack.getItemDamage()) < 5 && toolSave)) {
                return b;
            }
        }
        return mc.player.inventory.currentItem;
    }

    public static int getTool(boolean toolSave) {
        for (byte b = 0; b < 9; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (itemStack.getItem() instanceof ItemTool && !((itemStack.getMaxDamage() - itemStack.getItemDamage()) < 5 && toolSave)) {
                return b;
            }
        }
        return mc.player.inventory.currentItem;
    }

    public static int getBestSword(boolean toolSave) {
        int bestSword = mc.player.inventory.currentItem;
        float bestDamage = 1;
        for (byte b = 0; b < 9; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (itemStack.getItem() instanceof ItemSword itemSword && !((itemStack.getMaxDamage() - itemStack.getItemDamage()) < 5 && toolSave)) {
                float swordDamage = itemSword.getDamageVsEntity() + 4;
                swordDamage += (0.5 * EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(16)), itemStack)) + 0.5;
                swordDamage += (EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(20)), itemStack) * 4) - 1;
                if (swordDamage > bestDamage) {
                    bestDamage = swordDamage;
                    bestSword = b;
                }
            }
        }
        return bestSword;
    }

    public static int getBestTool(Block block, boolean toolSave, boolean canHarvest) {
        int bestTool = mc.player.inventory.currentItem;
        float bestPower = 1;
        for (byte b = 0; b < 9; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (itemStack.getItem() instanceof ItemTool && !((itemStack.getMaxDamage() - itemStack.getItemDamage()) < 5 && toolSave)) {
                float toolPower = itemStack.getStrVsBlock(block.getDefaultState()) > 1 ? (float) (itemStack.getStrVsBlock(block.getDefaultState()) + (EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(32)), itemStack) * 0.1)) : itemStack.getStrVsBlock(block.getDefaultState());
                if (toolPower > bestPower && !(itemStack.getItem() instanceof ItemPickaxe && !itemStack.canHarvestBlock(block.getDefaultState()) && canHarvest)) {
                    bestPower = toolPower;
                    bestTool = b;
                }
            }
        }
        return bestTool;
    }

    public static ArrayList<Integer> getArmorSlots(boolean armorSave) {
        ArrayList<Integer> armorStacks = new ArrayList<>();
        for (byte b = 0; b < 9; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (itemStack.getItem() instanceof ItemArmor && !((itemStack.getMaxDamage() - itemStack.getItemDamage()) < 5 && armorSave)) {
                armorStacks.add((int) b);
            }
        }
        return armorStacks;
    }

    public static ArrayList<Integer> getRArmorSlots(boolean armorSave) {
        ArrayList<Integer> armorStacks = new ArrayList<>(4);
        ArrayList<ItemArmor> armorItems = new ArrayList<>();
        HashMap<ItemArmor, ItemStack> armorMap = new HashMap<>();
        HashMap<ItemStack, Integer> stacksMap = new HashMap<>();
        for (byte b = 0; b < 36; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (itemStack.getItem() instanceof ItemArmor itemArmor && !((itemStack.getMaxDamage() - itemStack.getItemDamage()) < 5 && armorSave)) {
                armorItems.add(itemArmor);
                armorMap.put(itemArmor, itemStack);
                stacksMap.put(itemStack, (int) b);
            }
        }
        int[] bestValues = new int[4];
        ItemStack[] bestArmor = new ItemStack[4];
        armorItems.sort(Comparator.comparing(i -> i.armorType.getIndex()));
        for (ItemArmor i : armorItems) {
            int itemType = i.armorType.getIndex();
            int totalArmorValue = getTotalArmorValue(i, armorMap.get(i));
            if (totalArmorValue > bestValues[itemType]) {
                bestValues[itemType] = totalArmorValue;
                bestArmor[itemType] = armorMap.get(i);
            }
        }
        for (ItemStack i : bestArmor) {
            armorStacks.add(stacksMap.get(i));
        }
        return armorStacks;
    }

    public static int getTotalArmorValue(ItemArmor itemArmor, ItemStack itemStack) {
        int armorReduce = itemArmor.damageReduceAmount;
        int armorToughness = (int) itemArmor.toughness;
        int protectionValue = EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantments.PROTECTION), itemStack);
        int modifierDamage = Objects.requireNonNull(Enchantments.PROTECTION).calcModifierDamage(protectionValue, DamageSource.causePlayerDamage(mc.player));
        return armorReduce * 5 + modifierDamage * 3 + armorToughness;
    }

    public static ArrayList<Integer> getPotionSlots(boolean mustBeHeal) {
        ArrayList<Integer> potionStacks = new ArrayList<>();
        for (byte b = 0; b < 9; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (itemStack.getItem() instanceof ItemPotion && (itemStack.getItem() == Items.SPLASH_POTION) && (mustBeHeal ? isHealPotion(itemStack) : !isBadPotion(itemStack))) {
                potionStacks.add((int) b);
            }
        }
        return potionStacks;
    }

    public static int getBlock() {
        for (byte b = 0; b < 9; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (itemStack.getItem() instanceof ItemBlock itemBlock) {
                if (World.isFullBlock(itemBlock.getBlock())) {
                    return b;
                }
            }
        }
        return mc.player.inventory.currentItem;
    }

    public static int getPearl() {
        for (byte b = 0; b < 9; b++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(b);
            if (itemStack.getItem() instanceof ItemEnderPearl) {
                return b;
            }
        }
        return mc.player.inventory.currentItem;
    }

    public static boolean isBadPotion(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemPotion) {
            for (PotionEffect effect : PotionUtils.getEffectsFromStack(itemStack)) {
                if (effect.getPotion() == Potion.getPotionById(2) || effect.getPotion() == Potion.getPotionById(6) || effect.getPotion() == Potion.getPotionById(7) || effect.getPotion() == Potion.getPotionById(9) || effect.getPotion() == Potion.getPotionById(15) || effect.getPotion() == Potion.getPotionById(17) || effect.getPotion() == Potion.getPotionById(18) || effect.getPotion() == Potion.getPotionById(19) || effect.getPotion() == Potion.getPotionById(24) || effect.getPotion() == Potion.getPotionById(25) || effect.getPotion() == Potion.getPotionById(27)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isHealPotion(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemPotion) {
            for (PotionEffect effect : PotionUtils.getEffectsFromStack(itemStack)) {
                if (effect.getPotion() == Potion.getPotionById(6)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ItemStack getCurrentStack() {
        return mc.player.inventory.getStackInSlot(mc.player.inventory.currentItem);
    }

    public static boolean hasColoredItems(Entity entityIn) {
        for (ItemStack i : entityIn.getHeldEquipment()) {
            ItemArmor armorStack = (ItemArmor) i.getItem();
            if (armorStack.hasColor(i)) {
                return true;
            }
        }
        return false;
    }

}
