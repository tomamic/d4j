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

package test.pojo;

import it.unipr.aotlab.d4j.core.rulesmanager.RulesManager;
import org.drools.io.RuleSetReader;
import org.drools.rule.RuleSet;

/**
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: RulesManagerTest.java,v 1.4 2004/10/01 09:46:38 mic Exp $
 */
public class RulesManagerTest {

    public RulesManagerTest() {

        RulesManager rulesManager = new RulesManager();

        try {
            RuleSetReader ruleReader = new RuleSetReader();
            RuleSet numberRuleSet = ruleReader.read(RulesManagerTest.class.getResource("number.drl"));
            System.out.println("Only one...");
            rulesManager.addRuleSet(numberRuleSet);
            System.out.println(rulesManager.toString());

            ruleReader = new RuleSetReader();
            RuleSet helloRuleSet = ruleReader.read(RulesManagerTest.class.getResource("helloworld.drl"));
            System.out.println("\nAdding another set...");
            rulesManager.addRuleSet(helloRuleSet);
            System.out.println(rulesManager.toString());

            System.out.println("Removing first rule set...");
            rulesManager.removeRuleSet(numberRuleSet);
            System.out.println(rulesManager.toString());

            System.out.println("Cleaning the rule set");
            rulesManager.clearRuleSets();
            System.out.println(rulesManager.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new RulesManagerTest();
    }

}
