package win.oreo.stat_season2.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import win.oreo.stat_season2.stat.StatUtil;

import java.util.Random;

public class StatListener implements Listener {
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player player) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) || e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) || e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
                if (StatUtil.getStat(player.getName()) != null) {
                    double crit = (StatUtil.getStat(player.getName()).getINT() * 0.4);
                    Random random = new Random();
                    double c = random.nextDouble(100);
                    if (crit <= c) {
                        e.setDamage(e.getDamage() * 2);
                        player.sendMessage("CRIT! Damage : " + e.getDamage());
                    }
                }
            }
        }
    }
}
