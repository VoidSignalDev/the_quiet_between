package net.mcreator.thequietbetween.procedures;

import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import net.mcreator.thequietbetween.network.TheQuietBetweenModVariables;

import javax.annotation.Nullable;

import java.util.Comparator;

@EventBusSubscriber
public class ShadowStalkerTriggerProcedure {
	@SubscribeEvent
	public static void onWorldTick(LevelTickEvent.Post event) {
		execute(event, event.getLevel());
	}

	public static void execute(LevelAccessor world) {
		execute(null, world);
	}

	private static void execute(@Nullable Event event, LevelAccessor world) {
		double randomizer = 0;
		if (TheQuietBetweenModVariables.MapVariables.get(world).joinEvent == 1) {
			randomizer = Mth.nextInt(RandomSource.create(), 1, 8000);
			if (ShadowStalkerEntityProcedure.execute(world) == null && ShadowStalkerFlyingEntityProcedure.execute(world) == null && (randomizer == 1 || TheQuietBetweenModVariables.MapVariables.get(world).forceSpawnSS == true)
					&& TheQuietBetweenModVariables.MapVariables.get(world).playerY >= 63) {
				TheQuietBetweenModVariables.MapVariables.get(world).forceSpawnSS = false;
				TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = Mth.nextInt(RandomSource.create(), 1, 3);
				TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				if (TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior == 1) {
					TheQuietBetweenModVariables.MapVariables.get(world).sprintOrGone = Mth.nextInt(RandomSource.create(), 0, 1);
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				}
				ShadowstalkerspawnProcedure.execute(world, TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ,
						findEntityInWorldRange(world, Player.class, TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ,
								4));
			}
		}
	}

	private static Entity findEntityInWorldRange(LevelAccessor world, Class<? extends Entity> clazz, double x, double y, double z, double range) {
		return (Entity) world.getEntitiesOfClass(clazz, AABB.ofSize(new Vec3(x, y, z), range, range, range), e -> true).stream().sorted(Comparator.comparingDouble(e -> e.distanceToSqr(x, y, z))).findFirst().orElse(null);
	}
}