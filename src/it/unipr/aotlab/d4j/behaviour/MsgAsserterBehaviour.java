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

package it.unipr.aotlab.d4j.behaviour;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * This behaviour receives every incoming message that matches the
 * <tt>INFORM</tt> performative and asserts it as an object in the
 * <tt>Drools WorkingMemory</tt>.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 * @version $Id: MsgAsserterBehaviour.java,v 1.5 2004/10/03 14:49:11 a_beneventi Exp $
 */
public class MsgAsserterBehaviour extends SimpleBehaviour {

    private boolean finished = false;
    private ACLMessage msg = null;
    private BasicDroolsBehaviour droolsBehaviour = null;
    private MessageTemplate template = null;

    /**
     * The constructor for the behaviour.
     *
     * @param agent           The owner agent.
     * @param droolsBehaviour the behaviour that manages the <tt>WorkingMemory</tt>
     */
    public MsgAsserterBehaviour(Agent agent, BasicDroolsBehaviour droolsBehaviour) {
        this(agent, droolsBehaviour, null);
    }

    /**
     * The constructor for the behaviour.
     *
     * @param agent           The owner agent.
     * @param droolsBehaviour the behaviour that manages the <tt>WorkingMemory</tt>
     * @param template        in order to be asserted, the arriving message must match it
     */
    public MsgAsserterBehaviour(Agent agent, BasicDroolsBehaviour droolsBehaviour, MessageTemplate template) {
        super(agent);
        this.droolsBehaviour = droolsBehaviour;
        this.template = template;
    }

    /**
     * This behaviour waits indefinitely for a message to arrive.
     * When it arrives it is asserted in drools4jade...
     */
    public void action() {

        try {
            // droolsBehaviour.setApplicationData("agent", myAgent);

            /* next row is needed because some examples are not updated;
               they search for myAgent among facts, not in app-data */
            // droolsBehaviour.assertFact(myAgent);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Drools is Waiting for a Message...");

        /* Let's check if there is a message in the queue... */
        msg = myAgent.receive(template);

        if (!(msg == null)) {

            /* Yes, there's a message... */

            System.out.println("Passing message to drools4jade...");

            /* IMPORTANT: the next assertion is fundamental. Drools, in fact, must hold
                          a reference to the agent by which has been called in order to
                          modify behaviours, send and receive messages etc. etc.

                          So I must assert myself as an object in the working memory.

                          Without this instruction, every rule that tries to interact with
                          the platform will never be fired.
                          */


            /* The messagge is asserted in the working memory */
            try {
                droolsBehaviour.assertFact(msg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            /* The work is done and we can finish... */
            finished = true;
            System.out.println("Arrived...");

        } else {

            /* No message present. Let's wait again... */
            System.out.println("Drools is Still Waiting for a Message...");
            block();
        }
    }

    /**
     * Upon ending the behaviour is inserted again in the queue of the scheduled
     * behaviour.
     *
     * @return 0
     */
    public int onEnd() {

        reset();
        myAgent.addBehaviour(this);
        return 0;
    }

    public void reset() {

        super.reset();
        finished = false;
    }

    /**
     * The behaviour executes until a message arrives. Then the behaviour performs
     * its action and it is restarted.
     *
     * @return Returns <tt>true</tt> when a message arrives and <tt>false</tt> instead.
     */
    public boolean done() {

        return finished;
    }
}