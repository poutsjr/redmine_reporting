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
package com.gaos.redmine.reporting.batch.writer;

import com.gaos.redmine.reporting.batch.CsvIssue;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Arun Gupta
 */
@Named
public class IssueWriter extends AbstractItemWriter {

    private static final Logger LOGGER = Logger.getLogger(IssueWriter.class);

    private static final String SEPARATOR = ";";

    private OutputStream out;

    @PostConstruct
    public void init() throws FileNotFoundException {
        File outputFile = new File("issue.csv");
        LOGGER.info(outputFile.getAbsolutePath());
        out = new BufferedOutputStream(new FileOutputStream(outputFile));
    }

    @PreDestroy
    public void end() throws IOException {
        out.close();
    }

    @Override
    public void writeItems(List list) throws IOException {
        for (Object o : list) {
            CsvIssue record = (CsvIssue) o;
            String str = recordToCsv(record);
            out.write(str.getBytes());
        }
    }

    private static String recordToCsv(CsvIssue record) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        StringBuffer buffer = new StringBuffer().append(record.getIssueId()).append(SEPARATOR).append(format.format(record.getIssueDate())).append(SEPARATOR).append(record.getGaosWorkOn()).append(SEPARATOR).append(record.getProjectId()).append(SEPARATOR).append(record.getStatus()).append(SEPARATOR).append(record.getTracker()).append(SEPARATOR).append(record.getMonth()).append("\r\n");
        return buffer.toString();
    }
}
