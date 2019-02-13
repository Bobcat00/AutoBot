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

import org.bukkit.plugin.java.JavaPlugin;
import com.bobcat00.autobot.Config;

public class AutoBot extends JavaPlugin
{
    Config config;
    
    @Override
    public void onEnable()
    {
        config = new Config(this);
        saveDefaultConfig();
        
        if (config.getApiKey().equals("YOUR_API_KEY"))
        {
            getLogger().info("Edit config.yml to enter your API Key purchased from https://www.cleverbot.com/api/");
            getLogger().info("then restart the server. Plugin disabled.");
        }
        else
        {
            getServer().getPluginManager().registerEvents(new Listeners(this), this);
            getLogger().info("Plugin enabled.");
            getLogger().info("AutoBot uses the Cleverbot API from Existor. See https://www.cleverbot.com/api/");
        }
    }
        
    @Override
    public void onDisable()
    {
        // HandlerList.unregisterAll(listeners);
    }

}
