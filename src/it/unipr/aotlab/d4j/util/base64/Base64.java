/*****************************************************************
 The AOT Package is a rules engine add-on for Jade created by the
 University of Parma - 2003

 GNU Lesser General Public License

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation,
 version 2.1 of the License.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the
 Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 Boston, MA  02111-1307, USA.
 *****************************************************************/

package it.unipr.aotlab.d4j.util.base64;

import java.io.*;

/**
 * A collection of static methods to encode sequences of bytes or Java objects
 * to <tt>Base64</tt>-encoded string or viceversa.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 * @version $Id: Base64.java,v 1.1 2004/10/01 09:46:38 mic Exp $
 */
public class Base64 {

    /**
     * A static method to encode a sequence of bytes into a <tt>Base64</tt>-encoded
     * string.
     *
     * @param byteSequence the byte sequence to encode
     * @return a <tt>Base64</tt>-encoded string
     */
    public static String encode(byte[] byteSequence) {

        if (byteSequence == null) {
            return null;
        }
        else {

            char[] charSeqObject = starlight.util.Base64.encode(byteSequence);
            String strObject = new String(charSeqObject);

            return strObject;
        }
    }

    /**
     * A static method to encode a serializable Java object into a <tt>Base64</tt>-encoded
     * string.
     *
     * @param object the Java object to be encoded.
     * @return a <tt>Base64</tt>-encoded string
     * @throws IOException thrown if the object cannot be serialized
     */
    public static String encode(Object object) throws IOException {

        if (object == null) {
            return null;
        }
        else {

            ByteArrayOutputStream baOutStream = new ByteArrayOutputStream();
            ObjectOutputStream objOutStream = new ObjectOutputStream(baOutStream);

            objOutStream.writeObject(object);

            return encode(baOutStream.toByteArray());
        }
    }

    /**
     * A static method to decode a <tt>Base64</tt>-encoded string to
     * a sequence of bytes.
     *
     * @param encodedStr a <tt>Base64</tt>-encoded string
     * @return the decoded sequence of bytes
     */
    public static byte[] decode2bytes(String encodedStr) {
        if (encodedStr == null) {
            return null;
        }
        else {

            /* The String is converted into a sequence of chars */
            char[] charSeqObject = encodedStr.toCharArray();

            /* The sequence is decoded into a sequence of bytes
               representing the original object. */
            byte[] byteSeqObject = starlight.util.Base64.decode(charSeqObject);

            return byteSeqObject;
        }
    }

    /**
     * A static method to decode a <tt>Base64</tt>-encoded string to
     * a Java object.
     *
     * @param encodedStr a <tt>Base64</tt>-encoded string
     * @return the decoded Java object
     * @throws IOException            thrown if the object cannot be serialized
     * @throws ClassNotFoundException thrown if the Java class cannot
     *                                be found
     */
    public static Object decode2object(String encodedStr)
            throws IOException, ClassNotFoundException {

        if (encodedStr != null) {
            return null;
        }
        else {

            ObjectInputStream objInputStream = new ObjectInputStream(new ByteArrayInputStream(decode2bytes(encodedStr)));

            /* Here is is... the original object */
            return objInputStream.readObject();
        }
    }
}
