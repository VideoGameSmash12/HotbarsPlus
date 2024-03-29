## Hotbars+
Hotbars+ (also known as "MultiHotbar" in earlier versions) is a mod for Minecraft that dramatically increases how many hotbars can be saved by paginating the vanilla hotbar storage system.

### How does it work?
The mod works by hijacking the saved hotbar system Minecraft uses to use a different file to load and save from in place of the traditional `hotbar.nbt`, hot-swapping files for each page.

### What is required to use the mod?
The requirements depend on the version of Minecraft you're using. To help guide you, here's a table:

| Version            | Fabric Loader | Fabric API | Cotton Client Commands |
|--------------------| ------------- | ---------- | ---------------------- |
| 1.14.4 - 1.15.x    | Yes           | Yes        | Optional*              |
| 1.16.1 - 1.16.4    | Yes           | Yes        | Optional*              |
| 1.16.5             | Yes           | Yes        | No                     |
| 1.17.x - 1.18.2    | Yes           | Yes        | No                     |
| 1.19 - 1.19.4      | Yes           | Yes        | No                     |
| 1.20 - 1.20.2      | Yes           | Yes        | No                     |

\* = The mod will still launch without it, but you won't be able to run client-side commands like /hotbars+.
