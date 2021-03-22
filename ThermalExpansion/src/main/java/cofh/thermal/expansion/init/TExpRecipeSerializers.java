package cofh.thermal.expansion.init;

import cofh.thermal.expansion.util.managers.dynamo.*;
import cofh.thermal.expansion.util.managers.machine.*;
import cofh.thermal.expansion.util.recipes.dynamo.*;
import cofh.thermal.expansion.util.recipes.machine.*;
import cofh.thermal.lib.util.recipes.ThermalCatalystSerializer;
import cofh.thermal.lib.util.recipes.ThermalFuelSerializer;
import cofh.thermal.lib.util.recipes.ThermalRecipeSerializer;

import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;
import static cofh.thermal.expansion.init.TExpRecipeTypes.*;

public class TExpRecipeSerializers {

    private TExpRecipeSerializers() {

    }

    public static void register() {

        RECIPE_SERIALIZERS.register(ID_RECIPE_FURNACE, () -> new ThermalRecipeSerializer<>(FurnaceRecipe::new, FurnaceRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_SAWMILL, () -> new ThermalRecipeSerializer<>(SawmillRecipe::new, SawmillRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_PULVERIZER, () -> new ThermalRecipeSerializer<>(PulverizerRecipe::new, PulverizerRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_PULVERIZER_RECYCLE, () -> new ThermalRecipeSerializer<>(PulverizerRecycleRecipe::new, PulverizerRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_SMELTER, () -> new ThermalRecipeSerializer<>(SmelterRecipe::new, SmelterRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_SMELTER_RECYCLE, () -> new ThermalRecipeSerializer<>(SmelterRecycleRecipe::new, SmelterRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_INSOLATOR, () -> new InsolatorRecipeSerializer<>(InsolatorRecipe::new, InsolatorRecipeManager.instance().getDefaultEnergy(), InsolatorRecipeManager.instance().getDefaultWater()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_CENTRIFUGE, () -> new ThermalRecipeSerializer<>(CentrifugeRecipe::new, CentrifugeRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_PRESS, () -> new ThermalRecipeSerializer<>(PressRecipe::new, PressRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_CRUCIBLE, () -> new ThermalRecipeSerializer<>(CrucibleRecipe::new, CrucibleRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_CHILLER, () -> new ThermalRecipeSerializer<>(ChillerRecipe::new, ChillerRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_REFINERY, () -> new ThermalRecipeSerializer<>(RefineryRecipe::new, RefineryRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_PYROLYZER, () -> new ThermalRecipeSerializer<>(PyrolyzerRecipe::new, PyrolyzerRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_BREWER, () -> new ThermalRecipeSerializer<>(BrewerRecipe::new, BrewerRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_BOTTLER, () -> new ThermalRecipeSerializer<>(BottlerRecipe::new, BottlerRecipeManager.instance().getDefaultEnergy()));

        RECIPE_SERIALIZERS.register(ID_CATALYST_PULVERIZER, () -> new ThermalCatalystSerializer<>(PulverizerCatalyst::new));
        RECIPE_SERIALIZERS.register(ID_CATALYST_SMELTER, () -> new ThermalCatalystSerializer<>(SmelterCatalyst::new));
        RECIPE_SERIALIZERS.register(ID_CATALYST_INSOLATOR, () -> new ThermalCatalystSerializer<>(InsolatorCatalyst::new));

        RECIPE_SERIALIZERS.register(ID_FUEL_STIRLING, () -> new ThermalFuelSerializer<>(StirlingFuel::new, StirlingFuelManager.instance().getDefaultEnergy(), StirlingFuelManager.MIN_ENERGY, StirlingFuelManager.MAX_ENERGY));
        RECIPE_SERIALIZERS.register(ID_FUEL_COMPRESSION, () -> new ThermalFuelSerializer<>(CompressionFuel::new, CompressionFuelManager.instance().getDefaultEnergy(), CompressionFuelManager.MIN_ENERGY, CompressionFuelManager.MAX_ENERGY));
        RECIPE_SERIALIZERS.register(ID_FUEL_MAGMATIC, () -> new ThermalFuelSerializer<>(MagmaticFuel::new, MagmaticFuelManager.instance().getDefaultEnergy(), MagmaticFuelManager.MIN_ENERGY, MagmaticFuelManager.MAX_ENERGY));
        RECIPE_SERIALIZERS.register(ID_FUEL_NUMISMATIC, () -> new ThermalFuelSerializer<>(NumismaticFuel::new, NumismaticFuelManager.instance().getDefaultEnergy(), NumismaticFuelManager.MIN_ENERGY, NumismaticFuelManager.MAX_ENERGY));
        RECIPE_SERIALIZERS.register(ID_FUEL_LAPIDARY, () -> new ThermalFuelSerializer<>(LapidaryFuel::new, LapidaryFuelManager.instance().getDefaultEnergy(), LapidaryFuelManager.MIN_ENERGY, LapidaryFuelManager.MAX_ENERGY));
    }

}
