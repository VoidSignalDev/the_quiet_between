/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.thequietbetween.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.mcreator.thequietbetween.entity.ShadowStalkerFlyingEntity;
import net.mcreator.thequietbetween.entity.ShadowStalkerEntity;
import net.mcreator.thequietbetween.TheQuietBetweenMod;

@EventBusSubscriber
public class TheQuietBetweenModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, TheQuietBetweenMod.MODID);
	public static final DeferredHolder<EntityType<?>, EntityType<ShadowStalkerEntity>> SHADOW_STALKER = register("shadow_stalker",
			EntityType.Builder.<ShadowStalkerEntity>of(ShadowStalkerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).fireImmune().ridingOffset(-0.6f).sized(0.6f, 1.8f));
	public static final DeferredHolder<EntityType<?>, EntityType<ShadowStalkerFlyingEntity>> SHADOW_STALKER_FLYING = register("shadow_stalker_flying",
			EntityType.Builder.<ShadowStalkerFlyingEntity>of(ShadowStalkerFlyingEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).fireImmune().ridingOffset(-0.6f).sized(0.6f, 1.8f));

	// Start of user code block custom entities
	// End of user code block custom entities
	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(TheQuietBetweenMod.MODID, registryname))));
	}

	@SubscribeEvent
	public static void init(RegisterSpawnPlacementsEvent event) {
		ShadowStalkerEntity.init(event);
		ShadowStalkerFlyingEntity.init(event);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(SHADOW_STALKER.get(), ShadowStalkerEntity.createAttributes().build());
		event.put(SHADOW_STALKER_FLYING.get(), ShadowStalkerFlyingEntity.createAttributes().build());
	}
}