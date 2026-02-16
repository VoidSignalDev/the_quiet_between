package net.mcreator.thequietbetween.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.thequietbetween.network.TheQuietBetweenModVariables;

import java.util.Set;

public class WeepingAngelTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity playerNear = null;
		Vec3 playerLookAngle = Vec3.ZERO;
		Vec3 playerToEntity = Vec3.ZERO;
		Vec3 normalizedVector = Vec3.ZERO;
		double playerDot = 0;
		double playerEntityDistance = 0;
		double randomizer = 0;
		playerNear = PlayerEntityProcedure.execute(world);
		if (playerNear != null) {
			playerToEntity = new Vec3((entity.getX() - TheQuietBetweenModVariables.MapVariables.get(world).playerX), (entity.getY() - TheQuietBetweenModVariables.MapVariables.get(world).playerY),
					(entity.getZ() - TheQuietBetweenModVariables.MapVariables.get(world).playerZ));
			playerLookAngle = playerNear.getLookAngle();
			normalizedVector = playerToEntity.normalize();
			playerDot = 0;
			playerDot = playerLookAngle.x() * normalizedVector.x() + playerLookAngle.y() * normalizedVector.y() + playerLookAngle.z() * normalizedVector.z();
			playerEntityDistance = playerToEntity.length();
			if (playerDot < 0.25) {
				entity.lookAt(EntityAnchorArgument.Anchor.EYES,
						new Vec3(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ));
				if (entity instanceof Mob _entity)
					_entity.getNavigation().moveTo(TheQuietBetweenModVariables.MapVariables.get(world).playerX, TheQuietBetweenModVariables.MapVariables.get(world).playerY, TheQuietBetweenModVariables.MapVariables.get(world).playerZ, 1.75);
			} else {
				if (entity instanceof Mob _entity)
					_entity.getNavigation().stop();
			}
			if (playerEntityDistance <= 4) {
				if (!entity.level().isClientSide())
					entity.discard();
				randomizer = Mth.nextInt(RandomSource.create(), 1, 4);
				if (randomizer == 1) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("the_quiet_between:door_knock")), SoundSource.MASTER, 255, (float) 0.2);
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("the_quiet_between:door_knock")), SoundSource.MASTER, 255, (float) 0.2, false);
						}
					}
				} else if (randomizer == 2) {
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
				} else if (randomizer == 3) {
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
				} else if (randomizer == 4) {
					entity.push(1, 1, 1);
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 20, 1, false, true));
					entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.GENERIC)), (float) 2.5);
				}
			}
		}
	}
}