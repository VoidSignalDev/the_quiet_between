package net.mcreator.thequietbetween.procedures;

import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.ChatFormatting;

import net.mcreator.thequietbetween.network.TheQuietBetweenModVariables;
import net.mcreator.thequietbetween.TheQuietBetweenMod;

import javax.annotation.Nullable;

import java.util.Set;

@EventBusSubscriber
public class ConversationsProcedure {
	@SubscribeEvent
	public static void onChat(ServerChatEvent event) {
		execute(event, event.getPlayer().level(), event.getPlayer().getX(), event.getPlayer().getY(), event.getPlayer().getZ(), event.getPlayer(), event.getRawText());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, String text) {
		execute(null, world, x, y, z, entity, text);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, String text) {
		if (entity == null || text == null)
			return;
		if (TheQuietBetweenModVariables.MapVariables.get(world).conversationsOpen == 1) {
			if (TheQuietBetweenModVariables.MapVariables.get(world).pauseChat == false) {
				if (((text).strip()).toLowerCase().contains("hello")) {
					TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = true;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					TheQuietBetweenMod.queueServerWork(60, () -> {
						if (TheQuietBetweenModVariables.MapVariables.get(world).hello == 0) {
							if (world instanceof ServerLevel _level) {
								_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> Hello"), false);
							}
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
								} else {
									_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
								}
							}
							TheQuietBetweenModVariables.MapVariables.get(world).hello = 1;
							TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
						} else {
							TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar = Mth.nextInt(RandomSource.create(), 1, 2);
							TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
							if (TheQuietBetweenModVariables.MapVariables.get(world).hello == 1) {
								if (TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar == 1) {
									if (world instanceof ServerLevel _level) {
										_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> Still here.."), false);
									}
									if (world instanceof Level _level) {
										if (!_level.isClientSide()) {
											_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
										} else {
											_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
										}
									}
								} else if (TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar == 2) {
									if (world instanceof ServerLevel _level) {
										_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<>"), false);
									}
									if (world instanceof Level _level) {
										if (!_level.isClientSide()) {
											_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
										} else {
											_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
										}
									}
								}
								TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar = 0;
								TheQuietBetweenModVariables.MapVariables.get(world).hello = TheQuietBetweenModVariables.MapVariables.get(world).hello + 1;
								TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
							} else if (TheQuietBetweenModVariables.MapVariables.get(world).hello == 2) {
								if (world instanceof ServerLevel _level) {
									_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> you already asked that.."), false);
								}
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
									} else {
										_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
									}
								}
								TheQuietBetweenModVariables.MapVariables.get(world).hello = TheQuietBetweenModVariables.MapVariables.get(world).hello + 1;
								TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
							} else if (TheQuietBetweenModVariables.MapVariables.get(world).hello == 3) {
								if (world instanceof ServerLevel _level) {
									_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> Stop that"), false);
								}
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
									} else {
										_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
									}
								}
								TheQuietBetweenModVariables.MapVariables.get(world).hello = TheQuietBetweenModVariables.MapVariables.get(world).hello + 1;
								TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
							} else if (TheQuietBetweenModVariables.MapVariables.get(world).hello == 4) {
								if (world instanceof ServerLevel _level) {
									_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> STOP IT").withColor(0xff0000), false);
								}
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
									} else {
										_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
									}
								}
								TheQuietBetweenModVariables.MapVariables.get(world).hello = TheQuietBetweenModVariables.MapVariables.get(world).hello + 1;
								TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
							} else if (TheQuietBetweenModVariables.MapVariables.get(world).hello == 5) {
								TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 4;
								TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
								ShadowstalkerspawnProcedure.execute(world, x, y, z, entity);
								TheQuietBetweenModVariables.MapVariables.get(world).hello = TheQuietBetweenModVariables.MapVariables.get(world).hello + 1;
								TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
							} else if (TheQuietBetweenModVariables.MapVariables.get(world).hello == 6) {
								if (world instanceof ServerLevel _level) {
									_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<>").withColor(0xff0000), false);
								}
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
									} else {
										_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
									}
								}
							}
						}
						TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = false;
						TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					});
				} else if (((text).strip()).toLowerCase().contains("friendly") || ((text).strip()).toLowerCase().contains("friend")) {
					TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = true;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					TheQuietBetweenMod.queueServerWork(60, () -> {
						if (TheQuietBetweenModVariables.MapVariables.get(world).friendly == 0) {
							TheQuietBetweenMod.queueServerWork(60, () -> {
								if (world instanceof ServerLevel _level) {
									_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(" err.friend"), false);
								}
								TheQuietBetweenMod.queueServerWork(10, () -> {
									if (world instanceof ServerLevel _level) {
										_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(" reinitializing."), false);
									}
									TheQuietBetweenMod.queueServerWork(10, () -> {
										if (world instanceof ServerLevel _level) {
											_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(" reinitializing.."), false);
										}
										TheQuietBetweenMod.queueServerWork(10, () -> {
											if (world instanceof ServerLevel _level) {
												_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(" reinitializing..."), false);
											}
											TheQuietBetweenMod.queueServerWork(40, () -> {
												if (world instanceof ServerLevel _level) {
													_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(" FATAL.ERR").withColor(0xcc0000).withStyle(ChatFormatting.BOLD), false);
												}
												if (world instanceof Level _level) {
													if (!_level.isClientSide()) {
														_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
													} else {
														_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
													}
												}
												TheQuietBetweenMod.queueServerWork(40, () -> {
													if (world instanceof ServerLevel _level) {
														_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(" MANUAL.OVERIDE.ACTIVATED").withStyle(ChatFormatting.BOLD), false);
													}
													TheQuietBetweenMod.queueServerWork(20, () -> {
														if (world instanceof ServerLevel _level) {
															_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(" MANUAL.SEARCH.INITIALIZED").withStyle(ChatFormatting.BOLD), false);
														}
														TheQuietBetweenMod.queueServerWork(40, () -> {
															if (world instanceof ServerLevel _level) {
																_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(" friend.exe not found").withStyle(ChatFormatting.BOLD), false);
															}
															TheQuietBetweenMod.queueServerWork(40, () -> {
																if (world instanceof ServerLevel _level) {
																	_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(" Recovery aborted by user").withStyle(ChatFormatting.BOLD), false);
																}
																TheQuietBetweenModVariables.MapVariables.get(world).friendly = 1;
																TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = false;
																TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
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
							TheQuietBetweenModVariables.MapVariables.get(world).friendly = Mth.nextInt(RandomSource.create(), 1, 2);
							TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
							if (TheQuietBetweenModVariables.MapVariables.get(world).friendly == 0) {
								if (world instanceof ServerLevel _level) {
									_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> Better not tell.."), false);
								}
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
									} else {
										_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
									}
								}
							} else {
								entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.GENERIC)), 2);
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
									} else {
										_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
									}
								}
								TheQuietBetweenMod.queueServerWork(60, () -> {
									if (world instanceof ServerLevel _level) {
										_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> ..."), false);
									}
								});
							}
							TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = false;
							TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
						}
					});
				} else if (((text).strip()).toLowerCase().contains("where are you") || ((text).strip()).toLowerCase().contains("are you close")) {
					TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = true;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					TheQuietBetweenMod.queueServerWork(60, () -> {
						if (TheQuietBetweenModVariables.MapVariables.get(world).rightBehindYou == 0) {
							if (world instanceof ServerLevel _level) {
								_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> Right behind you"), false);
							}
							TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 2;
							TheQuietBetweenModVariables.MapVariables.get(world).rightBehindYou = 1;
							TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
							ShadowstalkerspawnProcedure.execute(world, x, y, z, entity);
						} else {
							if (world instanceof ServerLevel _level) {
								_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> close.."), false);
							}
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
								} else {
									_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
								}
							}
						}
						TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = false;
						TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					});
				} else if (((text).strip()).toLowerCase().contains("who are you")) {
					TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = true;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					TheQuietBetweenMod.queueServerWork(60, () -> {
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<>"), false);
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
							}
						}
						TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = false;
						TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					});
				} else if (((text).strip()).toLowerCase().contains("can you see me")) {
					TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = true;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					TheQuietBetweenMod.queueServerWork(60, () -> {
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> you looked behind you for a reason"), false);
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
							}
						}
						TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = false;
						TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					});
				} else if (((text).strip()).toLowerCase().contains("show yourself")) {
					TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = true;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					TheQuietBetweenMod.queueServerWork(60, () -> {
						TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar = Mth.nextInt(RandomSource.create(), 1, 5);
						TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
						if (TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar == 1) {
							TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 4;
							TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
							ShadowstalkerspawnProcedure.execute(world, x, y, z, entity);
						} else if (TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar > 1 && TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar <= 3) {
							TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 3;
							TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
							ShadowstalkerspawnProcedure.execute(world, x, y, z, entity);
						} else if (TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar > 3 && TheQuietBetweenModVariables.MapVariables.get(world).randomizerVar <= 5) {
							if (world instanceof ServerLevel _level) {
								_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> Better hide.."), false);
							}
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
								} else {
									_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
								}
							}
							TheQuietBetweenMod.queueServerWork(160, () -> {
								if (world instanceof ServerLevel _level) {
									_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> 3.."), false);
								}
								TheQuietBetweenMod.queueServerWork(60, () -> {
									if (world instanceof ServerLevel _level) {
										_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> 2..").withColor(0xffff33), false);
									}
									TheQuietBetweenMod.queueServerWork(60, () -> {
										if (world instanceof ServerLevel _level) {
											_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> 1..").withColor(0xff9999), false);
										}
										TheQuietBetweenMod.queueServerWork(60, () -> {
											if (world instanceof ServerLevel _level) {
												_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> Go0d Luck..").withColor(0xcc0000), false);
											}
											if (world instanceof Level _level) {
												if (!_level.isClientSide()) {
													_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, (float) 0.1, -4);
												} else {
													_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, (float) 0.1, -4, false);
												}
											}
											TheQuietBetweenMod.queueServerWork(60, () -> {
												if (world instanceof Level _level) {
													if (!_level.isClientSide()) {
														_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -4);
													} else {
														_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -4, false);
													}
												}
												TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 3;
												TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
												ShadowstalkerspawnProcedure.execute(world, x, y, z, entity);
											});
										});
									});
								});
							});
						}
						TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = false;
						TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					});
				} else if (((text).strip()).toLowerCase().contains("what do you want")) {
					TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = true;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					TheQuietBetweenMod.queueServerWork(60, () -> {
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> you already gave it to me"), false);
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
							}
						}
						TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = false;
						TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					});
				} else if (((text).strip()).toLowerCase().contains("how old are you")) {
					TheQuietBetweenMod.queueServerWork(60, () -> {
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> lost count"), false);
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
							}
						}
						TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = false;
						TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					});
				} else if (((text).strip()).toLowerCase().contains("are you real")) {
					TheQuietBetweenMod.queueServerWork(60, () -> {
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> you wouldn\u2019t be listening if I wasn\u2019t"), false);
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
							}
						}
						TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = false;
						TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					});
				} else if (((text).strip()).toLowerCase().contains("hurt me")) {
					TheQuietBetweenMod.queueServerWork(60, () -> {
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> not yet"), false);
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
							}
						}
						TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = false;
						TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					});
				} else if (((text).strip()).toLowerCase().contains("trust you")) {
					TheQuietBetweenMod.queueServerWork(60, () -> {
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> you already do"), false);
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
							}
						}
						TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = false;
						TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					});
				} else if (((text).strip()).toLowerCase().contains("talk to me") || ((text).strip()).toLowerCase().contains("talking to me")) {
					TheQuietBetweenMod.queueServerWork(60, () -> {
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> you\u2019re the only one who answered"), false);
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
							}
						}
						TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = false;
						TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					});
				} else if (((text).strip()).toLowerCase().contains("part of the world")) {
					TheQuietBetweenMod.queueServerWork(60, () -> {
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<> the world is part of me"), false);
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
							}
						}
						TheQuietBetweenModVariables.MapVariables.get(world).pauseChat = false;
						TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					});
				}
			}
		}
		if (TheQuietBetweenModVariables.MapVariables.get(world).developerMod) {
			if ((text).strip().contains("r1")) {
				if (ShadowStalkerEntityProcedure.execute(world) != null) {
					if (!ShadowStalkerEntityProcedure.execute(world).level().isClientSide())
						ShadowStalkerEntityProcedure.execute(world).discard();
				} else if (ShadowStalkerFlyingEntityProcedure.execute(world) != null) {
					if (!ShadowStalkerFlyingEntityProcedure.execute(world).level().isClientSide())
						ShadowStalkerFlyingEntityProcedure.execute(world).discard();
				}
				TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 1;
				TheQuietBetweenModVariables.MapVariables.get(world).sprintOrGone = Mth.nextInt(RandomSource.create(), 0, 1);
				TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				ShadowstalkerspawnProcedure.execute(world, x, y, z, entity);
			} else if ((text).strip().contains("r2")) {
				if (ShadowStalkerEntityProcedure.execute(world) != null) {
					if (!ShadowStalkerEntityProcedure.execute(world).level().isClientSide())
						ShadowStalkerEntityProcedure.execute(world).discard();
				} else if (ShadowStalkerFlyingEntityProcedure.execute(world) != null) {
					if (!ShadowStalkerFlyingEntityProcedure.execute(world).level().isClientSide())
						ShadowStalkerFlyingEntityProcedure.execute(world).discard();
				}
				TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 2;
				TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				ShadowstalkerspawnProcedure.execute(world, x, y, z, entity);
			} else if ((text).strip().contains("r3")) {
				if (ShadowStalkerEntityProcedure.execute(world) != null) {
					if (!ShadowStalkerEntityProcedure.execute(world).level().isClientSide())
						ShadowStalkerEntityProcedure.execute(world).discard();
				} else if (ShadowStalkerFlyingEntityProcedure.execute(world) != null) {
					if (!ShadowStalkerFlyingEntityProcedure.execute(world).level().isClientSide())
						ShadowStalkerFlyingEntityProcedure.execute(world).discard();
				}
				TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 3;
				TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				ShadowstalkerspawnProcedure.execute(world, x, y, z, entity);
			} else if ((text).strip().contains("s1")) {
				if (ShadowStalkerEntityProcedure.execute(world) != null) {
					if (!ShadowStalkerEntityProcedure.execute(world).level().isClientSide())
						ShadowStalkerEntityProcedure.execute(world).discard();
				} else if (ShadowStalkerFlyingEntityProcedure.execute(world) != null) {
					if (!ShadowStalkerFlyingEntityProcedure.execute(world).level().isClientSide())
						ShadowStalkerFlyingEntityProcedure.execute(world).discard();
				}
				TheQuietBetweenModVariables.MapVariables.get(world).shadowStalkerScripted = 1;
				TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				ShadowstalkerspawnProcedure.execute(world, x, y, z, entity);
			} else if ((text).strip().contains("s2")) {
				if (ShadowStalkerEntityProcedure.execute(world) != null) {
					if (!ShadowStalkerEntityProcedure.execute(world).level().isClientSide())
						ShadowStalkerEntityProcedure.execute(world).discard();
				} else if (ShadowStalkerFlyingEntityProcedure.execute(world) != null) {
					if (!ShadowStalkerFlyingEntityProcedure.execute(world).level().isClientSide())
						ShadowStalkerFlyingEntityProcedure.execute(world).discard();
				}
				TheQuietBetweenModVariables.MapVariables.get(world).shadowStalkerScripted = 2;
				TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				ShadowstalkerspawnProcedure.execute(world, x, y, z, entity);
			} else if ((text).strip().contains("hall")) {
				if (entity instanceof ServerPlayer _player && _player.level() instanceof ServerLevel _serverLevel) {
					ResourceKey<Level> destinationType = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("the_quiet_between:the_hallways"));
					if (_player.level().dimension() == destinationType)
						return;
					ServerLevel nextLevel = _serverLevel.getServer().getLevel(destinationType);
					if (nextLevel != null) {
						_player.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0));
						_player.teleportTo(nextLevel, _player.getX(), _player.getY(), _player.getZ(), Set.of(), _player.getYRot(), _player.getXRot(), true);
						_player.connection.send(new ClientboundPlayerAbilitiesPacket(_player.getAbilities()));
						for (MobEffectInstance _effectinstance : _player.getActiveEffects())
							_player.connection.send(new ClientboundUpdateMobEffectPacket(_player.getId(), _effectinstance, false));
						_player.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
					}
				}
			} else if ((text).strip().contains("void")) {
				if (entity instanceof ServerPlayer _player && _player.level() instanceof ServerLevel _serverLevel) {
					ResourceKey<Level> destinationType = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("the_quiet_between:white_void"));
					if (_player.level().dimension() == destinationType)
						return;
					ServerLevel nextLevel = _serverLevel.getServer().getLevel(destinationType);
					if (nextLevel != null) {
						_player.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0));
						_player.teleportTo(nextLevel, _player.getX(), _player.getY(), _player.getZ(), Set.of(), _player.getYRot(), _player.getXRot(), true);
						_player.connection.send(new ClientboundPlayerAbilitiesPacket(_player.getAbilities()));
						for (MobEffectInstance _effectinstance : _player.getActiveEffects())
							_player.connection.send(new ClientboundUpdateMobEffectPacket(_player.getId(), _effectinstance, false));
						_player.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
					}
				}
			} else if ((text).strip().contains("flat")) {
				if (entity instanceof ServerPlayer _player && _player.level() instanceof ServerLevel _serverLevel) {
					ResourceKey<Level> destinationType = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("the_quiet_between:flat_grass"));
					if (_player.level().dimension() == destinationType)
						return;
					ServerLevel nextLevel = _serverLevel.getServer().getLevel(destinationType);
					if (nextLevel != null) {
						_player.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0));
						_player.teleportTo(nextLevel, _player.getX(), _player.getY(), _player.getZ(), Set.of(), _player.getYRot(), _player.getXRot(), true);
						_player.connection.send(new ClientboundPlayerAbilitiesPacket(_player.getAbilities()));
						for (MobEffectInstance _effectinstance : _player.getActiveEffects())
							_player.connection.send(new ClientboundUpdateMobEffectPacket(_player.getId(), _effectinstance, false));
						_player.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
					}
				}
			} else if ((text).strip().contains("fss")) {
				TheQuietBetweenModVariables.MapVariables.get(world).forceSpawnSS = true;
				TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
			} else if ((text).strip().contains("gui")) {
				TheQuietBetweenModVariables.MapVariables.get(world).forceGui = true;
				TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
			}
		}
		if ((text).strip().contains("dev")) {
			if (TheQuietBetweenModVariables.MapVariables.get(world).developerMod == false) {
				TheQuietBetweenModVariables.MapVariables.get(world).developerMod = true;
				TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				if (world instanceof ServerLevel _level) {
					_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("Developer Options Enabled").withColor(0x33ff00), false);
				}
			} else {
				TheQuietBetweenModVariables.MapVariables.get(world).developerMod = false;
				TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				if (world instanceof ServerLevel _level) {
					_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("Developer Options Disabled").withColor(0xff3300), false);
				}
			}
		}
	}
}