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

package examples.event;

import jade.core.Agent;
import it.unipr.aotlab.d4j.behaviour.*;
import java.net.URL;

import org.drools.io.RuleSetReader;
import org.drools.rule.RuleSet;
import org.drools.event.*;

/**
 * This example shows how to use a Drools behaviour.
 * <br><br>
 * In this example the agent waits for a message to arrive. Then the message is asserted and Drools
 * performs its work. When all the assertions have been made and the matching rules fired the Drools
 * behaviour blocks and the next scheduled behaviour can execute.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">Università degli Studi di Parma</a>
 *
 * @version $Id: DrlAgent.java,v 1.1 2004/10/03 09:22:17 mic Exp $
 */
public class EventAgent extends Agent implements WorkingMemoryEventListener {

    private CyclicDroolsBehaviour droolsBehaviour = null;
    private MsgAsserterBehaviour recBehaviour = null;

    protected void setup() {
        URL drlFileUrl = this.getClass().getResource("../rules/drl/receiver.drl");
        RuleSetReader ruleSetReader = new RuleSetReader();

        droolsBehaviour = new CyclicDroolsBehaviour(this);

        try {
            RuleSet ruleSet = ruleSetReader.read(drlFileUrl);
            droolsBehaviour.addRuleSet(ruleSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        droolsBehaviour.addEventListener(this);

        /* The behaviour that receives the messages and assert them in drools4jade */
        recBehaviour = new MsgAsserterBehaviour(this, droolsBehaviour);

        addBehaviour(recBehaviour);
        addBehaviour(droolsBehaviour);

        System.out.println("EventAgent created");
    }

    // Methods that implement the interface of the event listener

    public void objectAsserted(ObjectAssertedEvent event) {
        System.out.println("I'm the event listener, I've just caught an ObjectAsserted event: ");
        System.out.println(event);
    }

    public void objectModified(ObjectModifiedEvent event) {
        System.out.println("I'm the event listener, I've just caught an ObjectModified event: ");
        System.out.println(event);
    }

    public void objectRetracted(ObjectRetractedEvent event) {
        System.out.println("I'm the event listener, I've just caught an ObjectRetracted event: ");
        System.out.println(event);

    }

    public void conditionTested(ConditionTestedEvent event) {
        System.out.println("I'm the event listener, I've just caught a ConditionTested event: ");
        System.out.println(event);
    }

    public void activationCancelled(ActivationCancelledEvent event) {
        System.out.println("I'm the event listener, I've just caught an CctivationCancelled event: ");
        System.out.println(event);
    }

    public void activationCreated(ActivationCreatedEvent event) {
        System.out.println("I'm the event listener, I've just caught an ActivationCreated event: ");
        System.out.println(event);
    }

    public void activationFired(ActivationFiredEvent event) {
        System.out.println("I'm the event listener, I've just caught an ActivationFired event: ");
        System.out.println(event);
    }
}
