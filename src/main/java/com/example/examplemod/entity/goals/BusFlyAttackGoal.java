package com.example.examplemod.entity.goals;

import com.example.examplemod.entity.Bus;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class BusFlyAttackGoal extends Goal {
    private final Bus mob;
    private LivingEntity target;
    private int updateCooldown = 0;

    public BusFlyAttackGoal(Bus mob) {
        this.mob = mob;

        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return mob.isFlyingPhase() && mob.getTarget() != null && mob.getTarget().isAlive();

    }
    @Override
    public void start(){
        target = mob.getTarget();
        updateCooldown = 0;
        mob.playSound(SoundEvents.BEE_LOOP_AGGRESSIVE, 1, 0.8f);
    }
    @Override
    public void tick(){
        this.target = mob.getTarget();
        if (target == null || !target.isAlive()) return;

        this.mob.getLookControl().setLookAt(target, 30, 30);

        double speed = 0.15;
        Vec3 movement = new Vec3(target.getX() - mob.getX(), target.getY() + target.getEyeHeight() + 1 - mob.getY(), target.getZ() - mob.getZ()).normalize().scale(speed);
        mob.setDeltaMovement(movement);

        updateCooldown--;
        if (updateCooldown < 0){
            double attackSqrt = 20;
            if (mob.distanceToSqr(target) <= attackSqrt){
                mob.doHurtTarget(target);
            }
            updateCooldown = 20;
        }
    }
    @Override
    public void stop(){
        target = null;
    }
    @Override
    public boolean requiresUpdateEveryTick(){
        return true;
    }
}
