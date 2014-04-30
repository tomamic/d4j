/*****************************************************************
 The AOT Package is a rules engine add-on for Jade created by the
 University of Parma - 2004

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
import jade.util.leap.List;

import java.io.Serializable;

/**
 * This class is the ontological representation of a <tt>org.drools4jade.rule.Rule</tt>.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: Rule.java,v 1.1 2004/10/01 15:24:07 mic Exp $
 */
public class Rule implements Concept, Serializable {

    private String name = null;
    private int salience = 20;
    private List parameters = null;
    //private List extractions = null;
    private List conditions = null;
    private int duration = -1;
    private String consequence = null;

    /**
     * This is the empty constructor, necessary fo the <tt>JavaBean</tt>
     * specifications.
     */
    public Rule() {
        /* Intentionally left blank */
    }

    /**
     * Creates a new <tt>Rule</tt> setting its name. All the other parameter
     * must be set using the setter methods.
     *
     * @param name the <tt>Rule</tt> name.
     */
    public Rule(String name) {
        this.name = name;
    }

    /**
     * Gets the <tt>Rule</tt> name.
     *
     * @return the <tt>Rule</tt> name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the <tt>Rule</tt> name.
     *
     * @param name the <tt>Rule</tt> name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the <tt>List</tt> of <tt>RuleParameter</tt> objects. At least
     * one <tt>RuleParameter</tt> must be specified.
     *
     * @return the <tt>List</tt> of <tt>RuleParameter</tt> objects.
     */
    public List getParameters() {
        return parameters;
    }

    /**
     * Sets the <tt>List</tt> of <tt>RuleParameter</tt> objects. At least
     * one <tt>RuleParameter</tt> must be specified.
     *
     * @param parameters the <tt>List</tt> of <tt>RuleParameter</tt> objects.
     */
    public void setParameters(List parameters) {
        this.parameters = parameters;
    }

    /**
     * Gets the <tt>List</tt> of <tt>RuleCondition</tt> objects. At least
     * one <tt>RuleCondition</tt> must be specified.
     *
     * @return the <tt>List</tt> of <tt>RuleCondition</tt> objects.
     */
    public List getConditions() {
        return conditions;
    }

    /**
     * Sets the <tt>List</tt> of <tt>RuleCondition</tt> objects. At least
     * one <tt>RuleCondition</tt> must be specified.
     *
     * @param conditions the <tt>List</tt> of <tt>RuleCondition</tt> objects.
     */
    public void setConditions(List conditions) {
        this.conditions = conditions;
    }

    /**
     * Gets the duration of the <tt>Rule</tt>.
     *
     * @return the duration of the <tt>Rule</tt>
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the <tt>Rule</tt>. This field is optional
     *
     * @param duration the duration of the <tt>Rule</tt>
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Gets the consequence of the <tt>Rule</tt>. The consequence consists of
     * a <tt>String</tt> object with the list of the statements to be executed
     * if the <tt>Rule</tt> fires.
     *
     * @return the consequence of the <tt>Rule</tt>
     */
    public String getConsequence() {
        return consequence;
    }

    /**
     * Sets the consequence of the <tt>Rule</tt>. The consequence consists of
     * a <tt>String</tt> object with the list of the statements to be executed
     * if the <tt>Rule</tt> fires.
     *
     * @param consequence the consequence of the <tt>Rule</tt>.
     */
    public void setConsequence(String consequence) {
        this.consequence = consequence;
    }

    public int getSalience() {
        return salience;
    }

    public void setSalience(int salience) {
        this.salience = salience;
    }

    /**
     * Returns the name of the <tt>Rule</tt> which is displayed in the
     * field of the graphical user interface.
     *
     * @return a the name of the declaration.
     */
    public String toString() {
        return name;
    }
}
