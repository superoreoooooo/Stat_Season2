package win.oreo.stat_season2;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import win.oreo.stat_season2.command.StatCommand;
import win.oreo.stat_season2.listener.StatListener;
import win.oreo.stat_season2.mgr.StatYml;
import win.oreo.stat_season2.stat.StatUtil;
import win.oreo.stat_season2.util.Color;

public final class Main extends JavaPlugin {

    public StatYml statYml;
    public FileConfiguration config;
    private StatUtil statUtil;


    public Main() {
        statYml = new StatYml(this);
        config = this.getConfig();
        statUtil = new StatUtil();
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new StatListener(), this);
        getCommand("stat").setExecutor(new StatCommand());
        statUtil.initialize();

        sendConsoleMsg(getConfigMessage("messages.server-enable"));
    }

    @Override
    public void onDisable() {
        statUtil.save();

        sendConsoleMsg(getConfigMessage("messages.server-disable"));
    }

    public static void sendConsoleMsg(String msg) {
        Bukkit.getConsoleSender().sendMessage(msg);
    }

    public static String getConfigMessage(String path) {
        FileConfiguration config = JavaPlugin.getPlugin(Main.class).getConfig();
        String text = config.getString(path);
        String prefix = config.getString("prefix");
        if (text == null) {
            return ChatColor.RED +"ERROR";
        }
        return Color.format(prefix + " " + text);
    }

    public static String getConfigMessage(String path, String[] args) {
        FileConfiguration config = JavaPlugin.getPlugin(Main.class).getConfig();
        String text = config.getString(path);
        String prefix = config.getString("prefix");
        if (text == null) {
            return ChatColor.RED + "ERROR";
        }

        boolean open = false;
        StringBuilder chars = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (c == '%') {
                if (open) {
                    final char[] CHARACTERS = chars.toString().toCharArray();
                    if (CHARACTERS[0] == 'a' && CHARACTERS[1] == 'r' && CHARACTERS[2] == 'g') {
                        final int ARG = Integer.parseInt(String.valueOf(CHARACTERS[3]));

                        text = text.replace(chars.toString(), args[ARG]);

                        chars = new StringBuilder();
                    }
                    open = false;
                } else {
                    open = true;
                }
                continue;
            }

            if (open) {
                chars.append(c);
            }
        }

        return Color.format(prefix + " " + text.replace("%", ""));
    }

    /**
    무력 : 무기 공격시 기본 무기 공격력 + 스탯 공격력;
    지력 : 확률적으로 데미지 2배
    신체 : 기본 이속 + (스탯 * 0.05)
    체력 : health_boost
    **/
}
