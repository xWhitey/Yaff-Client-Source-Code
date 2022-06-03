package store.yaff.event.impl;

import store.yaff.event.AbstractEvent;

public class Message {
    public static class Send extends AbstractEvent {
        protected String message;

        public Send(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    public static class Receive extends AbstractEvent {
        protected final String message;

        public Receive(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}
