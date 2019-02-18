/*  CleverBot API Wrapper - A simple Java API Wrapper for CleverBot!
 *
 *  Copyright (C) 2016 Michael Flaherty // michaelwflaherty.com // michaelwflaherty@me.com
 *  Modified to add tweak settings by Bobcat00, 2019
 * 
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * this program. If not, see http://www.gnu.org/licenses/.
 */
 
package com.michaelwflaherty.cleverbotapi;

import java.net.*;

//import org.bukkit.Bukkit;

import java.io.*;
import com.google.gson.*;

/**
 * A CleverBot Query
 *
 * A <code>CleverBotQuery</code> object contains all of the necessary tools to
 * query CleverBot API's servers
 *
 * @author Michael W Flaherty
 * @version 1.0.2
 */
public class CleverBotQuery
{
    /* Constants */
    
    /**
    * URL String used for CleverBot connection. <b>Used internally</b>
    */
    public static final String URL_STRING = "https://www.cleverbot.com/getreply?key=";

    /* Instance Variables */
    private String key;
    private String conversationID;
    private String phrase;
    private String response;
    private int random;
    private int tweak1;
    private int tweak2;
    private int tweak3;

    /* Constructor */

    /**
     * CleverBotQuery constructor
     * <p>
     * Conversation identifer is set empty, thus calling sendRequest immediately after
     * instantiation will create a new conversation
     * </p>
     *
     * @param key API key (cleverbot.com/api)
     * @param phrase input phrase
     * @param tweak1 0=sensible to 100=wacky
     * @param tweak2 0=shy to 100=talkative
     * @param tweak3 0=self-centered to 100=attentive
     */
    public CleverBotQuery(String key, String phrase, int tweak1, int tweak2, int tweak3)
    {
        this.key = key;
        this.conversationID = "";
        this.phrase = phrase;
        this.tweak1 = Math.min(Math.max(tweak1, 0), 100);
        this.tweak2 = Math.min(Math.max(tweak2, 0), 100);
        this.tweak3 = Math.min(Math.max(tweak3, 0), 100);
    }

    /**
     * CleverBotQuery constructor
     * <p>
     * Conversation identifer is set empty, thus calling sendRequest immediately after
     * instantiation will create a new conversation
     * </p>
     *
     * @param key API key (cleverbot.com/api)
     * @param phrase input phrase
     */
    public CleverBotQuery(String key, String phrase)
    {
        this(key, phrase, 50, 50, 50);
    }

    /* Getters & Setters */
    
    /**
    * Gets the input phrase
    *
    * @return String object containing the input phrase
    */
    public String getPhrase()
    {
        return this.phrase;
    }

    /**
    * Sets the input phrase to be send in the CleverBot query.
    *
    * @param phrase input phrase to be sent to CleverBot
    */
    public void setPhrase(String phrase)
    {
        this.phrase = phrase;
    }

    /**
    * Gets the response from a request. Must be used after <code>sendRequest()</code>
    *
    * @return String object containing the CleverBot response
    */
    public String getResponse()
    {
        return this.response;
    }

    /**
    * Sets the response value. Only used internally
    *
    * @param response CleverBot response
    */
    private void setResponse(String response)
    {
        this.response = response;
    }

    /**
    * Gets the API key
    *
    * @return String object containing the API key
    */
    public String getAPIKey()
    {
        return this.key;
    }

    /**
    * Sets the unique conversation identifier
    *
    * <p>
    * Set to empty ("") in order to start a new conversation with the CleverBot.
    * </p>
    * @param conversationID unique conversation identifier
    */
    public void setConversationID(String conversationID)
    {
        this.conversationID = conversationID;
    }

    /**
    * Sets the random number. Only used internally
    *
    * @param number number to set
    */
    private void setRandomNumber(int number)
    {
        this.random = number;
    }

    /**
    * Gets the random number
    *
    * <p>
    * CleverBot API queries provide a random number greater than zero and less than one thousand.
    * </p>
    * @return random number as an integer
    */
    public int getRandomNumber()
    {
        return this.random;
    }

