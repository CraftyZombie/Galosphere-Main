package net.orcinus.cavesandtrenches.init;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.world.gen.features.CrystalSpikeFeature;
import net.orcinus.cavesandtrenches.world.gen.features.MysteriaTreeFeature;
import net.orcinus.cavesandtrenches.world.gen.features.config.CrystalSpikeConfig;

@Mod.EventBusSubscriber(modid = CavesAndTrenches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, CavesAndTrenches.MODID);

    public static final RegistryObject<Feature<CrystalSpikeConfig>> CRYSTAL_SPIKE = FEATURES.register("crystal_spike", () -> new CrystalSpikeFeature(CrystalSpikeConfig.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> MYSTERIA_TREE = FEATURES.register("mysteria_tree", () -> new MysteriaTreeFeature(NoneFeatureConfiguration.CODEC));

}
