package com.company.miniq.broker.config;

/*
    Using builder pattern, assuming number of config parameters will grow over time,
    and build pattern is best way to instantiate configuration
 */
public class Configuration {
    private final int acknowledgeWaitTimeOut; // In Seconds

    public Configuration(ConfigurationBuilder builder) {
        this.acknowledgeWaitTimeOut = builder.acknowledgeWaitTimeOut;
    }

    public int getAcknowledgeWaitTimeOut() {
        return acknowledgeWaitTimeOut;
    }

    public static class ConfigurationBuilder {
        private int acknowledgeWaitTimeOut = 5; // In Seconds

        public ConfigurationBuilder setAcknowledgeWaitTimeOut(int time) {
            if(time > 0) {
                this.acknowledgeWaitTimeOut = time;
            }
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }
}
