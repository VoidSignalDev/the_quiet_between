package net.mcreator.thequietbetween.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;
import net.minecraft.ChatFormatting;

import net.mcreator.thequietbetween.network.TheQuietBetweenModVariables;
import net.mcreator.thequietbetween.TheQuietBetweenMod;

public class ChatSpamProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (TheQuietBetweenModVariables.MapVariables.get(world).trigger == true) {
			TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar = Mth.nextInt(RandomSource.create(), 1, 7);
			TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
			if (TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar == 1) {
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							"/tellraw @a [{\"text\":\"STOP!\",\"color\":\"red\"}]");
			} else if (TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar == 2) {
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							"/tellraw @a [{\"text\":\"fdfghjdadssadwakghrhs\",\"obfuscated\":true,\"color\":\"red\"}]");
			} else if (TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar == 3) {
				if (world instanceof ServerLevel _level) {
					_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("WHO ARE YOU").withColor(0xff3333).withStyle(ChatFormatting.BOLD), false);
				}
			} else if (TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar == 4) {
				if (world instanceof ServerLevel _level) {
					_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("WHY ARE YOU HERE").withColor(0xff3333).withStyle(ChatFormatting.BOLD), false);
				}
			} else if (TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar == 5) {
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							"/tellraw @a [{\"text\":\"HELP\",\"color\":\"red\"},{\"text\":\"\\nRIGHT HERE\",\"color\":\"red\",\"obfuscated\":true}]");
			} else if (TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar == 6) {
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							"/tellraw @a [{\"text\":\"LEAVE\",\"color\":\"red\"}]");
			} else if (TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar == 7) {
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							"/tellraw @a [{\"text\":\"fdfghjkghrhs\",\"obfuscated\":true,\"color\":\"red\"}]");
			}
			TheQuietBetweenModVariables.MapVariables.get(world).trigger = false;
			TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
		}
		TheQuietBetweenMod.queueServerWork(2, () -> {
			TheQuietBetweenModVariables.MapVariables.get(world).trigger = true;
			TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
		});
	}
}