# Hotbars+
**Hotbars+** (also known as MultiHotbar internally and in earlier versions) is a client-side Fabric mod that adds page support to the vanilla saved hotbar system. Ever needed more space to store your items? If so, then this is the mod for you.

## Benefits
Here are some benefits you get when using this mod:
* **Huge capacity increase**: 9,223,372,036,854,775,807 more pages (each containing 9 rows) to work with. All pages are compatible with vanilla Minecraft.
* **Easy backup system**: Press one button and the page you are actively using gets copied to a separate folder. Great for in case something goes wrong.
* **Fast file hotswapping**: Press one button and you can load in another hotbar file with ease. It also makes importing very simple: just copy a file into a certain folder, rename it to follow the format, and it'll load right up.

## How does it work?
The mod works by hijacking the saved hotbar system Minecraft uses to use a different file to load and save from in place of the traditional `hotbar.nbt`. It hotswaps files for each page. A simple concept, yes, but it works.

## Where is everything stored?

### Hotbar Pages
Hotbar pages after 0 are stored in the `hotbars` folder in the root of the Minecraft client installation. This is to retain backwards compatibility with vanilla Minecraft. Each file is saved in this format:
```none
hotbar.<number>.nbt
```

### Backups
Backups are stored in the `backups` folder, also in the root of the Minecraft client installation. Each file is saved in this format: 
```none
[<unix timestamp>] <file name>
```

## Comparison between Hotbars+ and More Toolbars
|   | More Toolbars | Hotbars+ | Both
| - | :-----------: | :------: | :--:
| Increased Hotbar Capacity  | :heavy_check_mark: | :heavy_check_mark:          | :heavy_check_mark:
| Backup Feature             | :x:                | :heavy_check_mark:          | :heavy_check_mark:
| Increased Row Count        | :heavy_check_mark: | :x:                         | :heavy_check_mark
| Quick and Easy Hotswapping | :x:                | :heavy_check_mark:          | :heavy_check_mark:
| Client-side Commands       | :x:                | :heavy_check_mark:          | :heavy_check_mark:
| Actively Maintained        | :x:                | :heavy_check_mark:          | :grey_question:
| Vanilla Compatibility      | :x:                | :heavy_check_mark:          | :x:
| Total Page Capacity*       | 1                  | 9,223,372,036,854,775,808   | 9,223,372,036,854,775,808
| Total Row Capacity*        | 27                 | 83,010,348,331,692,982,272  | 249,031,044,995,078,946,816
| Total Item Capacity*       | 243                | 747,093,134,985,236,840,448 | 2,241,279,404,955,710,521,344
*\*=These numbers may not be entirely accurate as they were calculated mathematically.*

While it is theoretically possible to run the two mods together, it's not recommended as they could conflict and cause unexpected behavior.

## Requirements
Hotbars+ requires at least Minecraft 1.16.5 and at least v0.31.0 of the Fabric API. You can download the latest version of the Fabric API here: [https://www.curseforge.com/minecraft/mc-mods/fabric-api](https://www.curseforge.com/minecraft/mc-mods/fabric-api). You will also need the latest version of the Cloth Config API for Fabric, which you can download here: [https://www.curseforge.com/minecraft/mc-mods/cloth-config](https://www.curseforge.com/minecraft/mc-mods/cloth-config). 

## Installation
Installing Hotbars+ is simple. Just download the latest release jar (be sure to get the regular `Hotbars+-<version>.jar` and not the sources or dev files) from [the releases page](https://github.com/VideoGameSmash12/HotbarsPlus/releases/) and put it into the mods folder.