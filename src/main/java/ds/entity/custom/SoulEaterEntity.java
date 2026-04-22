package ds.entity.custom;

import ds.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SoulEaterEntity extends PathAwareEntity implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SoulEaterEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }


    public static DefaultAttributeContainer.Builder createAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D);
    }


    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!this.getWorld().isClient) {
            if (stack.isOf(Items.SOUL_SAND)) {
                if(player.getItemCooldownManager().isCoolingDown(Items.SOUL_SAND)) return ActionResult.PASS;
                if (this.random.nextFloat() < 0.3f) {
                    this.dropItem(ModItems.SOUL);
                }
                stack.decrement(1);
                player.getItemCooldownManager().set(Items.SOUL_SAND, 60);
                this.playSound(SoundEvents.ENTITY_ENDERMAN_AMBIENT, 1.0F, 1.0F);
                ((ServerWorld)this.getWorld()).spawnParticles(
                        ParticleTypes.SOUL,
                        this.getX(), this.getY() + 1, this.getZ(),
                        10,
                        0.3, 0.3, 0.3,
                        0.01
                );
            }
            else if (stack.isOf(ModItems.PROFANED_SOUL)) {
                if(player.getItemCooldownManager().isCoolingDown(ModItems.PROFANED_SOUL)) return ActionResult.PASS;
                if (this.random.nextFloat() < 0.5f) {
                    this.dropItem(ModItems.SOUL);
                }
                stack.decrement(1);
                player.getItemCooldownManager().set(ModItems.PROFANED_SOUL, 40);
                this.playSound(SoundEvents.ENTITY_ENDERMAN_AMBIENT, 1.0F, 1.0F);
                ((ServerWorld)this.getWorld()).spawnParticles(
                        ParticleTypes.SOUL,
                        this.getX(), this.getY() + 1, this.getZ(),
                        10,
                        0.3, 0.3, 0.3,
                        0.01
                );
            }
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}