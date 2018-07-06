package net.tkarura.resourcedungeons.bukkit;

import net.minecraft.server.v1_12_R1.*;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTBase;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagByte;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagByteArray;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagCompound;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagDouble;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagEnd;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagFloat;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagInt;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagIntArray;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagList;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagLong;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagShort;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagString;

public final class NBTTagConverter {

    private NBTTagConverter() {
    }

    public static DNBTBase convert(NBTBase nbt) {

        if (nbt == null) {
            return new DNBTTagEnd();
        }

        switch (NBTBase.a[nbt.getTypeId()]) {

            case "BYTE": {
                return new DNBTTagByte(((NBTTagByte) nbt).g());
            }

            case "SHORT": {
                return new DNBTTagShort(((NBTTagShort) nbt).g());
            }

            case "INT": {
                return new DNBTTagInt(((NBTTagInt) nbt).g());
            }

            case "LONG": {
                return new DNBTTagLong(((NBTTagLong) nbt).g());
            }

            case "FLOAT": {
                return new DNBTTagFloat(((NBTTagFloat) nbt).g());
            }

            case "DOUBLE": {
                return new DNBTTagDouble(((NBTTagDouble) nbt).g());
            }

            case "BYTE[]": {
                return new DNBTTagByteArray(((NBTTagByteArray) nbt).c());
            }

            case "STRING": {
                return new DNBTTagString(((NBTTagString) nbt).c_());
            }

            case "LIST": {
                return convert((NBTTagList) nbt);
            }

            case "COMPOUND": {
                return convert((NBTTagCompound) nbt);
            }

            case "INT[]": {
                return new DNBTTagIntArray(((NBTTagIntArray) nbt).d());
            }

            case "END": {
                return new DNBTTagEnd();
            }

            default: {
                return new DNBTTagEnd();
            }

        }
    }

    public static NBTBase convert(DNBTBase nbt) {

        switch (nbt.getTypeId()) {

            case DNBTBase.TAG_BYTE: {
                return new NBTTagByte(((DNBTTagByte) nbt).getValue());
            }

            case DNBTBase.TAG_SHORT: {
                return new NBTTagShort(((DNBTTagShort) nbt).getValue());
            }

            case DNBTBase.TAG_INT: {
                return new NBTTagInt(((DNBTTagInt) nbt).getValue());
            }

            case DNBTBase.TAG_LONG: {
                return new NBTTagLong(((DNBTTagLong) nbt).getValue());
            }

            case DNBTBase.TAG_FLOAT: {
                return new NBTTagFloat(((DNBTTagFloat) nbt).getValue());
            }

            case DNBTBase.TAG_DOUBLE: {
                return new NBTTagDouble(((DNBTTagDouble) nbt).getValue());
            }

            case DNBTBase.TAG_BYTE_ARRAY: {
                return new NBTTagByteArray(((DNBTTagByteArray) nbt).getValue());
            }

            case DNBTBase.TAG_STRING: {
                return new NBTTagString(((DNBTTagString) nbt).getValue());
            }

            case DNBTBase.TAG_LIST: {
                return convert((DNBTTagList) nbt);
            }

            case DNBTBase.TAG_COMPOUND: {
                return convert((DNBTTagCompound) nbt);
            }

            case DNBTBase.TAG_INT_ARRAY: {
                return new NBTTagByte(((DNBTTagByte) nbt).getValue());
            }

            default: {
                return null;
            }

        }

    }

    public static DNBTTagCompound convert(NBTTagCompound nbt) {

        DNBTTagCompound result = new DNBTTagCompound();

        for (String key : nbt.c()) {

            result.set(key, convert(nbt.get(key)));

        }

        return result;
    }

    public static NBTTagCompound convert(DNBTTagCompound nbt) {

        NBTTagCompound result = new NBTTagCompound();

        for (String key : nbt.getValue().keySet()) {

            result.set(key, convert(nbt.getValue().get(key)));

        }

        return result;
    }

    public static DNBTTagList convert(NBTTagList nbt) {

        DNBTTagList result = new DNBTTagList();

        for (int i = 0; i < nbt.size(); i++) {

            result.add(convert(nbt.get(i)));

        }

        return result;
    }

    public static NBTTagList convert(DNBTTagList nbt) {

        NBTTagList result = new NBTTagList();

        for (DNBTBase tag : nbt.getValue()) {

            result.add(convert(tag));

        }

        return result;
    }

}
