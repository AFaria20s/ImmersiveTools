## Immersive Tools

**Mod ID:** `immersivetools`

**Version:** Minecraft Forge 1.16.5  
**Author:** [AfonsoFaria20s](https://github.com/AFaris20s)  
**Repository:** [AfonsoFaria20s/ImmersiveTools](https://github.com/AFaris20s/ImmersiveTools)

---

## About the Mod

**Immersive Tools** is an expansion mod for Minecraft focused on **non-destructive tool progression**. It introduces a deep, modular system for applying and upgrading permanent enhancements, known as **Perks**, to existing equipment. This system rewards players for engaging with challenging, late-game content by allowing them to continually invest in their favorite tools and weapons.

### Features

- **Modular Perk System**
    - Apply permanent enhancements (**Perks**) to standard tools and weapons (Pickaxes, Swords, Axes, etc.).
    - Perks are initially applied using **Perk-specific Gems** (e.g., `lightweight_gem`).

- **Tiered Progression**
    - Perks can be upgraded to higher tiers (currently up to **Tier 2**) using universal upgrade materials.
    - **Refined Essence** is used as the universal catalyst to upgrade existing Perks.

- **Mid-Game Integration**
    - The core upgrade component (**Essence**) is sourced from defeating **Guardians** in Ocean Monuments, tying tool progression to key vanilla challenges.

---

## Download

### For Players

1. Download and install **[Minecraft Forge 1.16.5](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.16.5.html)**.
2. Download the latest version of **Immersive Tools** from the [Releases](https://github.com/AFaris20s/ImmersiveTools/releases) page.
3. Place the `.jar` file inside your Minecraft `mods` folder:
```.minecraft/mods```

5. Launch the game using the **Forge** profile.

---

## Developer Setup (Manual Installation)

If you want to contribute or modify the mod locally:

1. **Clone the repository:**

```bash
git clone [https://github.com/AfonsoFaria20s/ImmersiveTools.git](https://github.com/AFaris20s/ImmersiveTools.git)
cd ImmersiveTools
```
2. **Import the project** into your IDE (e.g. IntelliJ IDEA or Eclipse) as a Gradle project.
3. **Run Gradle setup tasks:**
```bash
./gradlew genEclipseRuns      # for Eclipse
./gradlew genIntelliJRuns     # for IntelliJ IDEA
./gradlew build 
```
4. Once the environment is set up, you can start the client directly from your IDE using the ```runClient``` configuration to test the mod.
   
## License 
This project is licensed under the Apache 2.0 License.

See the [LICENSE](https://github.com/AFaria20s/ImmersiveTools/blob/master/LICENSE.txt) for more information.

## Feedback && Contribution
Feedback and contributions are highly encouraged.

If you find bugs or want to contribute:

- Open an issue on GitHub 
- Or create a pull request with your improvements