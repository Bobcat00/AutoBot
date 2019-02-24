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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TabComplete implements TabCompleter
{
    @SuppressWarnings("unused")
    private AutoBot plugin;
    
    public TabComplete(AutoBot plugin)
    {
        this.plugin = plugin;
    }
    
    // Check permission if the sender is a player, other return true
    private boolean hasPermission(CommandSender sender, String permission)
    {
        if (!(sender instanceof Player))
        {
            return true;
        }
        else
        {
            return sender.hasPermission(permission);
        }
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("auto"))
        {
            //plugin.getLogger().info(args.length + ": " + Arrays.toString(args));

            // Commands are:
            // clear
            // help
            // reload
            // status
            // tweak<n> m
            
            List<String> argList = new ArrayList<>();
            
            if (args.length == 1 && hasPermission(sender, "autobot.command"))
            {
                if (hasPermission(sender, "autobot.command.clear"))
                {
                    argList.add("clear");
                }
                if (hasPermission(sender, "autobot.command.help"))
                {
                    argList.add("help");
                }
                if (hasPermission(sender, "autobot.command.reload"))
                {
                    argList.add("reload");
                }
                if (hasPermission(sender, "autobot.command.status"))
                {
                    argList.add("status");
                }
                if (hasPermission(sender, "autobot.command.tweak"))
                {
                    argList.add("tweak1");
                    argList.add("tweak2");
                    argList.add("tweak3");
                }
                
                return argList.stream().filter(a -> a.startsWith(args[0])).collect(Collectors.toList());
            }
            
            if (args.length == 2 &&
                (args[0].equals("tweak1") || args[0].equals("tweak2") || args[0].equals("tweak3")) &&
                hasPermission(sender, "autobot.command") &&
                hasPermission(sender, "autobot.command.tweak"))
            {
                argList.add("<n>");
                
                return argList;
            }
            
            return argList; // returns an empty list
            
         }
        
        return null; // default return
    }

}
