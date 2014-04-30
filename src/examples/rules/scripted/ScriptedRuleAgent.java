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

package examples.rules.scripted;

import it.unipr.aotlab.d4j.behaviour.CyclicDroolsBehaviour;
import it.unipr.aotlab.d4j.behaviour.MsgAsserterBehaviour;
import it.unipr.aotlab.d4j.util.rule.RuleFactory;
import it.unipr.aotlab.d4j.util.rule.RuleCreationException;

import org.drools.rule.DuplicateRuleNameException;
import org.drools.rule.InvalidRuleException;
import org.drools.rule.Rule;
import org.drools.rule.RuleSet;

import jade.core.Agent;

/**
 * This is a modified version of the example in the package <code>...examples.rulecreation2</code>.<br>
 * Here the rules are created at runtime using the <code>RuleFactory</code> class.<br><br>
 * Please refer to the tutorial for further explanations.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">University of Parma Parma</a>
 *
 * @version $Id: RuleFactoryAgent.java,v 1.1 2004/10/03 09:22:17 mic Exp $
 *
 * @see it.unipr.aotlab.d4j.utilities.rulefactory.RuleFactory
 * @see examples.drools.rulecreation2
 * @see "AOT Tutorial"
 */
public class ScriptedRuleAgent extends Agent {

    private CyclicDroolsBehaviour droolsBehaviour = null;
    private MsgAsserterBehaviour recBehaviour = null;

    /**
     * Here is coded the agent's behaviour.
     */
    protected void setup() {

        try {
            RuleSet ruleSet = new RuleSet("Receiver Rules");

            /* Let's create (at runtime) the rules and add them to the RuleSet */
            ruleSet.addRule(createRule1(ruleSet));
            ruleSet.addRule(createRule2(ruleSet));

            /* The rule asserting behaviour */
            droolsBehaviour = new CyclicDroolsBehaviour(this);
            droolsBehaviour.addRuleSet(ruleSet);

            /* The behaviour that receives the messages and assert them in drools4jade */
            recBehaviour = new MsgAsserterBehaviour(this, droolsBehaviour);

            addBehaviour(recBehaviour);
            addBehaviour(droolsBehaviour);

        }
        catch (DuplicateRuleNameException drne) {
            System.err.println("A rule with this name already exists.");
            drne.printStackTrace();
        }
        catch (InvalidRuleException ire) {
            System.err.println("The rule is not valid.");
            ire.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The first rule is created.
     * If the content of the message contains the word "drools4jade" in lower, upper or mixed case
     * then some output is printed.
     *
     * @return created rule
     */
    private final Rule createRule1(RuleSet ruleSet) {

        Rule myRule = null;

        try {

            RuleFactory ruleFactory = new RuleFactory("Are you talking about me?", ruleSet);

            /* We declare an identifier named "msg". Its type is jade.lang.acl.ACLMessage.
               The identifier is also a root fact object which means that is expected to
               be provided from external resources (i.e. must be asserted using assertObject()) */
            ruleFactory.addParameter("msg", jade.lang.acl.ACLMessage.class);

            /* This is the condition that must be true for the condition to fire.
               msgContent is the declaration necessary for the processor (BeanShell) to
               check the condition. */
            ruleFactory.addCondition("msg.getContent().toUpperCase().indexOf(\"DROOLS\") != -1");

            /* If the rule has to be fired then the code below is executed by BeanShell */
            ruleFactory.setConsequence("System.out.println(\"Hi! I'm not that smart but I can understand\");System.out.println(\"that you're talking about Drools.\");");

            /* The rule is complete and can be retrieved. */
            myRule = ruleFactory.getRule();

        }
        catch (RuleCreationException e) {
            e.printStackTrace();
        }

        return myRule;
    }

    /**
     * The second rule is created.
     * If the performative of the message is INFORM then a simple reply is sent
     * to the sender.
     *
     * @return created rule
     */
    private final Rule createRule2(RuleSet ruleSet) {

        Rule myRule = null;

        try {
            RuleFactory ruleFactory = new RuleFactory("Thank You", ruleSet);

            /* We declare an identifier named "msg". Its type is jade.lang.acl.ACLMessage.
               The identifier is also a root fact object which means that is expected to
               be provided from external resources (i.e. must be asserted using assertObject()) */
            ruleFactory.addParameter("msg", jade.lang.acl.ACLMessage.class);

            /* We declare an identifier named "myAgent". Its type is jade.core.Agent
               The identifier is also a root fact object which means that is expected to
               be provided from external resources (i.e. must be asserted using assertObject()) */
            ruleFactory.addParameter("myAgent", Agent.class);

            /* This is the condition that must be true for the condition to fire.
               msgPerformative is the declaration necessary for the processor (BeanShell) to
               check the condition. */
            ruleFactory.addCondition("msg.getPerformative() == jade.lang.acl.ACLMessage.INFORM");

            StringBuffer buffer = new StringBuffer();
            buffer.append("import jade.core.behaviours.*;").append("\n");
            buffer.append("import jade.lang.acl.*;").append("\n");
            buffer.append("ACLMessage replyMsg = new ACLMessage(ACLMessage.INFORM);").append("\n");
            buffer.append("replyMsg.setSender(myAgent.getAID());").append("\n");
            buffer.append("replyMsg.addReceiver(msg.getSender());").append("\n");
            buffer.append("replyMsg.setContent(\"Thank You for writing.\");").append("\n");
            buffer.append("replyMsg.setConversationId(msg.getConversationId());").append("\n");
            buffer.append("replyMsg.setOntology(msg.getOntology());").append("\n");
            buffer.append("myAgent.addBehaviour(new SenderBehaviour(myAgent, replyMsg));").append("\n");

            /* The method retractObject must be called by a WorkingMemory object. Since
               this statement is executed by BeanShell we must remember to add the reference
               to the object which is "drools4jade$working$memory". This object is set in the
               BeanShell NameSpace everytime a BlockConsequence is invoked.
               This consideration also applies to the methods assertObject and retractObject.

               Please refer to the source code of the class org.drools4jade.semantics.java.BlockConsequence
               at the method "public void invoke(Tuple tuple,
                       WorkingMemory workingMemory) throws ConsequenceException" */
            buffer.append("drools.retractObject(msg);").append("\n");

            /* If the rule has to be fired then the code below is executed by BeanShell */
            ruleFactory.setConsequence(buffer.toString());

            myRule = ruleFactory.getRule();

        }
        catch (RuleCreationException e) {
            e.printStackTrace();
        }

        return myRule;
    }
}
