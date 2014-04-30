/*****************************************************************
 The AOT Package is a rules engine add-on for Jade created by the
 Università degli Studi di Parma - 2003

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

package it.unipr.aotlab.d4j.behaviour;

import jade.core.Agent;

/**
 * By extending a BasicDroolsBehaviour a one-shot behaviour is created. The need for a
 * one-shot behaviour comes from the way Drools works. In fact, if the behaviour
 * was not restarted at the end of a cycle, one one rule inspection would be
 * perfomed (provided that there is no recursion in the rule definitions).
 * <br><br>
 * So, a one-shot behaviour is needed when  we want an inspection to be perfomed
 * only once thus making this behaviour work like a sophisticated <tt>if - then</tt> or
 * a <tt>switch</tt> statement.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">Università degli Studi di Parma</a>
 * @version $Id: OneShotDroolsBehaviour.java,v 1.3 2004/10/03 14:49:30 a_beneventi Exp $
 */
public class OneShotDroolsBehaviour extends BasicDroolsBehaviour {

    /**
     * A one-shot Jade Drools-based behaviour is created.
     * <p/>
     * This constructor prepares the working environment without <tt>Rule</tt>s.
     * <p/>
     * <br><br>
     * No data (i.e. Java Objects) is available at the time. Java Objects must be asserted
     * separately by calling <tt>assertFacts()</tt> or <tt>assertFact()</tt> methods.<br>
     * No rule is analyzed until at least one object is asserted.
     *
     * @param agent The Agent owner of the behaviour
     */
    public OneShotDroolsBehaviour(Agent agent) {
        super(agent);
    }

    /**
     * A one.shot behaviour is modelled...
     *
     * @return Always true.
     */
    public boolean done() {
        return true;
    }

}
