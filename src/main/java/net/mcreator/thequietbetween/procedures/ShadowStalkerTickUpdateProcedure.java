package net.mcreator.thequietbetween.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.mcreator.thequietbetween.network.TheQuietBetweenModVariables;

public class ShadowStalkerTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior != 0) {
			ShadowStalkerOnEntityTickUpdateProcedure.execute(world, x, y, z, entity);
		} else {
			ShadowStalkerScriptedBehaviorProcedure.execute(world, x, y, z, entity);
		}
	}
}