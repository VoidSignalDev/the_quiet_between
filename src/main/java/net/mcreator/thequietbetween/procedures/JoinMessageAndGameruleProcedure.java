package net.mcreator.thequietbetween.procedures;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.thequietbetween.network.TheQuietBetweenModVariables;
import net.mcreator.thequietbetween.TheQuietBetweenMod;

import javax.annotation.Nullable;

@EventBusSubscriber
public class JoinMessageAndGameruleProcedure {
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
		if (world instanceof ServerLevel _level) {
			_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal((entity.getDisplayName().getString() + " joined the game")).withColor(0xffff00), false);
		}
		if (TheQuietBetweenModVariables.MapVariables.get(world).disableGreeting == false && TheQuietBetweenModVariables.MapVariables.get(world).joinEvent == 1) {
			TheQuietBetweenMod.queueServerWork(100, () -> {
				RandomGreetingProcedure.execute(world);
			});
		}
		TheQuietBetweenModVariables.MapVariables.get(world).disableGreeting = false;
		TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
		if (world instanceof ServerLevel _level)
			_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
					"/gamerule commandBlockOutput false");
		if (TheQuietBetweenModVariables.MapVariables.get(world).V0idJoined == 1) {
			TheQuietBetweenModVariables.MapVariables.get(world).V0idJoined = 2;
			TheQuietBetweenModVariables.MapVariables.get(world).markSyncDirty();
			if (entity instanceof LivingEntity _entity)
				_entity.removeAllEffects();
			RejoinAfterV0idJoinProcedure.execute(world);
		}
	}
}