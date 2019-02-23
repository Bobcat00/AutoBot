# AutoBot

*Chatbot plugin for CraftBukkit/Spigot servers*

**Note:** This plugin will not work unless you purchase an API key from Existor at
https://www.cleverbot.com/api/ Prices start at US$10.

## Description

Are your players bored? Are they looking for a friend to talk to? With this
plugin, they'll never be lonely again. If a player sends a chat message starting
with one of the plugin's trigger words, such as "Auto", the player's message
will be passed to the Cleverbot service and Cleverbot's reply will be shown in
the chat. If only a single player is on the server, all messages the player
types in the chat will be sent to Cleverbot, even without a trigger word. Your
players can have long conversations with Cleverbot.

You can try out Cleverbot for free at https://www.cleverbot.com

## Installation

You will need an API key purchased from Existor https://www.cleverbot.com/api/
Various monthly and pay-as-you-go pricing plans are available depending on how
many chatbot messages your server passes to Cleverbot. Please note that I do
NOT get any payment; you are paying the company running the Cleverbot service.

After you get your API Key, put the plugin .jar file in your plugins folder and
restart your server. Edit config.yml to enter your API key where indicated, then
either restart your server again or issue /auto reload

A note about your API key: Be careful not to post it publicly (for example by
posting a copy of your config.yml file). If someone uses your API key, they will
use the Cleverbot calls you paid for!

## Commands

**/auto clear** - Clears the conversation history. Cleverbot keeps track of the
conversation history in order to provide somewhat coherent conversations. This
command will cause the conversation history to be cleared and Cleverbot will
consider subsequent messages to be part of a new conversation.

**/auto help** - Displays a help message listing all commands.

**/auto reload** - Reloads the config file.

**/auto status** - Shows the plugin status including version number and tweak
settings.

**/auto tweak1 <n>** -\
**/auto tweak2 <n>** -\
**/auto tweak3 <n>** - These three commands can be used to tweak Cleverbot's
responses. The values are 0 to 100 and are interpreted as follows:

tweak1: 0=sensible to 100=wacky\
tweak2: 0=shy to 100=talkative\
tweak3: 0=self-centered to 100=attentive

The default for each of the tweak values is 50.

## Permissions

**autobot.message** - Allows Cleverbot to respond to player's chat (default true)

**autobot.command** - Required permission for all commands (default op)

**autobot.command.clear** - Allows use of the clear command (default op)

**autobot.command.help** - Allows use of the help command (default op)

**autobot.command.reload** - Allows use of the reload command (default op)

**autobot.command.status** - Allows use of the status command (default op)

**autobot.command.tweak** - Allows use of the tweak commands (default op)

## Configuration

**api-key** - This is where you enter your purchased API key.

**expiration-minutes** - Cleverbot keeps track of the conversation history. After
this time (default 15 minutes), the conversation history will be cleared and
Cleverbot will consider subsequent messages to be part of a new conversation.

**single-player** - If this is true, Cleverbot will respond to all chat messages,
even without a trigger word, if there's only one player on the server.

**triggers** - This is a list of trigger words that will cause the chat message to
be sent to Cleverbot. The words are case insensitive and must be present at the
start of the message to match.

**tweak1, tweak2, tweak3** - These three parameters can be used to tweak Cleverbot's
responses. The values are 0 to 100 and are interpreted as follows:
tweak1: 0=sensible to 100=wacky\
tweak2: 0=shy to 100=talkative\
tweak3: 0=self-centered to 100=attentive\
The default for each of the tweak values is 50.

## Source code

Source code is available online at https://github.com/Bobcat00/AutoBot

## Miscellaneous

Bobcat00 is not associated with Existor or Cleverbot, and receives no payment
from them.

This plugin is not associated with and uses no code from a plugin of the same
name created by Techdoodle.

This program is distributed WITHOUT ANY WARRANTY; without even the implied
warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. **If you purchase
an API Key and you can't get this plugin to work on your server, I have no
liability for your loss.** See the GNU General Public License Version 3 for more.

Copyright 2018 Bobcat00
