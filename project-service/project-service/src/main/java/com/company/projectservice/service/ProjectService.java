package com.company.projectservice.service;

import java.util.concurrent.CompletableFuture;

public interface ProjectService {

    CompletableFuture<String> getProjectForUser(String username);

}