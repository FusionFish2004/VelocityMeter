package io.github.jeremyhu.VelocityMeter;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class VehicleEnterListener implements Listener {

    private JavaPlugin plugin;

    public VehicleEnterListener(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onEnter(VehicleEnterEvent e){
        if(e.getEntered() instanceof Player){
            Player player = (Player) e.getEntered();
            VehicleTracer vt = new VehicleTracer(plugin,player);
            vt.runTaskTimerAsynchronously(plugin,0L,1L);
        }
    }
}
