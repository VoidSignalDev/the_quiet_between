package net.mcreator.thequietbetween.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.thequietbetween.world.inventory.EyeGuiMenu;
import net.mcreator.thequietbetween.init.TheQuietBetweenModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class EyeGuiScreen extends AbstractContainerScreen<EyeGuiMenu> implements TheQuietBetweenModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;

	public EyeGuiScreen(EyeGuiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("the_quiet_between:textures/screens/eye_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(RenderType::guiTextured, texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 29, this.topPos + 20, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 77, this.topPos + 20, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + -15, this.topPos + 33, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + -7, this.topPos + -6, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 18, this.topPos + 63, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + -27, this.topPos + 77, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 14, this.topPos + 107, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 54, this.topPos + 84, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 66, this.topPos + 51, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 184, this.topPos + 38, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 84, this.topPos + -19, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 82, this.topPos + -44, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 34, this.topPos + -21, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + -96, this.topPos + 42, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + -47, this.topPos + -6, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + -55, this.topPos + 40, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + -69, this.topPos + 81, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + -34, this.topPos + 119, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + -80, this.topPos + 124, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 56, this.topPos + 124, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 15, this.topPos + 148, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 98, this.topPos + 94, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 111, this.topPos + 49, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 121, this.topPos + 4, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 174, this.topPos + 105, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 130, this.topPos + -37, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 153, this.topPos + 32, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 166, this.topPos + 2, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 145, this.topPos + 73, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 138, this.topPos + 114, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 101, this.topPos + 131, 0, 0, 50, 50, 50, 50);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/eye.png"), this.leftPos + 135, this.topPos + 135, 0, 0, 50, 50, 50, 50);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
	}

	@Override
	public void init() {
		super.init();
	}
}