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
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: RuleSet.java,v 1.1 2004/10/01 15:24:07 mic Exp $
 */
public class RuleSet implements Concept, Serializable {

    private String name = null;
    private List rules = null;

    public RuleSet() {
        /* Intentionally left blank */
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getRules() {
        return rules;
    }

    public void setRules(List rules) {
        this.rules = rules;
    }
}
