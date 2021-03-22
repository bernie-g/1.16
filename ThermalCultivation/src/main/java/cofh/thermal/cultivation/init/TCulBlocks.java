package cofh.thermal.cultivation.init;

import cofh.lib.block.impl.CakeBlockCoFH;
import cofh.lib.block.impl.DirectionalBlock4Way;
import cofh.lib.block.impl.SoilBlock;
import cofh.lib.block.impl.TilledSoilBlock;
import cofh.lib.block.impl.crops.AttachedStemBlockCoFH;
import cofh.lib.block.impl.crops.StemBlockCoFH;
import cofh.thermal.cultivation.block.FrostMelonBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.ToolType;

import static cofh.lib.util.constants.Constants.CHARGED;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.util.RegistrationHelper.*;
import static cofh.thermal.cultivation.init.TCulIDs.*;
import static net.minecraft.block.AbstractBlock.Properties.create;

public class TCulBlocks {

    private TCulBlocks() {

    }

    public static void register() {

        registerPlants();
        registerFoods();
        registerStorage();
        registerMisc();
    }

    public static void setup() {

        FireBlock fire = (FireBlock) Blocks.FIRE;

        fire.setFireInfo(BLOCKS.get(block(ID_BARLEY)), 60, 20);
        fire.setFireInfo(BLOCKS.get(block(ID_CORN)), 60, 20);
    }

