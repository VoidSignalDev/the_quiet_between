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
}