package net.mcreator.thequietbetween.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import net.mcreator.thequietbetween.world.inventory.Question2Menu;
import net.mcreator.thequietbetween.network.Question2ButtonMessage;
import net.mcreator.thequietbetween.init.TheQuietBetweenModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class Question2Screen extends AbstractContainerScreen<Question2Menu> implements TheQuietBetweenModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private EditBox input;
	private Button button_enter;

	public Question2Screen(Question2Menu container, Inventory inventory, Component text) {
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
		if (elementType == 0 && elementState instanceof String stringState) {
			if (name.equals("input"))
				input.setValue(stringState);
		}
		menuStateUpdateActive = false;
	}

	@Override
	public boolean isPauseScreen() {
		return true;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("the_quiet_between:textures/screens/question_2.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		input.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (input.isFocused())
			return input.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String inputValue = input.getValue();
		super.resize(minecraft, width, height);
		input.setValue(inputValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.the_quiet_between.question_2.label_why_are_you_here"), 6, 34, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		input = new EditBox(this.font, this.leftPos + 79, this.topPos + 53, 118, 18, Component.translatable("gui.the_quiet_between.question_2.input"));
		input.setMaxLength(8192);
		input.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "input", content, false);
		});
		this.addWidget(this.input);
		button_enter = Button.builder(Component.translatable("gui.the_quiet_between.question_2.button_enter"), e -> {
			int x = Question2Screen.this.x;
			int y = Question2Screen.this.y;
			if (true) {
				PacketDistributor.sendToServer(new Question2ButtonMessage(0, x, y, z));
				Question2ButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 96, this.topPos + 124, 51, 20).build();
		this.addRenderableWidget(button_enter);
	}
}