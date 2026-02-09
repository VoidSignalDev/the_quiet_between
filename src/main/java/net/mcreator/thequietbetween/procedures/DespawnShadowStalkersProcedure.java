package net.mcreator.thequietbetween.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.thequietbetween.network.TheQuietBetweenModVariables;
import net.mcreator.thequietbetween.TheQuietBetweenMod;

public class DespawnShadowStalkersProcedure {
	public static void execute(LevelAccessor world) {
		TheQuietBetweenMod.queueServerWork(2400, () -> {
			if (ShadowStalkerEntityProcedure.execute(world) != null) {
				if (!ShadowStalkerEntityProcedure.execute(world).level().isClientSide())
					ShadowStalkerEntityProcedure.execute(world).discard();
			} else if (ShadowStalkerFlyingEntityProcedure.execute(world) != null) {
				if (!ShadowStalkerFlyingEntityProcedure.execute(world).level().isClientSide())
					ShadowStalkerFlyingEntityProcedure.execute(world).discard();
			}
			TheQuietBetweenModVariables.MapVariables.get(world).despawnShadowStalkerTrigger = true;
			TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
		});
	}
}