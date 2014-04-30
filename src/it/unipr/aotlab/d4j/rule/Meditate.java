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

import jade.content.AgentAction;
import jade.util.leap.List;

import java.io.Serializable;

/**
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: Meditate.java,v 1.2 2004/10/03 13:12:39 mic Exp $
 */
public class Meditate implements AgentAction, Serializable {

    private List ruleSets = null;
    private List facts = null;

    public Meditate() {
        /* Intentionally left blank */
    }

    public List getRuleSets() {
        return ruleSets;
    }

    public void setRuleSets(List ruleSets) {
        this.ruleSets = ruleSets;
    }

    public List getFacts() {
        return facts;
    }

    public void setFacts(List facts) {
        this.facts = facts;
    }

}
