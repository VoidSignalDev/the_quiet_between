/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.thequietbetween.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.mcreator.thequietbetween.TheQuietBetweenMod;

public class TheQuietBetweenModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, TheQuietBetweenMod.MODID);
	public static final DeferredHolder<SoundEvent, SoundEvent> DOOR_KNOCK = REGISTRY.register("door_knock", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("the_quiet_between", "door_knock")));
	public static final DeferredHolder<SoundEvent, SoundEvent> TV_STATIC = REGISTRY.register("tv_static", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("the_quiet_between", "tv_static")));
	public static final DeferredHolder<SoundEvent, SoundEvent> INTRO_SCARE = REGISTRY.register("intro_scare", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("the_quiet_between", "intro_scare")));
	public static final DeferredHolder<SoundEvent, SoundEvent> DISTANT_FEMALE_SCREAM = REGISTRY.register("distant_female_scream",
			() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("the_quiet_between", "distant_female_scream")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAVE_WHISPER = REGISTRY.register("cave_whisper", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("the_quiet_between", "cave_whisper")));
	public static final DeferredHolder<SoundEvent, SoundEvent> WHISPER_WEEP = REGISTRY.register("whisper_weep", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("the_quiet_between", "whisper_weep")));
}