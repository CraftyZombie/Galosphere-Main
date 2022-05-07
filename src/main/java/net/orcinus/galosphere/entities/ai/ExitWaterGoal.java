package net.orcinus.galosphere.entities.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.orcinus.galosphere.entities.SparkleEntity;

public class ExitWaterGoal extends Goal {
    private final SparkleEntity sparkle;

    public ExitWaterGoal(SparkleEntity sparkleEntity) {
        this.sparkle = sparkleEntity;
    }

    @Override
    public boolean canUse() {
        return false;
    }
}