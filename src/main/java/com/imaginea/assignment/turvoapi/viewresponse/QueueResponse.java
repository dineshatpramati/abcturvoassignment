package com.imaginea.assignment.turvoapi.viewresponse;

import java.util.List;

public class QueueResponse {

    private List<CounterResponse> queues;

    public QueueResponse(List<CounterResponse> queues) {
        this.queues = queues;
    }

    public List<CounterResponse> getQueues() {
        return queues;
    }

    public void setQueues(List<CounterResponse> queues) {
        this.queues = queues;
    }
}
