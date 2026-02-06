package net.mcreator.thequietbetween.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.thequietbetween.network.TheQuietBetweenModVariables;
import net.mcreator.thequietbetween.init.TheQuietBetweenModEntities;

public class ShadowstalkerspawnProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Vec3 lookVec = Vec3.ZERO;
		double X_Cord = 0;
		double Y_Cord = 0;
		double Z_Cord = 0;
		double angle = 0;
		double distance = 0;
		double yawRad = 0;
		double pitchRad = 0;
		double angleOffset = 0;
		double offsetX = 0;
		double lookX = 0;
		double lookY = 0;
		double lookZ = 0;
		double offsetZ = 0;
		if (TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior == 1 || TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior == 3) {
			TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerSpawn = 2;
			TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
		} else if (TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior == 2 || TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior == 6) {
			TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerSpawn = 0;
			TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
		} else if (TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior == 4) {
			TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerSpawn = 1;
			TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
		}
		if (TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerSpawn == 0 || TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerSpawn == 1) {
			yawRad = Math.toRadians(entity.getYRot());
			pitchRad = Math.toRadians(entity.getXRot());
			lookX = (-1) * Math.sin(yawRad) * Math.cos(pitchRad);
			lookY = (-1) * Math.sin(pitchRad);
			lookZ = Math.cos(yawRad) * Math.cos(pitchRad);
			lookVec = new Vec3(lookX, lookY, lookZ);
			if (TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerSpawn == 0) {
				distance = Math.random() * 10 + 20;
				angleOffset = (Math.random() - 0.5) * Math.toRadians(60);
				offsetX = lookVec.x() * Math.cos(angleOffset) - lookVec.z() * Math.sin(angleOffset);
				offsetZ = lookVec.x() * Math.sin(angleOffset) + lookVec.z() * Math.cos(angleOffset);
				X_Cord = x - offsetX * distance;
				Z_Cord = z - offsetZ * distance;
				Y_Cord = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) X_Cord, (int) Z_Cord);
			} else if (TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerSpawn == 1) {
				distance = 3;
				X_Cord = x + lookVec.x() * distance;
				Z_Cord = z + lookVec.z() * distance;
				Y_Cord = y;
			}
		} else if (TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerSpawn == 2) {
			distance = Math.random() * 10 + 20;
			angle = Math.random() * 3.14 * 2;
			X_Cord = x + distance * Math.cos(angle);
			Z_Cord = z + distance * Math.sin(angle);
			Y_Cord = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) X_Cord, (int) Z_Cord);
		}
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = TheQuietBetweenModEntities.SHADOW_STALKER.get().spawn(_level, BlockPos.containing(X_Cord, Y_Cord, Z_Cord), EntitySpawnReason.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setDeltaMovement(0, 0, 0);
			}
		}
		if (TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerSpawn == 2) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(X_Cord, Y_Cord, Z_Cord), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 255, 1);
				} else {
					_level.playLocalSound(X_Cord, Y_Cord, Z_Cord, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 255, 1, false);
				}
			}
		}
	}
}