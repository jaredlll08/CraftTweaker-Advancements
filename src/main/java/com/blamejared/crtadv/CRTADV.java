package com.blamejared.crtadv;

import org.apache.logging.log4j.Logger;

import com.blamejared.crtadv.advancements.AdvancementManagerWrapper;
import com.blamejared.crtadv.reference.Reference;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class CRTADV {
    
	public static Logger log;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		log = event.getModLog();
	}
	
    @Mod.EventHandler
    public void onFMLServerStarted(FMLServerStartedEvent event) {
    
    	for (WorldServer world : FMLCommonHandler.instance().getMinecraftServerInstance().worlds) {
    		
    		log.info("Wrapping advancement manager for {}", getWorldName(world));
    		world.advancementManager = new AdvancementManagerWrapper(world.advancementManager);
    	}
    }
    
    private static String getWorldName(WorldServer world) {
        
        String result = "Undefined";
        
        //TODO add more fallback options
        if (world.provider != null) {
            
            result = world.provider.getDimensionType().getName();
        }
        
        return result;
    }
}