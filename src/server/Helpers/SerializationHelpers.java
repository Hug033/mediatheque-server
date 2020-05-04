package server.Helpers;

import java.io.*;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * Classe qui contient des outils de sérialization
 *
 */
class SerializationHelpers {

    // Permet de sérialiser un Serializable
    static byte[] serialize(Serializable o) throws IOException {
        if (o != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(o);
            oos.flush();
            return bos.toByteArray();
        } else
        	throw new NullPointerException();
    }

    // Pert de désérialiser et retourne un Serializable
    static Serializable deserialize(byte[] data) throws IOException, ClassNotFoundException {
        if (data != null) {
            ByteArrayInputStream ba = new ByteArrayInputStream(data);
            ObjectInputStream obj = new ObjectInputStream(ba);
            return (Serializable) obj.readObject();
        } else
            throw new NullPointerException();
    }
}
