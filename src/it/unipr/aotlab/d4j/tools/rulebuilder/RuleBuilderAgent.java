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

import jade.core.Agent;
import jade.gui.GuiEvent;
import jade.content.onto.Ontology;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;

import java.awt.*;
import java.io.File;

// TODO: remove
// import it.unipr.aotlab.bsh.agent.GuiBasedBshAgent;
import it.unipr.aotlab.d4j.rule.RuleOntology;

/**
 * An agent that provides a simple gui to create <tt>Drools Rule</tt>s
 * easily.<br>One rules are built they can be saved to disk in the form
 * of a <tt>.drl</tt> file, serialized Java object or <tt>ACLMessage</tt>.
 * <br>The rules can also be sent to other agents like <tt>Drools</tt>
 * powered agent: in this case the rules are immediately integrated into
 * the rule engine.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 *
 * @version $Id: RuleBuilderAgent.java,v 1.3 2004/10/01 15:31:37 mic Exp $
 */
public class RuleBuilderAgent extends GuiBasedAgent {

    private RuleBuilderGui ruleBuilderGui = null;

    private Ontology ontology = RuleOntology.getInstance();
    private Codec codec = new SLCodec();

    /**
     * Sets up and displays the agent gui.
     */
    protected void setup() {

        super.setup(); /* Do not forget this, please... */

        ContentManager contentManager = getContentManager();
        contentManager.registerLanguage(codec);
        contentManager.registerOntology(ontology);

        /* The gui positioning, dimensioning and showing. */
        ruleBuilderGui = new RuleBuilderGui(this);
        ruleBuilderGui.pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = ruleBuilderGui.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        ruleBuilderGui.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        ruleBuilderGui.setVisible(true);
    }

    /**
     * The method that manages the events posted by the graphical user interface.
     *
     * @param ev Event posted by the graphical user interface
     */
    protected void onGuiEvent(GuiEvent ev) {

        switch (ev.getType()) {

            case EXIT_EVENT:
                ruleBuilderGui.dispose();
                doDelete();
                break;
        }
    }
}
