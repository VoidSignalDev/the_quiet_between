package net.mcreator.thequietbetween.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.thequietbetween.network.TheQuietBetweenModVariables;

public class RandomGreetingProcedure {
	public static void execute(LevelAccessor world) {
		double randomGreeting = 0;
		double V0idOrNULL = 0;
		randomGreeting = Mth.nextInt(RandomSource.create(), 1, 42);
		if (randomGreeting <= 10) {
			if (TheQuietBetweenModVariables.MapVariables.get(world).V0idJoined >= 1) {
				V0idOrNULL = Mth.nextInt(RandomSource.create(), 1, 2);
			} else {
				V0idOrNULL = 1;
			}
			if (V0idOrNULL == 1) {
				if (randomGreeting == 1) {
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> you are here"), false);
					}
				} else if (randomGreeting == 2) {
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> finally"), false);
					}
				} else if (randomGreeting == 3) {
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> i was wondering when you would join"), false);
					}
				} else if (randomGreeting == 4) {
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> i have been waiting"), false);
					}
				} else if (randomGreeting == 5) {
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> you should not have come"), false);
					}
				} else if (randomGreeting == 6) {
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> i remember you"), false);
					}
				} else if (randomGreeting == 7) {
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> we met before"), false);
					}
				} else if (randomGreeting == 8) {
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> you always stay too long"), false);
					}
				} else if (randomGreeting == 9) {
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> you can not stop"), false);
					}
				} else if (randomGreeting == 10) {
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(("<> hello " + PlayerEntityProcedure.execute(world).getDisplayName().getString())), false);
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ),
								BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 999999999, 1);
					} else {
						_level.playLocalSound(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ,
								BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 999999999, 1, false);
					}
				}
			} else if (V0idOrNULL == 2) {
				if (randomGreeting > 5) {
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<v0id> dGhlcmUgeW91IGFyZQ==").withColor(0xff0000), false);
					}
				} else if (randomGreeting <= 5) {
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(("<v0id> d2VsY29tZSBiYWNrIA== " + PlayerEntityProcedure.execute(world).getDisplayName().getString())), false);
					}
				}
			}
		}
	}
}