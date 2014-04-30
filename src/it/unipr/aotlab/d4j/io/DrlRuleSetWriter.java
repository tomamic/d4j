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

import it.unipr.aotlab.d4j.rule.*;

import jade.util.leap.List;

import java.io.IOException;
import java.io.Writer;

/**
 * <code>RuleSetWriter</code> ...
 *
 * @author <a href="mailto:tomamic@ce.unipr.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: DrlRuleSetWriter.java,v 1.4 2004/10/03 14:06:41 mic Exp $
 */
public class DrlRuleSetWriter implements RuleSetWriter {
      Writer writer = null;

      public DrlRuleSetWriter(Writer writer) {
            this.writer = writer;
      }

    public void write(RuleSet ruleSet) throws IOException {
          String name = ruleSet.getName();
          List rules = ruleSet.getRules();

        /* Fixed header... */
        writer.write("<?xml version=\"1.0\"?>\n");
        writer.write("<rule-set name=\"" + name + "\" xmlns=\"http://drools.org/rules\" xmlns:java=\"http://drools.org/semantics/java\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema-instance\" xs:schemaLocation=\"http://drools.org/rules rules.xsd http://drools.org/semantics/java java.xsd\">");

        if ((rules != null) && (rules.size() >= 1)) {

            for (int r = 0; r < rules.size(); r++) {

                Rule rule = (Rule)rules.get(r);
                writer.write("\t<rule name=\"" + rule.getName().trim() + "\" salience=\"" + rule.getSalience() + "\">\n");

                /* Parameters Section... */
                List parameters = rule.getParameters();
                if ((parameters != null) && (parameters.size() >= 1)) {

                    for (int p = 0; p < parameters.size(); p++) {
                        RuleParameter parameter = (RuleParameter)parameters.get(p);
                        writer.write("\t\t<parameter identifier=\""+parameter.getIdentifier().trim()+"\">\n");
                        writer.write("\t\t\t<java:class>" + parameter.getType().trim() + "</java:class>\n");
                        writer.write("\t\t</parameter>\n");
                    }
                }

                /* Fixed duration */
                if (rule.getDuration() > 0) {
                    writer.write("\t\t<duration seconds=\""+rule.getDuration()+"\">\n");
                }

                /* Conditions Section... */
                List conditions = rule.getConditions();
                if ((conditions != null) && (conditions.size() >= 1)) {

                    for (int p = 0; p < conditions.size(); p++) {
                        String condition = (String) conditions.get(p);
                        writer.write("\t\t\t<java:condition>" + condition + "</java:condition>\n");
                    }
                }

                /* Consequence block */
                String consequence = rule.getConsequence();
                if (consequence != null) {
                    writer.write("\t\t\t<java:consequence>" + consequence + "</java:consequence>\n");
                }

                writer.write("\t</rule>\n");
            }
        }

        writer.write("</rule-set>\n");

        writer.flush();
      }

    public void close() throws IOException {
          writer.close();
    }
}
