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
package com.sapienter.jbilling.client.order;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.util.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author emilc
 */
public class ListOrEditAction extends Action {

    private Logger LOG = Logger.getLogger(ListOrEditAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession(false);
        Integer userId = (Integer) session.getAttribute(
                Constants.SESSION_USER_ID);
        IUserSessionBean remoteUser = (IUserSessionBean) Context.getBean(Context.Name.USER_SESSION);
        if (remoteUser.isParentCustomer(userId).booleanValue()) {
            // remove the list from the cache, otherwise it will be the same
            session.removeAttribute(Constants.SESSION_LIST_KEY +
                                Constants.LIST_TYPE_SUB_ACCOUNTS);
            return mapping.findForward("list");
        } else {
            return mapping.findForward("order_edit");
        }
    }
}
