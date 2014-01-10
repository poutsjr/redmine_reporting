/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package com.gaos.redmine.reporting.batch;

import com.taskadapter.redmineapi.bean.Journal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Arun Gupta
 */
public class CsvIssue {

    public static final String DATE_PATTERN = "MM-yyyy";

    /**
     * Identifiers of GAOS team members;
     * //TODO Use an external (or dynamic) configuration
     */
    private static final List GAOS_USER_IDS = Arrays.asList(12, 14, 15, 23, 134);
    private int issueId;
    private Date issueDate;
    private Boolean isGaosWorkOn;
    private String status;
    private String month;
    private int projectId;
    private String tracker;

    public String getMonth() {
        if(month==null){
            initMonth();
        }
        return month;
    }

    private void initMonth() {
        if(issueDate!=null) {
            DateFormat f = new SimpleDateFormat(DATE_PATTERN);
            month = f.format(issueDate);
        }
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Boolean getGaosWorkOn() {
        return isGaosWorkOn;
    }

    public void setGaosWorkOn(List<Journal> journals) {
        isGaosWorkOn=false;
        System.out.println(journals.size());
        for (Journal journal : journals) {
            System.out.println(GAOS_USER_IDS + " contains " + journal.getUser().getId() + " : "+GAOS_USER_IDS.contains(journal.getUser().getId()));
            if (GAOS_USER_IDS.contains(journal.getUser().getId())) {
                isGaosWorkOn = true;
                break;
            }
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getTracker() {
        return tracker;
    }

    public void setTracker(String tracker) {
        this.tracker = tracker;
    }
}
