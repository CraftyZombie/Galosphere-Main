package net.orcinus.cavesandtrenches.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.blocks.blockentities.AuraListenerBlockEntity;

@Mod.EventBusSubscriber(modid = CavesAndTrenches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, CavesAndTrenches.MODID);

    public static final RegistryObject<BlockEntityType<AuraListenerBlockEntity>> AURA_LISTENER = BLOCK_ENTITIES.register("aura_listener", () -> BlockEntityType.Builder.of(AuraListenerBlockEntity::new, CTBlocks.AURA_LISTENER.get()).build(null));

}