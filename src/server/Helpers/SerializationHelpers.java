package server.Helpers;

import java.io.*;

public class SerializationHelpers {

    // Permet de sérialiser un Serializable
    public static byte[] serialize(Serializable o) throws IOException {
        if (o != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(o);
            oos.flush();
            return bos.toByteArray();
        } else
        	throw new NullPointerException();
    }

    // Pertmet de désérialiser et retourne un Serializable
    public static Serializable deserialize(byte[] data) throws IOException, ClassNotFoundException {
        if (data != null) {
            ByteArrayInputStream ba = new ByteArrayInputStream(data);
            ObjectInputStream obj = new ObjectInputStream(ba);
            return (Serializable) obj.readObject();
        } else
            throw new NullPointerException();
    }
}
