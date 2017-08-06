package com.blamejared.crtadv;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlacedBlockTrigger;
import net.minecraft.block.properties.IProperty;
import net.minecraft.command.FunctionObject.CacheableFunction;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

public class AdvancementUtils {
	
	public static DisplayInfo createInfo(ItemStack stack, String title, String description, ResourceLocation background, FrameType frame, boolean toast, boolean announce, boolean hide) {
		
		return new DisplayInfo(stack, new TextComponentString(title), new TextComponentString(description), background, frame, toast, announce, hide);
	}
	
	public static AdvancementRewards createRewards(int experience, ResourceLocation[] loot, ResourceLocation[] recipes) {
		
		return new AdvancementRewards(experience, loot, recipes, CacheableFunction.EMPTY);
	}
	
	public static Map<String, Criterion> createCriteria() {
		
		final Map<String, Criterion> crit = new HashMap<>();
		
        Map< IProperty<?>, Object> props = new HashMap<>();
        Blocks.DIAMOND_BLOCK.getDefaultState().getProperties().forEach(props::put);
		crit.put("test", new Criterion(new PlacedBlockTrigger.Instance(Blocks.DIAMOND_BLOCK, props, LocationPredicate.ANY, ItemPredicate.ANY)));
		return crit;
	}
}
