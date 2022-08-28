package org.bukkit.block.data;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.SoundGroup;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockSupport;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BlockData extends Cloneable {

    /**
     * Get the Material represented by this block data.
     *
     * @return the material
     */
    @NotNull
    Material getMaterial();

    /**
     * Gets a string, which when passed into a method such as
     * {@link Server#createBlockData(java.lang.String)} will unambiguously
     * recreate this instance.
     *
     * @return serialized data string for this block
     */
    @NotNull
    String getAsString();

    /**
     * Gets a string, which when passed into a method such as
     * {@link Server#createBlockData(java.lang.String)} will recreate this or a
     * similar instance where unspecified states (if any) may be optionally
     * omitted. If this instance was parsed and states are omitted, this exact
     * instance will be creatable when parsed again, else their equality cannot
     * be guaranteed.
     * <p>
     * This method will only take effect for BlockData instances created by
     * methods such as {@link Server#createBlockData(String)} or any similar
     * method whereby states are optionally defined. If otherwise, the result of
     * {@link #getAsString()} will be returned. The following behaviour would be
     * expected:
     * <pre>{@code
     * String dataString = "minecraft:chest[waterlogged=true]"
     * BlockData data = Bukkit.createBlockData(dataString);
     * dataString.equals(data.getAsString(true)); // This would return true
     * dataString.equals(data.getAsString(false)); // This would return false as all states are present
     * dataString.equals(data.getAsString()); // This is equivalent to the above, "getAsString(false)"
     * }</pre>
     *
     * @param hideUnspecified true if unspecified states should be omitted,
     * false if they are to be shown as performed by {@link #getAsString()}.
     *
     * @return serialized data string for this block
     */
    @NotNull
    String getAsString(boolean hideUnspecified);

    /**
     * Merges all explicitly set states from the given data with this BlockData.
     * <br>
     * Note that the given data MUST have been created from one of the String
     * parse methods, e.g. {@link Server#createBlockData(java.lang.String)} and
     * not have been subsequently modified.
     * <br>
     * Note also that the block types must match identically.
     *
     * @param data the data to merge from
     * @return a new instance of this blockdata with the merged data
     */
    @NotNull
    BlockData merge(@NotNull BlockData data);

    /**
     * Checks if the specified BlockData matches this block data.
     * <br>
     * The semantics of this method are such that for manually created or
     * modified BlockData it has the same effect as
     * {@link Object#equals(java.lang.Object)}, whilst for parsed data (that to
     * which {@link #merge(org.bukkit.block.data.BlockData)} applies, it will
     * return true when the type and all explicitly set states match.
     * <br>
     * <b>Note that these semantics mean that a.matches(b) may not be the same
     * as b.matches(a)</b>
     *
     * @param data the data to match against (normally a parsed constant)
     * @return if there is a match
     */
    boolean matches(@Nullable BlockData data);

    /**
     * Returns a copy of this BlockData.
     *
     * @return a copy of the block data
     */
    @NotNull
    BlockData clone();

    /**
     * Gets the block's {@link SoundGroup} which can be used to get its step
     * sound, hit sound, and others.
     *
     * @return the sound effect group
     */
    @NotNull
    SoundGroup getSoundGroup();

    /**
     * Checks if this state would be properly supported if it were placed at
     * the given {@link Block}.
     * <p>
     * This may be useful, for instance, to check whether or not a wall torch is
     * capable of surviving on its neighbouring block states.
     *
     * @param block the block position at which the state would be placed
     *
     * @return true if the block is supported, false if this state would not survive
     * the world conditions
     */
    boolean isSupported(@NotNull Block block);

    /**
     * Checks if this state would be properly supported if it were placed at
     * the block at the given {@link Location}.
     * <p>
     * This may be useful, for instance, to check whether or not a wall torch is
     * capable of surviving on its neighbouring block states.
     *
     * @param location the location at which the state would be placed
     *
     * @return true if the block is supported, false if this state would not survive
     * the world conditions
     */
    boolean isSupported(@NotNull Location location);

    /**
     * Checks if a state's {@link BlockFace} is capable of providing a given level
     * of {@link BlockSupport} for neighbouring block states.
     * <p>
     * Any given state may support either none, one, or more than one level of block
     * support depending on its states. A common example would be a wall's ability to support
     * torches only on the center of the upper block face, whereas a grass block would
     * support all levels of block support on all block faces.
     *
     * @param face the face to check
     * @param support the possible support level
     *
     * @return true if the face is sturdy and can support a block, false otherwise
     */
    boolean isFaceSturdy(@NotNull BlockFace face, @NotNull BlockSupport support);
}
