package ds.item.custom;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.component.type.ToolComponent.Rule;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WhipItem extends ToolItem {

    public WhipItem(ToolMaterial toolMaterial, Item.Settings settings) {
        super(toolMaterial, settings.component(DataComponentTypes.TOOL, createToolComponent()));
    }

    private static ToolComponent createToolComponent() {
        return new ToolComponent(
                List.of(
                        Rule.ofAlwaysDropping(List.of(Blocks.COBWEB), 15.0F),
                        Rule.of(BlockTags.SWORD_EFFICIENT, 1.5F)
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
                                Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID,
                                (double)attackSpeed,
                                Operation.ADD_VALUE),
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
            double range = 7.0;   // дальность
            double radius = 2.0;  // ширина
            double angle = 0.6;
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
                }
            }
            stack.damage(1, attacker, EquipmentSlot.MAINHAND);
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
                        net.minecraft.text.Text.literal("Цель отмечена"),
                        true
                );
                player.swingHand(hand);
            } else {
                player.sendMessage(
                        net.minecraft.text.Text.literal("Нет цели"),
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
}