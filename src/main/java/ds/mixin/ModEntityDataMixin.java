package ds.mixin;

import ds.util.IEntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class ModEntityDataMixin implements IEntityDataSaver {
    private int minionCount = 0;

    @Override
    public int darkswarm$getMinionCount() { return minionCount; }

    @Override
    public void darkswarm$setMinionCount(int count) { this.minionCount = count; }


    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    protected void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo info) {
        nbt.putInt("minionCount", minionCount);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    protected void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("minionCount")) {
            minionCount = nbt.getInt("minionCount");
        }
    }
}