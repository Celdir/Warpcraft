Warpcraft - a Minecraft teleportation plugin.
=============================================

### Description ###
Teleportation is acheived through WarpDrives, which are made out of lapis blocks and have a destination name for identification. WarpDrives can be created with "/warpdrive", visited with "/warp", and their use can be restricted to certain players with "/lock". 

### Commands ###
    * /warpdrive <name> - Right-click on lapis block to make it a WarpDrive / destination.
    * /warp <destination> - Warps player to destination.
    * /back - Warps player to previous destination. Can be used consecutively.
    * /lock <usernames> - Right-click on warpdrive to restrict its use to the specified users.
    * /lockadd <usernames> - Right-click on warpdrive to add the specified users to the warpdrive's whitelist.
    * /lockremove <usernames> - Right-click on warpdrive to remove the specified users to the warpdrive's whitelist.
    * /unlock - Right-click on warpdrive to unlock it to any user.

### Build Info ###
This package should be built using
    ant jar

### License ###
This plugin is copywritten under the MIT License, although I doubt anybody would want it.
