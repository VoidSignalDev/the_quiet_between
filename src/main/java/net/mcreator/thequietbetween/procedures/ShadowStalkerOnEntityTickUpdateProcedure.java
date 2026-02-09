package net.mcreator.thequietbetween.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.thequietbetween.world.inventory.TvStaticMenu;
import net.mcreator.thequietbetween.network.TheQuietBetweenModVariables;
import net.mcreator.thequietbetween.TheQuietBetweenMod;

import io.netty.buffer.Unpooled;

public class ShadowStalkerOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity playerNear = null;
		Vec3 PlayerLookAngle = Vec3.ZERO;
		Vec3 normalizedVector = Vec3.ZERO;
		Vec3 playerToEntity = Vec3.ZERO;
		Vec3 entityLookVector = Vec3.ZERO;
		double dot = 0;
		double distancePlayerAndEntity = 0;
		playerNear = PlayerEntityProcedure.execute(world);
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
						TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 0;
						TheQuietBetweenModVariables.MapVariables.get(world).trigger = false;
						TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					}
				}
				if ((new Vec3((entity.getX()), (entity.getY()), (entity.getZ())))
						.distanceTo((new Vec3(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ))) <= 10) {
					if (TheQuietBetweenModVariables.MapVariables.get(world).sprintOrGone == 1) {
						if (PlayerEntityProcedure.execute(world) instanceof ServerPlayer _ent) {
							BlockPos _bpos = BlockPos.containing(x, y, z);
							_ent.openMenu(new MenuProvider() {
								@Override
								public Component getDisplayName() {
									return Component.literal("TvStatic");
								}

								@Override
								public boolean shouldTriggerClientSideContainerClosingOnOpen() {
									return false;
								}

								@Override
								public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
									return new TvStaticMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
								}
							}, _bpos);
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null,
										BlockPos.containing(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ),
										BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("the_quiet_between:tv_static")), SoundSource.MASTER, 1, 1);
							} else {
								_level.playLocalSound(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ,
										BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("the_quiet_between:tv_static")), SoundSource.MASTER, 1, 1, false);
							}
						}
						TheQuietBetweenMod.queueServerWork(10, () -> {
							if (world instanceof ServerLevel _level)
								_level.getServer().getCommands()
										.performPrefixedCommand(new CommandSourceStack(CommandSource.NULL,
												new Vec3(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ),
												Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(), "/stopsound @a master the_quiet_between:tv_static");
							if (PlayerEntityProcedure.execute(world) instanceof Player _player)
								_player.closeContainer();
						});
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
								_entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 1, false, false));
							if (playerNear instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2, false, false));
							entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.GENERIC)), 1);
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
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2, false, false));
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
				entity.lookAt(EntityAnchorArgument.Anchor.EYES,
						new Vec3(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ));
				if (distancePlayerAndEntity <= 10) {
					TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 0;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					if (!entity.level().isClientSide())
						entity.discard();
					if (PlayerEntityProcedure.execute(world) instanceof ServerPlayer _ent) {
						BlockPos _bpos = BlockPos.containing(x, y, z);
						_ent.openMenu(new MenuProvider() {
							@Override
							public Component getDisplayName() {
								return Component.literal("TvStatic");
							}

							@Override
							public boolean shouldTriggerClientSideContainerClosingOnOpen() {
								return false;
							}

							@Override
							public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
								return new TvStaticMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null,
									BlockPos.containing(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ),
									BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("the_quiet_between:tv_static")), SoundSource.MASTER, 1, 1);
						} else {
							_level.playLocalSound(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ,
									BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("the_quiet_between:tv_static")), SoundSource.MASTER, 1, 1, false);
						}
					}
					TheQuietBetweenMod.queueServerWork(10, () -> {
						if (world instanceof ServerLevel _level)
							_level.getServer().getCommands()
									.performPrefixedCommand(new CommandSourceStack(CommandSource.NULL,
											new Vec3(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ), Vec2.ZERO,
											_level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(), "/stopsound @a master the_quiet_between:tv_static");
						if (PlayerEntityProcedure.execute(world) instanceof Player _player)
							_player.closeContainer();
					});
				}
				TheQuietBetweenMod.queueServerWork(4800, () -> {
					if (!entity.level().isClientSide())
						entity.discard();
					TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 0;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				});
			}
		}
		if (TheQuietBetweenModVariables.MapVariables.get(world).timer == 2000) {
			TheQuietBetweenModVariables.MapVariables.get(world).timer = 0;
			TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
		} else {
			TheQuietBetweenModVariables.MapVariables.get(world).timer = TheQuietBetweenModVariables.MapVariables.get(world).timer + 1;
			TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
		}
		if (TheQuietBetweenModVariables.MapVariables.get(world).despawnShadowStalkerTrigger == true) {
			TheQuietBetweenModVariables.MapVariables.get(world).despawnShadowStalkerTrigger = false;
			TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
			DespawnShadowStalkersProcedure.execute(world);
		}
	}
}