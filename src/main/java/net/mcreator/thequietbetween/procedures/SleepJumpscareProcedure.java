package net.mcreator.thequietbetween.procedures;

import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.thequietbetween.network.TheQuietBetweenModVariables;
import net.mcreator.thequietbetween.TheQuietBetweenMod;

import javax.annotation.Nullable;

import java.util.Set;

@EventBusSubscriber
public class SleepJumpscareProcedure {
	@SubscribeEvent
	public static void onPlayerInBed(CanPlayerSleepEvent event) {
		execute(event, event.getEntity().level(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Direction bedDirection = Direction.NORTH;
		boolean doorFound = false;
		double randomizer = 0;
		double X = 0;
		double Z = 0;
		BlockState isDoor = Blocks.AIR.defaultBlockState();
		if (!(world instanceof Level _lvl0 && _lvl0.isDay())) {
			randomizer = Mth.nextInt(RandomSource.create(), 1, 38);
			if (randomizer == 1) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("the_quiet_between:door_knock")), SoundSource.MASTER, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("the_quiet_between:door_knock")), SoundSource.MASTER, 1, 1, false);
					}
				}
			} else if (randomizer == 2) {
				TheQuietBetweenModVariables.MapVariables.get(world).doorX = x - 7;
				TheQuietBetweenModVariables.MapVariables.get(world).doorY = y;
				TheQuietBetweenModVariables.MapVariables.get(world).doorZ = z - 7;
				TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				for (int index0 = 0; index0 < 12; index0++) {
					TheQuietBetweenModVariables.MapVariables.get(world).doorX = TheQuietBetweenModVariables.MapVariables.get(world).doorX + 1;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
					for (int index1 = 0; index1 < 12; index1++) {
						TheQuietBetweenModVariables.MapVariables.get(world).doorZ = TheQuietBetweenModVariables.MapVariables.get(world).doorZ + 1;
						TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
						isDoor = (world
								.getBlockState(BlockPos.containing(TheQuietBetweenModVariables.MapVariables.get(world).doorX, TheQuietBetweenModVariables.MapVariables.get(world).doorY, TheQuietBetweenModVariables.MapVariables.get(world).doorZ)));
						if (isDoor.getBlock() == Blocks.OAK_DOOR || isDoor.getBlock() == Blocks.SPRUCE_DOOR || isDoor.getBlock() == Blocks.BIRCH_DOOR || isDoor.getBlock() == Blocks.JUNGLE_DOOR || isDoor.getBlock() == Blocks.ACACIA_DOOR
								|| isDoor.getBlock() == Blocks.DARK_OAK_DOOR || isDoor.getBlock() == Blocks.PALE_OAK_DOOR || isDoor.getBlock() == Blocks.CRIMSON_DOOR || isDoor.getBlock() == Blocks.WARPED_DOOR
								|| isDoor.getBlock() == Blocks.MANGROVE_DOOR || isDoor.getBlock() == Blocks.CHERRY_DOOR || isDoor.getBlock() == Blocks.BAMBOO_DOOR || isDoor.getBlock() == Blocks.IRON_DOOR || isDoor.getBlock() == Blocks.COPPER_DOOR
								|| isDoor.getBlock() == Blocks.EXPOSED_COPPER_DOOR || isDoor.getBlock() == Blocks.OXIDIZED_COPPER_DOOR || isDoor.getBlock() == Blocks.WEATHERED_COPPER_DOOR || isDoor.getBlock() == Blocks.WAXED_COPPER_DOOR
								|| isDoor.getBlock() == Blocks.WAXED_EXPOSED_COPPER_DOOR || isDoor.getBlock() == Blocks.WAXED_OXIDIZED_COPPER_DOOR || isDoor.getBlock() == Blocks.WAXED_WEATHERED_COPPER_DOOR) {
							doorFound = true;
							TheQuietBetweenMod.queueServerWork(40, () -> {
								{
									BlockPos _pos = BlockPos.containing(TheQuietBetweenModVariables.MapVariables.get(world).doorX, TheQuietBetweenModVariables.MapVariables.get(world).doorY, TheQuietBetweenModVariables.MapVariables.get(world).doorZ);
									Block.dropResources(world.getBlockState(_pos), world,
											BlockPos.containing(TheQuietBetweenModVariables.MapVariables.get(world).doorX, TheQuietBetweenModVariables.MapVariables.get(world).doorY, TheQuietBetweenModVariables.MapVariables.get(world).doorZ), null);
									world.destroyBlock(_pos, false);
								}
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null,
												BlockPos.containing(TheQuietBetweenModVariables.MapVariables.get(world).doorX, TheQuietBetweenModVariables.MapVariables.get(world).doorY, TheQuietBetweenModVariables.MapVariables.get(world).doorZ),
												BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("entity.zombie.attack_wooden_door")), SoundSource.MASTER, 1, 1);
									} else {
										_level.playLocalSound(TheQuietBetweenModVariables.MapVariables.get(world).doorX, TheQuietBetweenModVariables.MapVariables.get(world).doorY, TheQuietBetweenModVariables.MapVariables.get(world).doorZ,
												BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("entity.zombie.attack_wooden_door")), SoundSource.MASTER, 1, 1, false);
									}
								}
							});
							break;
						}
					}
					if (doorFound == true) {
						break;
					}
					TheQuietBetweenModVariables.MapVariables.get(world).doorZ = z - 7;
					TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
				}
			} else if (randomizer == 3) {
				TheQuietBetweenMod.queueServerWork(40, () -> {
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
				});
			}
		}
	}
}