    // region HELPERS
    private static void registerPlants() {

        // ANNUAL
        registerAnnual(ID_BARLEY);
        registerTallAnnual(ID_CORN);
        registerAnnual(ID_ONION);
        registerAnnual(ID_RADISH);
        registerAnnual(ID_RICE);
        registerAnnual(ID_SADIROOT);
        registerAnnual(ID_SPINACH);

        // PERENNIAL
        registerPerennial(ID_BELL_PEPPER);
        registerPerennial(ID_EGGPLANT);
        registerPerennial(ID_GREEN_BEAN);
        registerPerennial(ID_PEANUT);
        registerPerennial(ID_STRAWBERRY);
        registerPerennial(ID_TOMATO);

        registerPerennial(ID_COFFEE, 6);
        // registerTallPerennial(ID_HOPS);
        registerPerennial(ID_TEA);

        // STEM
        registerBlock(ID_FROST_MELON, () -> new FrostMelonBlock(create(Material.GOURD, MaterialColor.CYAN).tickRandomly().hardnessAndResistance(1.0F).harvestTool(ToolType.AXE).sound(SoundType.SNOW)), Rarity.UNCOMMON);
        registerBlockOnly(ID_FROST_MELON_STEM, () -> new StemBlockCoFH(create(Material.PLANTS).tickRandomly().doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.WOOD)).crop(BLOCKS.getSup(ID_FROST_MELON)).seed(ITEMS.getSup(seeds(ID_FROST_MELON))));
        registerBlockOnly(ID_FROST_MELON_STEM_ATTACHED, () -> new AttachedStemBlockCoFH(create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.STEM)).crop(BLOCKS.getSup(ID_FROST_MELON)).seed(ITEMS.getSup(seeds(ID_FROST_MELON))));
    }

    private static void registerFoods() {

        registerBlock(ID_CHOCOLATE_CAKE, () -> new CakeBlockCoFH(create(Material.CAKE).hardnessAndResistance(0.5F).sound(SoundType.CLOTH), TCulFoods.CHOCOLATE_CAKE));
        registerBlock(ID_SPICE_CAKE, () -> new CakeBlockCoFH(create(Material.CAKE).hardnessAndResistance(0.5F).sound(SoundType.CLOTH), TCulFoods.SPICE_CAKE));
    }

    private static void registerStorage() {

        registerBlock(block(ID_BARLEY), () -> new HayBlock(create(Material.ORGANIC, MaterialColor.GOLD).hardnessAndResistance(0.5F).sound(SoundType.PLANT).harvestTool(ToolType.HOE)));
        registerBlock(block(ID_CORN), () -> new Block(create(Material.WOOD, MaterialColor.YELLOW).hardnessAndResistance(1.5F).sound(SoundType.SCAFFOLDING).harvestTool(ToolType.AXE)));
        registerBlock(block(ID_ONION), () -> new Block(create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(1.5F).sound(SoundType.SCAFFOLDING).harvestTool(ToolType.AXE)));
        registerBlock(block(ID_RADISH), () -> new Block(create(Material.WOOD, MaterialColor.RED_TERRACOTTA).hardnessAndResistance(1.5F).sound(SoundType.SCAFFOLDING).harvestTool(ToolType.AXE)));
        registerBlock(block(ID_SADIROOT), () -> new Block(create(Material.WOOD, MaterialColor.GREEN_TERRACOTTA).hardnessAndResistance(1.5F).sound(SoundType.SCAFFOLDING).harvestTool(ToolType.AXE)));
        registerBlock(block(ID_SPINACH), () -> new Block(create(Material.WOOD, MaterialColor.GREEN).hardnessAndResistance(1.5F).sound(SoundType.SCAFFOLDING).harvestTool(ToolType.AXE)));

        registerBlock(block(ID_BELL_PEPPER), () -> new Block(create(Material.WOOD, MaterialColor.RED_TERRACOTTA).hardnessAndResistance(1.5F).sound(SoundType.SCAFFOLDING).harvestTool(ToolType.AXE)));
        registerBlock(block(ID_EGGPLANT), () -> new Block(create(Material.WOOD, MaterialColor.PURPLE_TERRACOTTA).hardnessAndResistance(1.5F).sound(SoundType.SCAFFOLDING).harvestTool(ToolType.AXE)));
        registerBlock(block(ID_GREEN_BEAN), () -> new Block(create(Material.WOOD, MaterialColor.GREEN).hardnessAndResistance(1.5F).sound(SoundType.SCAFFOLDING).harvestTool(ToolType.AXE)));
        registerBlock(block(ID_HOPS), () -> new Block(create(Material.WOOD, MaterialColor.GREEN).hardnessAndResistance(1.5F).sound(SoundType.SCAFFOLDING).harvestTool(ToolType.AXE)));
        registerBlock(block(ID_STRAWBERRY), () -> new Block(create(Material.WOOD, MaterialColor.RED_TERRACOTTA).hardnessAndResistance(1.5F).sound(SoundType.SCAFFOLDING).harvestTool(ToolType.AXE)));
        registerBlock(block(ID_TOMATO), () -> new Block(create(Material.WOOD, MaterialColor.RED).hardnessAndResistance(1.5F).sound(SoundType.SCAFFOLDING).harvestTool(ToolType.AXE)));

        registerBlock(block(ID_RICE), () -> new DirectionalBlock4Way(create(Material.WOOL, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(0.5F).sound(SoundType.WART)));

        registerBlock(block(ID_COFFEE), () -> new DirectionalBlock4Way(create(Material.WOOL, MaterialColor.RED_TERRACOTTA).hardnessAndResistance(0.5F).sound(SoundType.WART)));
        registerBlock(block(ID_PEANUT), () -> new DirectionalBlock4Way(create(Material.WOOL, MaterialColor.BROWN_TERRACOTTA).hardnessAndResistance(0.5F).sound(SoundType.WART)));
        registerBlock(block(ID_TEA), () -> new DirectionalBlock4Way(create(Material.WOOL, MaterialColor.GREEN_TERRACOTTA).hardnessAndResistance(0.5F).sound(SoundType.WART)));
    }

    private static void registerMisc() {

        registerBlock(ID_PHYTOSOIL, () -> new SoilBlock(create(Material.EARTH).tickRandomly().hardnessAndResistance(0.8F).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND).setLightLevel((state) -> state.get(CHARGED) > 0 ? 7 : 0)));
        registerBlock(ID_PHYTOSOIL_TILLED, () -> new TilledSoilBlock(create(Material.EARTH).tickRandomly().hardnessAndResistance(0.8F).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND).setLightLevel((state) -> state.get(CHARGED) > 0 ? 7 : 0)).dirt(BLOCKS.getSup(ID_PHYTOSOIL)));
    }
    // endregion
}
