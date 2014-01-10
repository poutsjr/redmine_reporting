package com.gaos.redmine.reporting.batch;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.Project;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tpoutrain
 * Date: 08/01/14
 * Time: 17:50
 * To change this template use File | Settings | File Templates.
 */
public interface BugTrackerManager {
    List<Project> getProjects() throws RedmineException;

    Project getProject(String projectKey) throws RedmineException;

    List<Issue> getIssues(String projectKey) throws RedmineException;

    public List<Issue> getIssues(String projectKey, int limit, int offset) throws RedmineException;

    Issue getIssue(int issueId) throws RedmineException;

}