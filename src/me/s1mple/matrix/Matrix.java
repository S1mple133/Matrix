package me.s1mple.matrix;

import Utils.Glow;
import com.projectkorra.projectkorra.ability.CoreAbility;
import me.s1mple.matrix.BattlePass.BattlePass;
import me.s1mple.matrix.BattlePass.Data.UserData;
import me.s1mple.matrix.BattlePass.command.Battlepass;
import me.s1mple.matrix.Util.DBManager;
import me.s1mple.matrix.listener.AbilityListener;
import me.s1mple.matrix.slide.Slide;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import skinsrestorer.bukkit.SkinsRestorer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;


public class Matrix extends JavaPlugin {
    public static Matrix plugin;
    public static ConfigManager configManager;
    public static AbilityManager abilityManager;
    public static String version = "1.10";
    public static String author = "S1mple";
    private DBManager dbManager;
    private SkinsRestorer skinApi;

    @Override
    public void onEnable() {
        getLogger().info("me.s1mple.matrix.Matrix is initializing...");
        this.plugin = this;
        this.configManager = new ConfigManager(this);
        this.abilityManager = new AbilityManager(this);
        this.dbManager = new DBManager("jdbc:mysql://localhost:6915/battlepass?useSSL=false", "root", "MatrixNtw1226!");
        this.skinApi = ((SkinsRestorer)Matrix.getPlugin().getServer().getPluginManager().getPlugin("SkinsRestorer"));

        registerGlow();

        CoreAbility.registerPluginAbilities(plugin, "me.s1mple.matrix.Abilities");
        CoreAbility.registerPluginAbilities(plugin, "me.s1mple.matrix.Abilities.Passive");
        CoreAbility.registerPluginAbilities(plugin, "me.s1mple.matrix.Abilities.MatrixBending");

        BattlePass.LoadBattlePass(this);
        plugin.getCommand("battlepass").setExecutor(new Battlepass());

        //plugin.getServer().getPluginManager().registerEvents(new Slide(), Matrix.plugin);
        plugin.getServer().getPluginManager().registerEvents(new AbilityListener(Matrix.plugin), Matrix.plugin);
    }


    @Override
    public void onDisable() {
        getLogger().info("me.s1mple.matrix.Matrix plugin is stopping...");
        this.plugin.saveConfig();
    }

    public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glow glow = new Glow(new NamespacedKey( Matrix.plugin, "glow_ench"));
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException e){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String helpMessage = ChatColor.AQUA + "/matrix vpn add: " + ChatColor.BLUE + "Creates vpn key\n";
        String staffHelpMessage = ChatColor.AQUA + "/matrix vpn getip <name>: " + ChatColor.BLUE + "Returns the real ip of the player.\n" +
                ChatColor.AQUA + "/matrix premium <name>: " + ChatColor.BLUE + "Makes player's battlepass premium.";
        // VPN perm: matrix.vpn
        // Staff perm: matrix.staff

        if(cmd.getName().equalsIgnoreCase("matrix")) {
            if(args.length == 0) {
                if(sender.hasPermission("matrix.staff")) {
                    sender.sendMessage(staffHelpMessage);
                }
                //sender.sendMessage(helpMessage);
                return true;
            }
            else if (args.length >= 1) {
                if(args[0].equalsIgnoreCase("help")) {
                    //sender.sendMessage(helpMessage);
                    if(sender.hasPermission("matrix.staff")) {
                        sender.sendMessage(staffHelpMessage);
                    }
                    return true;
                }
                else if (args[0].equalsIgnoreCase("vpn")) {
                    if(args.length == 3) {
                        if(sender.hasPermission("matrix.vpn")) {
                            if(args[1].equalsIgnoreCase("getip")) {
                                sender.sendMessage(getIpOfVpnPlayer(args[2]));
                                return true;
                            }
                        }
                        else {
                            sender.sendMessage(ChatColor.RED+ "Not enough permissions!");
                            return false;
                        }
                    }
                    else if(args.length == 2) {
                        if(args[1].equalsIgnoreCase("add")) {
                            sender.sendMessage(addVpnKeyForPlayer(plugin.getServer().getPlayer(args[2])));
                            return true;
                        }
                    }
                }
                else if(args[0].equalsIgnoreCase("premium") && args.length == 2 && sender.hasPermission("matrix.premium")) {
                    if(Bukkit.getServer().getPlayer(args[1]) != null) {
                        UserData.GetUserData(args[1]).makePremium();
                        sender.sendMessage(ChatColor.GREEN + "Made " + args[1] + "'s battlepass premium.");
                    }
                    else {
                        sender.sendMessage(ChatColor.RED + "Player doesnt exist!");
                        return false;
                    }
                }
            }
        }
        sender.sendMessage(ChatColor.RED + "Unknown command! Use /matrix help");
        return false;
    }

    private String getIpOfVpnPlayer(String arg) {
        return "Unimplemented!";
    }

    private String addVpnKeyForPlayer(Player player) {
        UUID actUUID = UUID.randomUUID();
        try {
            Runtime.getRuntime().exec("/etc/openvpn/client/gen_client.sh " + player.getName() + " " + actUUID.toString());
            player.sendMessage(ChatColor.GREEN + "Open this link, download the .zip and check the README.txt file out! Link: https://matrixnetwork.org/vpn_keys/index.php?id=" + actUUID.toString());
            return ChatColor.GREEN + "Success!";
        } catch (IOException e) {
            return ChatColor.RED + "Failed! Try again.";
        }
    }


    /**
     * @return Plugin instance
     */
    public static Matrix getPlugin() {
        return plugin;
    }

    public DBManager getDbManager() {
        return dbManager;
    }

    public SkinsRestorer getSkinsApi() {
        return skinApi;
    }
}


