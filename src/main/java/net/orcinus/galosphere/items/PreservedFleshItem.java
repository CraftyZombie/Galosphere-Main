package net.orcinus.galosphere.items;

import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;

public class PreservedFleshItem extends Item {

    public PreservedFleshItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (this.isEdible()) {
            return this.eat(livingEntity, level, itemStack);
        }
        return itemStack;
    }

    private ItemStack eat(LivingEntity livingEntity, Level level, ItemStack itemStack) {
        if (!(livingEntity instanceof Player player)) {
            return ItemStack.EMPTY;
        }
        player.getFoodData().eat(itemStack.getItem(), itemStack);
        player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5f, level.random.nextFloat() * 0.1f + 0.9f);
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, itemStack);
        }
        if (itemStack.isEdible()) {
            level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity.getEatingSound(itemStack), SoundSource.NEUTRAL, 1.0f, 1.0f + (level.random.nextFloat() - level.random.nextFloat()) * 0.4f);
            addEatEffect(itemStack, level, livingEntity);
            livingEntity.gameEvent(GameEvent.EAT);
        }
        return itemStack;
    }

    private void addEatEffect(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        Item item = itemStack.getItem();
        if (item.isEdible()) {
            List<Pair<MobEffectInstance, Float>> list = item.getFoodProperties().getEffects();
            for (Pair<MobEffectInstance, Float> pair : list) {
                if (level.isClientSide || pair.getFirst() == null || !(level.random.nextFloat() < pair.getSecond().floatValue())) continue;
                livingEntity.addEffect(new MobEffectInstance(pair.getFirst()));
            }
        }
    }
}