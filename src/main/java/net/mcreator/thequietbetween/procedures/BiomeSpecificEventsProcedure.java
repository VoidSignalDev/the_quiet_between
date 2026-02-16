package net.mcreator.thequietbetween.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.thequietbetween.network.TheQuietBetweenModVariables;

public class BiomeSpecificEventsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double angle = 0;
		double radius = 0;
		double X = 0;
		double Y = 0;
		double Z = 0;
		if (y >= 62 && world.getBiome(BlockPos.containing(x, y, z)).is(TagKey.create(Registries.BIOME, ResourceLocation.parse("minecraft:is_forest")))) {
			radius = 20;
			angle = Math.random() * 2 * Math.PI;
			X = TheQuietBetweenModVariables.MapVariables.get(world).playerX + radius * Math.cos(angle);
			Y = 66;
			Z = TheQuietBetweenModVariables.MapVariables.get(world).playerZ + radius * Math.sin(angle);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(X, Y, Z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("the_quiet_between:distant_female_scream")), SoundSource.MASTER, 255, 1);
				} else {
					_level.playLocalSound(X, Y, Z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("the_quiet_between:distant_female_scream")), SoundSource.MASTER, 255, 1, false);
				}
			}
		} else if (y <= 62) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ),
							BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("the_quiet_between:cave_whisper")), SoundSource.MASTER, 10, 1);
				} else {
					_level.playLocalSound(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ,
							BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("the_quiet_between:cave_whisper")), SoundSource.MASTER, 10, 1, false);
				}
			}
		}
	}
}