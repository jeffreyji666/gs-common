package com.ctrip.gs.common.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang.SerializationException;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

/**
 * HessianSerializer object
 * 
 * @author wgji
 */
public final class HessianSerializer {

    private HessianSerializer() {
    }

    /**
     * DeSerialzie by Hessian
     * 
     * @param bytes
     * @return
     * @throws SerializationException
     */
    public static Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }
        try {
            ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
            Hessian2Input in = new Hessian2Input(bin);
            return in.readObject();
        } catch (IOException e) {
            throw new SerializationException("fail for deserialize", e);
        }
    }

    /**
     * Serialize by Hessian
     * 
     * @param t
     * @return
     * @throws SerializationException
     */
    public static byte[] serialize(Object t) throws SerializationException {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Hessian2Output out = new Hessian2Output(bos);
            out.writeObject(t);
            out.flush();
            return bos.toByteArray();
        } catch (Exception e) {
            throw new SerializationException("fail for deserialize", e);
        }
    }

}
