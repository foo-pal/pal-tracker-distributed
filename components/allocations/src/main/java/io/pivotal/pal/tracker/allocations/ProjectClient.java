package io.pivotal.pal.tracker.allocations;

import com.google.common.cache.Cache;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.client.RestOperations;

public class ProjectClient {

    private final RestOperations restOperations;
    private final String registrationServerEndpoint;
    private final Cache<Long, ProjectInfo> cache;


    public ProjectClient(RestOperations restOperations,
                         String registrationServerEndpoint,
                         Cache<Long, ProjectInfo> cache) {

        this.restOperations= restOperations;
        this.registrationServerEndpoint = registrationServerEndpoint;
        this.cache = cache;
    }

    @HystrixCommand(fallbackMethod = "getProjectFromCache")
    public ProjectInfo getProject(long projectId) {
        ProjectInfo project = restOperations.getForObject(registrationServerEndpoint + "/projects/" + projectId, ProjectInfo.class);

        cache.put(projectId, project);

        return project;
    }

    public ProjectInfo getProjectFromCache(long projectId) {
        return cache.getIfPresent(projectId);
    }
}
