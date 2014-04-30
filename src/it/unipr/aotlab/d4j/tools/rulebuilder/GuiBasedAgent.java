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

import jade.gui.GuiEvent;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.Behaviour;

import java.util.Vector;


/**
 * This is the base class to create a gui-based <tt>BeanShell</tt> agent.
 * Please note that no gui is created here.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a>,
 *         <a href="mailto:tomamic@ce.unipr.it@tin.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 *
 * @version $Id: GuiBasedAgent.java,v 1.1 2004/10/01 10:03:42 mic Exp $
 */
public abstract class GuiBasedAgent extends Agent {

    public static final int EXIT_EVENT = 0;

    private Vector guiEventQueue;
    private Boolean guiEventQueueLock;


    /**
     * Main constructor of the Agent. The Interpreter is set-up.
     */
    public GuiBasedAgent() {

        guiEventQueue = new Vector();
        guiEventQueueLock = new Boolean(true);

        Behaviour guiHandlerBehaviour = new GuiHandlerBehaviour();
        addBehaviour(guiHandlerBehaviour);
    }

    protected abstract void onGuiEvent(GuiEvent ev);


    private class GuiHandlerBehaviour extends SimpleBehaviour {

        protected GuiHandlerBehaviour() {
            super(GuiBasedAgent.this);
        }

        public void action() {
            if (!guiEventQueue.isEmpty()) {
                GuiEvent ev = null;
                synchronized (guiEventQueueLock) {
                    try {
                        ev = (GuiEvent) guiEventQueue.elementAt(0);
                        guiEventQueue.removeElementAt(0);
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        ex.printStackTrace();
                    }
                }
                onGuiEvent(ev);
            } else
                block();
        }

        public boolean done() {
            return (false);
        }
    }

    public void postGuiEvent(GuiEvent e) {
        synchronized (guiEventQueueLock) {
            guiEventQueue.addElement(e);
            doWake();
        }
    }

}
