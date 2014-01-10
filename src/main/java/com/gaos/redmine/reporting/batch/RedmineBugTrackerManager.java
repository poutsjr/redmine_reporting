package com.gaos.redmine.reporting.batch;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.Project;
import org.apache.log4j.Logger;

import javax.enterprise.context.Dependent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Dependent
public class RedmineBugTrackerManager implements BugTrackerManager {

    private static final Logger LOGGER = Logger.getLogger(RedmineBugTrackerManager.class);

    private String redmineHost;
    private String apiAccessKey;
    private RedmineManager mgr;

    public RedmineBugTrackerManager() {
        redmineHost = "http://redmine";
        apiAccessKey = "bd00bb5ea55f58e09d461683dcd4952f6905855b";
        mgr = new RedmineManager(redmineHost, apiAccessKey);
    }

    @Override
    public List<Project> getProjects() throws RedmineException {
        return mgr.getProjects();
    }

    @Override
    public Project getProject(String projectKey) throws RedmineException {
        return mgr.getProjectByKey(projectKey);
    }

    @Override
    public List<Issue> getIssues(String projectKey) throws RedmineException {
        return getIssues(projectKey, 0, 0);
    }

    public List<Issue> getIssues(String projectKey, int limit, int offset) throws RedmineException {
        Map<String, String> map = new HashMap<>();
        map.put("project_id", projectKey);

        if (limit > 0) {
            map.put("limit", Integer.toString(limit));
            if (offset >= 0) {
                map.put("offset", Integer.toString(offset));
            }
        }
        LOGGER.info(map);
        return mgr.getIssues(map);
    }

    @Override
    public Issue getIssue(int issueId) throws RedmineException {
        return mgr.getIssueById(issueId, RedmineManager.INCLUDE.journals);
    }


}