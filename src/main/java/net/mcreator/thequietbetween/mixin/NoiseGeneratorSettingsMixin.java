package net.mcreator.thequietbetween.mixin;

import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.core.Holder;

import net.mcreator.thequietbetween.init.TheQuietBetweenModBiomes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;

@Mixin(NoiseGeneratorSettings.class)
public class NoiseGeneratorSettingsMixin implements TheQuietBetweenModBiomes.TheQuietBetweenModNoiseGeneratorSettings {
	@Unique
	private Holder<DimensionType> the_quiet_between_dimensionTypeReference;

	@WrapMethod(method = "surfaceRule")
	public SurfaceRules.RuleSource surfaceRule(Operation<SurfaceRules.RuleSource> original) {
		SurfaceRules.RuleSource retval = original.call();
		if (this.the_quiet_between_dimensionTypeReference != null) {
			retval = TheQuietBetweenModBiomes.adaptSurfaceRule(retval, this.the_quiet_between_dimensionTypeReference);
		}
		return retval;
	}

	@Override
	public void setthe_quiet_betweenDimensionTypeReference(Holder<DimensionType> dimensionType) {
		this.the_quiet_between_dimensionTypeReference = dimensionType;
	}
}