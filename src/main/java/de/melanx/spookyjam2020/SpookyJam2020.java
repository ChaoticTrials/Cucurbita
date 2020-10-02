package de.melanx.spookyjam2020;

import de.melanx.spookyjam2020.core.CreativeTab;
import de.melanx.spookyjam2020.core.Registration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SpookyJam2020.MODID)
public class SpookyJam2020 {

    public static final String MODID = "spookyjam2020";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final CreativeTab creativeTab = new CreativeTab();
    public SpookyJam2020 instance;

    public SpookyJam2020() {
        instance = this;

        Registration.init();
        MinecraftForge.EVENT_BUS.register(this);
    }
}
