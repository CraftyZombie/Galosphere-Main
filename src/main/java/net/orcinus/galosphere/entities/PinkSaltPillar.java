package net.orcinus.galosphere.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.orcinus.galosphere.init.GEntityTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class PinkSaltPillar extends Entity implements TraceableEntity {
    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUUID;
    private int warmupDelayTicks;
    private boolean sentSpikeEvent;
    private int lifeTicks = 22;

    public PinkSaltPillar(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public PinkSaltPillar(Level level, double d, double e, double f, float g, int i, LivingEntity livingEntity) {
        this(GEntityTypes.PINK_SALT_PILLAR, level);
        this.warmupDelayTicks = i;
        this.setOwner(livingEntity);
        this.setYRot(g * 57.295776f);
        this.setPos(d, e, f);
    }

    @Override
    protected void defineSynchedData() {
    }

    public void setOwner(@Nullable LivingEntity livingEntity) {
        this.owner = livingEntity;
        this.ownerUUID = livingEntity == null ? null : livingEntity.getUUID();
    }

    @Override
    @Nullable
    public LivingEntity getOwner() {
        Entity entity;
        if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel && (entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID)) instanceof LivingEntity) {
            this.owner = (LivingEntity)entity;
        }
        return this.owner;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        this.warmupDelayTicks = compoundTag.getInt("Warmup");
        if (compoundTag.hasUUID("Owner")) {
            this.ownerUUID = compoundTag.getUUID("Owner");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putInt("Warmup", this.warmupDelayTicks);
        if (this.ownerUUID != null) {
            compoundTag.putUUID("Owner", this.ownerUUID);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (--this.warmupDelayTicks < 0) {
                if (this.warmupDelayTicks == -8) {
                    List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2, 0.0, 0.2));
                    for (LivingEntity livingEntity : list) {
                        this.dealDamageTo(livingEntity);
                    }
                }
                if (!this.sentSpikeEvent) {
                    this.level().broadcastEntityEvent(this, (byte) 4);
                    this.sentSpikeEvent = true;
                }
                if (--this.lifeTicks < 0) {
                    this.discard();
                }
            }
        }
    }

    private void dealDamageTo(LivingEntity livingEntity) {
        LivingEntity livingEntity2 = this.getOwner();
        if (!livingEntity.isAlive() || livingEntity.isInvulnerable() || livingEntity == livingEntity2) {
            return;
        }
        if (livingEntity2 == null) {
            livingEntity.hurt(this.damageSources().magic(), 6.0f);
        } else {
            if (livingEntity2.isAlliedTo(livingEntity)) {
                return;
            }
            livingEntity.hurt(this.damageSources().indirectMagic(this, livingEntity2), 6.0f);
        }
    }

    @Override
    public void handleEntityEvent(byte b) {
        super.handleEntityEvent(b);
        if (b == 4) {
            if (!this.isSilent()) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.EVOKER_FANGS_ATTACK, this.getSoundSource(), 1.0f, this.random.nextFloat() * 0.2f + 0.85f, false);
            }
        }
    }
}