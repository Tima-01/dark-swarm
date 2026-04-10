package ds.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class MinionModel<T extends Entity> extends EntityModel<T> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of("darkswarm", "minion"), "main");
	private final ModelPart zerg;
	private final ModelPart noga;
	private final ModelPart noga2;
	private final ModelPart tail;

	public MinionModel(ModelPart root) {
		this.zerg = root.getChild("zerg");
		this.noga = this.zerg.getChild("noga");
		this.noga2 = this.zerg.getChild("noga2");
		this.tail = this.zerg.getChild("tail");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData zerg = modelPartData.addChild("zerg", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 16.0F, -2.0F));

		ModelPartData body2_r1 = zerg.addChild("body2_r1", ModelPartBuilder.create().uv(0, 15).cuboid(-2.0F, -2.4449F, -6.6067F, 5.0F, 2.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, -2.0F, -0.3054F, 0.0F, 0.0F));

		ModelPartData body1_r1 = zerg.addChild("body1_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -2.4449F, -7.6067F, 7.0F, 4.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

		ModelPartData head1_r1 = zerg.addChild("head1_r1", ModelPartBuilder.create().uv(38, 29).cuboid(-1.0F, -2.4449F, -8.6067F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, -2.0F, -0.1309F, 0.0F, 0.0F));

		ModelPartData head_r1 = zerg.addChild("head_r1", ModelPartBuilder.create().uv(20, 26).cuboid(-2.0F, -3.4449F, -10.6067F, 5.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		ModelPartData noga = zerg.addChild("noga", ModelPartBuilder.create().uv(0, 43).cuboid(-2.0F, 6.977F, 0.1495F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 0.0F, 0.0F));

		ModelPartData noga3_r1 = noga.addChild("noga3_r1", ModelPartBuilder.create().uv(12, 35).cuboid(-1.0F, -3.37F, -0.5961F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 6.0F, 3.0F, -0.3491F, 0.0F, 0.0F));

		ModelPartData noga2_r1 = noga.addChild("noga2_r1", ModelPartBuilder.create().uv(36, 33).cuboid(-1.0F, -0.8149F, -4.2028F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 3.0F, 5.0F, -0.3054F, 0.0F, 0.0F));

		ModelPartData noga1_r1 = noga.addChild("noga1_r1", ModelPartBuilder.create().uv(0, 35).cuboid(-2.0F, -1.4449F, -1.6067F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

		ModelPartData noga2 = zerg.addChild("noga2", ModelPartBuilder.create().uv(0, 43).cuboid(-2.0F, 6.977F, 0.1495F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(6.0F, 0.0F, 0.0F));

		ModelPartData noga4_r1 = noga2.addChild("noga4_r1", ModelPartBuilder.create().uv(12, 35).cuboid(-1.0F, -3.37F, -0.5961F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 6.0F, 3.0F, -0.3491F, 0.0F, 0.0F));

		ModelPartData noga3_r2 = noga2.addChild("noga3_r2", ModelPartBuilder.create().uv(36, 33).cuboid(-1.0F, -0.8149F, -4.2028F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 3.0F, 5.0F, -0.3054F, 0.0F, 0.0F));

		ModelPartData noga2_r2 = noga2.addChild("noga2_r2", ModelPartBuilder.create().uv(0, 35).cuboid(-2.0F, -1.4449F, -1.6067F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

		ModelPartData tail = zerg.addChild("tail", ModelPartBuilder.create().uv(36, 8).cuboid(-0.5F, -2.1F, 5.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(20, 40).cuboid(-0.5F, -1.1F, 9.0F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 3.0F));

		ModelPartData cube_r1 = tail.addChild("cube_r1", ModelPartBuilder.create().uv(28, 15).cuboid(-1.0F, -5.4449F, -1.6067F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 3.0F, -0.1309F, 0.0F, 0.0F));

		ModelPartData cube_r2 = tail.addChild("cube_r2", ModelPartBuilder.create().uv(0, 26).cuboid(-1.0F, -4.4449F, -3.6067F, 3.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, -3.0F, -0.3054F, 0.0F, 0.0F));

		ModelPartData cube_r3 = tail.addChild("cube_r3", ModelPartBuilder.create().uv(20, 33).cuboid(-2.0F, -5.4449F, -0.6067F, 5.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		zerg.render(matrices, vertexConsumer, light, overlay, color);
	}
}