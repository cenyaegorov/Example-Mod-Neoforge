package com.example.examplemod.entity.bus;

import com.example.examplemod.entity.ModEntities;
import com.example.examplemod.entity.goals.BusFlyAttackGoal;
import com.example.examplemod.entity.goals.BusPlaySoundsGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Bus extends Monster implements GeoAnimatable {
    private static final EntityDataAccessor<Boolean> isFlyingPhase = SynchedEntityData.defineId(Bus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<BusState> state = SynchedEntityData.defineId(Bus.class, ModEntities.BUS_STATE_ENTITY_DATA_SERIALIZER);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private MeleeAttackGoal meleeAttackGoal;
    private LeapAtTargetGoal leapAtTargetGoal;
    private WaterAvoidingRandomFlyingGoal waterAvoidingRandomFlyingGoal;
    private BusFlyAttackGoal busFlyAttackGoal;

    public Bus(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals(){
        this.meleeAttackGoal = new MeleeAttackGoal(this, BusConfig.ATTACK_MOVEMENT_SPEED, true);
        this.leapAtTargetGoal = new LeapAtTargetGoal(this, 1);
        this.waterAvoidingRandomFlyingGoal = new WaterAvoidingRandomFlyingGoal(this, 0.8);
        this.busFlyAttackGoal = new BusFlyAttackGoal(this);

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, meleeAttackGoal);
        this.goalSelector.addGoal(2, leapAtTargetGoal);
        this.goalSelector.addGoal(3, waterAvoidingRandomFlyingGoal);
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, BusConfig.LOOK_DISTANCE));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new BusPlaySoundsGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, BusConfig.MOVEMENT_SPEED)
                .add(Attributes.ATTACK_DAMAGE, BusConfig.ATTACK_DAMAGE)
                .add(Attributes.MAX_HEALTH, BusConfig.MAX_HEALTH)
                .add(Attributes.ARMOR, BusConfig.ARMOR)
                .add(Attributes.ATTACK_SPEED, BusConfig.ATTACK_SPEED);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<GeoAnimatable>(this, "mycontroller", this::predicate));
    }

    private PlayState predicate(AnimationState<GeoAnimatable> geoAnimatableAnimationState) {
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object object) {
        return tickCount;
    }
    public void setState(BusState busState) { this.entityData.set(state, busState);}
    public BusState getState() {return this.entityData.get(state);}
    @Override
    public void tick(){
        super.tick();
        if (!this.level().isClientSide() && !this.entityData.get(isFlyingPhase) && this.getHealth() < this.getMaxHealth() * 0.7){
            this.enterFlyingPhase();
        }
    }
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder){
        builder.define(isFlyingPhase, false);
        builder.define(state, BusState.IDLE);
        super.defineSynchedData(builder);

    }

    private void enterFlyingPhase() {
        this.entityData.set(isFlyingPhase, true);
        this.noPhysics = false;
        this.setNoGravity(true);

        //this.setDeltaMovement(this.getDeltaMovement().add(0, 0.01, 0));
        this.level().broadcastEntityEvent((Entity) this, (byte) 10);
        this.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 0.8F);

        this.goalSelector.removeGoal(waterAvoidingRandomFlyingGoal);
        this.goalSelector.removeGoal(meleeAttackGoal);
        this.goalSelector.removeGoal(leapAtTargetGoal);
        this.goalSelector.addGoal(2, busFlyAttackGoal);
    }
    public boolean isFlyingPhase(){
        return this.entityData.get(isFlyingPhase);
    }
    public void subdued(){
        this.goalSelector.removeGoal(meleeAttackGoal);
        this.goalSelector.removeGoal(busFlyAttackGoal);
        this.goalSelector.removeGoal(leapAtTargetGoal);
    }
}
