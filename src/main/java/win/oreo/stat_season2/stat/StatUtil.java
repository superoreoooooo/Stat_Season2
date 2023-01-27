package win.oreo.stat_season2.stat;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import win.oreo.stat_season2.Main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class StatUtil {
    private Main plugin;

    private static HashMap<String, Stat> statMap = new HashMap<>();

    public static Stat getStat(String player) {
        return statMap.get(player);
    }

    public static void setStat(String player, Stat stat) {
        statMap.put(player, stat);
        apply();
    }

    public void initialize() {
        plugin = JavaPlugin.getPlugin(Main.class);
        for (String player : plugin.statYml.getConfig().getConfigurationSection("player.").getKeys(false)) {
            double STR = plugin.statYml.getConfig().getDouble("player." + player + ".str");
            double INT = plugin.statYml.getConfig().getDouble("player." + player + ".int");
            double PHY = plugin.statYml.getConfig().getDouble("player." + player + ".phy");
            Stat stat = new Stat(STR, INT, PHY);
            statMap.put(player, stat);
        }
    }

    public void save() {
        plugin = JavaPlugin.getPlugin(Main.class);
        for (String player : statMap.keySet()) {
            plugin.statYml.getConfig().set("player." + player + ".str", statMap.get(player).getSTR());
            plugin.statYml.getConfig().set("player." + player + ".int", statMap.get(player).getINT());
            plugin.statYml.getConfig().set("player." + player + ".phy", statMap.get(player).getPHY());
            plugin.statYml.saveConfig();
        }
    }

    public static void setSTR(String player, double STR) {
        Stat stat = getStat(player);
        stat.setSTR(STR);
        setStat(player, stat);
    }

    public static void setINT(String player, double INT) {
        Stat stat = getStat(player);
        stat.setINT(INT);
        setStat(player, stat);
    }

    public static void setPHY(String player, double PHY) {
        Stat stat = getStat(player);
        stat.setPHY(PHY);
        setStat(player, stat);
    }

    public static void apply() {
        for (String player_str : statMap.keySet()) {
            if (statMap.containsKey(player_str)) {
                Stat stat = statMap.get(player_str);
                Player player = Bukkit.getPlayer(player_str);
                if (player == null) continue;
                double speed = (0.1 + (stat.getPHY() * 0.05));
                double hp = 20 + (stat.getSTR() * 3) + (stat.getINT()) + (stat.getPHY() * 2);
                if (speed > 1) {
                    speed = 1f;
                    player.sendMessage("warn! speed can't exceed 1.0! -> speed set to 1.0");
                }
                player.setWalkSpeed((float) speed);
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hp);
                player.sendMessage("HP changed : " + hp);
                player.sendMessage("Speed changed : " + speed);
            }
        }
    }
}
