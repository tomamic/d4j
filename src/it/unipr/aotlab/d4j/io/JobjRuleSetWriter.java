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

import it.unipr.aotlab.d4j.rule.RuleSet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * <code>AclmRuleSetWriter</code> ...
 *
 * @author <a href="mailto:tomamic@ce.unipr.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: JobjRuleSetWriter.java,v 1.3 2004/10/03 14:52:18 a_beneventi Exp $
 */
public class JobjRuleSetWriter implements RuleSetWriter {

    ObjectOutputStream oos = null;

    public JobjRuleSetWriter(OutputStream out) throws IOException {
        this.oos = new ObjectOutputStream(out);
    }

    public void write(RuleSet ruleSet) throws IOException {
        oos.writeObject(ruleSet);
        oos.flush();
    }

    public void close() throws IOException {
        oos.close();
    }
}
