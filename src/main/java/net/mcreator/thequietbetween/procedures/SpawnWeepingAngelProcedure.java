package net.mcreator.thequietbetween.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.mcreator.thequietbetween.init.TheQuietBetweenModEntities;

import javax.annotation.Nullable;

@EventBusSubscriber
public class SpawnWeepingAngelProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Vec3 lookAngleOfPlayer = Vec3.ZERO;
		double randomizer = 0;
		double distance = 0;
		double X = 0;
		double Y = 0;
		double Z = 0;
		if (world.getBiome(BlockPos.containing(x, y, z)).is(ResourceLocation.parse("the_quiet_between:white_void_biome"))) {
			randomizer = Mth.nextInt(RandomSource.create(), 1, 5000);
			if (randomizer == 1) {
				distance = 20;
				lookAngleOfPlayer = entity.getLookAngle();
				X = x - lookAngleOfPlayer.x() * distance;
				Z = z - lookAngleOfPlayer.z() * distance;
				Y = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) X, (int) Z);
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = TheQuietBetweenModEntities.WEEPING_ANGEL.get().spawn(_level, BlockPos.containing(X, Y, Z), EntitySpawnReason.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setDeltaMovement(0, 0, 0);
					}
				}
			}
		}
	}
}