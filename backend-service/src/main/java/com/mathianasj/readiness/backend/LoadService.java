package com.mathianasj.readiness.backend;

import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class LoadService {
    public Integer load = 0;

    public Integer getLoad() {
        return load;
    }

    public void setLoad(Integer load) {
        this.load = load;
    }

    @Scheduled(every="30s")
    public void decreaseLoad() {
        if(load > 0) {
            Log.info("Decrease load by 1");
            load -= 1;
        }
    }
}
