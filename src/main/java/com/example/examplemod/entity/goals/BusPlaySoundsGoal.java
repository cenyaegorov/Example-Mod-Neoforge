package com.example.examplemod.entity.goals;

import com.example.examplemod.entity.bus.Bus;
import com.example.examplemod.entity.bus.BusState;
import com.example.examplemod.sounds.ModSoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

public class BusPlaySoundsGoal extends Goal {
    private final Bus mob;
    private int idleSoundCooldown = 0;
    private boolean wasAngry = false;

    public BusPlaySoundsGoal(Bus mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        return true;
    }
    @Override
    public void tick(){
        LivingEntity target = mob.getTarget();

        if (target != null){
            if (!wasAngry){
                wasAngry = true;
                this.mob.setState(BusState.ANGRY);
                playAngrySound();
            }

            idleSoundCooldown = 0;
        }
        else {
            if (wasAngry) {
                wasAngry = false;
                this.mob.setState(BusState.IDLE);
            }

            if (--idleSoundCooldown <= 0){
                playIdleSound();
                idleSoundCooldown = 100 + mob.getRandom().nextInt(100);
            }
        }
    }

    private void playIdleSound() {
        if (!mob.level().isClientSide){
            Level level = mob.level();
            level.playSound(null,
                    mob.getX(),
                    mob.getY(),
                    mob.getZ(),
                    ModSoundEvents.BUS_IDLE.value(),
                    mob.getSoundSource(),
                    1.0f,
                    0.9f);
        }
    }

    private void playAngrySound() {
        if (!mob.level().isClientSide){
            Level level = mob.level();
            level.playSound(null,
                    mob.getX(),
                    mob.getY(),
                    mob.getZ(),
                    ModSoundEvents.BUS_ANGRY.value(),
                    mob.getSoundSource(),
                    0.8f,
                    0.8f);
        }
    }
}
