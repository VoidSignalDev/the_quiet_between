package net.mcreator.thequietbetween.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class V0idBlock extends Block {
	public V0idBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.EMPTY).strength(-1, 3600000));
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}
}