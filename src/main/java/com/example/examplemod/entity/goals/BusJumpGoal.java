package com.example.examplemod.entity.goals;

import com.example.examplemod.entity.bus.Bus;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class BusJumpGoal extends Goal {
    private final Bus mob;
    private LivingEntity target;
    private int attackTimer = 0;
    private AttackState state = AttackState.IDLE;

    private enum AttackState {
        IDLE,
        CHARGING_UP,
        SPINNING_UP,
        SPINNING_DOWN,
        EXPLODING,
        END
    }

    public BusJumpGoal(Bus mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return mob.getTarget() != null && this.mob.distanceToSqr(mob.getTarget()) < 100.0;
    }
    @Override
    public void start(){
        this.target = mob.getTarget();
        this.state = AttackState.CHARGING_UP;
        this.attackTimer = 20 + this.mob.getRandom().nextInt(200);
        //this.mob.getNavigation().stop();
    }
    @Override
    public void tick(){
        this.attackTimer--;
        this.target = mob.getTarget();

        switch (this.state){
            case CHARGING_UP:
                if (attackTimer <= 0){
                    this.state = AttackState.SPINNING_UP;
                    this.attackTimer = 40;

                    if (!this.mob.level().isClientSide()){
                        this.mob.setDeltaMovement(0, 1.5, 0);
                        this.mob.hasImpulse = true;
                        //здесь можно сделать синхронизацию с клиентом для рендера через EntityData
                    }
                }
                break;
            case SPINNING_UP:
                if (!this.mob.level().isClientSide()) {
                    this.mob.setYRot(this.mob.getYRot() + 45);
                    for (int i = 0; i < 5; i++) {
                        this.mob.level().addParticle(
                                ParticleTypes.CLOUD,
                                this.mob.getX() + (this.mob.getRandom().nextDouble() - 0.5),
                                this.mob.getY(),
                                this.mob.getZ() + (this.mob.getRandom().nextDouble() - 0.5),
                                0, 0, 0
                        );
                    }
                }
                if (attackTimer <= 0 || this.mob.getDeltaMovement().y <= 0){
                    this.state = AttackState.SPINNING_DOWN;
                    attackTimer = 60;
                }
                break;
            case SPINNING_DOWN:
                if (!this.mob.level().isClientSide()){
                    if (this.target != null && this.target.isAlive()){
                        Vec3 vec = this.target.position().subtract(this.mob.position()).normalize();
                        //брать normalize()
                        this.mob.setDeltaMovement(vec.scale(2.0).add(0, -0.5, 0));
                    }
                    else {
                        this.mob.setDeltaMovement(0, -1, 0);
                    }
                }

                if (this.target != null && (this.mob.onGround() || this.mob.distanceToSqr(this.target) < 4)){
                    this.state = AttackState.EXPLODING;
                    attackTimer = 2;
                }
                else if (this.mob.onGround()){
                    this.state = AttackState.END;
                }
                break;
            case EXPLODING:
                if (attackTimer <= 0) {
                    if (!this.mob.level().isClientSide()) {
                        this.mob.level().explode(this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ(), 3.0f, Level.ExplosionInteraction.MOB);
                        this.mob.heal(10);
                        for (Player player : this.mob.level().getEntitiesOfClass(Player.class, this.mob.getBoundingBox().inflate(5))) {
                            player.knockback(2, player.getX() - this.mob.getX(), player.getY() - this.mob.getY());
                        }
                        this.state = AttackState.END;
                    }

                }
                break;
            case END:
                if (!this.mob.level().isClientSide()){
                    this.mob.setDeltaMovement(0, 0, 0);
                }
                //this.state = AttackState.IDLE;
                this.start();
        }
    }
    @Override
    public boolean canContinueToUse() {
        return this.state != AttackState.IDLE;
    }
}
