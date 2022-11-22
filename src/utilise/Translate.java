package utilise;

import java.io.*;

/* Translate, serialization and deserialization of objects */
public class Translate {
	/* Objects are converted to byte arrays to serialize objects */
    public static byte[] ObjectToByte(Object obj) {
    	byte[] buffer=null;
        try {
        	ByteArrayOutputStream bo=new ByteArrayOutputStream(); //Byte array output stream
            ObjectOutputStream oo=new ObjectOutputStream(bo); //Object output stream
            oo.writeObject(obj); //The output object
            buffer=bo.toByteArray(); //Object serialization
        }catch(IOException ex) {}
        return buffer;
    } 
    /* Byte array is converted to Object Object form to realize Object deserialization */
    public static Object ByteToObject(byte[] buffer) {
        Object obj=null;
        try {
            ByteArrayInputStream bi=new ByteArrayInputStream(buffer); //Byte array input stream
            ObjectInputStream oi=new ObjectInputStream(bi); //Object input stream
            obj=oi.readObject(); //To object
        }catch(IOException | ClassNotFoundException ex) { }
        return obj;
    } 
}
