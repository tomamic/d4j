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

import it.unipr.aotlab.d4j.rule.RuleSet;
import it.unipr.aotlab.d4j.rule.RuleOntology;
import it.unipr.aotlab.d4j.io.*;

import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.StringACLCodec;
import jade.util.leap.List;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;

/**
 * Action that enables the GUI to save the rules as Java Serialized Objects,
 * ACLMessage or Drools XML-based files.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a>,
 *         <a href="mailto:tomamic@ce.unipr.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 *
 * @version $Id: SaveFileAction.java,v 1.3 2004/10/01 15:31:37 mic Exp $
 */
public class SaveFileAction extends AbstractAction {
      public static final String DRL_EXT = ".drl";
      public static final String JOBJ_EXT = ".jobj";
      public static final String ACLM_EXT = ".aclm";

    private RuleBuilderGui ownerFrame = null;
    private RuleBuilderAgent ownerAgent = null;

    public SaveFileAction(RuleBuilderAgent agent, RuleBuilderGui owner, String name) {
        super(name, GuiProperties.getIcon("SaveFileActionIcon"));
        this.ownerFrame = owner;
        this.ownerAgent = agent;
    }

    public void actionPerformed(ActionEvent e) {

        JFileChooser saveFileDialog = new JFileChooser();

        saveFileDialog.addChoosableFileFilter(
                new RuleFileFilter(new String[]{JOBJ_EXT}, "Java Serialized Object"));

        saveFileDialog.addChoosableFileFilter(
                new RuleFileFilter(new String[]{ACLM_EXT}, "Jade ACL Message"));

        saveFileDialog.setFileFilter(
                new RuleFileFilter(new String[]{DRL_EXT}, "Drools Rule File"));

        int answer = saveFileDialog.showSaveDialog(ownerFrame);

        if (answer == JFileChooser.APPROVE_OPTION) {
            RuleSet ruleSet = ownerFrame.getRuleSet();
            List rules = ruleSet.getRules();

            RuleFileFilter selectedFileFilter = (RuleFileFilter) saveFileDialog.getFileFilter();
            File outFile = selectedFileFilter.getCompleteName(saveFileDialog.getSelectedFile());
            String fileExt = selectedFileFilter.getPrimaryExtension();

            if (rules.size() > 0) {

                RuleSetWriter writer = null;
                try {
                    if (fileExt.equalsIgnoreCase(ACLM_EXT)) {
                        AID aid = new AID("null", AID.ISLOCALNAME);
                        writer = new AclmRuleSetWriter(new FileWriter(outFile), aid, aid);
                    } else if (fileExt.equalsIgnoreCase(DRL_EXT)) {
                        writer = new DrlRuleSetWriter(new FileWriter(outFile));
                    } else if (fileExt.equalsIgnoreCase(JOBJ_EXT)) {
                        writer = new JobjRuleSetWriter(new FileOutputStream(outFile));
                    } else throw new IOException("Unsupported file type");
                    writer.write(ownerFrame.getRuleSet());
                    writer.close();

                    //ok
                    JOptionPane.showMessageDialog(ownerFrame, "File saved correctly.",
                            "Done...", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(ownerFrame, "An error occurred while saving\nthe " + outFile + " file.\n" +
                            e1.getMessage(), "Error...", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
    }
}
