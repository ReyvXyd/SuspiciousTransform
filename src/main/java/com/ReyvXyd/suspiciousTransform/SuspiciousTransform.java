package com.ReyvXyd.suspiciousTransform;

import org.bukkit.plugin.java.JavaPlugin;

public final class SuspiciousTransform extends JavaPlugin {
    private static SuspiciousTransform instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getServer().getPluginManager().registerEvents(new FuncTransform(),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static SuspiciousTransform getInstance(){
        return instance;
    }
}

//       ░░░░░░░░░░░░░░░░░░░░░░░░░░░░
//       ░░░░████░░░██▒░██▒░░░░░░░░░░
//       ░░░░██▒░██░██▒░██▒░░░░░░░░░░
//       ░░░░████▒░░░░██▒░░░░░░░░░░░░
//       ░░░░██▒░██░██▒░██▒░░░░░░░░░░
//       ░░░░██▒░██░██▒░██▒░█████░░░░
//       ░░░░░░░░░░░░░░░░░░░░░░░░█░░░
//       ░░░░░░░░░░░░░░░░░░░░░░░░░█░░
//       ░░░░░░███░░░░░░░░░░░░░░██░░░
//       ░░░░██░░░█████░░░░░░░██░░░░░
//       ░░░█░░░░░░░░░░███████░░░░░░░
//       ░░█░░░░░░░░░░░░░░░░░░░░░░░░░
//       ░░█░░░░░░░░░░░░░░░░░░░░░░░░░
//       ░░░█░░░░█░░░░░░░░░░░░░░░░░░░
//       ░░░█░░░█░░░░░░░░░░░░░░░░░░░░
//       ░░░░███░░░░░░░░░░░░░░░░░░░░░
//       ░░░░░░░░░░░░░░░░░░░░░░░░░░░░
