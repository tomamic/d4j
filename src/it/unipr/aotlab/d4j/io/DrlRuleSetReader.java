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
import it.unipr.aotlab.d4j.util.rule.RuleConverter;

import java.io.IOException;
import java.io.Reader;

/**
 * <code>DrlRuleSetReader</code> ...
 *
 * @author <a href="mailto:tomamic@ce.unipr.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: DrlRuleSetReader.java,v 1.3 2004/10/03 14:51:40 a_beneventi Exp $
 */
public class DrlRuleSetReader implements RuleSetReader {
    Reader reader = null;

    public DrlRuleSetReader(Reader reader) {
        this.reader = reader;
    }

    public RuleSet read() throws IOException {
        org.drools.io.RuleSetReader dRuleSetReader = null;
        org.drools.rule.RuleSet dRuleSet = null;

        try {
            dRuleSetReader = new org.drools.io.RuleSetReader();
            dRuleSet = dRuleSetReader.read(reader);
            return RuleConverter.convert(dRuleSet);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public void close() throws IOException {
        reader.close();
    }
}
