/*
    jBilling - The Enterprise Open Source Billing System
    Copyright (C) 2003-2009 Enterprise jBilling Software Ltd. and Emiliano Conde

    This file is part of jbilling.

    jbilling is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    jbilling is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with jbilling.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.sapienter.jbilling.server.user;

import java.io.Serializable;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.common.SessionInternalError;
import java.util.ArrayList;

public class Menu implements Serializable {
    private Hashtable options;
    private List rootOptions; 
    private MenuOption selectedOption;
    private MenuOption selectedSubOption;
    private List subOptions;
    private List lmOptions;
    
    public Menu() {
        rootOptions = new ArrayList();
        subOptions = new ArrayList();
        lmOptions = new ArrayList();
        options = new Hashtable();
    }
    
    public List getOptions() {
        return rootOptions;
    }
    
    public List getSubOptions() {
        return subOptions;
    }
    
    public List getLMOptions() {
        return lmOptions;
    }
    
    protected void addOption(MenuOption option) throws NamingException {
        options.put(option.getId(), option);
    }
    

    // this has to be called after all the options are in the hashtable
    protected void init() throws SessionInternalError { 
        //Logger log = Logger.getLogger(Menu.class);
        for (Iterator it = options.values().iterator(); it.hasNext();) {
            MenuOption option = (MenuOption) it.next();

            if (option.getParentId() == null) {
                rootOptions.add(option);
                // I need something to be selected at any time ...
                if (selectedOption == null) {
                    selectedOption = option;
                    selectedOption.setSelected(new Boolean("true"));
                }
            } else {
                MenuOption parent = (MenuOption) options.get(
                        option.getParentId());
                        
                if (parent == null) {
                    throw new SessionInternalError("can't find parent " + option.getParentId() + " for " +
                        "option" + option);
                }
                /*
                Logger.getLogger(Menu.class).debug("adding suboption " + option +
                                        " parent = " + parent);*/

                parent.getOptions().add(option.getId());
            }
        }   
        
        // set the suboptions according to the initially selected option
        setList(selectedOption, subOptions);        

        // here the options are sorted, using the MenuOption compare method.
        // therefore, any option can be passed as the second parameter
        Collections.sort(rootOptions, selectedOption);
    }
    
    /**
     * 
     * @param id
     * @return true if a main option was selected, otherwise false
     * @throws SessionInternalError
     */
    public boolean selectOption(Integer id) 
            throws SessionInternalError {
        Logger log = Logger.getLogger(Menu.class);
        boolean retValue = false;
        MenuOption option = (MenuOption) options.get(id);
        if (option == null) {
            throw new SessionInternalError("Option not found in menu:" + id);
        }
        
        if (selectedOption.equals(option)) { //re-click
            return retValue;
        }
        
        int level = option.getLevelField();
        switch (level) {
        case 1: // redefine the suboptions
            setList(option, subOptions);
            // update who is selected and who's not
            selectedOption.setSelected(new Boolean(false));
            selectedOption = option;
            selectedOption.setSelected(new Boolean(true));
            if (selectedSubOption != null) {
                selectedSubOption.setSelected(new Boolean(false));
                selectedSubOption = null;
            }
            lmOptions.clear();
            retValue = true;
            log.debug("option selected now suboptions size = " + subOptions.size());
        break;
        case 2: // redefine the lf options
            setList(option, lmOptions);
            // update the selected flag of the suboptions
            if (selectedSubOption != null) {
                selectedSubOption.setSelected(new Boolean(false));
            }
            selectedSubOption = option;
            selectedSubOption.setSelected(new Boolean(true));
            
        break;
        case 3: // nothing to do
        break;
        default:
            throw new SessionInternalError("level not supported:" + level);
        }
        
        
        return retValue;
    }
    
    private void setList(MenuOption option, List toSet) {
        toSet.clear();
        for (Iterator it = option.getOptions().iterator(); it.hasNext(); ) {
            Integer key = (Integer) it.next();
            
            MenuOption subOption = (MenuOption) options.get(key);
            toSet.add(subOption);
        }
        // get these options sorted for display
        Collections.sort(toSet, selectedOption);
    }
}
