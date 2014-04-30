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
import jade.content.lang.Codec.CodecException;
import jade.content.lang.StringCodec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.StringACLCodec;

import java.io.IOException;
import java.io.Reader;

/**
 * <code>AclmRuleSetReader</code> ...
 *
 * @author <a href="mailto:tomamic@ce.unipr.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: AclmRuleSetReader.java,v 1.4 2004/10/03 14:51:10 a_beneventi Exp $
 */
public class AclmRuleSetReader implements RuleSetReader {
    static StringCodec codec = new SLCodec();
    static Ontology ontology = RuleOntology.getInstance();

    Reader reader = null;

    public AclmRuleSetReader(Reader reader) {
        this.reader = reader;
    }

    public RuleSet read() throws IOException {
        ACLMessage msg = readMsg();
        try {
            String content = msg.getContent();
            return decodeRuleSet(content);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public void close() throws IOException {
        reader.close();
    }

    public ACLMessage readMsg() throws IOException {
        try {
            StringACLCodec aclCodec = new StringACLCodec(reader, null);
            return aclCodec.decode();
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public static RuleSet decodeRuleSet(String encodedSet) throws OntologyException, CodecException {
        Action addAction = (Action) ontology.toObject(codec.decode(ontology, encodedSet));
        AddRuleSet add = (AddRuleSet) addAction.getAction();
        return add.getRuleSet();
    }
}
