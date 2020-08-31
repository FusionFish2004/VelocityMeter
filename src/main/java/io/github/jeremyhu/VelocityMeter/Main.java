package io.github.jeremyhu.VelocityMeter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

    @Override
    public void onEnable(){
        //this.getLogger().info("§e§l正在注册监听器...");
        //Bukkit.getPluginManager().registerEvents(new VehicleEnterListener(this),this);
        this.getLogger().info("§e§l启动主循环...");
        SpeedMeter sm = new SpeedMeter();
        sm.runTaskTimerAsynchronously(this,0L,1L);
    }

    public static void main(String args[]) {
        int r = 0;
        int g = 0;
        int b = 0;
        int period = 100;

    }

}
