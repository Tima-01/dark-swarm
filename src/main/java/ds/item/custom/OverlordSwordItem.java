package ds.item.custom;

import ds.item.client.OverlordSwordItemRenderer;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

import java.util.function.Consumer;

public class OverlordSwordItem extends SwordItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public OverlordSwordItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

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
            private final OverlordSwordItemRenderer renderer = new OverlordSwordItemRenderer();

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
}
