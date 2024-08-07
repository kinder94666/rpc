package com.yupi.yurpc.serializer;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HessianSerializer implements Serializer {

        @Override
        public <T> byte[] serialize(T object) throws IOException, IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            HessianOutput output = new HessianOutput(byteArrayOutputStream);
            output.writeObject(object);
            output.close();
            return byteArrayOutputStream.toByteArray();
        }

        @Override
        public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            HessianInput input = new HessianInput(byteArrayInputStream);
            T res = (T) input.readObject(type);
            input.close();
            return res;
        }
}
