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

package it.unipr.aotlab.d4j.tools.rulebuilder;

import it.unipr.aotlab.d4j.rule.*;
import it.unipr.aotlab.d4j.rule.RuleOntology;
import it.unipr.aotlab.d4j.io.AclmRuleSetWriter;

import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.behaviours.SenderBehaviour;
import jade.gui.AIDGui;
import jade.lang.acl.ACLMessage;
import jade.core.Agent;
import jade.core.AID;
import jade.util.leap.List;
import jade.util.leap.ArrayList;
import jade.lang.acl.ACLMessage;
import jade.content.onto.OntologyException;
import jade.content.onto.Ontology;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.StringCodec;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.ContentManager;
import jade.content.onto.basic.Action;
import jade.content.abs.AbsContentElement;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Action that enables the GUI to send the selected rule to another agent.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a>,
 *         <a href="mailto:tomamic@ce.unipr.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 *
 * @version $Id: SendRuleAction.java,v 1.4 2004/10/06 23:12:05 mic Exp $
 */
public class SendRuleAction extends AbstractAction {

    private RuleBuilderGui ownerFrame = null;
    private RuleBuilderAgent ownerAgent = null;

    public SendRuleAction(RuleBuilderAgent agent, RuleBuilderGui owner, String name) {
        super(name, GuiProperties.getIcon("SendRuleActionIcon"));
        this.ownerFrame = owner;
        this.ownerAgent = agent;
    }

    public void actionPerformed(ActionEvent e) {
        Rule selectedRule = ownerFrame.getSelectedRule();

        if (selectedRule != null) {
            RuleSet ruleSet = new RuleSet();
            ruleSet.setName(ownerFrame.getRuleSet().getName() + "-" + selectedRule.getName());
            ruleSet.setRules(new jade.util.leap.ArrayList());
            ruleSet.getRules().add(selectedRule);

            try {
                AIDGui aidGui = new AIDGui(ownerFrame);
                AID receiver = null;

                receiver = aidGui.ShowAIDGui(receiver, true, true);

                if (receiver != null) {
                    ACLMessage msg = AclmRuleSetWriter.createACLMessage(ruleSet, ownerAgent.getAID(), receiver);
                    msg.addReceiver(new AID("da0", AID.ISLOCALNAME));

                    ownerAgent.addBehaviour(new SenderBehaviour(ownerAgent, msg));

                    JOptionPane.showMessageDialog(ownerFrame, "Message sent.", "Done...",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (OntologyException e1) {
                JOptionPane.showMessageDialog(ownerFrame, "An error occurred while creating\nthe message.\n" +
                        e1.getMessage(), "Error...", JOptionPane.ERROR_MESSAGE);
            } catch (CodecException e1) {
                JOptionPane.showMessageDialog(ownerFrame, "An error occurred while creating\nthe message.\n" +
                        e1.getMessage(), "Error...", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
