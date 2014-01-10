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
package com.gaos.redmine.reporting.batch.reader;

import com.gaos.redmine.reporting.batch.BugTrackerManager;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.Project;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.batch.api.chunk.AbstractItemReader;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/**
 * @author Arun Gupta
 */
@Named
@Dependent
public class IssueReader extends AbstractItemReader {

    private static final Logger LOGGER = Logger.getLogger(IssueReader.class);

    @Inject
    private BugTrackerManager manager;

    private Iterator<Issue> iterator;

    /**
     * Set to 0 if no size limit
     */
    private static final int limitProjectSize = 5;

    /**
     * Set to 0 if no size limit
     */
    private static final int limitIssueSize = 5;

    @PostConstruct
    public void init() {
        try {
            LOGGER.info("Initialisation de IssueReader");
            List<Project> projects = Arrays.asList(manager.getProject("fc_gaos"));
//            List<Project> projects = manager.getProjects();
            Set<Issue> issues = new HashSet<>();
            LOGGER.info(projects.size() + " projets trouvés");
            LOGGER.info(limitProjectSize > 0 ? "Limité à " + limitProjectSize + " projets" : "Pas de limite projet");
            for (int i = 0; i < projects.size() && (i == 0 || i < limitProjectSize); i++) {
                Project project = projects.get(i);
                List<Issue> list = manager.getIssues(project.getIdentifier(), limitIssueSize, 0);
                LOGGER.info(project.getName());
                LOGGER.info(" [" + list.size() + "]");
                for (int j = 0; j < list.size()&& (j == 0 || j < limitIssueSize); j++) {
                    Issue issue = list.get(j);
                    issues.add(manager.getIssue(issue.getId()));
                }
            }
            iterator = issues.iterator();

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @PreDestroy
    public void end() {
        LOGGER.info("Fin de IssueReader");
    }

    @Override
    public Issue readItem() {
        if (iterator.hasNext()) {
            Issue issue = iterator.next();
            LOGGER.info("Issue suivante id:" + issue.getId());
            return issue;
        }
        return null;
    }
}
