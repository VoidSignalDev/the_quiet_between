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
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
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

public class ShadowStalkerOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity playerNear = null;
		Vec3 PlayerLookAngle = Vec3.ZERO;
		Vec3 normalizedVector = Vec3.ZERO;
		Vec3 playerToEntity = Vec3.ZERO;
		Vec3 entityLookVector = Vec3.ZERO;
		double timePassed = 0;
		double loop = 0;
		double dot = 0;
		double distancePlayerAndEntity = 0;
		double onGroundY = 0;
		double randomizer = 0;
		boolean repeating = false;
		playerNear = PlayerEntityProcedure.execute(world);
		final Entity finalPlayerNear = playerNear;
		if (playerNear != null) {
			TheQuietBetweenModVariables.MapVariables.get(world).loop = 1;
			TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
			playerToEntity = new Vec3((entity.getX() - TheQuietBetweenModVariables.MapVariables.get(world).playerX), (entity.getY() - TheQuietBetweenModVariables.MapVariables.get(world).playerY),
					(entity.getZ() - TheQuietBetweenModVariables.MapVariables.get(world).playerZ));
			PlayerLookAngle = playerNear.getLookAngle();
			normalizedVector = playerToEntity.normalize();
			dot = 0;
			dot = PlayerLookAngle.x() * normalizedVector.x() + PlayerLookAngle.y() * normalizedVector.y() + PlayerLookAngle.z() * normalizedVector.z();
			distancePlayerAndEntity = playerToEntity.length();
			if (TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior == 1) {
				entity.lookAt(EntityAnchorArgument.Anchor.EYES,
						new Vec3(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ));
				if (TheQuietBetweenModVariables.MapVariables.get(world).trigger == false) {
					TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerShy = Mth.nextInt(RandomSource.create(), 0, 1);
					TheQuietBetweenModVariables.MapVariables.get(world).trigger = true;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				}
				if (TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerShy == 1) {
					if (dot > 0.9 && distancePlayerAndEntity <= 20) {
						if (!entity.level().isClientSide())
							entity.discard();
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null,
										BlockPos.containing(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ),
										BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, (float) 1.5, (float) 0.1);
							} else {
								_level.playLocalSound(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ,
										BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, (float) 1.5, (float) 0.1, false);
							}
						}
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> dont look.."), false);
						}
						TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 0;
						TheQuietBetweenModVariables.MapVariables.get(world).trigger = false;
						TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					}
				}
				if ((new Vec3((entity.getX()), (entity.getY()), (entity.getZ())))
						.distanceTo((new Vec3(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ))) <= 10) {
					if (TheQuietBetweenModVariables.MapVariables.get(world).sprintOrGone == 1) {
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
						if (playerNear instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 1));
						if (playerNear instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
						if (!entity.level().isClientSide())
							entity.discard();
						TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 0;
						TheQuietBetweenModVariables.MapVariables.get(world).trigger = false;
						TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					} else {
						if (entity instanceof Mob _entity)
							_entity.getNavigation().moveTo(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ, 4);
						entity.lookAt(EntityAnchorArgument.Anchor.EYES,
								new Vec3(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ));
						if ((new Vec3((entity.getX()), (entity.getY()), (entity.getZ())))
								.distanceTo((new Vec3(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ))) <= 2) {
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
								_entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 1));
							if (playerNear instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
							if (playerNear instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 1, 3));
							playerNear.push(1, 1, 1);
							if (!entity.level().isClientSide())
								entity.discard();
							TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 0;
							TheQuietBetweenModVariables.MapVariables.get(world).trigger = false;
							TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
						}
					}
				}
			} else if (TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior == 2) {
				entity.lookAt(EntityAnchorArgument.Anchor.EYES,
						new Vec3(TheQuietBetweenModVariables.MapVariables.get(world).playerX, (TheQuietBetweenModVariables.MapVariables.get(world).playerY + 1), TheQuietBetweenModVariables.MapVariables.get(world).playerZ));
				if (TheQuietBetweenModVariables.MapVariables.get(world).shadowStalkerTeleported == 0) {
					TheQuietBetweenModVariables.MapVariables.get(world).shadowStalkerTeleported = 1;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								"/execute as @p at @s run tp @e[type=the_quiet_between:shadow_stalker,limit=1,sort=nearest] ^ ^1 ^-4");
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								"/execute at @e[type=the_quiet_between:shadow_stalker,limit=1,sort=nearest] run fill ^ ^1 ^1 ^ ^2 ^5 air destroy");
					world.destroyBlock(BlockPos.containing(entity.getX() + entity.getLookAngle().x, entity.getY() + 0, entity.getZ() + entity.getLookAngle().z), false);
					world.destroyBlock(BlockPos.containing(entity.getX() + entity.getLookAngle().x, entity.getY() + 1, entity.getZ() + entity.getLookAngle().z), false);
					TheQuietBetweenModVariables.MapVariables.get(world).playerYaw = Math.toRadians(playerNear.getYRot());
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				}
				if (dot <= 0.2) {
					if (entity instanceof Mob _entity)
						_entity.getNavigation().moveTo((TheQuietBetweenModVariables.MapVariables.get(world).playerX + Math.sin(TheQuietBetweenModVariables.MapVariables.get(world).playerYaw) * 5),
								(TheQuietBetweenModVariables.MapVariables.get(world).playerY + 1), (TheQuietBetweenModVariables.MapVariables.get(world).playerZ - Math.cos(TheQuietBetweenModVariables.MapVariables.get(world).playerYaw) * 5), 2);
				} else if (dot > 0.4) {
					if (!entity.level().isClientSide())
						entity.discard();
					if (playerNear instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
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
					TheQuietBetweenModVariables.MapVariables.get(world).shadowStalkerTeleported = 0;
					TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 0;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				}
			} else if (TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior == 3) {
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
				if ((new Vec3((entity.getX()), (entity.getY()), (entity.getZ())))
						.distanceTo((new Vec3(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ))) <= 2) {
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
					TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 0;
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
			} else if (TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior == 4) {
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
					TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 0;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					if (finalPlayerNear instanceof ServerPlayer sp2) {
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("Player.Annoying").withColor(0xff0000).withStyle(ChatFormatting.BOLD), false);
						}
						sp2.connection.disconnect(Component.literal("SHUTUPSTOPITSHUTUPSTOPITSHUTUPSTOPITSHUTUPSTOPITSHUTUPSTOPITSHUTUPSTOPITSHUTUPSTOPITSHUTUPSTOPITSHUTUPSTOPIT").withStyle(ChatFormatting.RED));
						return;
					}
				});
			} else if (TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior == 5) {
				entity.lookAt(EntityAnchorArgument.Anchor.EYES,
						new Vec3(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ));
				if (playerNear instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 40, 1));
				randomizer = Mth.nextInt(RandomSource.create(), 1, 5);
				if (randomizer == 1) {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								"/tellraw @a [{\"text\":\"fdfghjkghrhs\",\"obfuscated\":true,\"color\":\"red\"}]");
				} else if (randomizer == 2) {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								"/tellraw @a [{\"text\":\"fdfghjkghrhdawdawdsas\",\"obfuscated\":true,\"color\":\"red\"}]");
				} else if (randomizer == 3) {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								"/tellraw @a [{\"text\":\"fdfghjkgadahrhs\",\"obfuscated\":true,\"color\":\"red\"}]");
				} else if (randomizer == 4) {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								"/tellraw @a [{\"text\":\"fdfghjkghrhadawdaws\",\"obfuscated\":true,\"color\":\"red\"}]");
				} else if (randomizer == 5) {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								"/tellraw @a [{\"text\":\"fdfghhrhs\",\"obfuscated\":true,\"color\":\"red\"}]");
				}
				if (!(playerNear instanceof LivingEntity _livEnt103 && _livEnt103.isSleeping())) {
					if (!entity.level().isClientSide())
						entity.discard();
					TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 0;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> WAKE UP").withColor(0xff0000).withStyle(ChatFormatting.BOLD), false);
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