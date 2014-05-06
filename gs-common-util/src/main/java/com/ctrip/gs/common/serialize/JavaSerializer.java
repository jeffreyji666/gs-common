package com.ctrip.gs.common.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * java serializer
 * @author wgji
 *
 */
public class JavaSerializer {
    /**
     * java original Serialize method
     * 
     * @param source
     * @return
     * @throws IOException
     */
    public static byte[] serialize(Object source) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream output = new ObjectOutputStream(bout);
        output.writeObject(source);
        return bout.toByteArray();
    }

    /**
     * java original DeSerialize method
     * 
     * @param bvalue
     * @return
     * @throws ClassNotFoundException
     */
    public static Object deserialize(byte[] bvalue) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bin = new ByteArrayInputStream(bvalue);
        ObjectInputStream input = new ObjectInputStream(bin);
        return input.readObject();
    }
}
