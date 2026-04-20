package ds.entity.custom;

import ds.item.custom.OverlordSwordItem;
import ds.item.custom.OverlordWhipItem;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class MinionEntity extends TameableEntity {

    public MinionEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);

        //this.setCanPickUpLoot(true);
    }

    public static DefaultAttributeContainer.Builder createMinionAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)

                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));


        this.goalSelector.add(1, new MinionSwordCommandGoal(this));

        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1.2, false));


        this.goalSelector.add(4, new MinionFollowOwnerGoal(this, 1.1, 6.0f, 2.0f));

        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));

        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
        this.targetSelector.add(3, new RevengeGoal(this));
    }


    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }
    @Override
    public boolean cannotDespawn() {

        return this.isTamed() || super.cannotDespawn();
    }


    public boolean isCommanding() {
        LivingEntity owner = this.getOwner();
        return owner instanceof PlayerEntity p && (
                p.getMainHandStack().getItem() instanceof OverlordWhipItem ||
                        p.getMainHandStack().getItem() instanceof OverlordSwordItem)
                && p.isSneaking();
    }


    public boolean isBusyFighting() {
        return this.getTarget() != null && this.getTarget().isAlive();
    }


    public static class MinionSwordCommandGoal extends Goal {
        private final MinionEntity minion;

        public MinionSwordCommandGoal(MinionEntity minion) {
            this.minion = minion;
        }

        @Override
        public boolean canStart() {
            return minion.isCommanding();
        }

        @Override
        public void tick() {
            PlayerEntity owner = (PlayerEntity) minion.getOwner();
            if (owner == null) return;

            EntityHitResult entityHit = ProjectileUtil.raycast(
                    owner, owner.getCameraPosVec(1.0F),
                    owner.getCameraPosVec(1.0F).add(owner.getRotationVec(1.0F).multiply(50.0)),
                    owner.getBoundingBox().stretch(owner.getRotationVec(1.0F).multiply(50.0)).expand(1.0D),
                    entity -> !entity.isSpectator() && entity instanceof LivingEntity, 50.0
            );

            if (entityHit != null && entityHit.getEntity() instanceof LivingEntity target) {
                if (target != owner && !isAlly(target)) {
                    minion.setTarget(target);
                    return;
                }
            }

            HitResult hit = owner.raycast(50.0, 0.0f, false);
            if (hit.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) hit;
                minion.getNavigation().startMovingTo(blockHit.getPos().x, blockHit.getPos().y, blockHit.getPos().z, 1.3);
            }
        }

        private boolean isAlly(LivingEntity entity) {
            return entity instanceof MinionEntity m && m.getOwner() == minion.getOwner();
        }
    }


    public static class MinionFollowOwnerGoal extends Goal {
        private final MinionEntity minion;
        private final double speed;
        private final float minDistance;
        private final float maxDistance;

        public MinionFollowOwnerGoal(MinionEntity minion, double speed, float minDistance, float maxDistance) {
            this.minion = minion;
            this.speed = speed;
            this.minDistance = minDistance;
            this.maxDistance = maxDistance;
        }

        @Override
        public boolean canStart() {
            LivingEntity owner = minion.getOwner();

            if (owner == null || minion.isSitting() || minion.isCommanding() || minion.isBusyFighting()) return false;
            return minion.squaredDistanceTo(owner) > (double)(minDistance * minDistance);
        }

        @Override
        public void tick() {
            LivingEntity owner = minion.getOwner();
            if (owner == null) return;
            minion.getLookControl().lookAt(owner, 10.0F, (float)minion.getMaxLookPitchChange());
            if (minion.squaredDistanceTo(owner) > (double)(maxDistance * maxDistance)) {
                minion.getNavigation().startMovingTo(owner, speed);
            } else {
                minion.getNavigation().stop();
            }
        }

        @Override
        public boolean shouldContinue() {
            LivingEntity owner = minion.getOwner();

            if (owner == null || minion.isSitting() || minion.isCommanding() || minion.isBusyFighting()) return false;
            return minion.squaredDistanceTo(owner) > (double)(maxDistance * maxDistance);
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) { return false; }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) { return null; }
}