# Hotbars+
**Hotbars+** (also known as MultiHotbar in earlier versions) is a client-side Fabric mod that adds page support to the vanilla saved hotbar system. Ever needed more space to store your items? If so, then this is the mod for you.

## Benefits
Here are some benefits you get when using this mod:
* **Huge capacity increase**: Ludicrous increase in total item capacity to work with. All pages are compatible with vanilla Minecraft.
* **Easy backup system**: Press one button and the page you are actively using gets copied to a separate folder. Great for in case something goes wrong.
* **Fast file hotswapping**: Press one button and you can load in another hotbar file with ease. It also makes importing very simple: just copy a file into a certain folder, rename it to follow the format, and it'll load right up.

## How does it work?
The mod works by hijacking the saved hotbar system Minecraft uses to use a different file to load and save from in place of the traditional `hotbar.nbt`. It hotswaps files for each page. A simple concept, yes, but it works.

## Where is everything stored?

### Hotbar Pages
Hotbar pages before and after 0 are stored in the `hotbars` folder in the root of the Minecraft client installation. This is to retain backwards compatibility with vanilla Minecraft. Each file is saved in this format:
```none
hotbar.<number>.nbt
```

### Backups
Backups are stored in the `backups` folder, also in the root of the Minecraft client installation. Each file is saved in this format: 
```none
[<unix timestamp>] <file name>
```

## Requirements
Hotbars+ requires Minecraft 1.16.5 and at least v0.31.0 of the Fabric API. You can download the latest version of the Fabric API here: [https://www.curseforge.com/minecraft/mc-mods/fabric-api](https://www.curseforge.com/minecraft/mc-mods/fabric-api). You will also need the latest version of the Cloth Config API for Fabric, which you can download here: [https://www.curseforge.com/minecraft/mc-mods/cloth-config](https://www.curseforge.com/minecraft/mc-mods/cloth-config). 

## Installation
Installing Hotbars+ is simple. Just download the latest release jar (be sure to get the regular `Hotbars+-<version>.jar` and not the sources or dev files) from [the releases page](https://github.com/VideoGameSmash12/HotbarsPlus/releases/) and put it into the mods folder.
