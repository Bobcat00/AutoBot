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

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.bobcat00.autobot.AutoBot;
import com.michaelwflaherty.cleverbotapi.CleverBotQuery;

// This class listens for chat events. It also provides public methods for
// changing Cleverbot's settings.

public final class Listeners implements Listener
{
    private AutoBot plugin;
    private CleverBotQuery session;
    private long timestamp = 0L;
    
    // Constructor
    
    public Listeners(AutoBot plugin)
    {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    // Clear conversation history
    
    public void clearConversationHistory()
    {
        if (session != null)
        {
            session.setConversationID("");
        }
    }
    
    // Set tweak variables
    
    public void setTweak1(int tweak1)
    {
        if (session != null)
        {
            session.SetTweak1(tweak1);
        }
    }
    
    public void setTweak2(int tweak2)
    {
        if (session != null)
        {
            session.SetTweak2(tweak2);
        }
    }
    
    public void setTweak3(int tweak3)
    {
        if (session != null)
        {
            session.SetTweak3(tweak3);
        }
    }
    
    // Callback routine
    
    private interface BotCallback
    {
        public void botResponse(String response);
    }
    
    // Handle IOException when sending message to bot
    
    private void logException(IOException e)
    {
        Matcher exMsgStatusCodeMatcher = Pattern.compile("^Server returned HTTP response code: \\d+").matcher(e.getMessage());
        
        if(exMsgStatusCodeMatcher.find())
        {
            plugin.getLogger().info(exMsgStatusCodeMatcher.group(0));
        }
        else
        {
            plugin.getLogger().info("Server returned " + e.getClass().getSimpleName());
        }
    }
    
    // Send message to bot
    
    private void sendMessage(final BotCallback callback) // static ???
    {
        // Run outside of the tick loop
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable()
        {
            @Override
            public void run()
            {
                boolean exceptionOccurred = false;
                
                // Send message to bot and wait for response
                try
                {
                    session.sendRequest();
                } 
                catch (IOException e)
                {
                    logException(e);
                    exceptionOccurred = true;
                    //e.printStackTrace();
                }
                
                // Get response from bot
                String response;
                if (!exceptionOccurred)
                {
                    response = session.getResponse();
                }
                else
                {
                    response = "I'm sorry, an error occurred.";
                }
                
                // go back to the tick loop
                Bukkit.getScheduler().runTask(plugin, new Runnable()
                {
                    @Override
                    public void run() {
                        // call the callback with the result
                        callback.botResponse(response);
                    }
                }); // delay can go here if needed
            }
        }
        );
    }
    
    // Chat event handler
    
    // Use MONITOR so we get called last. This means we're not allowed to cancel the event.
    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent e)
    {
        Player player = e.getPlayer();
        
        if (e.isCancelled() ||
            !player.hasPermission("autobot.message") ||
            plugin.config.getApiKey().equals("YOUR_API_KEY"))
        {
            return;
        }
        
        // Check if there's only one player or message starts with trigger phrase.
        // Need to check trigger phrase even with one player so trigger can be removed.
        
        String message = e.getMessage();
        Boolean processMessage = false;
        
        List<String> triggers = plugin.config.getTriggers();
        for (String trigger : triggers)
        {
            if (message.trim().toLowerCase().startsWith(trigger.toLowerCase()))
            {
                message = message.substring(trigger.length());
                processMessage = true;
                break;
            }
        }
        
        // Also process message if getSinglePlayer is true and there's only one player
        
        if (plugin.config.getSinglePlayer() && (Bukkit.getServer().getOnlinePlayers().size() == 1))
        {
            processMessage = true;
        }
        
        // Process message
        
        if ((message.length() > 0) && processMessage)
        {
            //plugin.getLogger().info("\"" + message + "\"");
            
            // Send message to CleverBot
            
            long ts = System.currentTimeMillis();
            
            if (session == null)
            {
                this.session = new CleverBotQuery(plugin.config.getApiKey(),
                                                  message,
                                                  plugin.config.getTweak1(),
                                                  plugin.config.getTweak2(),
                                                  plugin.config.getTweak3());
            }
            else
            {
                session.setPhrase(message);
                // Check for conversation expiration
                if (ts-timestamp >= plugin.config.getExpirationMinutes() * 60L * 1000L )
                {
                    session.setConversationID("");
                    plugin.getLogger().info("Conversation history cleared.");
                }
            }
            
            timestamp = ts;

            // Send message to bot via asynchronous task
            
            sendMessage(new BotCallback()
            {
                @Override
                public void botResponse(String response)
                {
                    Bukkit.broadcastMessage("<" + ChatColor.GRAY + "Cleverbot" + ChatColor.WHITE + "> " + response);
                }
            }
            );
        
        }
        
    }

}
