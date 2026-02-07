package net.mcreator.thequietbetween.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;
import net.minecraft.ChatFormatting;

import net.mcreator.thequietbetween.network.TheQuietBetweenModVariables;
import net.mcreator.thequietbetween.TheQuietBetweenMod;

public class ShadowStalkerScriptedBehaviorProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity playerNear = null;
		Vec3 playerToEntity = Vec3.ZERO;
		double distancePlayerAndEntity = 0;
		double randomizer = 0;
		playerNear = PlayerEntityProcedure.execute(world);
		final Entity finalPlayerNear = playerNear;
		if (playerNear != null) {
			playerToEntity = new Vec3((entity.getX() - TheQuietBetweenModVariables.MapVariables.get(world).playerX), (entity.getY() - TheQuietBetweenModVariables.MapVariables.get(world).playerY),
					(entity.getZ() - TheQuietBetweenModVariables.MapVariables.get(world).playerZ));
			distancePlayerAndEntity = playerToEntity.length();
			if (TheQuietBetweenModVariables.MapVariables.get(world).shadowStalkerScripted == 1) {
				entity.lookAt(EntityAnchorArgument.Anchor.EYES,
						new Vec3(TheQuietBetweenModVariables.MapVariables.get(world).playerX, (TheQuietBetweenModVariables.MapVariables.get(world).playerY + 1.5), TheQuietBetweenModVariables.MapVariables.get(world).playerZ));
				playerNear.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(x, (y + 1.5), z));
				if (playerNear instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2));
				if (playerNear instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 40, 1));
				if (playerNear instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 40, 1));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ),
								BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, (float) 1.5, -5);
					} else {
						_level.playLocalSound(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ,
								BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, (float) 1.5, -5, false);
					}
				}
				ChatSpamProcedure.execute(world, x, y, z);
				TheQuietBetweenMod.queueServerWork(40, () -> {
					if (!entity.level().isClientSide())
						entity.discard();
					TheQuietBetweenModVariables.MapVariables.get(world).shadowStalkerScripted = 0;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					if (finalPlayerNear instanceof ServerPlayer sp2) {
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("Player.Annoying").withColor(0xff0000).withStyle(ChatFormatting.BOLD), false);
						}
						sp2.connection.disconnect(Component.literal("SHUTUPSTOPITSHUTUPSTOPITSHUTUPSTOPITSHUTUPSTOPITSHUTUPSTOPITSHUTUPSTOPITSHUTUPSTOPITSHUTUPSTOPITSHUTUPSTOPIT").withStyle(ChatFormatting.RED));
						return;
					}
				});
			} else if (TheQuietBetweenModVariables.MapVariables.get(world).shadowStalkerScripted == 2) {
				if (entity instanceof Mob _entity)
					_entity.getNavigation().moveTo(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ, 3);
				entity.lookAt(EntityAnchorArgument.Anchor.EYES,
						new Vec3(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ));
				if (TheQuietBetweenModVariables.MapVariables.get(world).timerReset == true) {
					TheQuietBetweenModVariables.MapVariables.get(world).timer = 0;
					TheQuietBetweenModVariables.MapVariables.get(world).timerReset = false;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				}
				if (TheQuietBetweenModVariables.MapVariables.get(world).timer % 20 == 0) {
					for (int index0 = 0; index0 < 6; index0++) {
						entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(TheQuietBetweenModVariables.MapVariables.get(world).playerX, (entity.getY() + 1), TheQuietBetweenModVariables.MapVariables.get(world).playerZ));
						if (world instanceof ServerLevel _level)
							_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
									"/execute at @e[type=the_quiet_between:shadow_stalker,limit=1,sort=nearest] run fill ^ ^ ^ ^ ^1 ^1 air destroy");
						if (world instanceof ServerLevel _level)
							_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
									"/execute at @e[type=the_quiet_between:shadow_stalker,limit=1,sort=nearest] run fill ^ ^ ^ ^ ^1 ^3 air destroy");
						if (world instanceof ServerLevel _level)
							_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
									"/execute at @e[type=the_quiet_between:shadow_stalker,limit=1,sort=nearest] run fill ^ ^ ^ ^ ^6 ^1 air destroy");
					}
				}
				if (TheQuietBetweenModVariables.MapVariables.get(world).timer == 300) {
					if (!entity.level().isClientSide())
						entity.discard();
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null,
									BlockPos.containing(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ),
									BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, (float) 1.5, -5);
						} else {
							_level.playLocalSound(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ,
									BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, (float) 1.5, -5, false);
						}
					}
					TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = false;
					TheQuietBetweenModVariables.MapVariables.get(world).timerReset = true;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				}
				if (distancePlayerAndEntity <= 2) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null,
									BlockPos.containing(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ),
									BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, (float) 1.5, -5);
						} else {
							_level.playLocalSound(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ,
									BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, (float) 1.5, -5, false);
						}
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null,
									BlockPos.containing(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ),
									BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("entity.player.breath")), SoundSource.MASTER, (float) 1.5, 1);
						} else {
							_level.playLocalSound(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ,
									BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("entity.player.breath")), SoundSource.MASTER, (float) 1.5, 1, false);
						}
					}
					if (playerNear instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 1));
					if (playerNear instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2));
					if (playerNear instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 50, 3));
					if (!entity.level().isClientSide())
						entity.discard();
					TheQuietBetweenModVariables.MapVariables.get(world).shadowStalkerScripted = 0;
					TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = false;
					TheQuietBetweenModVariables.MapVariables.get(world).timerReset = true;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, (y + 1), z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								"/setblock ~ ~ ~ oak_sign{front_text:{messages:['{\"text\":\"\"}','{\"text\":\"running is\"}','{\"text\":\"futile\"}','{\"text\":\"\"}']}} replace");
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(
								new CommandSourceStack(CommandSource.NULL, new Vec3((x + Math.round(Math.random())), y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								"/setblock ~ ~ ~ oak_sign[rotation=1]{front_text:{messages:['{\"text\":\"\"}','{\"text\":\"why are you here\"}','{\"text\":\"\"}','{\"text\":\"\"}']}} replace");
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(
								new CommandSourceStack(CommandSource.NULL, new Vec3((x - Math.round(Math.random())), y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								"/setblock ~ ~ ~ oak_sign[rotation=14]{front_text:{messages:['{\"text\":\"\"}','{\"text\":\"leave\"}','{\"text\":\"\"}','{\"text\":\"\"}']}} replace");
					if (finalPlayerNear instanceof ServerPlayer sp1) {
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("Player.Caught").withColor(0xff0000).withStyle(ChatFormatting.BOLD), false);
						}
						sp1.connection.disconnect(Component.literal("Got You..").withStyle(ChatFormatting.RED));
						return;
					}
				}
			}
		}
		if (TheQuietBetweenModVariables.MapVariables.get(world).timer == 2000) {
			TheQuietBetweenModVariables.MapVariables.get(world).timer = 0;
			TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
		} else {
			TheQuietBetweenModVariables.MapVariables.get(world).timer = TheQuietBetweenModVariables.MapVariables.get(world).timer + 1;
			TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
		}
	}
}