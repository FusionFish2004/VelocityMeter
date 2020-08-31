package io.github.jeremyhu.VelocityMeter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class VehicleTracer extends BukkitRunnable {

    private Player player;
    private JavaPlugin plugin;
    private long i;
    private File file;
    FileOutputStream fos;
    private Date date;

    public VehicleTracer(JavaPlugin plugin,Player player){

        this.player = player;
        this.plugin = plugin;
        Date date = new Date();
        this.date = date;
        plugin.getLogger().info("§e§l正在记录文件：" + player.getName() + "-" + date.getTime() + ".vrd");
        player.sendMessage("§e§l开始记录 - " + date.getTime());
        this.file = new File(plugin.getDataFolder(),player.getName() + "-" + date.getTime() + ".vrd");
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        i=0;

    }

    public void run(){
        if(player.getVehicle() != null){
            try {
                fos = new FileOutputStream(file,true);
                Location loc = player.getVehicle().getLocation();
                float yaw = player.getVehicle().getLocation().getYaw();
                String msg = "[" + i + "]" + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + yaw + "\r\n";
                try {
                    fos.write(msg.getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            i++;
        }else{
            player.sendMessage("§e§l停止记录 - " + date.getTime());
            plugin.getLogger().info("§e§l结束记录文件：" + player.getName() + "-" + date.getTime() + ".vrd");
            this.cancel();
        }
    }
}
