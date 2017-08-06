package com.blamejared.crtadv;

import com.blamejared.crtadv.reference.Reference;
import com.google.common.collect.Maps;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.block.properties.IProperty;
import net.minecraft.command.FunctionObject;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;

import java.util.*;

import static net.minecraft.init.Blocks.*;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class CRTADV {
    Advancement advancement;
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Map<ResourceLocation, Advancement.Builder> map = new HashMap<>();
        TextComponentString hello = new TextComponentString("hello");
        ItemStack icon = new ItemStack(Items.APPLE);
        DisplayInfo inf = new DisplayInfo(icon, hello, hello, null, FrameType.GOAL, false, false, false);
        AdvancementRewards rewardsIn = new AdvancementRewards(5, new ResourceLocation[0], new ResourceLocation[0], FunctionObject.CacheableFunction.EMPTY);
        Map<String, Criterion> crit = new HashMap<>();
        Map< IProperty<?>, Object> props = new HashMap<>();
        DIAMOND_BLOCK.getDefaultState().getProperties().forEach(props::put);
        crit.put("test", new Criterion(new PlacedBlockTrigger.Instance(DIAMOND_BLOCK, props, LocationPredicate.ANY, ItemPredicate.ANY)));
        advancement = new Advancement(new ResourceLocation("test"), null, inf, rewardsIn, crit, new String[0][0]);
        map.put(new ResourceLocation("test"), advancement.copy());
        System.out.println(AdvancementManager.ADVANCEMENT_LIST.getAdvancements());
        AdvancementManager.ADVANCEMENT_LIST.loadAdvancements(map);
        System.out.println(AdvancementManager.ADVANCEMENT_LIST.getAdvancements());
    }
    
    @Mod.EventHandler
    public void onFMLServerStarting(FMLServerStartingEvent event) {
    
    }
    
    @Mod.EventHandler
    public void onFMLServerAboutToStart(FMLServerAboutToStartEvent event) {
        
        event.getServer().getPlayerList().getPlayers().forEach(player ->{
            
            player.getAdvancements().grantCriterion(advancement, "test");
            System.out.println(player.getAdvancements().getProgress(advancement).getRemaningCriteria());
        });
    }
    
    @Mod.EventHandler
    public void onFMLServerStarted(FMLServerStartedEvent event) {
    
    }
}
