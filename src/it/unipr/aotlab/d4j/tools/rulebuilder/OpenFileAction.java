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
import it.unipr.aotlab.d4j.io.*;

import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLCodec;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.StringACLCodec;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;

/**
 * Action that enables the GUI to rules files created as Java Serialized Objects,
 * ACLMessage or Drools XML-based files.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a>,
 *         <a href="mailto:tomamic@ce.unipr.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 *
 * @version $Id: OpenFileAction.java,v 1.2 2004/10/01 15:24:07 mic Exp $
 */
public class OpenFileAction extends AbstractAction {
      public static final String DRL_EXT = ".drl";
      public static final String JOBJ_EXT = ".jobj";
      public static final String ACLM_EXT = ".aclm";

    private RuleBuilderGui ownerFrame = null;
    private RuleBuilderAgent ownerAgent = null;

    public OpenFileAction(RuleBuilderAgent agent, RuleBuilderGui owner, String name) {

        super(name, GuiProperties.getIcon("OpenFileActionIcon"));

        this.ownerFrame = owner;
        this.ownerAgent = agent;
    }

    /**
     *
     *
     * @param event
     */
    public void actionPerformed(ActionEvent event) {

        JFileChooser openFileDialog = new JFileChooser();

        /* These are the file filters displayed in the combo box... */
        openFileDialog.addChoosableFileFilter(
                new RuleFileFilter(new String[]{JOBJ_EXT}, "Java Serialized Object"));

        openFileDialog.addChoosableFileFilter(
                new RuleFileFilter(new String[]{ACLM_EXT}, "Jade ACL Message"));

        openFileDialog.setFileFilter(
                new RuleFileFilter(new String[]{DRL_EXT}, "Drools Rule File"));

        int answer = openFileDialog.showOpenDialog(ownerFrame);

        if (answer == JFileChooser.APPROVE_OPTION) {

            RuleFileFilter selectedFileFilter = (RuleFileFilter) openFileDialog.getFileFilter();
            File inFile = selectedFileFilter.getCompleteName(openFileDialog.getSelectedFile());

            /* This is the exension, thus the file type. */
            String fileExt = selectedFileFilter.getPrimaryExtension();

            /* I want to open a file that is an ACLMessage containing the rules
               in the content field formatted using the ontology. */
            try {
                RuleSetReader reader = null;
                if (fileExt.equalsIgnoreCase(ACLM_EXT)) {
                    reader = new AclmRuleSetReader(new FileReader(inFile));
                }
                else if (fileExt.equalsIgnoreCase(DRL_EXT)) {
                    reader = new DrlRuleSetReader(new FileReader(inFile));
                }
                else if (fileExt.equalsIgnoreCase(JOBJ_EXT)) {
                    reader = new JobjRuleSetReader(new FileInputStream(inFile));
                }
                else {
                    throw new IOException("Unsupported file type");
                }

                RuleSet ruleSet = reader.read();
                reader.close();
                ownerFrame.setRuleSet(ruleSet);

                // ok
                JOptionPane.showMessageDialog(ownerFrame, "File opened correctly.",
                        "Done...", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(ownerFrame, "An error occurred while opening\nthe " + inFile + " file.\n" +
                        e.getMessage(), "Error...", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
