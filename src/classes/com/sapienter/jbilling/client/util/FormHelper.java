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

package com.sapienter.jbilling.client.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.DynaActionForm;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.util.OptionDTO;

public class FormHelper {
	private final HttpSession mySession;

	public FormHelper(HttpSession session) {
		mySession = session;
	}
    
    public String getSessionId() {
        return mySession.getId();
    }

	public Float string2float(String arg) {
		return string2float(arg, mySession);
	}

    public BigDecimal string2decimal(String arg) {
        return string2decimal(arg, mySession);
    }

	public String float2string(Float arg) {
		return float2string(arg, mySession);
	}

    public String decimal2string(BigDecimal arg) {
        return decimal2string(arg, mySession);
    }

	public Integer parseInteger(String text){
		if (text == null){
			text = "";
		}
		text = text.trim();
		return text.length() == 0 ? null : Integer.valueOf(text);
	}
	
	public Integer getIntegerFieldValue(DynaActionForm form, String fieldName){
		String value = (String) form.get(fieldName);
		return parseInteger(value);
	}

	public static Float string2float(String arg, HttpSession sess) {
		if (arg == null || arg.trim().length() == 0) {
			return null;
		}
		UserDTOEx user = (UserDTOEx) sess.getAttribute(Constants.SESSION_USER_DTO);
		NumberFormat nf = NumberFormat.getInstance(user.getLocale());

		try {
			return new Float(nf.parse(arg).floatValue());
		} catch (ParseException e) {
			return null;
		}
	}

    public static BigDecimal string2decimal(String arg, HttpSession sess) {
        if (arg == null || arg.trim().equals(""))
            return null;

        UserDTOEx user = (UserDTOEx) sess.getAttribute(Constants.SESSION_USER_DTO);
		NumberFormat nf = NumberFormat.getInstance(user.getLocale());

        if (nf instanceof DecimalFormat) {
            ((DecimalFormat) nf).setParseBigDecimal(true);

            try {
                return (BigDecimal) nf.parse(arg);
            } catch (ParseException e) {
                return null;
            }
        }

        return new BigDecimal(arg);        
    }

	public static String float2string(Float arg, HttpSession sess) {
		if (arg == null) {
			return null;
		}
		UserDTOEx user = (UserDTOEx) sess
				.getAttribute(Constants.SESSION_USER_DTO);
		NumberFormat nf = NumberFormat.getInstance(user.getLocale());
		if (nf instanceof DecimalFormat) {
			((DecimalFormat) nf).applyPattern("0.00");
		}
		return nf.format(arg);
	}

    public static String decimal2string(BigDecimal arg, HttpSession sess) {
        if (arg == null) return null;

        UserDTOEx user = (UserDTOEx) sess.getAttribute(Constants.SESSION_USER_DTO);
        NumberFormat nf = NumberFormat.getInstance(user.getLocale());

		if (nf instanceof DecimalFormat) {
			((DecimalFormat) nf).applyPattern("0.00");
		}
		return nf.format(arg);
    }

    public String getOptionDescription(Integer id, String optionType) throws SessionInternalError {
        List<?> options = (List<?>) mySession.getAttribute("SESSION_" + optionType);
        if (options == null) {
            throw new SessionInternalError("can't find the vector of options" +
                    " in the session:" + optionType);
        }
        
        OptionDTO option;
        for (int f=0; f < options.size(); f++) {
            option = (OptionDTO) options.get(f);
            if (option.getCode().compareTo(id.toString()) == 0) {
                return option.getDescription();
            }
        }
        
        throw new SessionInternalError("id " + id + " not found in options " +
                optionType);
    }
    
    public static void cleanUpSession(HttpSession session) {
        Enumeration<?> entries = session.getAttributeNames();
        for (String entry = (String)entries.nextElement();
                entries.hasMoreElements();
                entry = (String)entries.nextElement()) {
            if (!entry.startsWith("sys_") && !entry.startsWith("org.apache.struts")) {
                //Logger.getLogger(GenericMaintainAction.class).debug("removing " + entry);
                session.removeAttribute(entry);
                // you can't modify the colleciton and keep iterating with the
                // same reference (doahhh :p )
                entries = session.getAttributeNames();
            }                
        }
    }
}
