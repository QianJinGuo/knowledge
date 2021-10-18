package tech.jinguo.kafkastreams.zmart.util.enums;


public enum Topics {

    COMPANIES {
        @Override
        public String toString() {
            return "companies";
        }
    },
    CLIENTS {
        @Override
        public String toString() {
            return "clients";
        }
    },
    FINANCIAL_NEWS {
        @Override
        public String toString() {
            return "financial-news";
        }
    },
    POPS_HOPS_PURCHASES {
        @Override
        public String topicName() {
            return "pops-hops-purchases";
        }
    };


    public String topicName(){
         return this.toString();
    }
}
