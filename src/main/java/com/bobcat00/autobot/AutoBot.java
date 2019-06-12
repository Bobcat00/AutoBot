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

import java.util.concurrent.Callable;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import com.bobcat00.autobot.Listeners;
import com.bobcat00.autobot.Commands;
import com.bobcat00.autobot.Config;

public class AutoBot extends JavaPlugin
{
    Config config;
    Listeners listeners;
    
    @Override
    public void onEnable()
    {
        config = new Config(this);
        saveDefaultConfig();
        
        boolean printDisclaimer = true;
        
        // Check if the API key has been entered        
        if (config.getApiKey().equals("YOUR_API_KEY"))
        {
            getLogger().info("Edit config.yml to enter your API Key purchased from https://www.cleverbot.com/api/");
            getLogger().info("then restart the server or use /auto reload");
            printDisclaimer = false;
        }
        
        // Reload config so tweak values are checked for proper range
        config.reloadConfig(true);
        
        // Register listeners
        listeners = new Listeners(this);
        
        // Register commands
        this.getCommand("auto").setExecutor(new Commands(this));
        this.getCommand("auto").setTabCompleter(new TabComplete(this));
        
        if (printDisclaimer)
        {
            // Disclaimer required by Cleverbot Terms and Conditions
            getLogger().info("Plugin enabled.");
            getLogger().info("AutoBot uses the Cleverbot API from Existor. See https://www.cleverbot.com/api/");
        }
        
        // Metrics
        Metrics metrics = new Metrics(this);
        if (metrics.isEnabled())
        {
            // Expiration
            metrics.addCustomChart(new Metrics.SimplePie("expiration", new Callable<String>()
            {
                @Override
                public String call() throws Exception
                {
                    final long expirationTime = config.getExpirationMinutes();
                    if (expirationTime == 0)
                        return "0";
                    else if (expirationTime <= 10)
                        return "1-10";
                    else if (expirationTime <= 20)
                        return "11-20";
                    else if (expirationTime <= 30)
                        return "21-30";
                    else if (expirationTime > 30)
                        return ">30";
                    else
                        return "Invalid";
                }
            } ));
            // Single player
            metrics.addCustomChart(new Metrics.SimplePie("single_player", new Callable<String>()
            {
                @Override
                public String call() throws Exception
                {
                    return config.getSinglePlayer() ? "Yes" : "No";
                }
            } ));
            // Tweaks
            metrics.addCustomChart(new Metrics.SimplePie("tweak1", new Callable<String>()
            {
                @Override
                public String call() throws Exception
                {
                    return chartData(config.getTweak1(), "Sensible", "Wacky");
                }
            } ));
            metrics.addCustomChart(new Metrics.SimplePie("tweak2", new Callable<String>()
            {
                @Override
                public String call() throws Exception
                {
                    return chartData(config.getTweak2(), "Shy", "Talkative");
                }
            } ));
            metrics.addCustomChart(new Metrics.SimplePie("tweak3", new Callable<String>()
            {
                @Override
                public String call() throws Exception
                {
                    return chartData(config.getTweak3(), "Self-centered", "Attentive");
                }
            } ));
            getLogger().info("Enabled metrics. You may opt-out by changing plugins/bStats/config.yml");
        }
    }
    
    private String chartData(final int value, final String low, final String high)
    {
        if (value <= 33)
            return low;
        else if (value >= 67)
            return high;
        else
            return "Normal";
    }
        
    @Override
    public void onDisable()
    {
        // HandlerList.unregisterAll(listeners);
    }

}
