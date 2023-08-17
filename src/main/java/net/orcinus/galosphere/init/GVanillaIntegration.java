package net.orcinus.galosphere.init;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.orcinus.galosphere.crafting.MonstrometerDispenseItemBehavior;
import net.orcinus.galosphere.crafting.GlowFlareDispenseItemBehavior;
import net.orcinus.galosphere.crafting.LumiereComposterDispenseItemBehavior;
import net.orcinus.galosphere.crafting.PickaxeDispenseItemBehavior;
import net.orcinus.galosphere.crafting.WarpedAnchorDispenseItemBehavior;

public class GVanillaIntegration {

    public static void init() {
        GVanillaIntegration.registerCompostables();
        GVanillaIntegration.registerDispenserBehaviors();
        GVanillaIntegration.registerBrewables();
    }

    public static void registerBrewables() {
        FabricBrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, Ingredient.of(GItems.CURED_MEMBRANE), GPotions.ASTRAL);
        FabricBrewingRecipeRegistry.registerPotionRecipe(GPotions.ASTRAL, Ingredient.of(Items.REDSTONE), GPotions.LONG_ASTRAL);
    }

    public static void registerCompostables() {
        CompostingChanceRegistry instance = CompostingChanceRegistry.INSTANCE;
        Util.make(ImmutableMap.<Block, Float>builder(), map -> {
            map.put(GBlocks.LICHEN_MOSS, 0.85F);
            map.put(GBlocks.BOWL_LICHEN, 0.65F);
            map.put(GBlocks.LICHEN_ROOTS, 0.3F);
            map.put(GBlocks.LICHEN_SHELF, 0.45F);
            map.put(GBlocks.LICHEN_CORDYCEPS, 0.4F);
        }).build().forEach(instance::add);
    }

    public static void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(GBlocks.ALLURITE_BLOCK.asItem(), new MonstrometerDispenseItemBehavior());
        DispenserBlock.registerBehavior(GBlocks.ALLURITE_BLOCK.asItem(), new WarpedAnchorDispenseItemBehavior());
        DispenserBlock.registerBehavior(GItems.LUMIERE_SHARD, new LumiereComposterDispenseItemBehavior());

        DispenserBlock.registerBehavior(GItems.GLOW_FLARE, new GlowFlareDispenseItemBehavior());

        BuiltInRegistries.ITEM.getTagOrEmpty(ItemTags.CLUSTER_MAX_HARVESTABLES).iterator().forEachRemaining(holder -> DispenserBlock.registerBehavior(holder.value(), new PickaxeDispenseItemBehavior()));
    }

}
