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

package it.unipr.aotlab.d4j.rule;

import jade.content.Concept;

import java.io.Serializable;

/**
 * The ontological representation of a <tt>Parameter</tt> declaration. The informations
 * needed are an identifier (the name of the variable) and a class name ( the
 * type of the object).
 * <p/>
 * A <tt>Parameter</tt> is equal to a <tt>Declaration</tt>. There is only a semantic
 * difference: in fact, a <tt>Parameter</tt> is a root fact-object and a <tt>Declaration</tt>
 * is an object which is instantiated and assigned inside the <tt>Rule</tt>.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a>,
 *         <a href="mailto:tomamic@ce.unipr.it@tin.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: RuleParameter.java,v 1.2 2004/10/03 13:12:39 mic Exp $
 */
public class RuleParameter implements Concept, Serializable {

    String identifier = null;
    String type = null;

    /**
     * Creates a new <tt>Parameter</tt> declaration from its identifier
     * and class name.
     *
     * @param identifier the identifier of the declaration.
     * @param type  the type of the declaration.
     */
    public RuleParameter(String identifier, String type) {
        this.identifier = identifier;
        this.type = type;
    }

    /**
     * This is the empty constructor, necessary fo the <tt>JavaBean</tt>
     * specifications.
     */
    public RuleParameter() {
        /* Intentionally left blank */
        super();
    }

    /**
     * Gets the identifier of the declaration.
     *
     * @return the identifier of the declaration.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the identifier of the declaration.
     *
     * @param identifier the identifier of the declaration.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets the class name of the declaration.
     *
     * @return the class name of the declaration.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the declaration.
     *
     * @param type the type of the declaration
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns a <tt>String</tt> representation of the declaration. This
     * representation is what is shown in the declaration combo box of
     * the graphical user interface.
     *
     * @return a <tt>String</tt> representation of the declaration
     */
    public String toString() {
        return identifier + " (" + type + ")";
    }
}
