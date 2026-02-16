package net.mcreator.thequietbetween.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.thequietbetween.network.TheQuietBetweenModVariables;

public class MakeEntityLookAtPlayerProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		entity.lookAt(EntityAnchorArgument.Anchor.EYES,
				new Vec3(TheQuietBetweenModVariables.MapVariables.get(world).playerX, (TheQuietBetweenModVariables.MapVariables.get(world).playerY + 1), TheQuietBetweenModVariables.MapVariables.get(world).playerZ));
	}
}