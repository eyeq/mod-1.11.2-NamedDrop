package eyeq.nameddrop;

import eyeq.nameddrop.event.NamedDropEventHandler;
import eyeq.util.client.renderer.ULanguageCreator;
import eyeq.util.client.renderer.ULanguageResourceManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

import static eyeq.nameddrop.NamedDrop.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class NamedDrop {
    public static final String MOD_ID = "eyeq_nameddrop";

    @Mod.Instance(MOD_ID)
    public static NamedDrop instance;

    public static final String I18n_ADJ_FRONT = "adj.front.txt";
    public static final String I18n_ADJ_BACK = "adj.back.txt";

    public static boolean isFront;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new NamedDropEventHandler());
        load(new Configuration(event.getSuggestedConfigurationFile()));
        if(event.getSide().isServer()) {
            return;
        }
        createFiles();
    }

    public static void load(Configuration config) {
        config.load();

        isFront = config.get("Boolean", "isFront", true).getBoolean(false);

        if(config.hasChanged()) {
            config.save();
        }
    }

    public static void createFiles() {
        File project = new File("../1.11.2-NamedDrop");

        ULanguageResourceManager language = new ULanguageResourceManager();
        language.register(ULanguageResourceManager.EN_US, I18n_ADJ_FRONT, "");
        language.register(ULanguageResourceManager.EN_US, I18n_ADJ_BACK, "'s ");

        language.register(ULanguageResourceManager.JA_JP, I18n_ADJ_FRONT, "");
        language.register(ULanguageResourceManager.JA_JP, I18n_ADJ_BACK, "の遺した");
        ULanguageCreator.createLanguage(project, MOD_ID, language);
    }
}
