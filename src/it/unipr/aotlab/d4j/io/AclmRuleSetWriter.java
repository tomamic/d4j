/*****************************************************************
 JADE - Java Agent DEvelopment Framework is a framework to develop
 multi-agent systems in compliance with the FIPA specifications.
 Copyright (C) 2000 CSELT S.p.A.

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

package it.unipr.aotlab.d4j.io;

import it.unipr.aotlab.d4j.rule.AddRuleSet;
import it.unipr.aotlab.d4j.rule.RuleOntology;
import it.unipr.aotlab.d4j.rule.RuleSet;
import it.unipr.aotlab.d4j.rule.RuleVocabulary;
import jade.content.abs.AbsContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.StringCodec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.StringACLCodec;

import java.io.IOException;
import java.io.Writer;

/**
 * <code>AclmRuleSetWriter</code> ...
 *
 * @author <a href="mailto:tomamic@ce.unipr.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: AclmRuleSetWriter.java,v 1.4 2004/10/03 14:51:25 a_beneventi Exp $
 */
public class AclmRuleSetWriter implements RuleSetWriter {
    static StringCodec codec = new SLCodec();
    static Ontology ontology = RuleOntology.getInstance();

    Writer writer = null;
    AID sender = null;
    AID receiver = null;

    public AclmRuleSetWriter(Writer writer, AID sender, AID receiver) {
        this.writer = writer;
        this.sender = sender;
        this.receiver = receiver;
    }

    public void write(RuleSet ruleSet) throws IOException {
        ACLMessage msg = null;
        try {
            msg = createACLMessage(ruleSet, sender, receiver);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
        writeMsg(msg);
    }

    public void close() throws IOException {
        writer.close();
    }

    public void writeMsg(ACLMessage msg) throws IOException {
        try {
            StringACLCodec codec = new StringACLCodec(null, writer);
            codec.write(msg);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public static ACLMessage createACLMessage(RuleSet ruleSet, AID sender, AID receiver) throws OntologyException, CodecException {
        String encoded = encodeRuleSet(ruleSet, receiver);

        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setLanguage(codec.getName());
        msg.setOntology(RuleVocabulary.NAME);
        msg.setContent(encoded);
        if (sender != null) msg.setSender(sender);
        if (receiver != null) msg.addReceiver(receiver);
        return msg;
    }

    public static String encodeRuleSet(RuleSet ruleSet, AID receiver) throws OntologyException, CodecException {
        AddRuleSet add = new AddRuleSet();
        add.setRuleSet(ruleSet);

        Action addAction = new Action(receiver, add);

        return codec.encode(ontology, (AbsContentElement) ontology.fromObject(addAction));
    }
}
