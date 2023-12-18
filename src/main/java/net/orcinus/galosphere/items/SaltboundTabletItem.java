package net.orcinus.galosphere.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.orcinus.galosphere.entities.PinkSaltPillar;
import net.orcinus.galosphere.init.GEnchantments;

public class SaltboundTabletItem extends Item {

    public SaltboundTabletItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBarColor(ItemStack itemStack) {
        return Mth.color(0.737f, 1, 0.737f);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        HitResult result = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
        if (result.getType() == HitResult.Type.BLOCK) {
            Vec3 location = result.getLocation();
            double d = Math.min(location.y(), player.getY());
            double e = Math.max(location.y(), player.getY()) + 1.0;
            float f = (float)Mth.atan2(location.z() - player.getZ(), location.x() - player.getX());
            ItemStack itemInHand = player.getItemInHand(interactionHand);
            if (player.isShiftKeyDown()) {
                for (int round = 2; round < 5; round++) {
                    for (float i = 0.0F; i < Mth.PI * 2; i += Mth.PI / 4) {
                        this.createPillar(player, player.getX() + (Mth.sin(i)) * round, player.getZ() + (Mth.cos(i)) * round, d, e, f, (int) i + round, itemInHand);
                    }
                }
            } else {
                for (int i = 0; i < 16; ++i) {
                    double h = 1.25 * (double)(i + 1);
                    this.createPillar(player, location.x() + (double)Mth.cos(f) * h, location.z() + (double)Mth.sin(f) * h, d, e, f, i, itemInHand);
                }
            }
            if (!player.getAbilities().instabuild) {
                player.getCooldowns().addCooldown(this, 10);
            }
            itemInHand.hurtAndBreak(1, player, entity -> entity.broadcastBreakEvent(interactionHand));
            return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide);
        }
        return super.use(level, player, interactionHand);
    }

    private void createPillar(Player player, double d, double e, double f, double g, float h, int i, ItemStack itemInHand) {
        BlockPos blockPos = BlockPos.containing(d, g, e);
        Level level = player.level();
        boolean bl = false;
        double j = 0.0;
        do {
            VoxelShape voxelShape;
            BlockPos blockPos2 = blockPos.below();
            BlockState blockState = level.getBlockState(blockPos2);
            if (!blockState.isFaceSturdy(level, blockPos2, Direction.UP)) continue;
            if (!level.isEmptyBlock(blockPos) && !(voxelShape = level.getBlockState(blockPos).getCollisionShape(level, blockPos)).isEmpty()) {
                j = voxelShape.max(Direction.Axis.Y);
            }
            bl = true;
            break;
        } while ((blockPos = blockPos.below()).getY() >= Mth.floor(f) - 1);
        if (bl) {
            int ticks = 22 * (EnchantmentHelper.getItemEnchantmentLevel(GEnchantments.SUSTAIN, itemInHand) + 1);
            level.addFreshEntity(new PinkSaltPillar(level, d, (double)blockPos.getY() + j, e, h, i, ticks, player));
        }
    }
}
