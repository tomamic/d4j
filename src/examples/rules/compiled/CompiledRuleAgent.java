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

package examples.rules.compiled;

import it.unipr.aotlab.d4j.behaviour.CyclicDroolsBehaviour;
import it.unipr.aotlab.d4j.behaviour.MsgAsserterBehaviour;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import org.drools.FactException;
import org.drools.WorkingMemory;
import org.drools.rule.*;
import org.drools.semantics.base.ClassObjectType;
import org.drools.spi.*;

/**
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: CompiledRuleAgent.java,v 1.1 2004/10/03 09:22:17 mic Exp $
 */
public class CompiledRuleAgent extends Agent {

    private CyclicDroolsBehaviour droolsBehaviour = null;
    private MsgAsserterBehaviour recBehaviour = null;

    /**
     *
     */
    protected void setup() {

        try {

            RuleSet ruleSet = new RuleSet("Receiver Rules");

            /* First of all we define an "ObjectType" for each type of object
               used in a "parameter", "declaration", "extraction", "condition" tag */
            ClassObjectType msgType = new ClassObjectType(ACLMessage.class);
            ClassObjectType agentType = new ClassObjectType(Agent.class);
            ClassObjectType integerType = new ClassObjectType(Integer.class);
            ClassObjectType stringType = new ClassObjectType(String.class);
            ClassObjectType droolsType = new ClassObjectType(KnowledgeHelper.class);

            /* First rule: if the content of the message contains the word
                           "drools" in lower, upper or mixed case then the
                           sentence "Hi! I'm not that smart but I can understand
                           that you're talking about Drools." is printed to the
                           console.  */
            Rule rule = new Rule("Are you talking about me?", ruleSet);

            rule.setSalience(10);


            /* These are the declarations used in  "parameter", "declaration",
               "extraction", "condition" tag.
               We declare "msgDeclarations" to have the name "msg" and its type
               is "msgType". This is something like

               msgDeclaration == "ACLMessage msg = new ACLMessage(...)"*/
            /* Only the root fact objects are added with addParameterDeclarations(...)
               The root fact objects are the ones used in the "consequence" section. */
            final Declaration msgDeclaration = rule.addParameterDeclaration("msg", msgType);

            /* Adds a condition to the rule. */
            rule.addCondition(new Condition() {

                public Declaration[] getRequiredTupleMembers() {
                    return new Declaration[] { msgDeclaration };
                }

                /**
                 * If the word "drools4jade" is present in the content of the message...
                 *
                 * @param tuple
                 * @return
                 */
                public boolean isAllowed(Tuple tuple) {
                    jade.lang.acl.ACLMessage msg = (jade.lang.acl.ACLMessage) tuple.get(msgDeclaration);
                    return msg.getContent().toUpperCase().indexOf("DROOLS") != -1;
                }
            });

            /* Adds a consequence to the rule. The consequence is executed when
               the rule is fired. */
            rule.setConsequence(new Consequence() {

                public void invoke(Tuple tuple) {
                    /* Only some text to the console */
                    System.out.println("\nHi! I'm not that smart but I can understand");
                    System.out.println("that you're talking about Drools.\n");
                }
            });

            /* End of the first rule. */
            ruleSet.addRule(rule);

            /* Second rule: if the performative act of the messagge is INFORM then
               a reply is sent to the sender. The content of the message is simply
               "Thank You for writing". */
            rule = new Rule("Thank You", ruleSet);

            rule.setSalience(5);

            rule.addParameterDeclaration("msg", msgType);
            final Declaration agentDeclaration = rule.addParameterDeclaration("myAgent", agentType);

            final Declaration droolsDeclaration = rule.addParameterDeclaration("drools", droolsType);

            rule.addCondition(new Condition() {

                public Declaration[] getRequiredTupleMembers() {
                    return new Declaration[] { msgDeclaration };
                }

                public boolean isAllowed(Tuple tuple) {
                    jade.lang.acl.ACLMessage msg = (jade.lang.acl.ACLMessage) tuple.get(msgDeclaration);
                    return msg.getPerformative() == jade.lang.acl.ACLMessage.INFORM;
                }
            });

            rule.setConsequence(new Consequence() {

                public void invoke(Tuple tuple) throws ConsequenceException {

                    jade.core.Agent myAgent = (jade.core.Agent) tuple.get(agentDeclaration);
                    jade.lang.acl.ACLMessage msg = (jade.lang.acl.ACLMessage) tuple.get(msgDeclaration);
                    KnowledgeHelper drools = (KnowledgeHelper) tuple.get(droolsDeclaration);

                    jade.lang.acl.ACLMessage replyMsg = new ACLMessage(ACLMessage.INFORM);
                    replyMsg.setSender(myAgent.getAID());
                    replyMsg.addReceiver(msg.getSender());
                    replyMsg.setContent("Thank You for writing.");
                    replyMsg.setConversationId(msg.getConversationId());
                    replyMsg.setOntology(msg.getOntology());

                    myAgent.addBehaviour(new jade.core.behaviours.SenderBehaviour(myAgent, replyMsg));

                    try {
                        drools.retractObject(tuple.getFactHandleForObject(msg));
                    } catch (FactException e) {
                        throw new ConsequenceException(e);
                    }

                }
            });

            ruleSet.addRule(rule);

            droolsBehaviour = new CyclicDroolsBehaviour(this);
            droolsBehaviour.addRuleSet(ruleSet);

            /* The behaviour that receives the messages and assert them in drools4jade */
            recBehaviour = new MsgAsserterBehaviour(this, droolsBehaviour);

            addBehaviour(recBehaviour);
            addBehaviour(droolsBehaviour);

        } catch (DuplicateRuleNameException e) {
            e.printStackTrace();
        } catch (InvalidRuleException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
