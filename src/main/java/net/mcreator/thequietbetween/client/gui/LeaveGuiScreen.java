package net.mcreator.thequietbetween.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.thequietbetween.world.inventory.LeaveGuiMenu;
import net.mcreator.thequietbetween.network.LeaveGuiButtonMessage;
import net.mcreator.thequietbetween.init.TheQuietBetweenModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class LeaveGuiScreen extends AbstractContainerScreen<LeaveGuiMenu> implements TheQuietBetweenModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private Button button_leave;

	public LeaveGuiScreen(LeaveGuiMenu container, Inventory inventory, Component text) {
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

	private static final ResourceLocation texture = ResourceLocation.parse("the_quiet_between:textures/screens/leave_gui.png");

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
		guiGraphics.drawString(this.font, Component.translatable("gui.the_quiet_between.leave_gui.label_leave"), 28, 114, -16777216, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.the_quiet_between.leave_gui.label_leave1"), 118, 91, -13434880, false);
	}

	@Override
	public void init() {
		super.init();
		button_leave = new PlainTextButton(this.leftPos + 100, this.topPos + 38, 51, 20, Component.translatable("gui.the_quiet_between.leave_gui.button_leave"), e -> {
			int x = LeaveGuiScreen.this.x;
			int y = LeaveGuiScreen.this.y;
			if (true) {
				PacketDistributor.sendToServer(new LeaveGuiButtonMessage(0, x, y, z));
				LeaveGuiButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}, this.font);
		this.addRenderableWidget(button_leave);
	}
}