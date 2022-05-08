# Introduction
The second group projoect related with @<Academia de Código_> #68 Bootcamp @ Porto. We were faced with our 2nd challenge... create a multiplayer game! As such we decided to simulate a server, where each player had his own socket that could connect to it and start.

# Western Game
**You've been challenged for a gun duel.**
There's no going back, otherwise your pride and glory as a duelist will be stained.
Don't worry there's someone watching over the duel, **in case someone cheats** or runs away... you only **DRAW** when the timer hits 0!
You have 2 options... either you win or lose. So what's it gonna be, **cowboy**?

## How To
 - Run the program.
 - Connect to the server port (8080).
 - In order for the other player to join:
    - Needs to be in the same network as the host.
    - Connect to the same IP as the host and the server port.
 - No players around you to test? No problem! There's other options like **Netcat** or **Nmap** to simulate each player.
    - **Netcat**: in 2 different terminals type "nc localhost 8080". 

## Requirements
 - 2 Players.
 - Each player name.
 
## Rules
 - Wait until the other player joins and the timer to start.
 - Once the timer ends... SHOOT! (by pressing Enter).
 - The results will reveal the winner and the reaction time.

## Libraries
 - [Prompt View](https://github.com/academia-de-codigo/prompt-view) made by **Jedi Masters** at **Academia de Código**

## Developers
This project was made by **Code Cadets** at **Academia de Código**, Team #~~Magnific0x~~ Argicultores
 - Nuno Teixeira
 - Igor Koury
 - Valentim Garcia
 - Yevheniy Shevchuk
