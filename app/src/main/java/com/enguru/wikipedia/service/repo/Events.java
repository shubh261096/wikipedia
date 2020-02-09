package com.enguru.wikipedia.service.repo;

import com.enguru.wikipedia.service.model.error.ErrorModel;
import com.enguru.wikipedia.service.model.search.SearchResponseModel;


public class Events {

    private Events() {
    }

    public static class BaseEvent {
        private ErrorModel errorModel;
        private boolean success;

        BaseEvent(ErrorModel errorModel, boolean success) {
            this.errorModel = errorModel;
            this.success = success;
        }

        public ErrorModel getErrorModel() {
            return errorModel;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    public static class SearchResponseEvent extends BaseEvent {
        private SearchResponseModel searchResponseModel;

        SearchResponseEvent(ErrorModel errorModel, boolean success, SearchResponseModel searchResponseModel) {
            super(errorModel, success);
            this.searchResponseModel = searchResponseModel;
        }

        public SearchResponseModel getSearchResponseModel() {
            return searchResponseModel;
        }
    }
}
