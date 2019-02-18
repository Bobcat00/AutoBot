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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import com.bobcat00.autobot.AutoBot;

public class Config
{
    private AutoBot plugin;
    
    public Config(AutoBot plugin)
    {
        this.plugin = plugin;
    }
    
    public String getApiKey()
    {
        return plugin.getConfig().getString("api-key");
    }

    public long getExpirationMinutes()
    {
        return plugin.getConfig().getLong("expiration-minutes");
    }

    public boolean getSinglePlayer()
    {
        return plugin.getConfig().getBoolean("single-player");
    }

    public List<String> getTriggers()
    {
        return plugin.getConfig().getStringList("triggers");
    }
    
    public int getTweak1()
    {
        return plugin.getConfig().getInt("tweak1");
    }
    
    public void setTweak1(int tweak1)
    {
        plugin.getConfig().set("tweak1",  tweak1);
    }
    
    public int getTweak2()
    {
        return plugin.getConfig().getInt("tweak2");
    }
    
    public void setTweak2(int tweak2)
    {
        plugin.getConfig().set("tweak2",  tweak2);
    }
    
    public int getTweak3()
    {
        return plugin.getConfig().getInt("tweak3");
    }
    
    public void setTweak3(int tweak3)
    {
        plugin.getConfig().set("tweak3",  tweak3);
    }
    
    //--------------------------------------------------------------------------
    
    // Reload config from disk.  Also check tweak values for proper range.
    
    public void reloadConfig(boolean validate)
    {
        plugin.reloadConfig();
        
        if (validate)
        {
            if (plugin.getConfig().getInt("tweak1") < 0 ||
                plugin.getConfig().getInt("tweak1") > 100)
            {
                plugin.getLogger().info("Invalid tweak1 value in config.");
            }
            
            if (plugin.getConfig().getInt("tweak2") < 0 ||
                plugin.getConfig().getInt("tweak2") > 100)
            {
                plugin.getLogger().info("Invalid tweak2 value in config.");
            }
            
            if (plugin.getConfig().getInt("tweak3") < 0 ||
                plugin.getConfig().getInt("tweak3") > 100)
            {
                plugin.getLogger().info("Invalid tweak3 value in config.");
            }
        }
    }
    
    //--------------------------------------------------------------------------
    
    // Save config to disk with embedded comments. Any change to the config file
    // format must also be changed here. Newlines are written as \n so they will
    // be the same on all platforms.
    
    public void saveConfig()
    {
        try
        {
            File outFile = new File(plugin.getDataFolder(), "config.yml");
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile.getAbsolutePath()));
            
            writer.write("# Enter your API key purchased from https://www.cleverbot.com/api/" + "\n");
            writer.write("api-key: " + plugin.getConfig().getString("api-key") + "\n");
            writer.write("\n");
            
            writer.write("# Time elapsed with no conversation to clear conversation history" + "\n");
            writer.write("expiration-minutes: " + plugin.getConfig().getLong("expiration-minutes") + "\n");
            writer.write("\n");
            
            writer.write("# Set to true for AutoBot to respond to all chat messages if there's only one player" + "\n");
            writer.write("single-player: " + plugin.getConfig().getBoolean("single-player") + "\n");
            writer.write("\n");
            
            writer.write("# List of trigger words in single quotes (case insensitive). Suggest having a trailing space." + "\n");
            writer.write("triggers:" + "\n");
            List<String> triggers = plugin.config.getTriggers();
            for (String trigger : triggers)
            {
                writer.write("- '" + trigger.replaceAll("'","''") + "'" + "\n");
            }
            writer.write("\n");
            
            writer.write("# Parameters to tweak Cleverbot's responses. Values are from 0 to 100. Defaults are 50." + "\n");
            writer.write("# tweak1 0=sensible to 100=wacky" + "\n");
            writer.write("# tweak2 0=shy to 100=talkative" + "\n");
            writer.write("# tweak3 0=self-centered to 100=attentive" + "\n");
            writer.write("tweak1: " + plugin.getConfig().getInt("tweak1") + "\n");
            writer.write("tweak2: " + plugin.getConfig().getInt("tweak2") + "\n");
            writer.write("tweak3: " + plugin.getConfig().getInt("tweak3") + "\n");
            
            writer.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //plugin.getLogger().info("Exception creating config file.");
        }
        
        plugin.reloadConfig();
        
    }

}
