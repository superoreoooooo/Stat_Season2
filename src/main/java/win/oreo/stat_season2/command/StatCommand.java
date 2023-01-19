package win.oreo.stat_season2.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import win.oreo.stat_season2.stat.Stat;
import win.oreo.stat_season2.stat.StatUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            switch (args.length) {
                case 1 -> {
                    if (args[0].equals("get")) {
                        Stat stat = StatUtil.getStat(player.getName());
                        if (stat != null) {
                            player.sendMessage("Stat : (STR:" + stat.getSTR() + " INT:" + stat.getINT() + " PHY:" + stat.getPHY() + ")");
                        } else {
                            player.sendMessage(ChatColor.RED + "Stat is null!");
                        }
                    }
                }
                case 2 -> {
                    if (args[0].equals("get")) {
                        Stat stat = StatUtil.getStat(args[1]);
                        if (stat != null) {
                            player.sendMessage("Stat : (STR:" + stat.getSTR() + " INT:" + stat.getINT() + " PHY:" + stat.getPHY() + ")");
                        } else {
                            player.sendMessage(ChatColor.RED + "Stat is null!");
                        }
                    }
                }
                case 4 -> {
                    if (args[0].equals("set")) {
                        if (StatUtil.getStat(args[1]) != null) {
                            Stat stat = new Stat(0, 0, 0);
                            StatUtil.setStat(args[1], stat);
                        } else {
                            Stat stat = StatUtil.getStat(args[1]);
                            switch (args[2]) {
                                case "STR" -> {
                                    stat.setSTR(Double.parseDouble(args[3]));
                                }
                                case "INT" -> {
                                    stat.setINT(Double.parseDouble(args[3]));
                                }
                                case "PHY" -> {
                                    stat.setPHY(Double.parseDouble(args[3]));
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
