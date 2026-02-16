package net.mcreator.thequietbetween.procedures;

import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
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

import javax.annotation.Nullable;

@EventBusSubscriber
public class RandomEventProcedure {
	@SubscribeEvent
	public static void onWorldTick(LevelTickEvent.Post event) {
		execute(event, event.getLevel());
	}

	public static void execute(LevelAccessor world) {
		execute(null, world);
	}

	private static void execute(@Nullable Event event, LevelAccessor world) {
		double eventRandomizer = 0;
		double deleteChunkConfirm = 0;
		double yLevel = 0;
		double xAxis = 0;
		double zAxis = 0;
		double startX = 0;
		double startZ = 0;
		double R = 0;
		Entity playerNear = null;
		if (TheQuietBetweenModVariables.MapVariables.get(world).joinEvent == 1) {
			if (Mth.nextInt(RandomSource.create(), 1, 200000) == 1) {
				eventRandomizer = Mth.nextInt(RandomSource.create(), 1, 5);
				playerNear = PlayerEntityProcedure.execute(world);
				final Entity finalPlayerNear = playerNear;
				if (eventRandomizer == 1) {
					if (world instanceof ServerLevel _level) {
						LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level, EntitySpawnReason.TRIGGERED);
						entityToSpawn.moveTo(Vec3.atBottomCenterOf(
								BlockPos.containing(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ)));;
						_level.addFreshEntity(entityToSpawn);
					}
				} else if (eventRandomizer == 2) {
					PlayerEntityProcedure.execute(world).lookAt(EntityAnchorArgument.Anchor.EYES,
							new Vec3((TheQuietBetweenModVariables.MapVariables.get(world).playerX + 5), TheQuietBetweenModVariables.MapVariables.get(world).playerY, (TheQuietBetweenModVariables.MapVariables.get(world).playerZ + 5)));
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands()
								.performPrefixedCommand(
										new CommandSourceStack(CommandSource.NULL,
												new Vec3((TheQuietBetweenModVariables.MapVariables.get(world).playerX + 5), TheQuietBetweenModVariables.MapVariables.get(world).playerY,
														(TheQuietBetweenModVariables.MapVariables.get(world).playerZ + 5)),
												Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
										"/setblock ~ ~ ~ oak_sign{front_text:{messages:['{\"text\":\"\"}','{\"text\":\"192.168.1.1\"}','{\"text\":\"\"}','{\"text\":\"\"}']}} replace");
				} else if (eventRandomizer == 3) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null,
									BlockPos.containing(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ),
									BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("the_quiet_between:door_knock")), SoundSource.MASTER, 255, 1);
						} else {
							_level.playLocalSound(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ,
									BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("the_quiet_between:door_knock")), SoundSource.MASTER, 255, 1, false);
						}
					}
				} else if (eventRandomizer == 4) {
					R = Math.random();
					if (0.5 > R) {
						R = 1;
					} else {
						R = -1;
					}
					xAxis = TheQuietBetweenModVariables.MapVariables.get(world).playerX + Mth.nextInt(RandomSource.create(), 30, 40) * R;
					zAxis = TheQuietBetweenModVariables.MapVariables.get(world).playerZ + Mth.nextInt(RandomSource.create(), 30, 40) * R;
					yLevel = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) xAxis, (int) zAxis);
					startX = xAxis;
					startZ = zAxis;
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(xAxis, yLevel, zAxis), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 255, 1);
						} else {
							_level.playLocalSound(xAxis, yLevel, zAxis, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 255, 1, false);
						}
					}
					while (yLevel > -65) {
						for (int index1 = 0; index1 < 16; index1++) {
							zAxis = zAxis + 1;
							for (int index2 = 0; index2 < 16; index2++) {
								xAxis = xAxis + 1;
								world.setBlock(BlockPos.containing(xAxis, yLevel, zAxis), Blocks.AIR.defaultBlockState(), 3);
							}
							xAxis = startX;
						}
						zAxis = startZ;
						yLevel = yLevel - 1;
					}
				} else if (eventRandomizer == 5) {
					if (TheQuietBetweenModVariables.MapVariables.get(world).V0idJoined == 0) {
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> the world noticed you"), false);
						}
						TheQuietBetweenMod.queueServerWork(120, () -> {
							if (world instanceof ServerLevel _level) {
								_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("despair joined the game").withColor(0xffff00), false);
							}
							TheQuietBetweenMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("loneliness joined the game").withColor(0xffff00), false);
								}
								TheQuietBetweenMod.queueServerWork(20, () -> {
									if (world instanceof ServerLevel _level) {
										_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("hopelessness joined the game").withColor(0xffff00), false);
									}
									TheQuietBetweenMod.queueServerWork(10, () -> {
										if (world instanceof ServerLevel _level) {
											_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("emptiness joined the game").withColor(0xffff00), false);
										}
										TheQuietBetweenMod.queueServerWork(10, () -> {
											if (world instanceof ServerLevel _level) {
												_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("dread joined the game").withColor(0xffff00), false);
											}
											TheQuietBetweenMod.queueServerWork(5, () -> {
												if (world instanceof ServerLevel _level) {
													_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("grief joined the game").withColor(0xffff00), false);
												}
												TheQuietBetweenMod.queueServerWork(5, () -> {
													if (world instanceof ServerLevel _level) {
														_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("sorrow joined the game").withColor(0xffff00), false);
													}
													TheQuietBetweenMod.queueServerWork(5, () -> {
														if (world instanceof ServerLevel _level) {
															_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("misery joined the game").withColor(0xffff00), false);
														}
														TheQuietBetweenMod.queueServerWork(5, () -> {
															if (world instanceof ServerLevel _level) {
																_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("anguish joined the game").withColor(0xffff00), false);
															}
															TheQuietBetweenMod.queueServerWork(3, () -> {
																if (world instanceof ServerLevel _level) {
																	_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("desolation joined the game").withColor(0xffff00), false);
																}
																TheQuietBetweenMod.queueServerWork(3, () -> {
																	if (world instanceof ServerLevel _level) {
																		_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("apathy joined the game").withColor(0xffff00), false);
																	}
																	TheQuietBetweenMod.queueServerWork(3, () -> {
																		if (world instanceof ServerLevel _level) {
																			_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("silence joined the game").withColor(0xffff00), false);
																		}
																		TheQuietBetweenMod.queueServerWork(3, () -> {
																			if (world instanceof ServerLevel _level) {
																				_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("withering joined the game").withColor(0xffff00), false);
																			}
																			TheQuietBetweenMod.queueServerWork(3, () -> {
																				if (world instanceof ServerLevel _level) {
																					_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("oblivion joined the game").withColor(0xffff00), false);
																				}
																				TheQuietBetweenMod.queueServerWork(3, () -> {
																					if (world instanceof ServerLevel _level) {
																						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("exile joined the game").withColor(0xffff00), false);
																					}
																					TheQuietBetweenMod.queueServerWork(1, () -> {
																						if (world instanceof ServerLevel _level) {
																							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("numbness joined the game").withColor(0xffff00), false);
																						}
																						TheQuietBetweenMod.queueServerWork(1, () -> {
																							if (world instanceof ServerLevel _level) {
																								_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("stillness joined the game").withColor(0xffff00), false);
																							}
																							TheQuietBetweenMod.queueServerWork(1, () -> {
																								if (world instanceof ServerLevel _level) {
																									_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("stillness joined the game").withColor(0xffff00), false);
																								}
																								TheQuietBetweenMod.queueServerWork(1, () -> {
																									if (world instanceof ServerLevel _level) {
																										_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("silence joined the game").withColor(0xffff00), false);
																									}
																									TheQuietBetweenMod.queueServerWork(1, () -> {
																										if (world instanceof ServerLevel _level) {
																											_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("absence joined the game").withColor(0xffff00), false);
																										}
																										TheQuietBetweenMod.queueServerWork(1, () -> {
																											if (world instanceof ServerLevel _level) {
																												_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("hollowness joined the game").withColor(0xffff00), false);
																											}
																											TheQuietBetweenMod.queueServerWork(1, () -> {
																												if (world instanceof ServerLevel _level) {
																													_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("vacancy joined the game").withColor(0xffff00), false);
																												}
																												TheQuietBetweenMod.queueServerWork(1, () -> {
																													if (world instanceof ServerLevel _level) {
																														_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("detachment joined the game").withColor(0xffff00),
																																false);
																													}
																													TheQuietBetweenMod.queueServerWork(1, () -> {
																														if (world instanceof ServerLevel _level) {
																															_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("isolation joined the game").withColor(0xffff00),
																																	false);
																														}
																														TheQuietBetweenMod.queueServerWork(1, () -> {
																															if (world instanceof ServerLevel _level) {
																																_level.getServer().getPlayerList()
																																		.broadcastSystemMessage(Component.literal("futility joined the game").withColor(0xffff00), false);
																															}
																															TheQuietBetweenMod.queueServerWork(1, () -> {
																																if (world instanceof ServerLevel _level) {
																																	_level.getServer().getPlayerList()
																																			.broadcastSystemMessage(Component.literal("erosion joined the game").withColor(0xffff00), false);
																																}
																																TheQuietBetweenMod.queueServerWork(1, () -> {
																																	if (world instanceof ServerLevel _level) {
																																		_level.getServer().getPlayerList()
																																				.broadcastSystemMessage(Component.literal("decay joined the game").withColor(0xffff00), false);
																																	}
																																	TheQuietBetweenMod.queueServerWork(1, () -> {
																																		if (world instanceof ServerLevel _level) {
																																			_level.getServer().getPlayerList()
																																					.broadcastSystemMessage(Component.literal("entropy joined the game").withColor(0xffff00), false);
																																		}
																																		TheQuietBetweenMod.queueServerWork(1, () -> {
																																			if (world instanceof ServerLevel _level) {
																																				_level.getServer().getPlayerList()
																																						.broadcastSystemMessage(Component.literal("collapse joined the game").withColor(0xffff00), false);
																																			}
																																			TheQuietBetweenMod.queueServerWork(1, () -> {
																																				if (world instanceof ServerLevel _level) {
																																					_level.getServer().getPlayerList().broadcastSystemMessage(
																																							Component.literal("forgotten joined the game").withColor(0xffff00), false);
																																				}
																																				TheQuietBetweenMod.queueServerWork(1, () -> {
																																					if (world instanceof ServerLevel _level) {
																																						_level.getServer().getPlayerList().broadcastSystemMessage(
																																								Component.literal("lost joined the game").withColor(0xffff00), false);
																																					}
																																					TheQuietBetweenMod.queueServerWork(1, () -> {
																																						if (world instanceof ServerLevel _level) {
																																							_level.getServer().getPlayerList().broadcastSystemMessage(
																																									Component.literal("displaced joined the game").withColor(0xffff00), false);
																																						}
																																						TheQuietBetweenMod.queueServerWork(1, () -> {
																																							if (world instanceof ServerLevel _level) {
																																								_level.getServer().getPlayerList().broadcastSystemMessage(
																																										Component.literal("hollow joined the game").withColor(0xffff00), false);
																																							}
																																							TheQuietBetweenMod.queueServerWork(1, () -> {
																																								if (world instanceof ServerLevel _level) {
																																									_level.getServer().getPlayerList().broadcastSystemMessage(
																																											Component.literal("empty joined the game").withColor(0xffff00), false);
																																								}
																																								TheQuietBetweenMod.queueServerWork(40, () -> {
																																									if (world instanceof Level _level) {
																																										if (!_level.isClientSide()) {
																																											_level.playSound(null,
																																													BlockPos.containing(
																																															TheQuietBetweenModVariables.MapVariables.get(world).playerX,
																																															TheQuietBetweenModVariables.MapVariables.get(world).playerY,
																																															TheQuietBetweenModVariables.MapVariables.get(world).playerZ),
																																													BuiltInRegistries.SOUND_EVENT
																																															.getValue(ResourceLocation.parse("ambient.cave")),
																																													SoundSource.MASTER, 255, (float) 1.2);
																																										} else {
																																											_level.playLocalSound(
																																													TheQuietBetweenModVariables.MapVariables.get(world).playerX,
																																													TheQuietBetweenModVariables.MapVariables.get(world).playerY,
																																													TheQuietBetweenModVariables.MapVariables.get(world).playerZ,
																																													BuiltInRegistries.SOUND_EVENT
																																															.getValue(ResourceLocation.parse("ambient.cave")),
																																													SoundSource.MASTER, 255, (float) 1.2, false);
																																										}
																																									}
																																									if (world instanceof ServerLevel _level) {
																																										_level.getServer().getPlayerList().broadcastSystemMessage(
																																												Component.literal("v0id joined the game").withColor(0xff0000), false);
																																									}
																																									TheQuietBetweenModVariables.MapVariables.get(world).V0idJoined = 1;
																																									TheQuietBetweenModVariables.MapVariables.get(world).disableGreeting = true;
																																									TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
																																									TheQuietBetweenMod.queueServerWork(120, () -> {
																																										if (world instanceof Level _level) {
																																											if (!_level.isClientSide()) {
																																												_level.playSound(null, BlockPos.containing(
																																														TheQuietBetweenModVariables.MapVariables.get(world).playerX,
																																														TheQuietBetweenModVariables.MapVariables.get(world).playerY,
																																														TheQuietBetweenModVariables.MapVariables.get(world).playerZ),
																																														BuiltInRegistries.SOUND_EVENT
																																																.getValue(ResourceLocation.parse("ambient.cave")),
																																														SoundSource.MASTER, 255, (float) 1.2);
																																											} else {
																																												_level.playLocalSound(
																																														TheQuietBetweenModVariables.MapVariables.get(world).playerX,
																																														TheQuietBetweenModVariables.MapVariables.get(world).playerY,
																																														TheQuietBetweenModVariables.MapVariables.get(world).playerZ,
																																														BuiltInRegistries.SOUND_EVENT
																																																.getValue(ResourceLocation.parse("ambient.cave")),
																																														SoundSource.MASTER, 255, (float) 1.2, false);
																																											}
																																										}
																																										if (world instanceof ServerLevel _level) {
																																											_level.getServer().getPlayerList().broadcastSystemMessage(Component
																																													.literal("<v0id> you are not like the others..").withColor(0xff0000),
																																													false);
																																										}
																																										TheQuietBetweenMod.queueServerWork(40, () -> {
																																											if (world instanceof ServerLevel _level) {
																																												_level.getServer().getPlayerList().broadcastSystemMessage(Component
																																														.literal(" whispers to you: LEAVE NOW").withColor(0xcccccc),
																																														false);
																																											}
																																											if (PlayerEntityProcedure.execute(world) instanceof LivingEntity _entity
																																													&& !_entity.level().isClientSide())
																																												_entity.addEffect(
																																														new MobEffectInstance(MobEffects.DARKNESS, 99999999, 1));
																																											TheQuietBetweenMod.queueServerWork(20, () -> {
																																												if (PlayerEntityProcedure.execute(world) instanceof LivingEntity _entity
																																														&& !_entity.level().isClientSide())
																																													_entity.addEffect(
																																															new MobEffectInstance(MobEffects.WITHER, 99999999, 3));
																																												if (PlayerEntityProcedure.execute(world) instanceof LivingEntity _entity
																																														&& !_entity.level().isClientSide())
																																													_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
																																															99999999, 3));
																																												if (PlayerEntityProcedure.execute(world) instanceof LivingEntity _entity
																																														&& !_entity.level().isClientSide())
																																													_entity.addEffect(
																																															new MobEffectInstance(MobEffects.BLINDNESS, 99999999, 3));
																																												if (world instanceof Level _level) {
																																													if (!_level.isClientSide()) {
																																														_level.playSound(null,
																																																BlockPos.containing(
																																																		TheQuietBetweenModVariables.MapVariables
																																																				.get(world).playerX,
																																																		TheQuietBetweenModVariables.MapVariables
																																																				.get(world).playerY,
																																																		TheQuietBetweenModVariables.MapVariables
																																																				.get(world).playerZ),
																																																BuiltInRegistries.SOUND_EVENT
																																																		.getValue(ResourceLocation.parse("ambient.cave")),
																																																SoundSource.MASTER, 255, (float) 1.2);
																																													} else {
																																														_level.playLocalSound(
																																																TheQuietBetweenModVariables.MapVariables
																																																		.get(world).playerX,
																																																TheQuietBetweenModVariables.MapVariables
																																																		.get(world).playerY,
																																																TheQuietBetweenModVariables.MapVariables
																																																		.get(world).playerZ,
																																																BuiltInRegistries.SOUND_EVENT
																																																		.getValue(ResourceLocation.parse("ambient.cave")),
																																																SoundSource.MASTER, 255, (float) 1.2, false);
																																													}
																																												}
																																												TheQuietBetweenMod.queueServerWork(120, () -> {
																																													if (finalPlayerNear instanceof ServerPlayer sp1) {
																																														if (world instanceof ServerLevel _level) {
																																															_level.getServer().getPlayerList().broadcastSystemMessage(
																																																	Component.literal("Player.Annoying")
																																																			.withColor(0xff0000)
																																																			.withStyle(ChatFormatting.BOLD),
																																																	false);
																																														}
																																														sp1.connection.disconnect(Component.literal("I said get out"));
																																														return;
																																													}
																																												});
																																											});
																																										});
																																									});
																																								});
																																							});
																																						});
																																					});
																																				});
																																			});
																																		});
																																	});
																																});
																															});
																														});
																													});
																												});
																											});
																										});
																									});
																								});
																							});
																						});
																					});
																				});
																			});
																		});
																	});
																});
															});
														});
													});
												});
											});
										});
									});
								});
							});
						});
					} else {
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> v0id remains.."), false);
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null,
										BlockPos.containing(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ),
										BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 255, 1);
							} else {
								_level.playLocalSound(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ,
										BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 255, 1, false);
							}
						}
					}
				} else if (eventRandomizer == 6) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null,
									BlockPos.containing(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ),
									BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.wooden_door.open")), SoundSource.MASTER, 255, 1);
						} else {
							_level.playLocalSound(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ,
									BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.wooden_door.open")), SoundSource.MASTER, 255, 1, false);
						}
					}
				}
			}
		}
	}
}