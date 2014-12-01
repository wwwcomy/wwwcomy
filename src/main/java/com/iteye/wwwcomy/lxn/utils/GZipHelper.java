package com.iteye.wwwcomy.lxn.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipHelper {

    private GZipHelper() {
    }

    public static byte[] writeCompressObject(Object object_) {
        byte[] data_ = null;
        try {
            if (object_ != null) {
                // if (logger.isDebugEnable()) {
                // ByteArrayOutputStream beforeO = new ByteArrayOutputStream();
                // ObjectOutputStream beforeOut = new ObjectOutputStream(beforeO);
                // beforeOut.writeObject(object_);
                // beforeOut.flush();
                // beforeOut.close();
                // beforeO.close();
                // int unCompressed = beforeO.toByteArray().length;
                // String toWrite = "\tBefore Compressed: " + unCompressed + "(byte) \r\n\t";
                // logger.logDebug(toWrite);
                // }
                ByteArrayOutputStream o = new ByteArrayOutputStream();
                GZIPOutputStream gzout = new GZIPOutputStream(o);
                ObjectOutputStream out = new ObjectOutputStream(gzout);
                out.writeObject(object_);
                out.flush();
                out.close();
                gzout.close();
                data_ = o.toByteArray();
//                int compressed = data_.length;
//                String toWrite = "After Compressed: " + compressed + "(byte) \r\n\r\n";
                // logger.logDebug(toWrite);
                o.close();
            }
        } catch (IOException e) {
            // logger.logError(e);
        }
        return (data_);
    }

    public static Object readCompressObject(byte[] data_) {
        Object object_ = null;
        try {
            if (data_ != null && data_.length > 0) {
                ByteArrayInputStream i = new ByteArrayInputStream(data_);
                GZIPInputStream gzin = new GZIPInputStream(i);
                ObjectInputStream in = new ObjectInputStream(gzin);
                object_ = in.readObject();
                i.close();
                gzin.close();
                in.close();
            }
        } catch (ClassNotFoundException e) {
            // logger.logError(e);
        } catch (IOException e) {
            // logger.logError(e);
        }
        return (object_);
    }
}
