package win.oreo.stat_season2.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import win.oreo.stat_season2.Main;
import win.oreo.stat_season2.stat.StatUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatListener implements Listener {

    List<Entity> coolDown = new ArrayList<>();

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player player) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) || e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) || e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
                if (StatUtil.getStat(player.getName()) != null) {
                    if (!coolDown.contains(e.getEntity())) {
                        coolDown.add(e.getEntity());
                        delay(e.getEntity());

                        double finalDamage = e.getDamage();
                        finalDamage = finalDamage + (StatUtil.getStat(player.getName()).getSTR() * 0.4);

                        double crit = (StatUtil.getStat(player.getName()).getINT() * 4);
                        Random random = new Random();
                        double c = random.nextDouble(1000);

                        if (c <= crit) {
                            finalDamage = finalDamage * 2;
                            spawnChecker(e.getEntity().getLocation());
                        }

                        e.setDamage(finalDamage);
                        player.sendMessage(String.valueOf(finalDamage));
                    }
                }
            }
        }
    }

    public void spawnChecker(Location location) {
        location.add(0, 1.5, 0);
        ArmorStand checker = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        checker.setCustomName(ChatColor.RED + "CRITICAL!");
        checker.setGravity(true);
        checker.setCanPickupItems(false);
        checker.setVisible(false);
        checker.setCanMove(false);
        checker.setMarker(true);
        checker.setCustomNameVisible(true);
        Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(Main.class), checker::remove, 20);
    }

    public void delay(Entity entity) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(JavaPlugin.getPlugin(Main.class), () -> coolDown.remove(entity), 1);
    }
}
