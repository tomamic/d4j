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

package it.unipr.aotlab.d4j.core.factsmanager;

import org.drools.FactHandle;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the facts that are used into the Brain/Working Memory. This
 * class is necessary in order to maintain the objects when the
 * <code>WorkingMemory</code> needs to be recreated because a <code>RuleSet</code>
 * needs to be added.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a>,
 *         <a href="mailto:tomamic@ce.unipr.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: FactsManager.java,v 1.3 2004/10/03 09:22:17 mic Exp $
 */
public class FactsManager {

    private Map facts = new HashMap();
    private Map idToHandler = new HashMap();

    /**
     * Creates a new <code>FactsManager</code> that uses the provided
     * algorithm to generate unique identifiers for the handles
     */
    public FactsManager() {
        /* Intentionally left blank */
    }

    /**
     * Stores the given <code>object</code> into the <code>Manager</code>
     * with its <code>handle</code>.
     *
     * @param id id of the object
     * @param object object to be stored
     * @return object associated to the handle
     */
    public Object putFact(String id, Object object) {
        return facts.put(id, object);
    }

    /**
     * Returns the <code>object</code> associated to the given handle
     *
     * @param id id of the object
     * @return object associated to the handle
     */
    public Object getFact(String id) {
        return facts.get(id);
    }

    /**
     * Removes the <code>object</code> associated to the given handle
     *
     * @param id id of the object
     * @return object associated to the handle
     */
    public Object removeFact(String id) {
        return facts.remove(id);
    }

    /**
     * Sets a <code>FactHandler</code> that will corresponds to the given
     * identifier.
     *
     * @param id the identifier assigned to the handle
     */
    public FactHandle putHandle(String id, FactHandle fh) {
        return (FactHandle) idToHandler.put(id, fh);
    }

    /**
     * Returns the <code>FactHandler</code> that corresponds to the given
     * identifier. If it is not possible to find the handle <code>null</code>
     * is returned instead.
     *
     * @param id id identifier of the handle to search for
     * @return handle searched for
     */
    public FactHandle getHandle(String id) {
        return (FactHandle) idToHandler.get(id);
    }

    /**
     * Removes the <code>FactHandler</code> that corresponds to the given
     * identifier.
     *
     * @param id id identifier of the handle to search for
     */
    public FactHandle removeHandle(String id) {
        return (FactHandle) idToHandler.remove(id);
    }

    /**
     * Returns an array containing all the objects
     *
     * @return an array containing all the objects
     */
    public Object[] getFacts() {
        return facts.values().toArray();
    }

    /**
     * Returns an array of <code>String</code> containing all the id(s)
     *
     * @return an array of <code>String</code> containing all the id(s)
     */
    public String[] getIds() {
        return (String[]) idToHandler.keySet()
                .toArray(new String[0]);
    }

    /**
     * Clears all the objects in the store
     */
    public void clearFacts() {
        facts.clear();
        idToHandler.clear();
    }
}
