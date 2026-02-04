package net.mcreator.thequietbetween.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.thequietbetween.procedures.CloseGuiProcedure;
import net.mcreator.thequietbetween.TheQuietBetweenMod;

@EventBusSubscriber
public record Question3ButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	public static final Type<Question3ButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(TheQuietBetweenMod.MODID, "question_3_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, Question3ButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, Question3ButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new Question3ButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public Type<Question3ButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final Question3ButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			CloseGuiProcedure.execute(entity);
		}
		if (buttonID == 1) {

			CloseGuiProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		TheQuietBetweenMod.addNetworkMessage(Question3ButtonMessage.TYPE, Question3ButtonMessage.STREAM_CODEC, Question3ButtonMessage::handleData);
	}
}