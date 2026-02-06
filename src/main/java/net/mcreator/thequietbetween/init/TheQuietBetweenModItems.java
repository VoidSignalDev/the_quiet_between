/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.thequietbetween.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.mcreator.thequietbetween.TheQuietBetweenMod;

import java.util.function.Function;

public class TheQuietBetweenModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(TheQuietBetweenMod.MODID);
	public static final DeferredItem<Item> TELEPORTER_SPAWN;
	public static final DeferredItem<Item> TELEPORTER_WHITE_VOID;
	public static final DeferredItem<Item> TELEPORTER_HALLWAYS;
	public static final DeferredItem<Item> V_0ID;
	static {
		TELEPORTER_SPAWN = block(TheQuietBetweenModBlocks.TELEPORTER_SPAWN);
		TELEPORTER_WHITE_VOID = block(TheQuietBetweenModBlocks.TELEPORTER_WHITE_VOID);
		TELEPORTER_HALLWAYS = block(TheQuietBetweenModBlocks.TELEPORTER_HALLWAYS);
		V_0ID = block(TheQuietBetweenModBlocks.V_0ID);
	}

	// Start of user code block custom items
	// End of user code block custom items
	private static <I extends Item> DeferredItem<I> register(String name, Function<Item.Properties, ? extends I> supplier) {
		return REGISTRY.registerItem(name, supplier);
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return block(block, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.registerItem(block.getId().getPath(), prop -> new BlockItem(block.get(), prop), properties);
	}
}