package win.oreo.stat_season2.stat;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import win.oreo.stat_season2.Main;

import java.util.HashMap;

public class StatUtil {
    private Main plugin;

    public StatUtil() {
        plugin = JavaPlugin.getPlugin(Main.class);
    }

    private static HashMap<String, Stat> statMap = new HashMap<>();

    public static Stat getStat(String player) {
        return statMap.get(player);
    }

    public static void setStat(String player, Stat stat) {
        statMap.put(player, stat);
    }

    public void initialize() {
        for (String player : plugin.statYml.getConfig().getConfigurationSection("player.").getKeys(false)) {
            double atk = plugin.statYml.getConfig().getDouble("player." + player + ".atk");
            double crit = plugin.statYml.getConfig().getDouble("player." + player + ".crit");
            double spd = plugin.statYml.getConfig().getDouble("player." + player + ".spd");
            Stat stat = new Stat(atk, crit, spd);
            statMap.put(player, stat);
        }

        Bukkit.getScheduler().runTaskTimer(plugin, this::applyStat, 0, 20);
    }

    public void applyStat() {
        for (String player_str : statMap.keySet()) {
            if (statMap.containsKey(player_str)) {
                Stat stat = statMap.get(player_str);
                Player player = Bukkit.getPlayer(player_str);
                double hp = 20 + (stat.getSTR() * 3) + (stat.getINT()) + (stat.getPHY() * 2);
                double speed = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getDefaultValue() + (stat.getPHY() * 0.05);
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hp);
                player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
            }
        }
    }
}
