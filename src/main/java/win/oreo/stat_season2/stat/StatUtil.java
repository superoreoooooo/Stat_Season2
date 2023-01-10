package win.oreo.stat_season2.stat;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class StatUtil {
    public static HashMap<Player, Stat> statMap = new HashMap<>();

    public static Stat getStat(Player player) {
        return statMap.get(player);
    }

    public static void setStat(Player player, Stat stat) {
        statMap.put(player, stat);
    }
}
