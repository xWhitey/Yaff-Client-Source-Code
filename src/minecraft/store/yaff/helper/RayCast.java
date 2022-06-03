package store.yaff.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class RayCast {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean isInView(float yawIn, float pitchIn, Entity entityIn, double distanceIn) {
        Vec3d vec3d = mc.player.getPositionEyes(1f);
        Vec3d vec3d1 = Entity.getVectorForRotation(pitchIn, yawIn);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.xCoord * distanceIn, vec3d1.yCoord * distanceIn, vec3d1.zCoord * distanceIn);
        return mc.world.rayTraceBlocks(vec3d, vec3d2, false, false, true) == null || entityIn.getEntityBoundingBox().expandXyz(0.04).calculateIntercept(vec3d, vec3d2) == null;
    }

    @Nullable
    public static Entity rayCast(Entity entityIn, double distanceIn) {
        Vec3d vec3d = entityIn.getPositionVector().add(new Vec3d(0, entityIn.getEyeHeight(), 0));
        Vec3d vec3d1 = mc.player.getPositionVector().add(new Vec3d(0, mc.player.getEyeHeight(), 0));
        AxisAlignedBB axisAligned = mc.player.getEntityBoundingBox().addCoord(vec3d.xCoord - vec3d1.xCoord, vec3d.yCoord - vec3d1.yCoord, vec3d.zCoord - vec3d1.zCoord).expandXyz(1);
        for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, axisAligned)) {
            AxisAlignedBB axisAligned1 = entity.getEntityBoundingBox().expandXyz(entity.getCollisionBorderSize());
            RayTraceResult rayTrace = axisAligned1.calculateIntercept(vec3d1, vec3d);
            if (axisAligned1.isVecInside(vec3d1)) {
                return entity;
            }
            if (rayTrace != null) {
                if (vec3d1.distanceTo(rayTrace.hitVec) < distanceIn) {
                    return entity;
                }
            }
        }
        return null;
    }

}