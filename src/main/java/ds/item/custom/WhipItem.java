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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
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
        return true;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient) {
            double range = 12.0;   // дальность хлыста
            double radius = 3.5;   // ширина области
            double angle = 0.5;    // угол (меньше = шире сектор)

            float damage = 6.0f;

            var start = player.getCameraPosVec(1.0F);
            var look = player.getRotationVec(1.0F);

            // создаём зону перед игроком
            var box = player.getBoundingBox()
                    .stretch(look.multiply(range))
                    .expand(radius);

            boolean hitSomething = false;

            for (LivingEntity entity : world.getEntitiesByClass(
                    LivingEntity.class,
                    box,
                    e -> e != player
            )) {

                // направление к сущности
                var dirToEntity = entity.getPos().subtract(player.getPos()).normalize();

                // проверка угла (сектор)
                double dot = look.dotProduct(dirToEntity);

                if (dot > angle) {
                    hitSomething = true;

                    entity.damage(world.getDamageSources().playerAttack(player), damage);

                    entity.takeKnockback(
                            0.6,
                            player.getX() - entity.getX(),
                            player.getZ() - entity.getZ()
                    );
                }
            }

            // анимация удара всегда
            player.swingHand(hand);

            // износ только если кого-то задел
            if (hitSomething) {
                stack.damage(1, player, EquipmentSlot.MAINHAND);
            }
        }

        return TypedActionResult.success(stack, world.isClient());
    }
}