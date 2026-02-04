package net.mcreator.thequietbetween.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.thequietbetween.network.TheQuietBetweenModVariables;

import javax.annotation.Nullable;

@EventBusSubscriber
public class PlayerPositionProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
	}

	public static void execute(LevelAccessor world, double x, double y, double z) {
		execute(null, world, x, y, z);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z) {
		TheQuietBetweenModVariables.MapVariables.get(world).playerX = x;
		TheQuietBetweenModVariables.MapVariables.get(world).playerY = y;
		TheQuietBetweenModVariables.MapVariables.get(world).playerZ = z;
		TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
	}
}