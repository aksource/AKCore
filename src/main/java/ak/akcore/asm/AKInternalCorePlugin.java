package ak.akcore.asm;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Map;

/**
 * Created by A.K. on 14/03/13.
 */
public class AKInternalCorePlugin implements IFMLLoadingPlugin {
    public static Logger logger = LogManager.getLogger("AKInternalCore");
    public static int maxDamageModifier;
    public static int maxAnvilLevelModifier;
    public static int setAnvilLevelModifier;
    public static int beaconBaseRange;
    public static int beaconLevelRange;

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{"ak.akcore.asm.EnchantmentHelperTransformer",
                "ak.a.asm.AnvilLevelTransformer",
                "ak.akcore.asm.AnvilLevelClientTransformer",
                "ak.akcore.asm.TileEntityBeaconTransformer"
        };
    }

    @Override
    public String getModContainerClass() {
        return "ak.akcore.asm.AKInternalCoreModContainer";
    }

    @Override
    public String getSetupClass() {
        return null/*"codechicken.core.launch.DepLoader"*/;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        if (data.containsKey("mcLocation")) {
            File mcLocation = (File) data.get("mcLocation");
            File configLocation = new File(mcLocation, "config");
            File configFile = new File(configLocation, "AKCore.cfg");
            initConfig(configFile);
        }
    }

    private void initConfig(File configFile) {
        Configuration config = new Configuration(configFile);
        config.load();
        maxDamageModifier = config.get(Configuration.CATEGORY_GENERAL, "maxDamageModifier", 25, "set damagemodifier max value. default:25").getInt();
        maxAnvilLevelModifier = config.get(Configuration.CATEGORY_GENERAL, "maxAnvilLevel", 40, "set Anvil max levle. default:40").getInt();
        setAnvilLevelModifier = maxAnvilLevelModifier - 1;
        beaconBaseRange = config.get(Configuration.CATEGORY_GENERAL, "beaconBaseRange", 10, "set Beacon Constant Range. default:10").getInt();
        beaconLevelRange = config.get(Configuration.CATEGORY_GENERAL, "beaconLevelRange", 10, "set Beacon Level-changable Range. default:10").getInt();
        config.save();
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
