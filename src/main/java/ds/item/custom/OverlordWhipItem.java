package ds.item.custom;

import ds.enchantment.ModEnchantments;
import ds.item.client.OverlordWhipItemRenderer;
import ds.util.ItemUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

import java.util.List;
import java.util.function.Consumer;

public class OverlordWhipItem extends SwordItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public OverlordWhipItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings.component(DataComponentTypes.TOOL, createToolComponent()));
    }

    private static ToolComponent createToolComponent() {
        return new ToolComponent(
                List.of(
                        ToolComponent.Rule.ofAlwaysDropping(List.of(Blocks.COBWEB), 15.0F),
                        ToolComponent.Rule.of(BlockTags.SWORD_EFFICIENT, 1.5F)
                ),
                1.0F,
                2
        );
    }



    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, int baseAttackDamage, float attackSpeed) {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID,
                                (double)((float)baseAttackDamage + material.getAttackDamage()),
                                EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID,
                                (double)attackSpeed,
                                EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .build();
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!(attacker instanceof PlayerEntity player)) return false;
        World world = player.getWorld();
        if (!world.isClient) {
            double baseRange = 3.0;
            double baseRadius = 2;
            double baseAngle = 0.3;

            int rangeLevel = ItemUtil.getLevel(world, stack, ModEnchantments.LONGWHIP);
            int wideLevel = ItemUtil.getLevel(world, stack, ModEnchantments.WIDE_SWING);

            double range = baseRange + rangeLevel * 2.0;
            double radius = baseRadius + wideLevel * 1.0;
            double angle = baseAngle + wideLevel * 0.15;

            float aoeDamage = 2.5f;
            var look = player.getRotationVec(1.0F);
            var box = player.getBoundingBox()
                    .stretch(look.multiply(range))
                    .expand(radius);
            for (LivingEntity entity : world.getEntitiesByClass(
                    LivingEntity.class,
                    box,
                    e -> e != player && e != target
            )) {
                var dir = entity.getPos().subtract(player.getPos()).normalize();
                double dot = look.dotProduct(dir);
                if (dot > angle) {
                    entity.damage(world.getDamageSources().playerAttack(player), aoeDamage);
                    entity.takeKnockback(
                            0.2,
                            player.getX() - entity.getX(),
                            player.getZ() - entity.getZ()
                    );
                    world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS);
                    ((ServerWorld) world).spawnParticles(
                            ParticleTypes.SWEEP_ATTACK,
                            entity.getX(),
                            entity.getBodyY(0.5),
                            entity.getZ(),
                            1,
                            0.0, 0.0, 0.0,
                            0.0
                    );
                }
            }
        }
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!world.isClient) {
            double range = 256.0;
            var start = player.getCameraPosVec(1.0F);
            var look = player.getRotationVec(1.0F);
            var end = start.add(look.multiply(range));
            var box = player.getBoundingBox()
                    .stretch(look.multiply(range))
                    .expand(2.0);
            var hit = ProjectileUtil.raycast(
                    player,
                    start,
                    end,
                    box,
                    entity -> entity instanceof LivingEntity && entity != player,
                    range
            );
            if (hit != null && hit.getEntity() instanceof LivingEntity target) {
                double radius = 20.0;
                for (MobEntity mob : world.getEntitiesByClass(
                        MobEntity.class,
                        player.getBoundingBox().expand(radius),
                        e -> !e.equals(player) && !e.equals(target)
                )) {
                    boolean isFriendly =
                            mob.isTeammate(player) ||
                                    mob instanceof net.minecraft.entity.passive.TameableEntity tame && tame.getOwner() == player;

                    if (isFriendly) {
                        mob.setTarget(target);
                    }
                }
                player.sendMessage(
                        net.minecraft.text.Text.translatable("whip_text.dark-swarm.target_acquired"),
                        true
                );
                player.swingHand(hand);
            } else {
                player.sendMessage(
                        net.minecraft.text.Text.translatable("whip_text.dark-swarm.target_not_found"),
                        true
                );
            }
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if(Screen.hasShiftDown()){
            tooltip.add(Text.translatable("tooltip.dark-swarm.whip.tooltip.shiftdown"));
            tooltip.add(Text.translatable("tooltip.dark-swarm.whip.tooltip.shiftdown1"));
        } else {
            tooltip.add(Text.translatable("tooltip.dark-swarm.whip.tooltip"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }


    // Гекколибовские штуки
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtil.getCurrentTick();
    }


    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private final OverlordWhipItemRenderer renderer = new OverlordWhipItemRenderer();

            @Override
            public @Nullable BuiltinModelItemRenderer getGeoItemRenderer() {
                return this.renderer;
            }
        });
    }
    @Override
    public Object getRenderProvider() {
        return GeoItem.super.getRenderProvider();
    }


}
