package com.gaos.redmine.reporting.batch;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.StepExecution;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static junit.framework.Assert.assertNotNull;

/**
 * @author Roberto Cortez
 */
@RunWith(Arquillian.class)
public class BatchMultipleStepsTest {

    @Inject
    private BugTrackerManager manager;

    @Deployment
    public static WebArchive createDeployment() {
        System.out.println("createDeployment()");
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackages(true,"com.gaos.redmine.reporting.batch")
                .addPackages(true,"com.taskadapter.redmineapi")
                .addPackages(true,"org.json")
                .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"))
                .addAsResource("META-INF/batch-jobs/myJob.xml");
        System.out.println(war.toString(true));
        return war;
    }

    @Before
    public void testCdi() {
        assertNotNull(manager);
    }

    @Test
    public void testBatchMultipleSteps() throws Exception {
        System.out.println("testBatchMultipleSteps()");
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("myJob", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        Thread.sleep(30000);

        List<StepExecution> stepExecutions = jobOperator.getStepExecutions(executionId);
        List<String> executedSteps = new ArrayList<>();
//        for (StepExecution stepExecution : stepExecutions) {
//            executedSteps.add(stepExecution.getStepName());
//
//            if (stepExecution.getStepName().equals("step1")) {
//                Map<Metric.MetricType, Long> metricsMap = BatchTestHelper.getMetricsMap(stepExecution.getMetrics());
//                System.out.println(metricsMap);
//                assertEquals(10L, metricsMap.get(Metric.MetricType.READ_COUNT).longValue());
//                assertEquals(10L / 2, metricsMap.get(Metric.MetricType.WRITE_COUNT).longValue());
//                assertEquals(10L / 3 + (10L % 3 > 0 ? 1 : 0), metricsMap.get(Metric.MetricType.COMMIT_COUNT).longValue());
//            }
//        }
//        assertEquals(2, stepExecutions.size());
//        assertArrayEquals(new String[]{"step1", "step2"}, executedSteps.toArray());
//        assertEquals(BatchStatus.COMPLETED, jobExecution.getBatchStatus());
    }
}
