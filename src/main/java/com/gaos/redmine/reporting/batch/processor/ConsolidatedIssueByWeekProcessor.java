package com.gaos.redmine.reporting.batch.processor;

import com.gaos.redmine.reporting.batch.CsvIssue;

import javax.annotation.PostConstruct;
import javax.batch.api.chunk.ItemProcessor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: tpoutrain
 * Date: 10/01/14
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
public class ConsolidatedIssueByWeekProcessor implements ItemProcessor {

    Map<String, Integer> map = new HashMap<>();

    @PostConstruct
    public void init() {
        //TODO use external properties
        Calendar beginDate = new GregorianCalendar(2013, 0, 1);
        Calendar currentDate = Calendar.getInstance();
        while (currentDate.after(beginDate)) {
            DateFormat f = new SimpleDateFormat(CsvIssue.DATE_PATTERN);
            String month = f.format(beginDate);
            map.put(month, 0);
            beginDate.add(Calendar.MONTH, 1);
        }
    }

    @Override
    public Object processItem(Object o) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
