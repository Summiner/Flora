package rs.jamie.flora;

import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;
import rs.jamie.flora.modules.*;

public class FloraClient extends MeteorAddon {

    public static final Logger LOG = LogUtils.getLogger();
    public static final Category MODULES = new Category("Flora");

    @Override
    public void onInitialize() {
        LOG.info("[] Initializing Flora...");
        // Modules
        Modules.get().add(new SwapCrash());
        Modules.get().add(new NBTParserCrash());
        Modules.get().add(new OOBSlotCrash());
        Modules.get().add(new LecternCrash());
        // Finalize
        LOG.info("[] Initialized Flora!");
    }

    @Override
    public String getPackage() {
        return "rs.jamie.flora";
    }

    public static <T extends Module> T getModule(Class<T> klass) {
        return Modules.get().get(klass);
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(MODULES);
    }

}
