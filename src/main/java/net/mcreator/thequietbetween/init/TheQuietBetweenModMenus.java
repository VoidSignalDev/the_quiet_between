/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.thequietbetween.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import net.mcreator.thequietbetween.world.inventory.*;
import net.mcreator.thequietbetween.network.MenuStateUpdateMessage;
import net.mcreator.thequietbetween.TheQuietBetweenMod;

import java.util.Map;

public class TheQuietBetweenModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, TheQuietBetweenMod.MODID);
	public static final DeferredHolder<MenuType<?>, MenuType<Question1Menu>> QUESTION_1 = REGISTRY.register("question_1", () -> IMenuTypeExtension.create(Question1Menu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<Question2Menu>> QUESTION_2 = REGISTRY.register("question_2", () -> IMenuTypeExtension.create(Question2Menu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<Question3Menu>> QUESTION_3 = REGISTRY.register("question_3", () -> IMenuTypeExtension.create(Question3Menu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<LeaveGuiMenu>> LEAVE_GUI = REGISTRY.register("leave_gui", () -> IMenuTypeExtension.create(LeaveGuiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<EyeGuiMenu>> EYE_GUI = REGISTRY.register("eye_gui", () -> IMenuTypeExtension.create(EyeGuiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<TvStaticMenu>> TV_STATIC = REGISTRY.register("tv_static", () -> IMenuTypeExtension.create(TvStaticMenu::new));

	public interface MenuAccessor {
		Map<String, Object> getMenuState();

		Map<Integer, Slot> getSlots();

		default void sendMenuStateUpdate(Player player, int elementType, String name, Object elementState, boolean needClientUpdate) {
			getMenuState().put(elementType + ":" + name, elementState);
			if (player instanceof ServerPlayer serverPlayer) {
				PacketDistributor.sendToPlayer(serverPlayer, new MenuStateUpdateMessage(elementType, name, elementState));
			} else if (player.level().isClientSide) {
				if (Minecraft.getInstance().screen instanceof TheQuietBetweenModScreens.ScreenAccessor accessor && needClientUpdate)
					accessor.updateMenuState(elementType, name, elementState);
				PacketDistributor.sendToServer(new MenuStateUpdateMessage(elementType, name, elementState));
			}
		}

		default <T> T getMenuState(int elementType, String name, T defaultValue) {
			try {
				return (T) getMenuState().getOrDefault(elementType + ":" + name, defaultValue);
			} catch (ClassCastException e) {
				return defaultValue;
			}
		}
	}
}