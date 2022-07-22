# Day/Night Immersion Plugin

This Minecraft plugin is used to synchronize the time of the real world with the time of a Minecraft server.

Made for version 1.19+

## How to Use
1. Run the following commands in a command prompt or terminal
    1. If on Windows:
        1. `gradlew clean`
        2. `gradlew build`

    2. If on macOS/Linux
        1. `chmod +x gradlew`
        2. `./gradlew clean`
        3. `./gradlew build`
2. The JAR file for the plugin will be located in `build\libs\`. Simply move the JAR file into the plugins folder, and the plugin will start working when the server is started.
3. Use the following command as an OP player `/settimezone <enter time zone here>`