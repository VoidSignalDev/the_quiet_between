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

import net.mcreator.thequietbetween.world.inventory.Question3Menu;
import net.mcreator.thequietbetween.network.Question3ButtonMessage;
import net.mcreator.thequietbetween.init.TheQuietBetweenModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class Question3Screen extends AbstractContainerScreen<Question3Menu> implements TheQuietBetweenModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private Button button_yes;
	private Button button_no;

	public Question3Screen(Question3Menu container, Inventory inventory, Component text) {
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

	@Override
	public boolean isPauseScreen() {
		return true;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("the_quiet_between:textures/screens/question_3.png");

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
		guiGraphics.drawString(this.font, Component.translatable("gui.the_quiet_between.question_3.label_are_you_alone"), 51, 52, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_yes = new PlainTextButton(this.leftPos + -102, this.topPos + 151, 40, 20, Component.translatable("gui.the_quiet_between.question_3.button_yes"), e -> {
			int x = Question3Screen.this.x;
			int y = Question3Screen.this.y;
			if (true) {
				PacketDistributor.sendToServer(new Question3ButtonMessage(0, x, y, z));
				Question3ButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}, this.font);
		this.addRenderableWidget(button_yes);
		button_no = Button.builder(Component.translatable("gui.the_quiet_between.question_3.button_no"), e -> {
			int x = Question3Screen.this.x;
			int y = Question3Screen.this.y;
			if (true) {
				PacketDistributor.sendToServer(new Question3ButtonMessage(1, x, y, z));
				Question3ButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 69, this.topPos + 88, 35, 20).build();
		this.addRenderableWidget(button_no);
	}
}