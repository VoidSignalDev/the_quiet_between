package net.mcreator.thequietbetween.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;

import net.mcreator.thequietbetween.TheQuietBetweenMod;

public class RejoinAfterV0idJoinProcedure {
	public static void execute(LevelAccessor world) {
		TheQuietBetweenMod.queueServerWork(100, () -> {
			if (world instanceof ServerLevel _level) {
				_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> you shouldn\u2019t have come"), false);
			}
			TheQuietBetweenMod.queueServerWork(40, () -> {
				if (world instanceof ServerLevel _level) {
					_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> you invited it back"), false);
				}
				TheQuietBetweenMod.queueServerWork(40, () -> {
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> it remembers you"), false);
					}
					TheQuietBetweenMod.queueServerWork(40, () -> {
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> you think leaving helps? it doesn\u2019t"), false);
						}
						TheQuietBetweenMod.queueServerWork(40, () -> {
							if (world instanceof ServerLevel _level) {
								_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> you don\u2019t understand what\u2019s following you"), false);
							}
							TheQuietBetweenMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> v0id doesn\u2019t forget"), false);
								}
							});
						});
					});
				});
			});
		});
	}
}