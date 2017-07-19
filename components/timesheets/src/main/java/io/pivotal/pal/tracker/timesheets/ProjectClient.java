package io.pivotal.pal.tracker.timesheets;

import com.google.common.cache.Cache;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.client.RestOperations;

public class ProjectClient {

    private final RestOperations restOperations;
    private final String endpoint;
    private final Cache<Long, ProjectInfo> cache;

    public ProjectClient(RestOperations restOperations,
                         String registrationServerEndpoint,
                         Cache<Long, ProjectInfo> cache) {

        this.restOperations = restOperations;
        this.endpoint = registrationServerEndpoint;
        this.cache = cache;
    }

    @HystrixCommand(fallbackMethod = "getProjectFromCache")
    public ProjectInfo getProject(long projectId) {
        ProjectInfo project = restOperations.getForObject(endpoint + "/projects/" + projectId, ProjectInfo.class);

        cache.put(projectId, project);

        return project;
    }

    public ProjectInfo getProjectFromCache(long projectId) {
        return cache.getIfPresent(projectId);
    }
}
