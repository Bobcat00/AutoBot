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
    
    public int getTweak2()
    {
        return plugin.getConfig().getInt("tweak2");
    }
    
    public int getTweak3()
    {
        return plugin.getConfig().getInt("tweak3");
    }

}
