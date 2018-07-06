package net.tkarura.resourcedungeons.bukkit;

import net.minecraft.server.v1_12_R1.*;
import net.tkarura.resourcedungeons.core.server.IDungeonWorld;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagCompound;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

public class BukkitWorld implements IDungeonWorld {

    private CraftWorld world;

    public BukkitWorld(org.bukkit.World world) {
        this.world = (CraftWorld) world;
    }

    @Override
    public void spawnEntityFromId(String registry_id, int x, int y, int z) {
        World world = this.world.getHandle();
        MinecraftKey key = new MinecraftKey(registry_id);
        Entity entity = EntityTypes.a(key, world);
        entity.setLocation(x, y, z, entity.yaw, entity.pitch);
        world.addEntity(entity);
    }

    @Override
    public void spawnEntityFromId(int id, int x, int y, int z) {

    }

    @Override
    public void spawnEntityFromNBT(DNBTTagCompound dnbtTagCompound, int x, int y, int z) {
        World world = this.world.getHandle();
        NBTTagCompound nbt = NBTTagConverter.convert(dnbtTagCompound);
        Entity entity = EntityTypes.a(nbt, world);
        entity.setLocation(x, y, z, entity.yaw, entity.pitch);
        world.addEntity(entity);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setBlockId(String registry_id, int x, int y, int z) {
        String domainId = registry_id.split(":").length == 0 ? "minecraft:" : registry_id.split(":")[0];
        String blockId = registry_id.split(":").length == 0 ? registry_id : registry_id.split(":")[1];
        MinecraftKey key = new MinecraftKey(domainId, blockId);
        BlockPosition pos = new BlockPosition(x, y, z);
        Block block = Block.REGISTRY.get(key);
        byte damage = (byte) block.toLegacyData(block.getBlockData());
        IBlockData iBlockData = block.fromLegacyData(damage);
        World world = this.world.getHandle();
        world.setTypeAndData(pos, iBlockData, 2);
    }

    @Override
    public void setBlockId(int block_id, int x, int y, int z) {
        RegistryBlocks<MinecraftKey, Block> registryBlocks = Block.REGISTRY;
        MinecraftKey key = registryBlocks.b(registryBlocks.getId(block_id));
        String registryId = key.b() + ":" + key.getKey();
        this.setBlockId(registryId, x, y, z);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setBlockData(byte data, int x, int y, int z) {
        World world = this.world.getHandle();
        BlockPosition pos = new BlockPosition(x, y, z);
        IBlockData iBlockData = world.getType(pos);
        world.setTypeAndData(pos, iBlockData.getBlock().fromLegacyData(data), 3);
    }

    @Override
    public void setTileEntityFromNBT(DNBTTagCompound dnbtTagCompound, int x, int y, int z) {
        World world = this.world.getHandle();
        NBTTagCompound nbt = NBTTagConverter.convert(dnbtTagCompound);
        BlockPosition pos = new BlockPosition(x, y, z);
        TileEntity entity = TileEntity.create(world, nbt);
        world.setTileEntity(pos, entity);
    }

    @Override
    public void setBiome(String registry_id, int x, int z) {
        World world = this.world.getHandle();
        Chunk chunk = world.getChunkAt(x, z);
        MinecraftKey key = new MinecraftKey(registry_id);
        BiomeBase base = BiomeBase.REGISTRY_ID.get(key);
        byte[] biomevals = chunk.getBiomeIndex();
        biomevals[(z & 15) << 4 | x & 15] = (byte) BiomeBase.REGISTRY_ID.a(base);
        chunk.markDirty();
    }

    @Override
    public String getBlockId(int x, int y, int z) {
        World world = this.world.getHandle();
        BlockPosition pos = new BlockPosition(x, y, z);
        IBlockData iBlockData = world.getType(pos);
        MinecraftKey key = Block.REGISTRY.b(iBlockData.getBlock());
        return key.b() + ":" + key.getKey();
    }

    @Override
    public byte getBlockData(int x, int y, int z) {
        World world = this.world.getHandle();
        BlockPosition pos = new BlockPosition(x, y, z);
        IBlockData iBlockData = world.getType(pos);
        return (byte) iBlockData.getBlock().toLegacyData(iBlockData);
    }

    @Override
    public String getBiome(int x, int z) {
        World world = this.world.getHandle();
        BlockPosition pos = new BlockPosition(x, 0, z);
        RegistryMaterials<MinecraftKey, BiomeBase> registry = BiomeBase.REGISTRY_ID;
        MinecraftKey key = registry.b(world.getBiome(pos));
        return key.b() + ":" + key.getKey();
    }

    @Override
    public long getSeed() {
        return world.getSeed();
    }

}
