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
 * By extending a BasicDroolsBehaviour a cyclic one is created. The need for a
 * cyclic behaviour comes from the way Drools works. In fact, if the behaviour
 * was not restarted at the end of a cycle, one one rule inspection would be
 * perfomed (provided that there is no recursion in the rule definitions).
 * <br><br>
 * A cyclic behaviour is needed when we want an inspection to be perfomed every
 * time one ore more objects are modified, added or retracted from the
 * <tt>WorkingMemory</tt>.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">Università degli Studi di Parma</a>
 * @version $Id: CyclicDroolsBehaviour.java,v 1.2 2004/10/03 14:39:42 a_beneventi Exp $
 */
public class CyclicDroolsBehaviour extends BasicDroolsBehaviour {

    /**
     * A cyclic Jade Drools-based behaviour is created.
     *
     * @param agent
     */
    public CyclicDroolsBehaviour(Agent agent) {
        super(agent);
    }

    /**
     * A cyclic behaviour is modelled...
     *
     * @return Always false. So we never stop.
     */
    public boolean done() {
        return false;
    }
}
