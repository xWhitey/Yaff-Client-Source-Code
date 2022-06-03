package store.yaff.event.impl;

import net.minecraft.item.ItemStack;
import store.yaff.event.AbstractEvent;

public class ItemDamageEvent extends AbstractEvent {
    protected final ItemStack itemStack;
    protected final int amount;

    public ItemDamageEvent(ItemStack itemStack, int amount) {
        this.itemStack = itemStack;
        this.amount = amount;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getAmount() {
        return amount;
    }

}
