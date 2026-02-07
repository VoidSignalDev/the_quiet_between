package net.mcreator.thequietbetween.network;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;

import net.mcreator.thequietbetween.TheQuietBetweenMod;

@EventBusSubscriber
public class TheQuietBetweenModVariables {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, TheQuietBetweenMod.MODID);

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		TheQuietBetweenMod.addNetworkMessage(SavedDataSyncMessage.TYPE, SavedDataSyncMessage.STREAM_CODEC, SavedDataSyncMessage::handleData);
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			SavedData mapdata = MapVariables.get(event.getEntity().level());
			SavedData worlddata = WorldVariables.get(event.getEntity().level());
			if (mapdata != null)
				PacketDistributor.sendToPlayer(player, new SavedDataSyncMessage(0, mapdata));
			if (worlddata != null)
				PacketDistributor.sendToPlayer(player, new SavedDataSyncMessage(1, worlddata));
		}
	}

	@SubscribeEvent
	public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			SavedData worlddata = WorldVariables.get(event.getEntity().level());
			if (worlddata != null)
				PacketDistributor.sendToPlayer(player, new SavedDataSyncMessage(1, worlddata));
		}
	}

	@SubscribeEvent
	public static void onWorldTick(LevelTickEvent.Post event) {
		if (event.getLevel() instanceof ServerLevel level) {
			WorldVariables worldVariables = WorldVariables.get(level);
			if (worldVariables._syncDirty) {
				PacketDistributor.sendToPlayersInDimension(level, new SavedDataSyncMessage(1, worldVariables));
				worldVariables._syncDirty = false;
			}
			MapVariables mapVariables = MapVariables.get(level);
			if (mapVariables._syncDirty) {
				PacketDistributor.sendToAllPlayers(new SavedDataSyncMessage(0, mapVariables));
				mapVariables._syncDirty = false;
			}
		}
	}

	public static class WorldVariables extends SavedData {
		public static final String DATA_NAME = "the_quiet_between_worldvars";
		boolean _syncDirty = false;

		public static WorldVariables load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
			WorldVariables data = new WorldVariables();
			data.read(tag, lookupProvider);
			return data;
		}

		public void read(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
		}

		@Override
		public CompoundTag save(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			return nbt;
		}

		public void markSyncDirty() {
			this.setDirty();
			this._syncDirty = true;
		}

		static WorldVariables clientSide = new WorldVariables();

		public static WorldVariables get(LevelAccessor world) {
			if (world instanceof ServerLevel level) {
				return level.getDataStorage().computeIfAbsent(new SavedData.Factory<>(WorldVariables::new, WorldVariables::load), DATA_NAME);
			} else {
				return clientSide;
			}
		}
	}

	public static class MapVariables extends SavedData {
		public static final String DATA_NAME = "the_quiet_between_mapvars";
		boolean _syncDirty = false;
		public double ShadowStalkerBehavior = 0;
		public double joinEvent = 0.0;
		public double conversationsOpen = 0;
		public double hello = 0;
		public double timer = 0.0;
		public double shadowStalkerTeleported = 0;
		public double playerYaw = 0;
		public double sprintOrGone = 0;
		public double randomizerVar = 0;
		public boolean trigger = true;
		public double playerX = 0;
		public double playerY = 0;
		public double playerZ = 0;
		public boolean developerMod = false;
		public double ShadowStalkerSpawn = 0;
		public double loop = 0;
		public boolean pauseChat = false;
		public boolean timerReset = false;
		public double friendly = 0;
		public double rightBehindYou = 0;
		public double showYourself = 0;
		public double ShadowStalkerShy = 0.0;
		public double ShadowStalkerDespawn = 0;
		public boolean devEventTrigger = false;
		public double theQuietOneRevealCountdown = 30000.0;
		public boolean forceSpawnSS = false;
		public double doorX = 0;
		public double doorY = 0;
		public double doorZ = 0;
		public double guiQuestionsCounter = 0;
		public double time = 0;
		public double shadowStalkerScripted = 0;

		public static MapVariables load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
			MapVariables data = new MapVariables();
			data.read(tag, lookupProvider);
			return data;
		}

		public void read(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			ShadowStalkerBehavior = nbt.getDouble("ShadowStalkerBehavior");
			joinEvent = nbt.getDouble("joinEvent");
			conversationsOpen = nbt.getDouble("conversationsOpen");
			hello = nbt.getDouble("hello");
			timer = nbt.getDouble("timer");
			shadowStalkerTeleported = nbt.getDouble("shadowStalkerTeleported");
			playerYaw = nbt.getDouble("playerYaw");
			sprintOrGone = nbt.getDouble("sprintOrGone");
			randomizerVar = nbt.getDouble("randomizerVar");
			trigger = nbt.getBoolean("trigger");
			playerX = nbt.getDouble("playerX");
			playerY = nbt.getDouble("playerY");
			playerZ = nbt.getDouble("playerZ");
			developerMod = nbt.getBoolean("developerMod");
			ShadowStalkerSpawn = nbt.getDouble("ShadowStalkerSpawn");
			loop = nbt.getDouble("loop");
			pauseChat = nbt.getBoolean("pauseChat");
			timerReset = nbt.getBoolean("timerReset");
			friendly = nbt.getDouble("friendly");
			rightBehindYou = nbt.getDouble("rightBehindYou");
			showYourself = nbt.getDouble("showYourself");
			ShadowStalkerShy = nbt.getDouble("ShadowStalkerShy");
			ShadowStalkerDespawn = nbt.getDouble("ShadowStalkerDespawn");
			devEventTrigger = nbt.getBoolean("devEventTrigger");
			theQuietOneRevealCountdown = nbt.getDouble("theQuietOneRevealCountdown");
			forceSpawnSS = nbt.getBoolean("forceSpawnSS");
			doorX = nbt.getDouble("doorX");
			doorY = nbt.getDouble("doorY");
			doorZ = nbt.getDouble("doorZ");
			guiQuestionsCounter = nbt.getDouble("guiQuestionsCounter");
			time = nbt.getDouble("time");
			shadowStalkerScripted = nbt.getDouble("shadowStalkerScripted");
		}

		@Override
		public CompoundTag save(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			nbt.putDouble("ShadowStalkerBehavior", ShadowStalkerBehavior);
			nbt.putDouble("joinEvent", joinEvent);
			nbt.putDouble("conversationsOpen", conversationsOpen);
			nbt.putDouble("hello", hello);
			nbt.putDouble("timer", timer);
			nbt.putDouble("shadowStalkerTeleported", shadowStalkerTeleported);
			nbt.putDouble("playerYaw", playerYaw);
			nbt.putDouble("sprintOrGone", sprintOrGone);
			nbt.putDouble("randomizerVar", randomizerVar);
			nbt.putBoolean("trigger", trigger);
			nbt.putDouble("playerX", playerX);
			nbt.putDouble("playerY", playerY);
			nbt.putDouble("playerZ", playerZ);
			nbt.putBoolean("developerMod", developerMod);
			nbt.putDouble("ShadowStalkerSpawn", ShadowStalkerSpawn);
			nbt.putDouble("loop", loop);
			nbt.putBoolean("pauseChat", pauseChat);
			nbt.putBoolean("timerReset", timerReset);
			nbt.putDouble("friendly", friendly);
			nbt.putDouble("rightBehindYou", rightBehindYou);
			nbt.putDouble("showYourself", showYourself);
			nbt.putDouble("ShadowStalkerShy", ShadowStalkerShy);
			nbt.putDouble("ShadowStalkerDespawn", ShadowStalkerDespawn);
			nbt.putBoolean("devEventTrigger", devEventTrigger);
			nbt.putDouble("theQuietOneRevealCountdown", theQuietOneRevealCountdown);
			nbt.putBoolean("forceSpawnSS", forceSpawnSS);
			nbt.putDouble("doorX", doorX);
			nbt.putDouble("doorY", doorY);
			nbt.putDouble("doorZ", doorZ);
			nbt.putDouble("guiQuestionsCounter", guiQuestionsCounter);
			nbt.putDouble("time", time);
			nbt.putDouble("shadowStalkerScripted", shadowStalkerScripted);
			return nbt;
		}

		public void markSyncDirty() {
			this.setDirty();
			_syncDirty = true;
		}

		static MapVariables clientSide = new MapVariables();

		public static MapVariables get(LevelAccessor world) {
			if (world instanceof ServerLevelAccessor serverLevelAcc) {
				return serverLevelAcc.getLevel().getServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(new SavedData.Factory<>(MapVariables::new, MapVariables::load), DATA_NAME);
			} else {
				return clientSide;
			}
		}
	}

	public record SavedDataSyncMessage(int dataType, SavedData data) implements CustomPacketPayload {
		public static final Type<SavedDataSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(TheQuietBetweenMod.MODID, "saved_data_sync"));
		public static final StreamCodec<RegistryFriendlyByteBuf, SavedDataSyncMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SavedDataSyncMessage message) -> {
			buffer.writeInt(message.dataType);
			if (message.data != null)
				buffer.writeNbt(message.data.save(new CompoundTag(), buffer.registryAccess()));
		}, (RegistryFriendlyByteBuf buffer) -> {
			int dataType = buffer.readInt();
			CompoundTag nbt = buffer.readNbt();
			SavedData data = null;
			if (nbt != null) {
				data = dataType == 0 ? new MapVariables() : new WorldVariables();
				if (data instanceof MapVariables mapVariables)
					mapVariables.read(nbt, buffer.registryAccess());
				else if (data instanceof WorldVariables worldVariables)
					worldVariables.read(nbt, buffer.registryAccess());
			}
			return new SavedDataSyncMessage(dataType, data);
		});

		@Override
		public Type<SavedDataSyncMessage> type() {
			return TYPE;
		}

		public static void handleData(final SavedDataSyncMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.enqueueWork(() -> {
					if (message.dataType == 0)
						MapVariables.clientSide.read(message.data.save(new CompoundTag(), context.player().registryAccess()), context.player().registryAccess());
					else
						WorldVariables.clientSide.read(message.data.save(new CompoundTag(), context.player().registryAccess()), context.player().registryAccess());
				}).exceptionally(e -> {
					context.connection().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}
	}
}