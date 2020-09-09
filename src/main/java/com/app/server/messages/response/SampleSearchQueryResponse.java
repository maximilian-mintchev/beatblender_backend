package com.app.server.messages.response;

import com.app.server.model.SampleSearchQuery;

public class SampleSearchQueryResponse {

    private SampleSearchQuery[] sampleSearchQueries;

    public SampleSearchQueryResponse() {
    }

    public SampleSearchQueryResponse(SampleSearchQuery[] sampleSearchQueries) {
        this.sampleSearchQueries = sampleSearchQueries;
    }

    public void setSampleSearchQueries(SampleSearchQuery[] sampleSearchQueries) {
        this.sampleSearchQueries = sampleSearchQueries;
    }

    public SampleSearchQuery[] getSampleSearchQueries() {
        return sampleSearchQueries;
    }
}