package cofh.thermal.core.tileentity.device;

import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.inventory.SimpleItemHandler;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.lib.util.helpers.InventoryHelper;
import cofh.lib.util.helpers.MathHelper;
import cofh.lib.xp.XpStorage;
import cofh.thermal.core.inventory.container.device.DeviceFisherContainer;
import cofh.thermal.lib.tileentity.DeviceTileBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.lib.util.StorageGroup.INPUT;
import static cofh.lib.util.StorageGroup.OUTPUT;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.lib.util.helpers.AugmentableHelper.getAttributeMod;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_FISHER_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;
import static cofh.thermal.lib.common.ThermalConfig.deviceAugments;

public class DeviceFisherTile extends DeviceTileBase implements ITickableTileEntity {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_AREA_EFFECT, TAG_AUGMENT_TYPE_FILTER);

    protected static final int TIME_CONSTANT = 7200;

    protected ItemStorageCoFH inputSlot = new ItemStorageCoFH(item -> filter.valid(item));
    protected SimpleItemHandler internalHandler;

    protected boolean cached;
    protected boolean valid;

    protected static final int RADIUS = 2;
    public int radius = RADIUS;

    protected int process = TIME_CONSTANT / 2;

    public DeviceFisherTile() {

        super(DEVICE_FISHER_TILE);

        inventory.addSlot(inputSlot, INPUT);
        inventory.addSlots(OUTPUT, 15, item -> filter.valid(item));

        xpStorage = new XpStorage(getBaseXpStorage());

        addAugmentSlots(deviceAugments);
        initHandlers();
        internalHandler = new SimpleItemHandler(this, inventory.getOutputSlots());
    }

    @Override
    protected void updateValidity() {

        if (world == null || !world.isAreaLoaded(pos, 1) || Utils.isClientWorld(world)) {
            return;
        }
        int adjWaterSource = 0;
        valid = false;

        BlockPos[] cardinals = new BlockPos[]{
                pos.down(),
                pos.north(),
                pos.south(),
                pos.west(),
                pos.east(),
        };
        for (BlockPos adj : cardinals) {
            FluidState state = world.getFluidState(adj);
            if (state.getFluid().equals(Fluids.WATER)) {
                ++adjWaterSource;
            }
        }
        valid = adjWaterSource > 1;
        cached = true;
    }

    @Override
    protected void updateActiveState() {

        if (!cached) {
            updateValidity();
        }
        super.updateActiveState();
    }

    @Override
    protected boolean isValid() {

        return valid;
    }

    @Override
    public void tick() {

        updateActiveState();

        if (!isActive) {
            return;
        }
        --process;
        if (process > 0) {
            return;
        }
        process = getTimeConstant();

        if (valid) {
            LootTable table = world.getServer().getLootTableManager().getLootTableFromLocation(LootTables.GAMEPLAY_FISHING_FISH);
            LootContext.Builder contextBuilder = new LootContext.Builder((ServerWorld) world).withRandom(world.rand);

            boolean caught = false;
            for (int i = 0; i < baseMod; ++i) {
                for (ItemStack stack : table.generate(contextBuilder.build(LootParameterSets.EMPTY))) {
                    caught |= InventoryHelper.insertStackIntoInventory(internalHandler, stack, false).isEmpty();
                }
            }
            if (xpStorageFeature && caught) {
                xpStorage.receiveXp(1 + world.rand.nextInt(3), false);
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new DeviceFisherContainer(i, world, pos, inventory, player);
    }

    // region HELPERS
    protected int getTimeConstant() {

        if (world == null) {
            return TIME_CONSTANT;
        }
        int constant = TIME_CONSTANT;
        Iterable<BlockPos> area = BlockPos.getAllInBoxMutable(pos.add(-radius, 1 - radius, -radius), pos.add(radius, 0, radius));
        for (BlockPos scan : area) {
            FluidState state = world.getFluidState(scan);
            if (state.getFluid().equals(Fluids.WATER)) {
                constant -= 20;
            }
        }
        boolean isOcean = Utils.hasBiomeType(world, pos, BiomeDictionary.Type.OCEAN);
        boolean isRiver = Utils.hasBiomeType(world, pos, BiomeDictionary.Type.RIVER);
        boolean isRaining = world.isRainingAt(pos);

        if (isOcean) {
            constant /= 3;
        }
        if (isRiver) {
            constant /= 2;
        }
        if (isRaining) {
            constant /= 2;
        }
        return MathHelper.clamp(constant, TIME_CONSTANT / 20, TIME_CONSTANT);
    }
    // endregion

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }

    @Override
    protected void resetAttributes() {

        super.resetAttributes();

        radius = RADIUS;
    }

    @Override
    protected void setAttributesFromAugment(CompoundNBT augmentData) {

        super.setAttributesFromAugment(augmentData);

        radius += getAttributeMod(augmentData, TAG_AUGMENT_RADIUS);
    }
    // endregion
}