    /**
    * Gets conversation identifier.
    * <p>
    * This is a unique string used to identify conversation context to the CleverBot API servers.
    * This string is updated after every <code>sendRequest()</code> call.
    * </p>
    * @return String object containing the conversation identifier
    */
    public String getConversationID()
    {
        return this.conversationID;
    }

    /**
    * Gets the tweak1 parameter
    * </p>
    * @return 0=sensible to 100=wacky
    */
    public int getTweak1()
    {
        return this.tweak1;
    }

    /**
    * Sets the tweak1 parameter
    * </p>
    * @param tweak1 0=sensible to 100=wacky
    */
    public void SetTweak1(int tweak1)
    {
        this.tweak1 = Math.min(Math.max(tweak1, 0), 100);
    }

    /**
    * Gets the tweak2 parameter
    * </p>
    * @return 0=shy to 100=talkative
    */
    public int getTweak2()
    {
        return this.tweak2;
    }

    /**
    * Sets the tweak2 parameter
    * </p>
    * @param tweak2 0=shy to 100=talkative
    */
    public void SetTweak2(int tweak2)
    {
        this.tweak2 = Math.min(Math.max(tweak2, 0), 100);
    }

    /**
    * Gets the tweak3 parameter
    * </p>
    * @return 0=self-centered to 100=attentive
    */
    public int getTweak3()
    {
        return this.tweak3;
    }

    /**
    * Sets the tweak3 parameter
    * </p>
    * @param tweak3 0=self-centered to 100=attentive
    */
    public void SetTweak3(int tweak3)
    {
        this.tweak3 = Math.min(Math.max(tweak3, 0), 100);
    }

    /**
    * Sends request to CleverBot servers. API key and phrase should be set prior to this call
    *
    * @throws IOException exception upon query failure
    */
    public void sendRequest() throws IOException
    {
        /* Create & Format URL */
        String urlString = CleverBotQuery.formatRequest(CleverBotQuery.URL_STRING, this.key, this.phrase, this.conversationID, this.tweak1, this.tweak2, this.tweak3);
        
        // Uncomment for debugging, also import org.bukkit.Bukkit above
        //int keyStart = URL_STRING.length();
        //int keyEnd = urlString.indexOf("&input=", keyStart);
        //Bukkit.getLogger().info(urlString.substring(0, keyStart) + "*****" + urlString.substring(keyEnd));
        
        URL url = new URL(urlString);

        /* Open Connection */
        URLConnection urlConnection = url.openConnection();

        /* Read input */
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine = in.readLine();

        /* Create JSON Object */
        JsonObject jsonObject = new JsonParser().parse(inputLine).getAsJsonObject(); // convert to JSON

        /* Get params */
        this.setConversationID(jsonObject.get("cs").getAsString()); // update conversation ID
        this.setResponse(jsonObject.get("output").getAsString()); // get output
        this.setRandomNumber(Integer.parseInt(jsonObject.get("random_number").getAsString())); // get output
        in.close(); // close!
    }

    /* Helper Methods */
    /**
    * URL request formater
    *
    * @param url starting url to connect to 
    * @param key API key (cleverbot.com/api)
    * @param phrase input to be sent to CleverBot servers
    * @param conversationID unique conversation identifer
    * @param tweak1 0=sensible to 100=wacky
    * @param tweak2 0=shy to 100=talkative
    * @param tweak3 0=self-centered to 100=attentive
    * @return String object containing properly formatted URL
    */
    private static String formatRequest(String url, String key, String phrase, String conversationID, int tweak1, int tweak2, int tweak3)
    {
        String formattedPhrase = phrase.replaceAll("\\s+", "+");
        //                    u k        f                           c 1 2 3
        return String.format("%s%s&input=%s&wrapper=Headline22JavaAPI%s%s%s%s",
                             url,
                             key,
                             formattedPhrase,
                             (conversationID.equals("")) ? "" : ("&cs=" + conversationID),
                             (tweak1 == 50) ? "" : ("&cb_settings_tweak1=" + tweak1),
                             (tweak2 == 50) ? "" : ("&cb_settings_tweak2=" + tweak2),
                             (tweak3 == 50) ? "" : ("&cb_settings_tweak3=" + tweak3)
                            );
    }
}
