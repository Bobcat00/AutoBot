// AutoBot - Cleverbot chat plugin for CraftBukkit/Spigot servers
// Copyright 2018 Bobcat00
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

package com.bobcat00.autobot;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor
{
    private AutoBot plugin;
    
    public Commands(AutoBot plugin)
    {
        this.plugin = plugin;
    }
    
    // Process arugments from the tweak commands, and saves the config if successful
    private void processTweakCommand(CommandSender sender, String[] args, int n)
    {
        if (sender instanceof Player && !sender.hasPermission("autobot.command.tweak"))
        {
            sender.sendMessage("You do not have permission for the tweak command");
            return;
        }
        if (args.length !=2)
        {
            sender.sendMessage("Invalid number of arguments");
            return;
        }
        
        try
        {
            int tweak = Integer.parseInt(args[1]);
            if (tweak >=0 && tweak <= 100)
            {
                switch (n)
                {
                case 1: plugin.config.setTweak1(tweak);
                        plugin.listeners.setTweak1(tweak);
                        break;
                        
                case 2: plugin.config.setTweak2(tweak);
                        plugin.listeners.setTweak2(tweak);
                        break;
                        
                case 3: plugin.config.setTweak3(tweak);
                        plugin.listeners.setTweak3(tweak);
                        break;
                        
                default: break;
                }
                plugin.config.saveConfig();
                sender.sendMessage(ChatColor.AQUA + "Tweak" + n + " updated to " + tweak);
            }
            else
            {
                sender.sendMessage("Value must be between 0 and 100, inclusive");
                return;
            }
        }
        catch (NumberFormatException e)
        {
            sender.sendMessage("Invalid value");
            return;
        }
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("auto"))
        {
            if (sender instanceof Player && !sender.hasPermission("autobot.command"))
            {
                sender.sendMessage("You do not have permission for this command");
                return true;
            }
            
            // Commands are:
            // clear
            // help
            // reload
            // status
            // tweak<n> m
            
            if (args.length == 1 && args[0].equalsIgnoreCase("clear"))
            {
                if (sender instanceof Player && !sender.hasPermission("autobot.command.clear"))
                {
                    sender.sendMessage("You do not have permission for the clear command");
                    return true;
                }
                plugin.listeners.clearConversationHistory();
                sender.sendMessage(ChatColor.AQUA + "Conversion history cleared");
                
            }
            else if (args.length == 0 ||
                     (args.length == 1 && args[0].equalsIgnoreCase("help")))
            {
                if (sender instanceof Player && !sender.hasPermission("autobot.command.help"))
                {
                    sender.sendMessage("You do not have permission for the help command");
                    return true;
                }
                sender.sendMessage(ChatColor.AQUA + "clear - Clear conversion history");
                sender.sendMessage(ChatColor.AQUA + "help - This help message");
                sender.sendMessage(ChatColor.AQUA + "reload - Reload config file");
                sender.sendMessage(ChatColor.AQUA + "status - Show plugin status");
                sender.sendMessage(ChatColor.AQUA + "tweak1 <n> - Set tweak1, 0=sensible to 100=wacky");
                sender.sendMessage(ChatColor.AQUA + "tweak2 <n> - Set tweak2, 0=shy to 100=talkative");
                sender.sendMessage(ChatColor.AQUA + "tweak3 <n> - Set tweak3, 0=self-centered to 100=attentive");
                
            }
            else if (args.length == 1 && args[0].equalsIgnoreCase("reload"))
            {
                if (sender instanceof Player && !sender.hasPermission("autobot.command.reload"))
                {
                    sender.sendMessage("You do not have permission for the reload command");
                    return true;
                }
                
                // Reload config and update tweak settings even if they're invalid
                plugin.config.reloadConfig(false);
                plugin.listeners.setTweak1(plugin.config.getTweak1());
                plugin.listeners.setTweak2(plugin.config.getTweak2());
                plugin.listeners.setTweak3(plugin.config.getTweak3());
                
                boolean error = false;
                
                if (plugin.config.getTweak1() < 0 ||
                    plugin.config.getTweak1() > 100)
                {
                    sender.sendMessage("Invalid value for tweak1, must be in range 0 to 100");
                    error = true;
                }
                if (plugin.config.getTweak2() < 0 ||
                    plugin.config.getTweak2() > 100)
                {
                    sender.sendMessage("Invalid value for tweak2, must be in range 0 to 100");
                    error = true;
                }
                if (plugin.config.getTweak3() < 0 ||
                    plugin.config.getTweak3() > 100)
                {
                    sender.sendMessage("Invalid value for tweak3, must be in range 0 to 100");
                    error = true;
                }
                
                if (!error)
                {
                    sender.sendMessage(ChatColor.AQUA + "Config reloaded");
                }
                
            }
            else if ((args.length == 1 && args[0].equalsIgnoreCase("status")) ||
                     (args.length == 1 && args[0].equalsIgnoreCase("version")))
            {
                if (sender instanceof Player && !sender.hasPermission("autobot.command.status"))
                {
                    sender.sendMessage("You do not have permission for the status command");
                    return true;
                }
                sender.sendMessage(ChatColor.AQUA + "AutoBot version " + plugin.getDescription().getVersion());
                if (plugin.config.getApiKey().equals("YOUR_API_KEY"))
                {
                    sender.sendMessage(ChatColor.YELLOW + "WARNING: API Key is not set in config file!");
                }
                sender.sendMessage(ChatColor.AQUA + "Conversation expiration time " + plugin.config.getExpirationMinutes() + " minutes");
                sender.sendMessage(ChatColor.AQUA + "Single player response is " + plugin.config.getSinglePlayer());
                sender.sendMessage(ChatColor.AQUA + "Tweak1 " + plugin.config.getTweak1() + " (0=sensible to 100=wacky)");
                sender.sendMessage(ChatColor.AQUA + "Tweak2 " + plugin.config.getTweak2() + " (0=shy to 100=talkative)");
                sender.sendMessage(ChatColor.AQUA + "Tweak3 " + plugin.config.getTweak3() + " (0=self-centered to 100=attentive)");
                
            }
            else if (args.length >= 1 && args[0].equalsIgnoreCase("tweak1"))
            {
                processTweakCommand(sender, args, 1);
                
            }
            else if (args.length >= 1 && args[0].equalsIgnoreCase("tweak2"))
            {
                processTweakCommand(sender, args, 2);
                
            }
            else if (args.length >= 1 && args[0].equalsIgnoreCase("tweak3"))
            {
                processTweakCommand(sender, args, 3);
                
            }
            else
            {
                sender.sendMessage("Invalid Auto command");
                return true;
            }
            
            // Normal return
            return true;
        }
        
        return false;
    }
    
}
