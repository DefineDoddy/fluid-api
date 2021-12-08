package me.definedoddy.fluidapi;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**The base class to extend from when registering a plugin*/
public class FluidPlugin extends JavaPlugin {
    /**@return the resource id of the plugin instance*/
    public int getResourceId() {
        return 0;
    }

    /**@return the latest version published to the plugin's spigot page. This page is determined by getResourceId()*/
    public String getLatestVersion() throws Exception {
        if (getResourceId() == 0) {
            throw new Exception("Resource id not set");
        }
        try (InputStream stream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + getResourceId()).openStream()) {
            Scanner scanner = new Scanner(stream);
            if (scanner.hasNext()) {
                return scanner.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**@return whether the current plugin version is the same as getLatestVersion()*/
    public final boolean isLatestVersion() throws Exception {
        return getDescription().getVersion().equalsIgnoreCase(getLatestVersion());
    }
}
