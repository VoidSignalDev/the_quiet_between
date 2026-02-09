/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.thequietbetween.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.thequietbetween.client.gui.*;

@EventBusSubscriber(Dist.CLIENT)
public class TheQuietBetweenModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(TheQuietBetweenModMenus.QUESTION_1.get(), Question1Screen::new);
		event.register(TheQuietBetweenModMenus.QUESTION_2.get(), Question2Screen::new);
		event.register(TheQuietBetweenModMenus.QUESTION_3.get(), Question3Screen::new);
		event.register(TheQuietBetweenModMenus.LEAVE_GUI.get(), LeaveGuiScreen::new);
		event.register(TheQuietBetweenModMenus.EYE_GUI.get(), EyeGuiScreen::new);
		event.register(TheQuietBetweenModMenus.TV_STATIC.get(), TvStaticScreen::new);
		event.register(TheQuietBetweenModMenus.INTRO_JUMPSCARE_GUI.get(), IntroJumpscareGuiScreen::new);
		event.register(TheQuietBetweenModMenus.BLACK_SCREEN_GUI.get(), BlackScreenGuiScreen::new);
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}