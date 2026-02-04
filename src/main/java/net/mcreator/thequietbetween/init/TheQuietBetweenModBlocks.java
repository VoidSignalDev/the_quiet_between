/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.thequietbetween.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

import net.mcreator.thequietbetween.block.TeleporterSpawnBlock;
import net.mcreator.thequietbetween.TheQuietBetweenMod;

import java.util.function.Function;

public class TheQuietBetweenModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(TheQuietBetweenMod.MODID);
	public static final DeferredBlock<Block> TELEPORTER_SPAWN;
	static {
		TELEPORTER_SPAWN = register("teleporter_spawn", TeleporterSpawnBlock::new);
	}

	// Start of user code block custom blocks
	// End of user code block custom blocks
	private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
		return REGISTRY.registerBlock(name, supplier);
	}
}