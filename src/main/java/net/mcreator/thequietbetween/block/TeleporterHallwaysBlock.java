package net.mcreator.thequietbetween.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import net.mcreator.thequietbetween.procedures.TeleportToHallwaysProcedure;

public class TeleporterHallwaysBlock extends Block {
	public TeleporterHallwaysBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.EMPTY).strength(1f, 10f).noCollission());
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}

	@Override
	public void entityInside(BlockState blockstate, Level world, BlockPos pos, Entity entity) {
		super.entityInside(blockstate, world, pos, entity);
		TeleportToHallwaysProcedure.execute(entity);
	}
}