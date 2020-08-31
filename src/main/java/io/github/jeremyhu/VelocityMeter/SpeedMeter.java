package io.github.jeremyhu.VelocityMeter;

import io.github.jeremyhu.VelocityMeter.Util.ActionBar;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.HashMap;

public class SpeedMeter extends BukkitRunnable {

    private boolean fx = true;
    private boolean sound = false;
    private HashMap<Player, Vector> data = new HashMap<Player, Vector>();
    private HashMap<Player, Integer> state = new HashMap<Player, Integer>();
    private HashMap<Player,Double> speed = new HashMap<Player, Double>();

    private Vector ldisplacement = new Vector();

    private int r = 0;
    private int g = 0;
    private int b = 0;
    private int j = 0;
    private int period = 50;
    private float d = 1F;
    private float l = 0.6F;

    public void run(){


        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.getVehicle() != null){
                int newstate = 0;
                int laststate;

                Vector thisLoc = p.getVehicle().getLocation().toVector();
                Vector lastLoc = data.get(p);
                if (lastLoc == null) {
                    lastLoc = thisLoc.clone();
                }else{
                    lastLoc = data.get(p);
                }

                Vector displacement = thisLoc.clone().subtract(lastLoc);
                //Double accelerate = (displacement.clone().subtract(ldisplacement).length()) / 0.0025D;



                Double speed = (displacement.clone().length()) / 0.05D;

                Double lastspeed = this.speed.get(p);

                if(this.speed.get(p) == null){
                    lastspeed = speed;
                }else{
                    lastspeed = this.speed.get(p);
                }




                Double yaw = Math.toRadians(-(p.getVehicle().getLocation().getYaw()));

                if(fx){

                    World world = p.getWorld();
                    Location loc = p.getVehicle().getLocation();
                    Location locc = p.getVehicle().getLocation();

                    loc.add(-d * Math.sin(yaw),0,-d * Math.cos(yaw));
                    loc.add(0,0.1,0);

                    locc.add(d * Math.sin(yaw),0,d * Math.cos(yaw));
                    locc.add(0,0.1,0);

                    Location loc1 = loc.clone().add(-l * Math.cos(yaw),0,l * Math.sin(yaw));
                    Location loc2 = loc.clone().add(l * Math.cos(yaw),0,-l * Math.sin(yaw));

                    Location loc3 = locc.clone().add(-l * Math.cos(yaw),0,l * Math.sin(yaw));
                    Location loc4 = locc.clone().add(l * Math.cos(yaw),0,-l * Math.sin(yaw));

                    if(speed > 10 && speed < 20){
                        newstate = 1;
                    }
                    if(speed >= 20 && speed < 30){
                        newstate = 2;
                    }
                    if(speed >= 30 && speed < 40){;
                        newstate = 3;
                    }
                    if(speed >= 40){
                        newstate = 4;
                    }

                    if(lastspeed > 10 && (lastspeed - speed) > 10){
                        //world.playSound(loc,Sound.BLOCK_ANVIL_LAND,0.1F,1F);

                    }

                    //Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(r,g,b),1.5F);
                    if(speed > 1) {
                        if (!p.getVehicle().getLocation().clone().add(0, -1, 0).getBlock().isEmpty()) {
                            world.spawnParticle(Particle.DRIP_LAVA, loc1, 3, 0.05D, 0.05D, 0.05D, 0.001D);
                            world.spawnParticle(Particle.DRIP_LAVA, loc2, 3, 0.05D, 0.05D, 0.05D, 0.001D);

                            world.spawnParticle(Particle.DRIP_LAVA, loc3, 3, 0.05D, 0.05D, 0.05D, 0.001D);
                            world.spawnParticle(Particle.DRIP_LAVA, loc4, 3, 0.05D, 0.05D, 0.05D, 0.001D);
                        }

                        if (lastLoc.clone().toLocation(p.getWorld()).add(0, -1, 0).getBlock().isEmpty() && !thisLoc.clone().toLocation(p.getWorld()).add(0, -1, 0).getBlock().isEmpty()) {
                            world.spawnParticle(Particle.EXPLOSION_LARGE, thisLoc.toLocation(world), 1, 0.5D, 0.5D, 0.5D, 0.01D);
                            world.playSound(thisLoc.toLocation(world), Sound.ENTITY_PLAYER_BIG_FALL, 0.5F, 1F);
                        }

                        world.spawnParticle(Particle.SMOKE_NORMAL, loc1, 2, 0.1D, 0.1D, 0.1D, 0.1D);
                        world.spawnParticle(Particle.SMOKE_NORMAL, loc2, 2, 0.1D, 0.1D, 0.1D, 0.1D);

                        world.spawnParticle(Particle.CLOUD, loc1, 1, 0D, 0D, 0D, 0.1D);
                        world.spawnParticle(Particle.CLOUD, loc2, 1, 0D, 0D, 0D, 0.1D);
                    }
                    //world.spawnParticle(Particle.SMOKE_NORMAL,loc3,2,0.1D,0.1D,0.1D,0.1D);
                    //world.spawnParticle(Particle.SMOKE_NORMAL,loc4,2,0.1D,0.1D,0.1D,0.1D);

                    //world.spawnParticle(Particle.CLOUD,loc3,1,0.1D,0.1D,0.1D,1D);
                    //world.spawnParticle(Particle.CLOUD,loc4,1,0.1D,0.1D,0.1D,1D);

                    if(state.get(p) == null){
                        laststate = newstate;
                    }else{
                        laststate = state.get(p);
                    }

                    if(newstate > laststate && sound){
                        if(newstate == 1){world.playSound(loc,Sound.BLOCK_NOTE_BLOCK_BIT,1F,1.681793F);}
                        if(newstate == 2){world.playSound(loc,Sound.BLOCK_NOTE_BLOCK_BIT,1F,1.781797F);}
                        if(newstate == 3){world.playSound(loc,Sound.BLOCK_NOTE_BLOCK_BIT,1F,1.887749F);}
                        if(newstate == 4){world.playSound(loc,Sound.BLOCK_NOTE_BLOCK_BIT,1F,2F);}
                    }
                    state.put(p,newstate);
                }
                data.put(p,thisLoc.clone());
                this.speed.put(p,speed);
                showSpeed(p,displacement,newstate);

                //ldisplacement = displacement.clone();
            }
        }
    }

    private void showSpeed(Player player,Vector displacement,int state){
            DecimalFormat df = new DecimalFormat("#.00");
            Double speed = (displacement.clone().length()) / 0.05D;
            String s = df.format(speed);
            if(s.startsWith(".")){
                s = "0" + s;
            }
            String line1 = "§7| §8§l瞬时速率： " + getColor(state) + s + "m/s §7|";
            ActionBar.sendActionBar(player,line1);
    }

    private String getColor(int state){
        String color = "";
        if(state == 0){color = "§e";}
        if(state == 1){color = "§a";}
        if(state == 2){color = "§9";}
        if(state == 3){color = "§5";}
        if(state == 4){color = "§c";}
        return color;
    }
}
