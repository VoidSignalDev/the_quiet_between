package net.mcreator.thequietbetween.procedures;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementHolder;

import net.mcreator.thequietbetween.network.TheQuietBetweenModVariables;
import net.mcreator.thequietbetween.TheQuietBetweenMod;

import javax.annotation.Nullable;

@EventBusSubscriber
public class JoineventProcedure {
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		TheQuietBetweenMod.queueServerWork(1, () -> {
			if (TheQuietBetweenModVariables.MapVariables.get(world).joinEvent == 0) {
				TheQuietBetweenModVariables.MapVariables.get(world).joinEvent = 1;
				TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ambient.cave")), SoundSource.MASTER, 1, -1, false);
					}
				}
				if (world instanceof ServerLevel _level) {
					_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(" joined the game").withColor(0xffff00), false);
				}
				TheQuietBetweenModVariables.MapVariables.get(world).ShadowStalkerBehavior = 1;
				TheQuietBetweenModVariables.MapVariables.get(world).sprintOrGone = 1;
				TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				ShadowstalkerspawnProcedure.execute(world, x, y, z, entity);
				TheQuietBetweenModVariables.MapVariables.get(world).conversationsOpen = 1;
				TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				TheQuietBetweenMod.queueServerWork(80, () -> {
					if (entity instanceof ServerPlayer _player && _player.level() instanceof ServerLevel _level) {
						AdvancementHolder _adv = _level.getServer().getAdvancements().get(ResourceLocation.parse("the_quiet_between:who_are_you"));
						if (_adv != null) {
							AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
							if (!_ap.isDone()) {
								for (String criteria : _ap.getRemainingCriteria())
									_player.getAdvancements().award(_adv, criteria);
							}
						}
					}
					TheQuietBetweenMod.queueServerWork(5, () -> {
						if (entity instanceof ServerPlayer _player && _player.level() instanceof ServerLevel _level) {
							AdvancementHolder _adv = _level.getServer().getAdvancements().get(ResourceLocation.parse("the_quiet_between:how_did_you_get_here"));
							if (_adv != null) {
								AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
								if (!_ap.isDone()) {
									for (String criteria : _ap.getRemainingCriteria())
										_player.getAdvancements().award(_adv, criteria);
								}
							}
						}
						TheQuietBetweenMod.queueServerWork(5, () -> {
							if (entity instanceof ServerPlayer _player && _player.level() instanceof ServerLevel _level) {
								AdvancementHolder _adv = _level.getServer().getAdvancements().get(ResourceLocation.parse("the_quiet_between:what_are_you_doing_here"));
								if (_adv != null) {
									AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
									if (!_ap.isDone()) {
										for (String criteria : _ap.getRemainingCriteria())
											_player.getAdvancements().award(_adv, criteria);
									}
								}
							}
							TheQuietBetweenMod.queueServerWork(20, () -> {
								if (entity instanceof ServerPlayer _player && _player.level() instanceof ServerLevel _level) {
									AdvancementHolder _adv = _level.getServer().getAdvancements().get(ResourceLocation.parse("the_quiet_between:who_are_you"));
									if (_adv != null) {
										AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
										if (_ap.isDone()) {
											for (String criteria : _ap.getCompletedCriteria())
												_player.getAdvancements().revoke(_adv, criteria);
										}
									}
								}
								if (entity instanceof ServerPlayer _player && _player.level() instanceof ServerLevel _level) {
									AdvancementHolder _adv = _level.getServer().getAdvancements().get(ResourceLocation.parse("the_quiet_between:what_are_you_doing_here"));
									if (_adv != null) {
										AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
										if (_ap.isDone()) {
											for (String criteria : _ap.getCompletedCriteria())
												_player.getAdvancements().revoke(_adv, criteria);
										}
									}
								}
								if (entity instanceof ServerPlayer _player && _player.level() instanceof ServerLevel _level) {
									AdvancementHolder _adv = _level.getServer().getAdvancements().get(ResourceLocation.parse("the_quiet_between:how_did_you_get_here"));
									if (_adv != null) {
										AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
										if (_ap.isDone()) {
											for (String criteria : _ap.getCompletedCriteria())
												_player.getAdvancements().revoke(_adv, criteria);
										}
									}
								}
							});
						});
					});
				});
			}
		});
	}
}