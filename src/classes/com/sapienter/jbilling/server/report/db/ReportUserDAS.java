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
package com.sapienter.jbilling.server.report.db;

import java.util.Date;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.server.user.db.UserDAS;
import com.sapienter.jbilling.server.util.db.AbstractDAS;

public class ReportUserDAS extends AbstractDAS<ReportUserDTO> {

    public ReportUserDTO create(String title, ReportDTO report, Integer userId) {
        ReportUserDTO rudto = new ReportUserDTO();

        rudto.setBaseUser(new UserDAS().find(userId));
        rudto.setTitle(title);
        rudto.setReport(report);
        rudto.setCreateDatetime(new Date());

        return save(rudto);
    }

    public Collection findByTypeUser(Integer report, Integer userId) {
        /*
         * query="SELECT OBJECT(a) FROM report_user a WHERE a.userId = ?2 AND
         * a.report.id = ?1 " result-type-mapping="Local"
         */
        Criteria criteria = getSession().createCriteria(ReportUserDTO.class);
        criteria.createAlias("report", "rep").add(
                Restrictions.eq("rep.id", report.intValue()));
        criteria.createAlias("baseUser", "baseUser").add(
                Restrictions.eq("baseUser.id", userId.intValue()));

        return criteria.list();

    }
}
