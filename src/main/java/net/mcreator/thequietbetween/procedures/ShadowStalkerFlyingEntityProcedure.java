package net.mcreator.thequietbetween.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.mcreator.thequietbetween.network.TheQuietBetweenModVariables;
import net.mcreator.thequietbetween.entity.ShadowStalkerFlyingEntity;

import java.util.Comparator;

public class ShadowStalkerFlyingEntityProcedure {
	public static Entity execute(LevelAccessor world) {
		return findEntityInWorldRange(world, ShadowStalkerFlyingEntity.class, TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY,
				TheQuietBetweenModVariables.MapVariables.get(world).playerZ, 512);
	}

	private static Entity findEntityInWorldRange(LevelAccessor world, Class<? extends Entity> clazz, double x, double y, double z, double range) {
		return (Entity) world.getEntitiesOfClass(clazz, AABB.ofSize(new Vec3(x, y, z), range, range, range), e -> true).stream().sorted(Comparator.comparingDouble(e -> e.distanceToSqr(x, y, z))).findFirst().orElse(null);
	}
}