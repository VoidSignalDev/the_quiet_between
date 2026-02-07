package net.mcreator.thequietbetween.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.thequietbetween.world.inventory.TvStaticMenu;
import net.mcreator.thequietbetween.init.TheQuietBetweenModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class TvStaticScreen extends AbstractContainerScreen<TvStaticMenu> implements TheQuietBetweenModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private ImageButton imagebutton_tvstatic;
	private ImageButton imagebutton_tvstatic1;
	private ImageButton imagebutton_tvstatic2;
	private ImageButton imagebutton_tvstatic3;
	private ImageButton imagebutton_tvstatic4;
	private ImageButton imagebutton_tvstatic5;

	public TvStaticScreen(TvStaticMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 450;
		this.imageHeight = 250;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

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
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/tvstatic.png"), this.leftPos + -45, this.topPos + -33, 0, 0, 600, 337, 600, 337);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/tvstatic.png"), this.leftPos + -571, this.topPos + -313, 0, 0, 600, 337, 600, 337);
		guiGraphics.blit(RenderType::guiTextured, ResourceLocation.parse("the_quiet_between:textures/screens/tvstatic.png"), this.leftPos + 1, this.topPos + -325, 0, 0, 600, 337, 600, 337);
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
		imagebutton_tvstatic = new ImageButton(this.leftPos + 436, this.topPos + -325, 600, 337,
				new WidgetSprites(ResourceLocation.parse("the_quiet_between:textures/screens/tvstatic.png"), ResourceLocation.parse("the_quiet_between:textures/screens/tvstatic.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderType::guiTextured, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_tvstatic);
		imagebutton_tvstatic1 = new ImageButton(this.leftPos + 436, this.topPos + -10, 600, 337,
				new WidgetSprites(ResourceLocation.parse("the_quiet_between:textures/screens/tvstatic.png"), ResourceLocation.parse("the_quiet_between:textures/screens/tvstatic.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderType::guiTextured, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_tvstatic1);
		imagebutton_tvstatic2 = new ImageButton(this.leftPos + 306, this.topPos + 235, 600, 337,
				new WidgetSprites(ResourceLocation.parse("the_quiet_between:textures/screens/tvstatic.png"), ResourceLocation.parse("the_quiet_between:textures/screens/tvstatic.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderType::guiTextured, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_tvstatic2);
		imagebutton_tvstatic3 = new ImageButton(this.leftPos + -279, this.topPos + 245, 600, 337,
				new WidgetSprites(ResourceLocation.parse("the_quiet_between:textures/screens/tvstatic.png"), ResourceLocation.parse("the_quiet_between:textures/screens/tvstatic.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderType::guiTextured, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_tvstatic3);
		imagebutton_tvstatic4 = new ImageButton(this.leftPos + -599, this.topPos + -25, 600, 337,
				new WidgetSprites(ResourceLocation.parse("the_quiet_between:textures/screens/tvstatic.png"), ResourceLocation.parse("the_quiet_between:textures/screens/tvstatic.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderType::guiTextured, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_tvstatic4);
		imagebutton_tvstatic5 = new ImageButton(this.leftPos + -574, this.topPos + 225, 600, 337,
				new WidgetSprites(ResourceLocation.parse("the_quiet_between:textures/screens/tvstatic.png"), ResourceLocation.parse("the_quiet_between:textures/screens/tvstatic.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderType::guiTextured, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_tvstatic5);
	}
}