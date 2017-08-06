package com.blamejared.crtadv.advancements;

import java.util.HashMap;
import java.util.Map;

import com.blamejared.crtadv.CRTADV;
import com.google.common.collect.Iterables;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementTreeNode;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlacedBlockTrigger;
import net.minecraft.block.properties.IProperty;
import net.minecraft.command.FunctionObject;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

public class AdvancementManagerWrapper extends AdvancementManager {

	private AdvancementManager parent;
	
	public AdvancementManagerWrapper(AdvancementManager parent) {
		
		super(parent.advancementsDir);
		this.parent = parent;
	}

	@Override
    public void reload() {

		if (this.parent != null) {
			
			this.parent.reload();
		}
		
		CRTADV.log.info("Hooking into advancements. {} existing advancements found.", Iterables.size(AdvancementManager.ADVANCEMENT_LIST.getAdvancements()));
		
        Map<ResourceLocation, Advancement.Builder> map = new HashMap<>();
        TextComponentString hello = new TextComponentString("hello");
        ItemStack icon = new ItemStack(Items.APPLE);
        DisplayInfo inf = new DisplayInfo(icon, hello, hello, null, FrameType.GOAL, true, true, false);
        AdvancementRewards rewardsIn = new AdvancementRewards(5, new ResourceLocation[0], new ResourceLocation[0], FunctionObject.CacheableFunction.EMPTY);
        Map<String, Criterion> crit = new HashMap<>();
        crit.put("test", new Criterion(new PlacedBlockTrigger.Instance(Blocks.DIAMOND_BLOCK, null, LocationPredicate.ANY, ItemPredicate.ANY)));
        Advancement advancement = new Advancement(new ResourceLocation("test"), AdvancementManager.ADVANCEMENT_LIST.getAdvancement(new ResourceLocation("minecraft:story/upgrade_tools")), inf, rewardsIn, crit, new String[0][0]);
        map.put(new ResourceLocation("test"), advancement.copy());
        System.out.println(AdvancementManager.ADVANCEMENT_LIST.getAdvancements());
        AdvancementManager.ADVANCEMENT_LIST.loadAdvancements(map);
        System.out.println(AdvancementManager.ADVANCEMENT_LIST.getAdvancements());
        
        for (Advancement advancement1 : ADVANCEMENT_LIST.getRoots())
        {
            if (advancement1.getDisplay() != null)
            {
                AdvancementTreeNode.layout(advancement1);
            }
        }
        
		CRTADV.log.info("{} advancements found.", Iterables.size(AdvancementManager.ADVANCEMENT_LIST.getAdvancements()));
    }

	@Override
    public boolean hasErrored() {
		
        return this.parent.hasErrored();
    }

	@Override
    public Map<ResourceLocation, Advancement.Builder> loadCustomAdvancements() {

    	return this.parent.loadCustomAdvancements();
    }

	@Override
    public void loadBuiltInAdvancements(Map<ResourceLocation, Advancement.Builder> map) {

		this.parent.loadBuiltInAdvancements(map);
    }

	@Override
    public Advancement getAdvancement(ResourceLocation id) {
		
        return this.parent.getAdvancement(id);
    }

	@Override
    public Iterable<Advancement> getAdvancements() {
		
		return this.parent.getAdvancements();
    }
}