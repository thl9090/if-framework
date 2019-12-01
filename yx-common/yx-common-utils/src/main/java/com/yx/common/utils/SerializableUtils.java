package com.yx.common.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableUtils {

    public static String serializeToString(Object object) {
        String byteStr = "";
        try {
            byte[] bytes = getByteArrayOutputStream(object).toByteArray();
            byteStr = Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteStr;
    }

    public static byte[] deserializeToBytesByString(String serializeString) {
        byte[] bytes = null;
        try {
            bytes = Base64.decodeBase64(serializeString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static Object deserialize(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            obj = objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static byte[] serialize(Object object) {
        return getByteArrayOutputStream(object).toByteArray();
    }

    public static Object deserializeByString(String serializeString) {
        return deserialize(deserializeToBytesByString(serializeString));
    }

    private static ByteArrayOutputStream getByteArrayOutputStream(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream;
    }

}
